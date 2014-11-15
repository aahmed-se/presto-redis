package com.aahmedse.presto.connector;


import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import com.facebook.presto.spi.ConnectorFactory;
import com.facebook.presto.spi.Plugin;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;


public class PrestoRedisPlugin implements Plugin{
	
	private Map<String, String> optionalConfig;

	public PrestoRedisPlugin() {
	}

	@Override
	public void setOptionalConfig(Map<String, String> optionalConfig) {
		this.optionalConfig = ImmutableMap.copyOf(checkNotNull(optionalConfig, "optionalConfig is null"));
	}

	public Map<String, String> getOptionalConfig() {
		return optionalConfig;
	}

	@Override
	public <T> List<T> getServices(Class<T> type) {

		if (type == ConnectorFactory.class) {
            return ImmutableList.of(type.cast(new PrestoRedisConnectorFactory(getOptionalConfig())));
        }
        return ImmutableList.of();
	}

}
