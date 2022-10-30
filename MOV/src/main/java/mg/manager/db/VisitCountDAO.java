package mg.manager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class VisitCountDAO {

//	private static VisitCountDAO instance;
//	
//	public static VisitCountDAO getInstance(){
//		if(instance==null)
//			instance = new VisitCountDAO();
//		return instance;
//	}
	private DataSource ds;
	public VisitCountDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
		}catch(Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
		}
	}
	
	public void setTotalCount() throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("insert into visit(to_date) values(sysdate)");
			
			conn = ds.getConnection();
			
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
		}finally {
			
			try {
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(conn != null) {conn.close(); conn=null;}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getTotalCount() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) as TotalCnt from visit");
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next())
				totalCount = rs.getInt("TotalCnt");
			return totalCount;
		}catch(Exception e) {
			conn.rollback();
		}finally {
			
			try {
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(conn != null) {conn.close(); conn=null;}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return totalCount;
	}
	
	public int getTodayCount() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int todayCount = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) as TotalCnt from visit");
			sql.append(" where to_date(v_date, 'yyyy-mm-dd') = to_date(sysdate, 'yyyy-mm-dd')");
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next())
				todayCount = rs.getInt("TodayCnt");
			
			return todayCount;
		}catch(Exception e) {
			conn.rollback();
		}finally {
			
			try {
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(conn != null) {conn.close(); conn=null;}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return todayCount;
	}

	public static VisitCountDAO getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
