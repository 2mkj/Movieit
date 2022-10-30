package net.zzim.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ZzimDAO {
	private DataSource ds;

	// 생성자에서 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public ZzimDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
		}
	}

	// 영화 정보 추가
	public boolean insert(Zzim z) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("insert into zzim values(zzim_seq.nextval,?,?,?,?)");
			pstmt.setString(1, z.getZzim_name());
			pstmt.setString(2, z.getZzim_title());
			pstmt.setString(3, z.getZzim_date());
			pstmt.setString(4, z.getZzim_poster());
			result = pstmt.executeUpdate();// 삽입 성공시 result는 1
			if(result == 1) {
				return true;
			}
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
		return false;
	}
	
	public Zzim zzim_info(String email,String title, String mdate) {
		Zzim z = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from zzim where zzim_name=? and zzim_title=? and zzim_date=?");
			pstmt.setString(1, email);
			pstmt.setString(2, title);
			pstmt.setString(3, mdate);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				z = new Zzim();
				z.setZzim_name(rs.getString("zzim_name"));
				z.setZzim_title(rs.getString("zzim_title"));
				z.setZzim_date(rs.getString("zzim_date"));
				z.setZzim_poster(rs.getString("zzim_poster"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
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
		return z;
	}

	public boolean zzimDelete(String email, String title, String mdate) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from zzim "
				   + "where zzim_name=? and zzim_title=? and zzim_date=? ";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, title);
			pstmt.setString(3, mdate);
			int result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("찜 제거");
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getDetail() 에러 : " + ex);
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
		return false;
	}

	public int getmyListCount(String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select count(*) from zzim where zzim_name = ? ");
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getListCount() 에러: " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
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
		return x;
	}

	public List<Zzim> getmyZzim(String email, int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//page : 페이지
		//limit : 페이지 당 목록의 수
		//board_re_ref desc, board_re_seq asc에 의해 정렬한 것을
		//조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.
		
		String board_list_sql = "select * "
							  + "from (select rownum rnum, j.* "
							  + "	  from (select * from zzim "
							  + "			where zzim_name = ? "
							  + "		    order by zzim_num desc ) j "
							  + "	  where rownum <= ? "
							  + "	  ) "
							  + "where rnum>=? and rnum<= ? ";
		
		List<Zzim> list = new ArrayList<Zzim>();
		// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지, 3페이지, 4페이지...
		int startrow = (page - 1) * limit + 1;  // 읽기 시작할 row 번호(1 11 21 31 ...)
		int endrow = startrow + limit - 1; 		// 읽을 마지막 row 번호(10 20 30 40 ...)
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(board_list_sql);
			pstmt.setString(1, email);
			pstmt.setInt(2, endrow);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, endrow);
			rs = pstmt.executeQuery();
			
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				Zzim z = new Zzim();
				z.setZzim_name(rs.getString("zzim_name"));
				z.setZzim_title(rs.getString("zzim_title"));
				z.setZzim_date(rs.getString("zzim_date"));
				z.setZzim_poster(rs.getString("zzim_poster"));
				list.add(z); // 값을 담은 객체를 리스트에 저장합니다.
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getmyWriting() 에러 : " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
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
		return list;
	}

	public int getmyListCount(String email, String value) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select count(*) from zzim where zzim_name=? "
										+ "and zzim_title like ?");
			pstmt.setString(1, email);
			pstmt.setString(2, "%"+value+"%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("찜 getmyListCount() 에러: " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
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
		return x;
	}

	public List<Zzim> getmyZzim(String email, String value, int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//page : 페이지
		//limit : 페이지 당 목록의 수
		//board_re_ref desc, board_re_seq asc에 의해 정렬한 것을
		//조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.
		
		String board_list_sql = "select * "
							  + "from (select rownum rnum, j.* "
							  + "	  from (select * from zzim "
							  + "			where zzim_name = ? "
							  + "			and zzim_title like ? "
							  + "		    order by zzim_num desc ) j "
							  + "	  where rownum <= ? "
							  + "	  ) "
							  + "where rnum>=? and rnum<= ? ";
		
		List<Zzim> list = new ArrayList<Zzim>();
		// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지, 3페이지, 4페이지...
		int startrow = (page - 1) * limit + 1;  // 읽기 시작할 row 번호(1 11 21 31 ...)
		int endrow = startrow + limit - 1; 		// 읽을 마지막 row 번호(10 20 30 40 ...)
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(board_list_sql);
			pstmt.setString(1, email);
			pstmt.setString(2, "%"+value+"%");
			pstmt.setInt(3, endrow);
			pstmt.setInt(4, startrow);
			pstmt.setInt(5, endrow);
			rs = pstmt.executeQuery();
			
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				Zzim z = new Zzim();
				z.setZzim_name(rs.getString("zzim_name"));
				z.setZzim_title(rs.getString("zzim_title"));
				z.setZzim_date(rs.getString("zzim_date"));
				z.setZzim_poster(rs.getString("zzim_poster"));
				list.add(z); // 값을 담은 객체를 리스트에 저장합니다.
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getmyWriting() 에러 : " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
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
		return list;
	}
	// 메인 프리뷰 - 유저가 찜한 순서대로 5개만 노출
	public List<Zzim> preZzim() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String board_list_sql = "SELECT  * "
							  + "FROM(SELECT ROW_NUMBER() OVER(PARTITION BY zzim_title ORDER BY zzim_num DESC ) AS RNUM, "
							  + "zzim.* "
							  + "FROM zzim ) "
							  + "WHERE RNUM = 1 "
							  + "ORDER BY zzim_num DESC ";
		List<Zzim> list = new ArrayList<Zzim>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(board_list_sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Zzim z = new Zzim();
				z.setZzim_name(rs.getString("zzim_name"));
				z.setZzim_title(rs.getString("zzim_title"));
				z.setZzim_date(rs.getString("zzim_date"));
				z.setZzim_poster(rs.getString("zzim_poster"));
				list.add(z); // 값을 담은 객체를 리스트에 저장합니다.
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getmyWriting() 에러 : " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
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
		return list;
	}

}
