package com.ucac.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class DBUtil {

	private static DataSource dataSource = DBPoolUtils.getInstance()
			.getDataSource();

	public DBUtil() {
	}

	public static Connection getconn() {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	public static PreparedStatement getstst(Connection conn, String sql)
			throws SQLException {
		PreparedStatement sts = null;
		sts = conn.prepareStatement(sql);

		return sts;
	}

	public static Statement getstst(Connection conn) {
		Statement sts = null;
		try {
			sts = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sts;
	}

	public static ResultSet getrest(Statement sts, String sql)
			throws SQLException {
		ResultSet rs = null;
		rs = sts.executeQuery(sql);

		return rs;

	}

	public static void close(Connection conn, Statement sts) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			conn = null;
		}
		if (sts != null) {
			try {
				sts.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			sts = null;
		}
	}
}
