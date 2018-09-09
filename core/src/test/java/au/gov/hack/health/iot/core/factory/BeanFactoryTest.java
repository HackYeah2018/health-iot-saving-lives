package au.gov.hack.health.iot.core.factory;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.gson.Gson;

import au.gov.hack.health.iot.core.config.EnvironmentProperties;
import au.gov.hack.health.iot.core.config.HBaseProperties;
import au.gov.hack.health.iot.core.config.HdfsProperties;
import au.gov.hack.health.iot.core.config.KafkaProperties;
import au.gov.hack.health.iot.core.config.KerberosProperties;
import au.gov.hack.health.iot.core.config.SerializedPropertyFactory;
import au.gov.hack.health.iot.core.config.ZookeeperProperties;
import au.gov.hack.health.iot.core.test.HdfsTest;
import au.gov.hack.health.iot.core.test.UnitTest;
import au.gov.hack.health.iot.core.utils.SaslUtil;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;
import org.junit.Before;

@Category(UnitTest.class)
public class BeanFactoryTest {
	
	private static final Logger logger = Logger.getLogger(BeanFactoryTest.class);
	
	@Before
	public void setupEnvironment() {
		EnvironmentProperties e = new EnvironmentProperties();
		e.setUnitTestMode(true);
		
		KafkaProperties kafka = new KafkaProperties();
		kafka.setBrokers("toot-nn-1.lab1.com:9092");
		kafka.setPortalTopic("ingest");
		kafka.setPortalErrorTopic("tcsi-ingest-error");
		kafka.setPortalResponseTopic("tcsi-ingest-resp");
		
		e.setKafka(kafka);
		
		
		HBaseProperties hbase = new HBaseProperties();
		e.setHbase(hbase);
		
		HdfsProperties hdfs = new HdfsProperties();
		e.setHdfs(hdfs);
		
		ZookeeperProperties zk = new ZookeeperProperties();
		zk.setClientPort("2181");
		zk.setQuorum("toot-nn-1.lab1.com");
		e.setZookeeper(zk);
		
		KerberosProperties kerberos = new KerberosProperties();
		e.setKerberos(kerberos);
		
		Gson g = new Gson();
		String result = g.toJson(e);
		System.setProperty(SerializedPropertyFactory.SERIALIZED_RUNTIME_CONFIG, result);
	}
	
	@Test
	public void testInit() {
		
		
		BeanFactory factory = new BeanFactory();
		factory.init();
		
		//RuntimePropertyFactory runtimePropertyFactory = (RuntimePropertyFactory) factory.getBeanByName("runtimePropertyFactory");
		//assertNotNull(runtimePropertyFactory);
		
		SaslUtil saslUtil = (SaslUtil) factory.getBeanByName("saslUtil");
		assertNotNull(saslUtil);
		assertEquals("2181", saslUtil.getZookeeperClientPortText());
		
	}
	

	
}
