package com.data.source.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.data.source.PooledConnection;
import com.data.source.core.PoolManager;
import com.data.source.impl.MyPoolImpl;

public class Test {
	private static MyPoolImpl poolImpl = PoolManager.getInstance();

	public synchronized static void selctData() {
		PooledConnection connection = poolImpl.getConnection();
		ResultSet rs = connection.queryBysql("SELECT * FROM user");
		try {
			while (rs.next()) {
				System.out.println(rs.getString("ID") + "\t\t");
				System.out.println(rs.getString("USERNAME") + "\t\t");
				System.out.println(rs.getString("PASSWORD") + "\t\t");
				System.out.println();
			}
			rs.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 1500; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					selctData();
				}
			}).start();
		}
	}
}