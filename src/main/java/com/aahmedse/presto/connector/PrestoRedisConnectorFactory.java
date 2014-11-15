package com.aahmedse.presto.connector;

import java.util.Map;
import java.util.Map.Entry;

import redis.clients.collections.MapStructure;
import redis.clients.collections.builder.RedisStrutureBuilder;

import com.aahmedse.presto.pojo.Employee;
import com.aahmedse.presto.redis.RedisConnection;
import com.facebook.presto.spi.Connector;
import com.facebook.presto.spi.ConnectorFactory;

public class PrestoRedisConnectorFactory implements ConnectorFactory {
	
	private Map<String, String> optionalConfig;

	public PrestoRedisConnectorFactory(Map<String, String> optionalConfig) {
		this.optionalConfig = optionalConfig;

		for(Entry e : optionalConfig.entrySet())
			System.out.println(e.getKey() + " = " + e.getValue());
	}

	@Override
	public String getName() {
		return "redis";
	}

	@Override
	public Connector create(String connectorId, Map<String, String> requiredConfig) {

		MapStructure dbInstance = RedisStrutureBuilder.ofMap(RedisConnection.JEDIS, Employee.class).withNameSpace("employeedb").build();

		PrestoRedisConnector con = new PrestoRedisConnector(connectorId, requiredConfig, dbInstance);

		return con;
	}

}
