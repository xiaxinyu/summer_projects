package com.data.source;

public interface IMyPool {
	PooledConnection getConnection();

	void createConnection(int count);
}
