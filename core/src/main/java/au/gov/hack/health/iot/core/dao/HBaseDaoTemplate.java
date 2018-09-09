package au.gov.hack.health.iot.core.dao;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import au.gov.hack.health.iot.core.dao.mapper.HBaseResultMapper;
import au.gov.hack.health.iot.core.exceptions.PersistenceException;
import au.gov.hack.health.iot.core.exceptions.ProcessingException;
import au.gov.hack.health.iot.core.utils.SaslUtil;

@Component
public abstract class HBaseDaoTemplate {

	private static final Logger logger = Logger.getLogger(HBaseDaoTemplate.class);
	
	private boolean initialized = false;
	
	@Value("${hbase.namespace}")
	protected String namespace;
	
	@Value("${zookeeper.clientPort}")
	protected String zookeeperClientPortText;
	
	@Value("${zookeeper.quorum}")
	protected String zookeeperQuorum;
	
	@Value("${hbase.znodeParent}")
	protected String znode;
	
	@Value("${unitTestMode}")
	protected String unitTestMode;
	
	private Table table;

	@Autowired
	protected SaslUtil saslUtil;
	
	/**
	 * Returns a connection to the table. So many things to improve here before
	 * prod.
	 * 
	 * @return
	 * @throws IOException
	 */
	@PostConstruct
	public void init() {
		if (initialized) {
			return;
		}
		
		if (StringUtils.isBlank(zookeeperQuorum)) {
			throw new IllegalArgumentException("Missing zookeeper quorum");
		}
		
		if (StringUtils.isBlank(zookeeperClientPortText)) {
			throw new IllegalArgumentException("Blank zookeeper port");
		}
		
		int zookeeperClientPort = Integer.valueOf(zookeeperClientPortText);
		if (zookeeperClientPort <= 0) {
			throw new IllegalArgumentException("Invalid zookeeper port [" + zookeeperClientPort + "]");
		}
		
		if (StringUtils.isBlank(znode)) {
			throw new IllegalArgumentException("znode specified can not be blank");
		}
		
		try {
			Configuration conf = new Configuration();
			
			// FIXME Use another method to pass these as application config
			conf.set("hbase.zookeeper.property.clientPort", Integer.toString(zookeeperClientPort));
			conf.set("hbase.zookeeper.quorum", zookeeperQuorum);
			conf.set("zookeeper.znode.parent", znode);
			logger.info("Zookeeper quorum: " + conf.get("hbase.zookeeper.quorum"));

			conf.set("hadoop.security.authentication", "kerberos");
			conf.set("hbase.security.authentication", "kerberos");
			conf.set("com.sun.security.auth.module.Krb5LoginModule", "required");
			
			conf.set("hbase.master.kerberos.principal", "hbase/_HOST@LAB1.COM");
			conf.set("hbase.regionserver.kerberos.principal", "hbase/_HOST@LAB1.COM");

			
			TableName tableName = TableName.valueOf(namespace, getTableName());

			// This instantiates an HTable object that connects you to the table
			//User user = User
			User user = User.create(saslUtil.getCachedUgi());
			
			if (StringUtils.isNotBlank(unitTestMode) && unitTestMode.equalsIgnoreCase("true")) {
				logger.warn("No connection made as we are running in unit test mode");
			} else {
				Connection connection = ConnectionFactory.createConnection(conf, user);
				table = connection.getTable(tableName);
			}
			
			//table = new HTable(conf, tableName);
		} catch (IOException e) {
			throw new ProcessingException("Unable to connect to table: " + e.getMessage(), e);
		}
		
		initialized = true;
	}

	public abstract String getTableName();
	
	
	
	public <T> T get(Get get, HBaseResultMapper<T> mapper) throws PersistenceException {
		logger.info("Performing get operation on [" + table.getName() + "] for record key [" + Bytes.toString(get.getRow()) + "]");
		
		try {
			UserGroupInformation cached = saslUtil.getCachedUgi();
			Result r = cached.doAs(new PrivilegedExceptionAction<Result>() {

				@Override
				public Result run() throws Exception {
					return table.get(get);
				}
			});

			
			if (r == null) {
				logger.info("Result is null");
				return null;
			} else {
				logger.info("Mapping result");
				return mapper.map(r);
			}
		} catch (Exception e) {
			throw new PersistenceException("Unable to retrieve record [" + get.getId() + "]: " + e.getMessage(), e);
		}
	}

	public <T> List<T> scan(Scan scan, HBaseResultMapper<T> mapper, boolean stringKey) throws PersistenceException {
		logger.info("Performing scan operation on [" + table.getName() + "]");
		if (scan.getStartRow() != null) {
			String startRow = stringKey ? new String(scan.getStartRow()) : DatatypeConverter.printHexBinary(scan.getStartRow());
			logger.info("Start row is [" + startRow + "]");
		}
		if (scan.getStopRow() != null) {
			String stopRow = stringKey ? new String(scan.getStopRow()) : DatatypeConverter.printHexBinary(scan.getStopRow());
			logger.info("Stop row is [" + stopRow + "]");
		}
		
		
		try {
			UserGroupInformation cached = saslUtil.getCachedUgi();
			ResultScanner rs = cached.doAs(new PrivilegedExceptionAction<ResultScanner>() {

				@Override
				public ResultScanner run() throws Exception {
					return table.getScanner(scan);
				}
			});

			
			if (rs == null) {
				logger.info("Result is null");
				return new ArrayList<>();
			} else {
				logger.info("Mapping result");
				List<T> results = new ArrayList<>();
				for (Result r : rs) {
					T mapped = mapper.map(r);
					if (mapped == null) {
						continue;
					}
					results.add(mapped);
				}
				return results;
			}
		} catch (Exception e) {
			throw new PersistenceException("Unable to retrieve record [" + scan + "]: " + e.getMessage(), e);
		}
	}	
	
	public void doPut(Put put) {
		try {
		UserGroupInformation cached = saslUtil.getCachedUgi();
		cached.doAs(new PrivilegedExceptionAction<Void>() {

			@Override
			public Void run() throws Exception {
				table.put(put);
				return null;
			}
		});
		
		} catch (Exception e) {
			throw new PersistenceException("Unable to perform put: " + e.getMessage(), e);
		}
	}
	
	protected boolean checkAndPut(byte[] id, Put p, String columnFamily) throws InterruptedException, IOException {
		UserGroupInformation cached = saslUtil.getCachedUgi();
		return cached.doAs(new PrivilegedExceptionAction<Boolean>() {

			@Override
			public Boolean run() throws Exception {
				return table.checkAndPut(id, Bytes.toBytes(columnFamily), Bytes.toBytes("id"), null, p);
			}
		});
		
	}
	
	public void doDelete(Delete op) {
		UserGroupInformation cached = saslUtil.getCachedUgi();
		try {
		cached.doAs(new PrivilegedExceptionAction<Void>() {

			@Override
			public Void run() throws Exception {
				table.delete(op);
				return null;
			}
		});
		} catch (Exception e) {
			throw new PersistenceException("Unable to delete from table [" + getTableName() + "] row [" + new String(op.getRow()) + "]: " + e.getMessage());
		}
	}
	
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setZookeeperQuorum(String zookeeperQuorum) {
		this.zookeeperQuorum = zookeeperQuorum;
	}

	public void setZnode(String znode) {
		this.znode = znode;
	}

	public void setSaslUtil(SaslUtil saslUtil) {
		this.saslUtil = saslUtil;
	}

	public void setZookeeperClientPortText(String zookeeperClientPortText) {
		this.zookeeperClientPortText = zookeeperClientPortText;
	}


	

	
}
