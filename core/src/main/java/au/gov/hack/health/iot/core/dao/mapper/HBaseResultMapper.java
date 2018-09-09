package au.gov.hack.health.iot.core.dao.mapper;

import org.apache.hadoop.hbase.client.Result;

import au.gov.hack.health.iot.core.exceptions.PersistenceException;

public interface HBaseResultMapper<T> {

	public T map(Result r) throws PersistenceException ;
	
}
