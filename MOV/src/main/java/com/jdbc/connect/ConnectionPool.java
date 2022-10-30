package com.jdbc.connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {
	private static String jdbcURL = "java:comp/env/jdbc/OracleDB";

	public static Connection getConnection() {
		DataSource ds = getDataSource();
		Connection conn = null;
		try {
			conn = ds.getConnection();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("<ConnectionPool>Connectio에서 에러.");
		}
		return conn;
	}

	public static DataSource getDataSource() {
		Context context = null;
		DataSource ds = null;
		try {
			context = new InitialContext();
			ds = (DataSource) context.lookup(jdbcURL);
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println("<ConnectionPool>DataSource에서 에러.");
		}
		return ds;
	}

	public static void Test() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql  = "select * from review";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("nice");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
