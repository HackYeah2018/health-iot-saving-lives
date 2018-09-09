package au.gov.hack.health.iot.core.dao.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class HBaseUtil {

	public String getStringValue(Result r, String columnFamily, String columnName) {
		//Cell c = r.getColumnLatestCell(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName));
		//if (c == null) {
		//	return null;
		//} else {
		//	return Bytes.toString(CellUtil.cloneValue(c));
		//}
		byte[] value = r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName));
		if (value == null || value.length == 0) {
			return null;
		} else {
			return new String(value);
		}
	}

	public Long getLongValue(Result r, String columnFamily, String columnName) {
		Cell c = r.getColumnLatestCell(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName));
		if (c == null) {
			return null;
		} else {
			return Bytes.toLong(CellUtil.cloneValue(c));
		}
	}

	public Boolean getBooleanValue(Result r, String cf, String columnName) {
		Cell c = r.getColumnLatestCell(Bytes.toBytes(cf), Bytes.toBytes(columnName));
		if (c == null) {
			return null;
		} else {
			return Bytes.toBoolean(CellUtil.cloneValue(c));
		}
	}
	
	public LocalDate getLocalDate(Result r, String cf, String columnName) {
		String text = getStringValue(r, cf, columnName);
		if (StringUtils.isBlank(text)) {
			return null;
		} else {
			return new LocalDate(text);
		}
	}

}
