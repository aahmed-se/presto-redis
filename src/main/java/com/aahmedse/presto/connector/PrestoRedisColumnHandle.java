package com.aahmedse.presto.connector;

import com.facebook.presto.spi.ColumnMetadata;
import com.facebook.presto.spi.ConnectorColumnHandle;
import com.facebook.presto.spi.type.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PrestoRedisColumnHandle implements ConnectorColumnHandle {
	
	private String connectorId;
	private ColumnMetadata metadata;
	private String columnName;
	private Type columnType;
	private int ordinalPosition;
	
	@JsonCreator
	public PrestoRedisColumnHandle(
            @JsonProperty("connectorId") String connectorId,
            @JsonProperty("columnName") String columnName,
            @JsonProperty("columnType") Type columnType,
            @JsonProperty("ordinalPosition") int ordinalPosition) {
		this.connectorId = connectorId;
		this.columnName = columnName;
		this.columnType = columnType;
		this.ordinalPosition = ordinalPosition;
		this.metadata = new ColumnMetadata(columnName, columnType, ordinalPosition, false);
	}

	@JsonProperty
	public String getConnectorId() {
		return connectorId;
	}

	@JsonProperty
	public String getColumnName() {
		return columnName;
	}

	@JsonProperty
	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	@JsonProperty
	public Type getColumnType() {
		return columnType;
	}

	@JsonIgnore
	public ColumnMetadata getMetadata() {
		return metadata;
	}


}
