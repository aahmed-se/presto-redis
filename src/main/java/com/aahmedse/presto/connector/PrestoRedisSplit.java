package com.aahmedse.presto.connector;

import java.util.List;

import com.facebook.presto.spi.ConnectorSplit;
import com.facebook.presto.spi.HostAddress;
import com.facebook.presto.spi.SchemaTableName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

public class PrestoRedisSplit implements ConnectorSplit {
	
	private String connectorId;
	private SchemaTableName table;
	
	@JsonCreator
	public PrestoRedisSplit(
			@JsonProperty("connectorId") String connectorId,
			@JsonProperty("table") SchemaTableName table){
		this.connectorId = connectorId;
		this.table = table;
	}
	
	@JsonProperty
	public String getConnectorId() {
		return connectorId;
	}
	
	@Override
	public List<HostAddress> getAddresses() {
		return ImmutableList.of( HostAddress.fromParts("localhost", 8383) );
	}

	@Override
	public Object getInfo() {
		return this;
	}

	@Override
	public boolean isRemotelyAccessible() {
		return true;
	}

	@JsonProperty
	public SchemaTableName getTable() {
		return table;
	}

}
