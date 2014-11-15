package com.aahmedse.presto.connector;


import redis.clients.collections.MapStructure;

import com.facebook.presto.spi.ConnectorTableMetadata;

public class PrestoRedisTableMetadata extends ConnectorTableMetadata {

	public PrestoRedisTableMetadata(MapStructure dbInstance,
			PrestoRedisTableHandle tableHandle) {

		super(tableHandle.getTable(), PrestoRedisConnectorUtil.getColumns(tableHandle.getTable(), dbInstance));

	}
}
