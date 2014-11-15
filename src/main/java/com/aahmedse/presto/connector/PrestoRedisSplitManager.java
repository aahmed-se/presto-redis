package com.aahmedse.presto.connector;

import java.util.ArrayList;
import java.util.List;

import com.facebook.presto.spi.ConnectorColumnHandle;
import com.facebook.presto.spi.ConnectorPartition;
import com.facebook.presto.spi.ConnectorPartitionResult;
import com.facebook.presto.spi.ConnectorSplit;
import com.facebook.presto.spi.ConnectorSplitManager;
import com.facebook.presto.spi.ConnectorSplitSource;
import com.facebook.presto.spi.ConnectorTableHandle;
import com.facebook.presto.spi.FixedSplitSource;
import com.facebook.presto.spi.TupleDomain;
import com.facebook.presto.util.Types;

public class PrestoRedisSplitManager implements ConnectorSplitManager {

	private String connectorId;

	public PrestoRedisSplitManager(String connectorId) {
		this.connectorId = connectorId;
	}

	@Override
	public ConnectorPartitionResult getPartitions(ConnectorTableHandle table,
			TupleDomain<ConnectorColumnHandle> tupleDomain) {

		List<ConnectorPartition> partitions = new ArrayList<>();
		// pretending we have single partition for now
		partitions.add( new PrestoRedisPartition(table) );

		return new ConnectorPartitionResult(partitions , tupleDomain);
	}

	@Override
	public ConnectorSplitSource getPartitionSplits(ConnectorTableHandle table,
			List<ConnectorPartition> partitions) {

		List<ConnectorSplit> splits = new ArrayList<>();
		PrestoRedisTableHandle hzTable = Types.checkType(table, PrestoRedisTableHandle.class, "table");
		splits.add( new PrestoRedisSplit(connectorId, hzTable.getTable()) );

		return new FixedSplitSource(connectorId, splits);
	}

}
