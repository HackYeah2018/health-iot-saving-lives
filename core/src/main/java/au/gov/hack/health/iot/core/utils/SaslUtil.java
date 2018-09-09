package au.gov.hack.health.iot.core.utils;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.Credentials;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.gov.hack.health.iot.core.config.KerberosProperties;
import au.gov.hack.health.iot.core.exceptions.InitializationException;


@Component("saslUtil")
public class SaslUtil {

	private static final Logger logger = Logger.getLogger(SaslUtil.class);

	private boolean initialized = false;

	private static Credentials credentials;

	@Value("${zookeeper.clientPort}")
	protected String zookeeperClientPortText;

	@Value("${zookeeper.quorum}")
	protected String zookeeperQuorum;

	@Value("${hbase.znodeParent}")
	protected String znode;

	@Autowired
	private KerberosProperties kerberos;

	private UserGroupInformation cachedUgi;

	public static void setCredentials(Credentials credentials) {
		SaslUtil.credentials = credentials;
	}

	@PostConstruct
	public void init() {
		if (initialized) {
			return;
		}

		logger.info("Initializing the SaslUtil");

		if (kerberos != null && kerberos.isEnabled()) {
			// System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");

			logger.info("Kerberos is enabled");

			String hbasePrincipal = kerberos.getHbasePrincipal();
			if (StringUtils.isBlank(hbasePrincipal)) {
				throw new InitializationException("HBase principal must not be blank");
			} else {
				logger.info("HBase principal is [" + hbasePrincipal + "]");
			}

			Configuration conf = new Configuration();

			conf.set("hadoop.security.authentication", "kerberos");
			conf.set("com.sun.security.auth.module.Krb5LoginModule", "required");

			// FIXME Use another method to pass these as application config
			conf.set("hbase.zookeeper.property.clientPort", zookeeperClientPortText);
			conf.set("hbase.zookeeper.quorum", zookeeperQuorum);
			conf.set("zookeeper.znode.parent", znode);
			logger.info("Zookeeper quorum: " + conf.get("hbase.zookeeper.quorum"));

			conf.set("hadoop.security.authentication", "kerberos");
			conf.set("hbase.security.authentication", "kerberos");
			conf.set("com.sun.security.auth.module.Krb5LoginModule", "required");

			conf.set("hbase.master.kerberos.principal", hbasePrincipal);
			conf.set("hbase.regionserver.kerberos.principal", hbasePrincipal);

			logger.info("Initializing credentials");
			final String user = kerberos.getPrincipal();
			final String keyPath = kerberos.getKeytab();
			try {
				logger.info("Key path: " + keyPath);

				UserGroupInformation.setConfiguration(conf);
				UserGroupInformation.loginUserFromKeytab(user, keyPath);
				cachedUgi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(user, keyPath);
			} catch (IOException e) {
				throw new InitializationException("Unable to initialize kerberos with keytab [" + keyPath
						+ "] and user [" + user + "]: " + e.getMessage(), e);
			}
			// }
			logger.info("Kerberos initialization complete");
		}

		initialized = true;
	}

	public void setZookeeperClientPortText(String zookeeperClientPortText) {
		this.zookeeperClientPortText = zookeeperClientPortText;
	}

	public void setZookeeperQuorum(String zookeeperQuorum) {
		this.zookeeperQuorum = zookeeperQuorum;
	}

	public void setZnode(String znode) {
		this.znode = znode;
	}

	public void setKerberos(KerberosProperties kerberos) {
		this.kerberos = kerberos;
	}

	public UserGroupInformation getCachedUgi() {
		return cachedUgi;
	}

	public String getZookeeperClientPortText() {
		return zookeeperClientPortText;
	}

	public String getZookeeperQuorum() {
		return zookeeperQuorum;
	}

}
