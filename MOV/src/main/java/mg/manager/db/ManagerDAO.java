package mg.manager.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;




public class ManagerDAO {
	private DataSource ds;
	
	//생성자에서 JSDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public ManagerDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
		}catch(Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
		}
	}
	
	public int isId(String email) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1; // DB에 해당 id가 없습니다.
		try {
			con = ds.getConnection();
			
			String sql = "select * from memberall where email = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = 0; // DB에 해당 id가 있습니다.
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null)
				try {
					rs.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if(con != null)
				try {
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		}
		return result;
	}

	
	public int isId(String email, String pass) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1; // 아이디가 존재하지 않습니다.
		try {
			con = ds.getConnection();
			
			String sql = "select email, pass from memberall where email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(2).equals(pass)) {
					result = 1; // 아이디와 비밀번호 일치하는 경우
				}else {
					result = 0; // 비밀번호가 일치하지 않는 경우
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null)
				try {
					rs.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if(con != null)
				try {
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		}
		return result;
	}

	public Manager member_info(String id) {
		Manager m = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			
			String sql = " select * from memberall "
					+ "where email = ?  ";
			
			//PreparedStatement객체 얻기
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				m = new Manager();
				m.setEmail(rs.getString(1));
//				m.setDomain(rs.getString(2));
//				m.setPass(rs.getString(3));
				m.setUser_name(rs.getString(4));
				m.setName(rs.getString(5));
//				m.setJumin1(rs.getString(6));
//				m.setJumin2(rs.getString(7));
				m.setGender(rs.getString(8));
				m.setPhone(rs.getString(9));
//				m.setPost(rs.getString(10));
//				m.setAddress(rs.getString(11));
//				m.setMemberfile(rs.getString(12));

			}
				
			}catch(Exception se) {
				se.printStackTrace();
			}finally {
				try {
					if(rs != null)
						rs.close();
				}catch(SQLException e) {
					System.out.println(e.getMessage());
				}
				try {
					if(con != null)
						con.close();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
			} // finally end
			return m;
		} // selectAll() end

	public int update(Manager m) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = ds.getConnection();
			if(m.getMemberfile() != null) { // 프로필 사진 수정 안함 
				pstmt = con.prepareStatement( "update memberall "
					   		   + " set pass=?, user_name=?, name=?, "
					   		   + "     phone=?, post=?, address=?, memberfile=? "
					   		   + " where email=? ");
			
				pstmt.setString(1, m.getPass());
				pstmt.setString(2, m.getUser_name());
				pstmt.setString(3, m.getName());
				pstmt.setString(4, m.getPhone());
				pstmt.setString(5, m.getPost());
				pstmt.setString(6, m.getAddress());
				pstmt.setString(7, m.getMemberfile());
				pstmt.setString(8, m.getEmail());
				result = pstmt.executeUpdate();
				}else {							// 프로필 사진 수정함 
				pstmt = con.prepareStatement(
							 "update memberall "
						   + " set pass=?, user_name=?, name=?, "
						   + "     phone=?, post=?, address=? "
						   + " where email=? ");
				pstmt.setString(1, m.getPass());
				pstmt.setString(2, m.getUser_name());
				pstmt.setString(3, m.getName());
				pstmt.setString(4, m.getPhone());
				pstmt.setString(5, m.getPost());
				pstmt.setString(6, m.getAddress());
				pstmt.setString(7, m.getEmail());
				result = pstmt.executeUpdate();
				}
			}catch(Exception se) {
				se.printStackTrace();
			}finally {
				try {
					if(pstmt != null)
						pstmt.close();
				}catch(SQLException e) {
					System.out.println(e.getMessage());
				}
				try {
					if(con != null)
						con.close();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				} 
					
			}
			return result;
		}

	
	public int delete(String email) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			
			con = ds.getConnection();
			String delete_sql = "delete from memberall "
							+ "  where email = ? ";
			pstmt = con.prepareStatement(delete_sql);
			pstmt.setString(1, email);
			result = pstmt.executeUpdate();
			System.out.println("이메일값="+email);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(pstmt != null)
						pstmt.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
				try {
					if(con != null)
						con.close(); // 4단계 : DB연결을 끊는다.
				}catch(Exception e) {
					e.printStackTrace();
				} 
					
			}// finally end
			return result;
		}

	public int getListCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement("select count(*) from memberall where email != 'admin'");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				x = rs.getInt(1);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("getListCount() 에러: " + ex);
		}finally {
			if(rs != null)
				try {
					rs.close();
				}catch(SQLException e) {
					System.out.println(e.getMessage());
				}
			if(pstmt != null)
				try {
					pstmt.close();
				}catch(SQLException e) {
					System.out.println(e.getMessage());
				}
			if(con != null)
				try {
					con.close();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
		}
		return x;
	} 
	
	public int getListCount(String field, String value) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try{
			con = ds.getConnection();
			String sql = "select count(*) from memberll "
						+ " where email != 'admin' "
						+ " and " + field + " like ? ";
			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+value+"%"); // '%a%'
			rs = pstmt.executeQuery();
			if(rs.next()) {
				x = rs.getInt(1);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("getListCount() 에러: " + ex);
		}finally {
			if(rs != null)
				try {
					rs.close();
				}catch(SQLException e) {
					System.out.println(e.getMessage());
				}
			if(pstmt != null)
				try {
					pstmt.close();
				}catch(SQLException e) {
					System.out.println(e.getMessage());
				}
			if(con != null)
				try {
					con.close();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
		}
		return x;
	} 
	
	public List<Manager> getmanagerList(int page, int limit) {
		List<Manager> list = new ArrayList<Manager>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			
			String sql = "select * "
					+ " from(select b.*, rownum rnum "
					+ " 	from(select * from memberall "
					+ "			 where email != 'admin' "
					+ "			 order by email) b "
					+ "		     )"
					+ "	 	where rnum>=? and rnum<=?";
			pstmt = con.prepareStatement(sql);
			// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지, 3페이지, 4페이지 ...
			int startrow = (page - 1) * limit + 1;
						   // 읽기 시작할 row 번호(1 11, 21, 31...
			int endrow = startrow + limit - 1;
			 			   // 읽을 마지막 row 번호(10 20, 30, 40...
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Manager m = new Manager();
				m.setEmail(rs.getString("email"));
				m.setPass(rs.getString(2));
				m.setName(rs.getString(3));
				m.setUser_name(rs.getString(4));
				m.setName(rs.getString(5));
				m.setGender(rs.getString(6));
				list.add(m);
			}
			
			}catch(Exception ex) {
				ex.printStackTrace();
				System.out.println("getListCount() 에러: " + ex);
			}finally {
				if(rs != null)
					try {
						rs.close();
					}catch(SQLException e) {
						System.out.println(e.getMessage());
					}
				if(pstmt != null)
					try {
						pstmt.close();
					}catch(SQLException e) {
						System.out.println(e.getMessage());
					}
				if(con != null)
					try {
						con.close();
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
			}
			return list;
		} 
	public List<Manager> getmanagerList(String field, String value, int page, int limit) {
		List<Manager> list = new ArrayList<Manager>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			
			String sql = "select * "
					+ " from(select b.*, rownum rnum "
					+ " 	from(select * from memberall "
					+ "			 where email != 'admin' "
					+ "			 and " + field + " like ? "
					+ "			 order by id) b "
					+ "		     )"
					+ "	 	where rnum between ? and ?";
			
			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+value+"%");
			
				// 읽기 시작할 row 번호(1 11, 21, 31...
			int startrow = (page - 1) * limit + 1;
				// 읽을 마지막 row 번호(10 20, 30, 40... 
			int endrow = startrow + limit - 1;
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Manager m = new Manager();
				m.setEmail(rs.getString("email"));
//				m.setPassword(rs.getString(2));
				m.setName(rs.getString(3));
//				m.setAge(rs.getInt(4));
//				m.setGender(rs.getString(5));
//				m.setEmail(rs.getString(6));
				list.add(m);
			}
		}catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				if(rs != null)
					try {
						rs.close();
					}catch(SQLException e) {
						System.out.println(e.getMessage());
					}
				if(pstmt != null)
					try {
						pstmt.close();
					}catch(SQLException e) {
						System.out.println(e.getMessage());
					}
				if(con != null)
					try {
						con.close();
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
			}
			return list;
		}



	

	
}