package com.aahmedse.presto.connector;

import com.facebook.presto.spi.*;

public class PrestoRedisHandleResolver implements ConnectorHandleResolver {
	
	private String connectorId;

	public PrestoRedisHandleResolver(String connectorId) {
		this.connectorId = connectorId;
	}

	@Override
	public boolean canHandle(ConnectorTableHandle table) {
		return table instanceof PrestoRedisTableHandle && ((PrestoRedisTableHandle)table).getConnectorId().equals(connectorId)  ;
	}

	@Override
	public boolean canHandle(ConnectorColumnHandle col) {
		return col instanceof PrestoRedisColumnHandle && ((PrestoRedisColumnHandle)col).getConnectorId().equals(connectorId)  ;
	}

	@Override
	public boolean canHandle(ConnectorSplit split) {
		return split instanceof PrestoRedisSplit && ((PrestoRedisSplit)split).getConnectorId().equals(connectorId);
	}

	@Override
	public boolean canHandle(ConnectorIndexHandle indexHandle) {
		return false;
	}

	@Override
	public boolean canHandle(ConnectorOutputTableHandle tableHandle) {
		return false;
	}

	@Override
	public boolean canHandle(ConnectorInsertTableHandle tableHandle) {
		return false;
	}

	@Override
	public Class<? extends ConnectorTableHandle> getTableHandleClass() {
		return PrestoRedisTableHandle.class;
	}

	@Override
	public Class<? extends ConnectorColumnHandle> getColumnHandleClass() {
		return PrestoRedisColumnHandle.class;
	}

	@Override
	public Class<? extends ConnectorSplit> getSplitClass() {
		return PrestoRedisSplit.class;
	}

	@Override
	public Class<? extends ConnectorIndexHandle> getIndexHandleClass() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<? extends ConnectorOutputTableHandle> getOutputTableHandleClass() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<? extends ConnectorInsertTableHandle> getInsertTableHandleClass() {
		throw new UnsupportedOperationException();
	}


}
