package au.gov.hack.health.iot.core.config;


public class EnvironmentProperties {

	private KafkaProperties kafka;

	private HBaseProperties hbase;

	private ZookeeperProperties zookeeper;

	private HdfsProperties hdfs;

	private KerberosProperties kerberos;
	
	private boolean unitTestMode;
	
	public void validate() {
		if (hdfs == null) {
			throw new IllegalArgumentException("Runtime properties are missing hdfs property");
		} else {
			hdfs.validate();
		}
		
		if (hbase == null) {
			throw new IllegalArgumentException("Runtime properties are missing hbase property");
		} else {
			hbase.validate();
		}
		
		if (kafka == null) {
			throw new IllegalArgumentException("Runtime properties are missing kafka property");
		} else {
			kafka.validate();
		}

		if (zookeeper == null) {
			throw new IllegalArgumentException("Runtime properties are missing zookeeper property");
		} else {
			zookeeper.validate();
		}
		
		if (kerberos == null) {
			throw new IllegalArgumentException("Runtime properties are missing kerberos property");
		} else {
			kerberos.validate();
		}
		

	}
	
	public KafkaProperties getKafka() {
		return kafka;
	}

	public void setKafka(KafkaProperties kafka) {
		this.kafka = kafka;
	}

	public HBaseProperties getHbase() {
		return hbase;
	}

	public void setHbase(HBaseProperties hbase) {
		this.hbase = hbase;
	}

	public ZookeeperProperties getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(ZookeeperProperties zookeeper) {
		this.zookeeper = zookeeper;
	}

	public HdfsProperties getHdfs() {
		return hdfs;
	}

	public void setHdfs(HdfsProperties hdfs) {
		this.hdfs = hdfs;
	}

	public KerberosProperties getKerberos() {
		return kerberos;
	}

	public void setKerberos(KerberosProperties kerberos) {
		this.kerberos = kerberos;
	}

	public boolean isUnitTestMode() {
		return unitTestMode;
	}

	public void setUnitTestMode(boolean unitTestMode) {
		this.unitTestMode = unitTestMode;
	}

	
}
