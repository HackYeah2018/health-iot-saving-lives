package au.gov.hack.health.iot.core.dao.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.gov.hack.health.iot.core.dao.util.HBaseUtil;
import au.gov.hack.health.iot.core.domain.Gender;
import au.gov.hack.health.iot.core.domain.Person;
import au.gov.hack.health.iot.core.exceptions.PersistenceException;

import static au.gov.hack.health.iot.core.domain.Person.CF;

@Component
public class PersonMapper implements HBaseResultMapper<Person>{

	private static final Logger logger = Logger.getLogger(PersonMapper.class);
	
	@Autowired
	HBaseUtil hbaseUtil;
	
	@Override
	public Person map(Result r) throws PersistenceException {
		Person result = new Person();
		
		Boolean deleted = hbaseUtil.getBooleanValue(r, CF, "deleted");
		if (deleted == null || deleted.equals(Boolean.FALSE)) {
			result.setId(hbaseUtil.getStringValue(r, CF, "id"));
			if (StringUtils.isBlank(result.getId())) {
				throw new IllegalArgumentException("For row key [" + Bytes.toString(r.getRow()) + "] the id is blank");
			}
			result.setName(hbaseUtil.getStringValue(r, CF, "name"));
			result.setEmail(hbaseUtil.getStringValue(r, CF, "email"));
			result.setDateOfBirth(hbaseUtil.getLocalDate(r, CF, "dateOfBirth"));
			String genderAsText = hbaseUtil.getStringValue(r, CF, "gender");
			result.setGender(Gender.find(genderAsText));
			String admin = hbaseUtil.getStringValue(r, CF, "admin");
			result.setAdmin(admin == null ? false : Boolean.valueOf(admin));
			
			
			return result;
		} else {
			logger.info("Will not return provider [" + Bytes.toString(r.getRow()) + "] as deleted");
			return null;
		}
		
	}

}
