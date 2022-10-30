package net.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.review.action.Review;

public class MemberDAO2 {
	private DataSource ds;
	
	//생성자에서 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public MemberDAO2() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : "+ ex);
		}
	}

	public Memberall member_info(String email) {
		Memberall m = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			conn = ds.getConnection();
			
			String sql = "select * from memberall where email = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				m = new Memberall();
				m.setEmail(rs.getString("email"));
				m.setPass(rs.getString("pass"));
				m.setUser_name(rs.getString("user_name"));
				m.setName(rs.getString("name"));
				m.setJumin1(rs.getString("jumin1"));
				m.setJumin2(rs.getString("jumin2"));
				m.setGender(rs.getString("gender"));
				m.setPhone(rs.getString("phone"));
				m.setPost(rs.getString("post"));
				m.setAddress(rs.getString("address"));
				m.setMemberfile(rs.getString("memberfile"));
				
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
		return m;
	}


	public int update(Memberall m) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			if(m.getMemberfile() != null) { // 프로필 사진 수정 안함 
			pstmt = conn.prepareStatement(
						 "update memberall "
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
			pstmt = conn.prepareStatement(
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
		return result;
	}


	public int delete(String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0; 
		try {
			conn = ds.getConnection();
			String sql = "delete memberall where email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			result = pstmt.executeUpdate();
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
	
	public int insert(Memberall m) {
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
			result = pstmt.executeUpdate();//삽입 성공시 result는 1
			
		//primary key 제약조건 위반했을 경우 발생하는 에러
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

	public int getmyReviewCount(String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			String sql = "select count(*) from review "
					   + "	where review_id = ? ";
			System.out.println(sql);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getmyReviewCount() 에러: " + ex);
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
	
	public List<Review> getmyReview(String email, int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String board_list_sql = "select *  "
							  + "from (select rownum rnum, j.*  "
							  + "	 from (select review.* "
							  + "		   from review  "
							  + "		   where review.review_id = ?  "
							  + "		   order by review_date desc ) j   "
							  + "	where rownum <= ? "
							  + "	)  "
							  + "where rnum>=? and rnum<=?";
		
		List<Review> list = new ArrayList<Review>();
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
				Review review = new Review();
				review.setReview_id(rs.getString("review_id"));
				review.setReview_title(rs.getString("review_title"));
				review.setReview_content(rs.getString("review_content"));
				review.setReview_date(rs.getString("review_date"));
				review.setReview_star(rs.getString("review_star"));
				review.setReview_readcount(rs.getInt("review_readcount"));
				review.setReview_move_name(rs.getString("review_move_name"));
				//review.setReview_poster(rs.getString("review_poster"));
			
				list.add(review); // 값을 담은 객체를 리스트에 저장합니다.
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getBoardList() 에러 : " + ex);
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

	public int getmyReviewCount(String email, String value) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			String sql = "select count(*) from review "
					   + "	where review_id = ? "
					   + "  and ( review_title like ? "
					   + "  or review_content like ? "
					   + "  or review_move_name like ? )";
			System.out.println(sql);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, "%"+value+"%");
			pstmt.setString(3, "%"+value+"%");
			pstmt.setString(4, "%"+value+"%"); //'%a%'
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getmyReviewCount() 에러: " + ex);
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

	public List<Review> getmyReview(String email, String value, int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String board_list_sql = "select * "
							  + "   from (select rownum rnum, j.* "
							  + "    	    from (select review.*  "
							  + "               from review "
							  + "				where review_id = ? "
							  + "   			and ( review_title like ? "
							  + " 				or review_content like ? "
							  + "   			or review_move_name like ? ) "
							  +"				order by review_date desc ) j " 
							  + "     	where rownum <= ? "
							  + "		) "
							  + " where rnum>=? and rnum<=?";
		
		List<Review> list = new ArrayList<Review>();
		int startrow = (page - 1) * limit + 1;  // 읽기 시작할 row 번호(1 11 21 31 ...)
		int endrow = startrow + limit - 1; 		// 읽을 마지막 row 번호(10 20 30 40 ...)
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(board_list_sql);
			pstmt.setString(1, email);
			pstmt.setString(2, "%"+value+"%");
			pstmt.setString(3, "%"+value+"%");
			pstmt.setString(4, "%"+value+"%"); //'%a%'
			pstmt.setInt(5, endrow);
			pstmt.setInt(6, startrow);
			pstmt.setInt(7, endrow);
			rs = pstmt.executeQuery();
			
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				Review review = new Review();
				review.setReview_id(rs.getString("review_id"));
				review.setReview_title(rs.getString("review_title"));
				review.setReview_content(rs.getString("review_content"));
				review.setReview_date(rs.getString("review_date"));
				review.setReview_star(rs.getString("review_star"));
				review.setReview_readcount(rs.getInt("review_readcount"));
				review.setReview_move_name(rs.getString("review_move_name"));
				//review.setReview_poster(rs.getString("review_poster"));
				list.add(review); // 값을 담은 객체를 리스트에 저장합니다.
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getBoardList() 에러 : " + ex);
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
