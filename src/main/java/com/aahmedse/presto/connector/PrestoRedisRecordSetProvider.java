package com.aahmedse.presto.connector;

import java.util.List;

import redis.clients.collections.MapStructure;
import com.facebook.presto.spi.ConnectorColumnHandle;
import com.facebook.presto.spi.ConnectorRecordSetProvider;
import com.facebook.presto.spi.ConnectorSplit;
import com.facebook.presto.spi.RecordSet;

public class PrestoRedisRecordSetProvider implements ConnectorRecordSetProvider {

	private String connectorId;
	private MapStructure dbInstance;

	public PrestoRedisRecordSetProvider(String connectorId, MapStructure dbInstance) {
		this.connectorId = connectorId;
		this.dbInstance = dbInstance;
	}

	@Override
	public RecordSet getRecordSet(ConnectorSplit split, List<? extends ConnectorColumnHandle> columns) {
		return new PrestoRedisRecordSet(split, columns, dbInstance);
	}

}
