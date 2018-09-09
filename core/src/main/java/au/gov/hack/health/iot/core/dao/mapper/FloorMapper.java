package au.gov.hack.health.iot.core.dao.mapper;

import static au.gov.hack.health.iot.core.domain.Person.CF;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import au.gov.hack.health.iot.core.dao.util.HBaseUtil;
import au.gov.hack.health.iot.core.domain.Floor;
import au.gov.hack.health.iot.core.domain.Gender;
import au.gov.hack.health.iot.core.domain.Person;
import au.gov.hack.health.iot.core.domain.Point;
import au.gov.hack.health.iot.core.exceptions.PersistenceException;

@Component
public class FloorMapper implements HBaseResultMapper<Floor> {

	@Autowired
	private HBaseUtil hbaseUtil;

	@Override
	public Floor map(Result r) throws PersistenceException {
		Floor result = new Floor();

		result.setId(hbaseUtil.getStringValue(r, CF, "id"));
		if (StringUtils.isBlank(result.getId())) {
			throw new IllegalArgumentException("For row key [" + Bytes.toString(r.getRow()) + "] the id is blank");
		}
		result.setHospitalId(hbaseUtil.getStringValue(r, CF, "hospitalId"));
		result.setName(hbaseUtil.getStringValue(r, CF, "name"));
		String cornersAsText = hbaseUtil.getStringValue(r, CF, "corners");

		Gson gson = new Gson();
		List<Point> corners = gson.fromJson(cornersAsText, new TypeToken<List<Point>>() {
		}.getType());
		result.setCorners(corners);
		return result;
	}

}
