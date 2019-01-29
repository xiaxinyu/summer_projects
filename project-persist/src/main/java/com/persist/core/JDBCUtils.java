package com.persist.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCUtils {
	private static final Logger logger = LoggerFactory.getLogger(JDBCUtils.class);
	private static String connect;
	private static String driverClassName;
	private static String URL;
	private static String userName;
	private static String password;
	private static boolean autoCommit;

	private static Connection conn;

	static {
		config();
	}

	private static void config() {
		driverClassName = "com.mysql.jdbc.Driver";
		URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";
		userName = "root";
		password = "mysql";
		autoCommit = false;
	}

	private static boolean load() {
		try {
			Class.forName(driverClassName);
			return true;
		} catch (ClassNotFoundException e) {
			logger.error("Loading driver class has error.", e);
		}
		return false;
	}

	private static boolean invalid() {
		if (conn != null) {
			try {
				// Determine whether the connection is valid.
				// If connection isn't closed or connection is valid, return true
				if (conn.isClosed() || !conn.isValid(3)) {
					return true;
				}
			} catch (SQLException e) {
				logger.error("Checking status of connection has error.", e);
			}
			return false;
		} else {
			return true;
		}
	}

	public static Connection connect() {
		if (invalid()) {
			boolean loadFlag = load();
			if (loadFlag) {
				try {
					conn = DriverManager.getConnection(URL, userName, password);
				} catch (SQLException e) {
					logger.error("Creating connection has error.", e);
				}
			} else {
				logger.error("Loading driver class fail.");
			}
		}
		return conn;
	}

	public static void transaction() {
		try {
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			logger.error("Setting the mode of auto commit has error.", e);
		}
	}

	public static Statement statement() {
		Statement st = null;
		Connection conn = connect();
		if (conn != null) {
			try {
				transaction();
				st = conn.createStatement();
			} catch (SQLException e) {
				logger.error("Creating statement has error.", e);
			}
		}
		return st;
	}

	public static PreparedStatement prepare(String sql, boolean autoGeneratedKeys) {
		PreparedStatement ps = null;
		Connection conn = connect();
		if (conn != null) {
			try {
				transaction();
				if (autoGeneratedKeys) {
					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				} else {
					ps = conn.prepareStatement(sql);
				}
			} catch (SQLException e) {
				logger.error("Creating prepare statement has error.", e);
			}
		}
		return ps;
	}

	public static ResultSet query(String sql, List<Object> params) {
		if (sql == null || sql.trim().isEmpty() || sql.trim().toLowerCase().startsWith("select")) {
			throw new RuntimeException("The syntax of query  is illegal.");
		}
		ResultSet rs = null;
		if (params.size() > 0) {
			PreparedStatement ps = prepare(sql, false);

			if (ps == null) {
				return rs;
			}

			try {
				for (int i = 0; i < params.size(); i++) {
					ps.setObject(i + 1, params.get(i));
				}
				rs = ps.executeQuery();
			} catch (SQLException e) {
				logger.error("Executing query prepared statement has error.", e);
			}
		} else {
			Statement st = statement();

			if (st == null) {
				return rs;
			}

			try {
				rs = st.executeQuery(sql);
			} catch (SQLException e) {
				logger.error("Executing query statement has error.", e);
			}
		}
		return rs;
	}
	
	
}