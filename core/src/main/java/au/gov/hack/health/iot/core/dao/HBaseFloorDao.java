package au.gov.hack.health.iot.core.dao;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.gov.hack.health.iot.core.dao.mapper.FloorMapper;
import au.gov.hack.health.iot.core.domain.Floor;

@Component
public class HBaseFloorDao extends HBaseDaoTemplate {

	@Autowired
	private FloorMapper mapper;
	
	public Floor get(String id) {
		Get get = new Get(Bytes.toBytes(id));
		return super.get(get, mapper);
	}

	@Override
	public String getTableName() {
		return "hospital_floor";
	}
	
}
