package co.comment.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CommentDAO {
private DataSource ds;
	
	//생성자에서 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public CommentDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : "+ ex);
			return;
		}
	}
	
	//글의 갯수 구하기
	public int getListCount(int comment_board_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		String sql = " select count(*) "
				   + " from comm "
				   + " where comment_board_num=? ";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment_board_num);
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

	public JsonArray getCommentList(int comment_board_num, int state) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sort = "asc";
		if(state==2) {
			sort="desc";
		}
		String sql = "	 select num, comm.id, content, reg_date, comment_re_lev,  "
				   + "		    comment_re_seq, "
				   + "		    comment_re_ref, memberall.memberfile, memberall.user_name "
				   + "	 from   comm join memberall "
				   + "	 on     comm.id=memberall.email "
				   + "	 where  comment_board_num = ? "
				   + "	 order  by comment_re_ref "+ sort +", "
				   + "          comment_re_seq asc";
		
		JsonArray array = new JsonArray();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment_board_num);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JsonObject object = new JsonObject();
				object.addProperty("num", rs.getInt(1));
				object.addProperty("id", rs.getString(2));
				object.addProperty("content", rs.getString(3));
				object.addProperty("reg_date", rs.getString(4));
				object.addProperty("comment_re_lev", rs.getInt(5));
				object.addProperty("comment_re_seq", rs.getInt(6));
				object.addProperty("comment_re_ref", rs.getInt(7));
				object.addProperty("memberfile", rs.getString(8));
				object.addProperty("user_name", rs.getString(9));
				array.add(object);
			}
		} catch (Exception ex) {
			System.out.println("getCommentList() 에러 : " + ex);
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
		return array;
	}

	public int commentsInsert(Comment c) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "insert into comm " 
				  + " values(com_seq.nextval, ?, ?, sysdate, ?,?,?,com_seq.nextval)";

		try {
			conn = ds.getConnection();
			// 새로운 글을 등록하는 부분입니다.
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, c.getId());
			pstmt.setString(2, c.getContent());
			pstmt.setInt(3, c.getComment_board_num());
			pstmt.setInt(4, c.getComment_re_lev());
			pstmt.setInt(5, c.getComment_re_seq());
			result = pstmt.executeUpdate();
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
		return result;
	}

	public int commentsUpdate(Comment c) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = ds.getConnection();
			String sql = "update comm set content=? "
					   + "where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, c.getContent());
			pstmt.setInt(2, c.getNum());
			
			result = pstmt.executeUpdate();
			if(result == 1) {
				System.out.println("데이터 수정 되었습니다.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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

	public int commentsDelete(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = ds.getConnection();
			String sql = "delete comm where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
			if(result == 1) {
				System.out.println("데이터 삭제 되었습니다.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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

	public int commentsReply(Comment c) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			StringBuilder update_sql=new StringBuilder();
			update_sql.append("update comm ");
			update_sql.append("set    comment_re_seq=comment_re_seq +1 ");
			update_sql.append("where  comment_re_ref= ? ");
			update_sql.append("and    comment_re_seq> ? ");
			pstmt = conn.prepareStatement(update_sql.toString());
			pstmt.setInt(1,c.getComment_re_ref());
			pstmt.setInt(2,c.getComment_re_seq());
			pstmt.executeUpdate();
			pstmt.close();
				
			String sql = "insert into comm "
					   + "values(com_seq.nextval,?,?,sysdate,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, c.getId());
			pstmt.setString(2, c.getContent());
			pstmt.setInt(3, c.getComment_board_num());
			pstmt.setInt(4, c.getComment_re_lev()+1);
			pstmt.setInt(5, c.getComment_re_seq()+1);
			pstmt.setInt(6, c.getComment_re_ref());
			result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("reply 삽입 완료되었습니다.");
				conn.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
		  if(pstmt != null)
			try {
				pstmt.close();
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		  if(conn != null)
			try {
				conn.setAutoCommit(true);
				conn.close();
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public int getmyCommentListCount(String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		String sql = " select count(*) "
				   + " from comm "
				   + " where id=? ";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getmyCommentListCount() 에러: " + ex);
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

	public List<Comment> getmyCommentWriting(String email, int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = " select * "
				   + " from (select rownum rnum, j.*  "
				   + "	     from (select comm.*  "
				   + "			   from comm "
				   + "			   where id = ? "
				   + "			   and content != '삭제된 댓글 입니다.'"
				   + "			   order by reg_date desc ) j  "
				   + "	  where rownum <= ?  "
				   + "	  ) "
				   + " where rnum>=? and rnum<=?";
		
		List<Comment> list = new ArrayList<Comment>();
		int startrow = (page - 1) * limit + 1;  // 읽기 시작할 row 번호(1 11 21 31 ...)
		int endrow = startrow + limit - 1; 		// 읽을 마지막 row 번호(10 20 30 40 ...)
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setInt(2, endrow);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, endrow);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Comment co = new Comment();
				co.setNum(rs.getInt("num"));
				co.setId(rs.getString("id"));
				co.setContent(rs.getString("content"));
				co.setReg_date(rs.getString("reg_date"));
				co.setComment_board_num(rs.getInt("comment_board_num"));
				co.setComment_re_lev(rs.getInt("comment_re_lev"));
				co.setComment_re_seq(rs.getInt("comment_re_seq"));
				co.setComment_re_ref(rs.getInt("comment_re_ref"));
				list.add(co);
			}
		} catch (Exception ex) {
			System.out.println("getmyCommentWriting() 에러 : " + ex);
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

	public int getmyCommentListCount(String email, String value) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		String sql = " select count(*) "
				   + " from comm "
				   + " where id=? "
				   + " and content like ? ";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, "%"+value+"%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getmyCommentListCount() search 에러: " + ex);
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

	public List<Comment> getmyCommentWriting(String email, String value, int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = " select * "
				   + " from (select rownum rnum, j.*  "
				   + "	     from (select comm.*  "
				   + "			   from comm "
				   + "			   where id = ? "
				   + "			   and content != '삭제된 댓글 입니다.'"
				   + "			   and content like ? "
				   + "			   order by reg_date desc ) j  "
				   + "	  where rownum <= ?  "
				   + "	  ) "
				   + " where rnum>=? and rnum<=? ";
		
		List<Comment> list = new ArrayList<Comment>();
		int startrow = (page - 1) * limit + 1;  // 읽기 시작할 row 번호(1 11 21 31 ...)
		int endrow = startrow + limit - 1; 		// 읽을 마지막 row 번호(10 20 30 40 ...)
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, "%"+value+"%");
			pstmt.setInt(3, endrow);
			pstmt.setInt(4, startrow);
			pstmt.setInt(5, endrow);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Comment co = new Comment();
				co.setNum(rs.getInt("num"));
				co.setId(rs.getString("id"));
				co.setContent(rs.getString("content"));
				co.setReg_date(rs.getString("reg_date"));
				co.setComment_board_num(rs.getInt("comment_board_num"));
				co.setComment_re_lev(rs.getInt("comment_re_lev"));
				co.setComment_re_seq(rs.getInt("comment_re_seq"));
				co.setComment_re_ref(rs.getInt("comment_re_ref"));
				list.add(co);
			}
		} catch (Exception ex) {
			System.out.println("getmyCommentWriting() search 에러 : " + ex);
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

	public int userCommentsDel(Comment c) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = ds.getConnection();
			String sql = "update comm set content='삭제된 댓글 입니다.' "
					   + "where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, c.getNum());
			result = pstmt.executeUpdate();
			if(result == 1) {
				System.out.println("사용자의 댓글이 삭제되었습니다.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
