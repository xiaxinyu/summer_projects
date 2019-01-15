package com.data.source;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PooledConnection {
	private boolean isBusy = false;
	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public PooledConnection(Connection connection, boolean isBusy) {
		this.connection = connection;
		this.isBusy = isBusy;
	}

	public void close() {
		this.isBusy = false;
	}

	public ResultSet queryBysql(String sql) {
		Statement sm = null;
		ResultSet rs = null;

		try {
			sm = connection.createStatement();
			rs = sm.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}
}
