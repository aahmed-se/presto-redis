package com.aahmedse.presto.connector;

import java.util.Map;

import redis.clients.collections.MapStructure;

import com.facebook.presto.spi.Connector;
import com.facebook.presto.spi.ConnectorHandleResolver;
import com.facebook.presto.spi.ConnectorIndexResolver;
import com.facebook.presto.spi.ConnectorMetadata;
import com.facebook.presto.spi.ConnectorPageSourceProvider;
import com.facebook.presto.spi.ConnectorRecordSetProvider;
import com.facebook.presto.spi.ConnectorRecordSinkProvider;
import com.facebook.presto.spi.ConnectorSplitManager;

public class PrestoRedisConnector implements Connector {

	private MapStructure dbInstance;
	private Map<String, String> config;
	private String connectorId;

	public PrestoRedisConnector(String connectorId, Map<String, String> config, MapStructure dbInstance) {
		this.dbInstance = dbInstance;
		this.config = config;
		this.connectorId = connectorId;
	}

	@Override
	public ConnectorHandleResolver getHandleResolver() {
		return new PrestoRedisHandleResolver(connectorId);
	}

	@Override
	public ConnectorMetadata getMetadata() {
		return new PrestoRedisConnectorMetadata(connectorId, dbInstance);
	}

	@Override
	public ConnectorSplitManager getSplitManager() {
		return new PrestoRedisSplitManager(connectorId);
	}

	@Override
	public ConnectorRecordSetProvider getRecordSetProvider() {
		return new PrestoRedisRecordSetProvider(connectorId, dbInstance);
	}

	@Override
	public ConnectorRecordSinkProvider getRecordSinkProvider() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ConnectorIndexResolver getIndexResolver() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ConnectorPageSourceProvider getPageSourceProvider() {
		throw new UnsupportedOperationException();
	}

}