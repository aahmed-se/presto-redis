package com.aahmedse.presto.connector;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import redis.clients.collections.MapStructure;

import com.aahmedse.presto.redis.PrestoRedisServer;
import com.facebook.presto.spi.ColumnMetadata;
import com.facebook.presto.spi.ConnectorColumnHandle;
import com.facebook.presto.spi.ConnectorSession;
import com.facebook.presto.spi.ConnectorTableHandle;
import com.facebook.presto.spi.ConnectorTableMetadata;
import com.facebook.presto.spi.ReadOnlyConnectorMetadata;
import com.facebook.presto.spi.SchemaTableName;
import com.facebook.presto.spi.SchemaTablePrefix;

import static com.facebook.presto.util.Types.checkType;
import static com.aahmedse.presto.connector.PrestoRedisConnectorUtil.getColumns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class PrestoRedisConnectorMetadata extends ReadOnlyConnectorMetadata {
	
	private MapStructure schemaInstance;
	private String connectorId;
	public static String SCHEMA_NAME = "maps";

	public PrestoRedisConnectorMetadata(String connectorId, MapStructure dbInstance) {
		this.schemaInstance = dbInstance;
		this.connectorId = connectorId;
	}

	@Override
	public List<String> listSchemaNames(ConnectorSession session) {
		return ImmutableList.of(SCHEMA_NAME);
	}

	@Override
	public ConnectorTableHandle getTableHandle(ConnectorSession session,
			SchemaTableName tableName) {
		return new PrestoRedisTableHandle(connectorId, tableName);
	}

	@Override
	public ConnectorTableMetadata getTableMetadata(ConnectorTableHandle tableHandle) {

		PrestoRedisTableHandle table = checkType( tableHandle, PrestoRedisTableHandle.class, "tableHandle");

		return new PrestoRedisTableMetadata(schemaInstance, table);
	}

	@Override
	public List<SchemaTableName> listTables(ConnectorSession session,
			String schemaNameOrNull) {

		List<SchemaTableName> tables = new ArrayList<>();
		for (String tableName : PrestoRedisServer.tableNames) {
			tables.add( new SchemaTableName(SCHEMA_NAME, tableName) );
		}

		return tables;
	}

	@Override
	public ConnectorColumnHandle getSampleWeightColumnHandle(ConnectorTableHandle tableHandle) {
		return null;
	}

	@Override
	public Map<String, ConnectorColumnHandle> getColumnHandles(ConnectorTableHandle tableHandle) {

		PrestoRedisTableHandle table = checkType(tableHandle, PrestoRedisTableHandle.class, "tableHandle");
		List<ColumnMetadata> cols = PrestoRedisConnectorUtil.getColumns(table.getTable(), schemaInstance);

		Map<String, ConnectorColumnHandle> colHandles = new LinkedHashMap<>();
		PrestoRedisColumnHandle col;
		for(ColumnMetadata md: cols) {
			col = new PrestoRedisColumnHandle(connectorId, md.getName(), md.getType(), md.getOrdinalPosition());
			colHandles.put(md.getName(), col);
		}

		return colHandles;
	}

	@Override
	public ColumnMetadata getColumnMetadata(ConnectorTableHandle tableHandle,
			ConnectorColumnHandle columnHandle) {
		return checkType(columnHandle, PrestoRedisColumnHandle.class, "tableHandle").getMetadata();
	}

	@Override
	public Map<SchemaTableName, List<ColumnMetadata>> listTableColumns(
			ConnectorSession session, SchemaTablePrefix prefix) {

		List<SchemaTableName> tables = listTables(session, SCHEMA_NAME);
		Map<SchemaTableName, List<ColumnMetadata>> columns = new LinkedHashMap<>();
		for (SchemaTableName table : tables) {
			columns.put(table, getColumns(table, schemaInstance) );
		}

		return columns;
	}

}
