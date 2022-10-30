package me.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class MemberDAO {
	private DataSource ds;
	
	public MemberDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : "+ ex);
		}
	}
	
	public int isId(String email, String pass) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			conn = ds.getConnection();

			String sql = "select email, pass from memberall where email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString(2).equals(pass)) {
					result = 1;
				} else {
					result = 0;
				}
			}
		} catch (Exception se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (conn != null)
					conn.close(); 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} 
		return result;
	}
	
	public int isId(String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			conn = ds.getConnection();

			String sql = "select email from memberall where email = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = 0;
			}
		} catch (Exception se) {
			se.printStackTrace();
		}  finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} 
		return result;
	}
	
	public int insert(Member m) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result=0;
		try {
			con = ds.getConnection();
			System.out.println("getConnection : insert()");
			
			pstmt = con.prepareStatement(
					"insert into memberall "
					+ " values(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, m.getEmail());
			pstmt.setString(2, m.getPass());
			pstmt.setString(3, m.getUser_name());
			pstmt.setString(4, m.getName());
			pstmt.setString(5, m.getJumin1());
			pstmt.setString(6, m.getJumin2());
			pstmt.setString(7, m.getGender());
			pstmt.setString(8, m.getPhone());
			pstmt.setString(9, m.getPost());
			pstmt.setString(10, m.getAddress());
			pstmt.setString(11, m.getMemberfile());
			result = pstmt.executeUpdate();
			
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			result = -1;
			System.out.println("멤버 아이디 중복 에러입니다.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public int isID(String name, String phone) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = ds.getConnection();

			String sql = "select email, name, phone from memberall where name=? and phone=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			rs = pstmt.executeQuery();
			System.out.println("이름=" + name);
			System.out.println("전화번호=" + phone);
			if (rs.next()) {
				if (rs.getString(2).equals(name) && rs.getString(3).equals(phone)) {
					result = 1;
				}
			}
		} catch (Exception se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (conn != null)
					conn.close(); 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} 
		return result;
	}
	
	public String isEmail(String name, String phone) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			conn = ds.getConnection();

			String sql = "select email from memberall where name=? and phone=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (conn != null)
					conn.close(); 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} 
		return result;
	}

	public int isPW(String email, String name, String phone) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = ds.getConnection();

			String sql = "select email, pass, name, phone from memberall where email=? and name=? and phone=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, name);
			pstmt.setString(3, phone);
			rs = pstmt.executeQuery();
			System.out.println("이메일=" + email);
			System.out.println("이름=" + name);
			System.out.println("전화번호=" + phone);
			if (rs.next()) {
				if (rs.getString(1).equals(email) && rs.getString(3).equals(name) && rs.getString(4).equals(phone)) {
					result = 1;
				}
			}
		} catch (Exception se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (conn != null)
					conn.close(); 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} 
		return result;
	}

	public String isPass(String email, String name, String phone) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			conn = ds.getConnection();

			String sql = "select pass from memberall where email=? and name=? and phone=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, name);
			pstmt.setString(3, phone);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if (conn != null)
					conn.close(); 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} 
		return result;
	}

	public int newPass(String email, String newpass) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(
						 "update memberall "
					   + " set pass=? where email=? ");
			pstmt.setString(1, newpass);
			pstmt.setString(2, email);
			result = pstmt.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("newPass() 에러 : " + ex);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
