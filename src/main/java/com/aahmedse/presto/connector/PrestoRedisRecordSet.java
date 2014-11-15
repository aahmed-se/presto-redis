package com.aahmedse.presto.connector;

import java.util.ArrayList;
import java.util.List;

import redis.clients.collections.MapStructure;
import static com.facebook.presto.util.Types.checkType;

import com.facebook.presto.spi.ConnectorColumnHandle;
import com.facebook.presto.spi.ConnectorSplit;
import com.facebook.presto.spi.RecordCursor;
import com.facebook.presto.spi.RecordSet;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.util.Types;

public class PrestoRedisRecordSet implements RecordSet {
	
	private PrestoRedisSplit split;
	private List<PrestoRedisColumnHandle> columns = new ArrayList<>();
	private List<Type> types = new ArrayList<>();
	private MapStructure dbInstance;

	public PrestoRedisRecordSet(ConnectorSplit split, List<? extends ConnectorColumnHandle> columnHandles, MapStructure dbInstance) {
		this.split = checkType(split, PrestoRedisSplit.class, "split");
		this.dbInstance = dbInstance;

		for (ConnectorColumnHandle col : columnHandles) {
			PrestoRedisColumnHandle column = Types.checkType(col, PrestoRedisColumnHandle.class, "col");
			this.columns.add( column );
			types.add( column.getMetadata().getType() );
		}
	}

	@Override
	public List<Type> getColumnTypes() {
		return types ;
	}

	@Override
	public RecordCursor cursor() {
		return new PrestoRedisCursor(split, columns, dbInstance);
	}

}
