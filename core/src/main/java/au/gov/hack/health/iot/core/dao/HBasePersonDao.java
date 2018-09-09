package au.gov.hack.health.iot.core.dao;

import java.util.List;
import java.util.UUID;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import au.gov.hack.health.iot.core.dao.mapper.PersonMapper;
import au.gov.hack.health.iot.core.domain.Person;
import au.gov.hack.health.iot.core.exceptions.PersistenceException;
import au.gov.hack.health.iot.core.exceptions.ProcessingException;

import static au.gov.hack.health.iot.core.domain.Person.CF;

@Component
public class HBasePersonDao extends HBaseDaoTemplate {

	@Autowired
	private PersonMapper mapper;
	
	@Override
	public String getTableName() {
		return "people";
	}

	public List<Person> getAll() {
		try {
			Scan scan = new Scan();
			return super.scan(scan, mapper, true);
		} catch (PersistenceException e) {
			throw new ProcessingException("Unable to search providers: " + e.getMessage(), e);
		}

	}
	
	public void persist(Person person) throws PersistenceException {
		Put p = new Put(Bytes.toBytes(person.getId()));
		p.addColumn(Bytes.toBytes(CF), Bytes.toBytes("name"), Bytes.toBytes(person.getName()));
		p.addColumn(Bytes.toBytes(CF), Bytes.toBytes("email"), Bytes.toBytes(person.getEmail()));
		p.addColumn(Bytes.toBytes(CF), Bytes.toBytes("id"), Bytes.toBytes(person.getId()));
		p.addColumn(Bytes.toBytes(CF), Bytes.toBytes("deleted"), Bytes.toBytes(Boolean.FALSE));

		try {
			super.doPut(p);
		} catch (Exception e) {
			throw new PersistenceException(
					"Unable to create new provider [" + person.getName() + "]: " + e.getMessage(), e);
		}

	}

	public void deleteSoft(String id) {
		Put p = new Put(Bytes.toBytes(id));
		p.addColumn(Bytes.toBytes(CF), Bytes.toBytes("deleted"), Bytes.toBytes(Boolean.TRUE));
		super.doPut(p);
	}
	
	public void deleteHard(String id) {
		Delete d = new Delete(Bytes.toBytes(id));
		super.doDelete(d);
	}

	public Person get(String id) {
		Get get = new Get(Bytes.toBytes(id));
		return super.get(get, mapper);
	}
}
