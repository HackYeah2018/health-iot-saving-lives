package au.gov.hack.health.iot.core.dao;

import java.util.List;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.gov.hack.health.iot.core.dao.mapper.FloorMapper;
import au.gov.hack.health.iot.core.dao.mapper.RoomMapper;
import au.gov.hack.health.iot.core.domain.Floor;
import au.gov.hack.health.iot.core.domain.Room;

@Component
public class HBaseRoomDao extends HBaseDaoTemplate {

	@Autowired
	private RoomMapper mapper;
	
	public Room get(String id) {
		Get get = new Get(Bytes.toBytes(id));
		return super.get(get, mapper);
	}

	@Override
	public String getTableName() {
		return "hospital_floor_room";
	}

	public List<Room> allForFloor(String floorId) {
		Scan s = new Scan();
		byte[] start = Bytes.toBytes(floorId + "_0");
		byte[] end = Bytes.toBytes(floorId + "_{");
		s.setStartRow(start);
		s.setStopRow(end);
		// s.setStopRow(end);

		return super.scan(s, mapper, true);
	}
	
}
