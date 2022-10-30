package no.noticeboard.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class NoticeDAO {
	
private DataSource ds;
	
	//생성자에서 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public NoticeDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : "+ ex);
			return;
		}
	}

	public int getListCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select count(*) from notice");
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

	public List<Notice> getBoardList(int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//page : 페이지
		//limit : 페이지 당 목록의 수
		//board_re_ref desc, board_re_seq asc에 의해 정렬한 것을
		//조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.
		
		String board_list_sql = "select * "
							+ "   from (select rownum rnum, j.* "
							+ "    	    from (select b.* , m.user_name, nvl(cnt, 0) as cnt "
							+ "              from notice b left outer join (select comment_board_num, count(*) cnt "
							+ "                                           from comm "
							+ "											  group by comment_board_num) c "
							+ "              on b.board_num = c.comment_board_num "
							+ "              left join memberall m "
							+ "              on b.board_name = m.email "
							+ "     	      order by board_num desc ) j " 
							+ "     	where rownum <= ? "
							+ "			) "
							+ " where rnum>=? and rnum<=?";
		
		List<Notice> list = new ArrayList<Notice>();
		// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지, 3페이지, 4페이지...
		int startrow = (page - 1) * limit + 1;  // 읽기 시작할 row 번호(1 11 21 31 ...)
		int endrow = startrow + limit - 1; 		// 읽을 마지막 row 번호(10 20 30 40 ...)
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(board_list_sql);
			pstmt.setInt(1, endrow);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			rs = pstmt.executeQuery();
			
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				Notice board = new Notice();
				board.setBoard_num(rs.getInt("BOARD_NUM"));
				board.setBoard_name(rs.getString("BOARD_NAME"));
				board.setBoard_user_name(rs.getString("user_name"));
				board.setBoard_subject(rs.getString("BOARD_SUBJECT"));
				board.setBoard_content(rs.getString("BOARD_CONTENT"));
				board.setBoard_readcount(rs.getInt("BOARD_READCOUNT"));
				board.setBoard_date(rs.getString("BOARD_DATE"));
				board.setCnt(rs.getInt("cnt"));
				list.add(board); // 값을 담은 객체를 리스트에 저장합니다.
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

	public List<Notice> getmyWriting(String id, int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//page : 페이지
		//limit : 페이지 당 목록의 수
		//board_re_ref desc, board_re_seq asc에 의해 정렬한 것을
		//조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.
		
		String board_list_sql = "select * "
							+ "   from (select rownum rnum, j.* "
							+ "    	    from (select .*,  nvl(cnt, 0) cnt "
							+ "               from notice left outer join (select comment_board_num, count(*) cnt "
							+ "                                           from comm "
							+ "											  group by comment_board_num) "
							+ "               on board_num = comment_board_num"
							+ "               where board_name = ? "
							+ "     	     order by board_num desc  ) j " 
							+ "     	where rownum <= ? "
							+ "			) "
							+ " where rnum>=? and rnum<=?";
		
		List<Notice> list = new ArrayList<Notice>();
		// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지, 3페이지, 4페이지...
		int startrow = (page - 1) * limit + 1;  // 읽기 시작할 row 번호(1 11 21 31 ...)
		int endrow = startrow + limit - 1; 		// 읽을 마지막 row 번호(10 20 30 40 ...)
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(board_list_sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, endrow);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, endrow);
			rs = pstmt.executeQuery();
			
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				Notice board = new Notice();
				board.setBoard_num(rs.getInt("BOARD_NUM"));
				board.setBoard_name(rs.getString("BOARD_NAME"));
				board.setBoard_subject(rs.getString("BOARD_SUBJECT"));
				board.setBoard_content(rs.getString("BOARD_CONTENT"));
				board.setBoard_readcount(rs.getInt("BOARD_READCOUNT"));
				board.setBoard_date(rs.getString("BOARD_DATE"));
				board.setCnt(rs.getInt("cnt"));
				list.add(board); // 값을 담은 객체를 리스트에 저장합니다.
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

	public int getmyListCount(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select count(*) from notice where board_name=?");
			pstmt.setString(1, id);
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

	public int myWritingDelete(int[] num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int[] result = null; 
		int res=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("DELETE from notice where board_num=?");
			for(int i=0; i<num.length; i++) {
			pstmt.setInt(1, num[i]);
			
			pstmt.addBatch();
			
			}
			result = pstmt.executeBatch();
			
			for(int i=0; i<result.length; i++) {
				if(result[i]==2) {
					res++;
				}
			}
			if(num.length==res) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("myWritingDelete() 에러: " + ex);
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
		return res;
	}

	public boolean boardInsert(Notice board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result=0;
		try {
			conn = ds.getConnection();
			
			String max_sql = "(select nvl(max(board_num),0)+1 from notice)";

			// 원문글의 BOARD_RE_REF 필드는 자신의 글번호 입니다.
			String sql = "insert into notice " 
			            + "(BOARD_NUM, BOARD_NAME, BOARD_USER_NAME,  BOARD_SUBJECT,"
					    + " BOARD_CONTENT, BOARD_READCOUNT) "
					    + " values(" + max_sql + ",?,?,?," 
			            + "        ?,?)";

			// 새로운 글을 등록하는 부분입니다.
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, board.getBoard_name());
			pstmt.setString(2, board.getBoard_user_name());
			pstmt.setString(3, board.getBoard_subject());
			pstmt.setString(4, board.getBoard_content());
			pstmt.setInt(5, 0); // BOARD_READCOUNT 필드

			result = pstmt.executeUpdate();
			
			if (result == 1 ) {
				System.out.println("데이터 삽입이 모두 완료되었습니다.");
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("boardInsert() 에러 : " + ex);
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

	public void setReadCountUpdate(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = " update notice "
				   + " set BOARD_READCOUNT = BOARD_READCOUNT + 1 "
				   + " where BOARD_NUM = ? ";
		try{
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		}catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("setReadCountUpdate() 에러 : " + ex);
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
		
	}

	public Notice getDetail(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice board = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select * from notice "
					+ "					   where board_num = ? ");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				board = new Notice();
				board.setBoard_num(rs.getInt("BOARD_NUM"));
				board.setBoard_name(rs.getString("BOARD_NAME"));
				board.setBoard_user_name(rs.getString("BOARD_USER_NAME"));
				board.setBoard_subject(rs.getString("BOARD_SUBJECT"));
				board.setBoard_content(rs.getString("BOARD_CONTENT"));
				board.setBoard_readcount(rs.getInt("BOARD_READCOUNT"));
				board.setBoard_date(rs.getString("BOARD_DATE"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getDetail() 에러 : " + ex);
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
		return board;
	}

	public int getmyListCount(String id, String value) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			String sql = "select count(*) from notice "
					   + "	where board_name = ? "
					   + "  and ( board_subject like ? "
					   + "  or board_content like ? )";
			System.out.println(sql);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, "%"+value+"%");
			pstmt.setString(3, "%"+value+"%"); //'%a%'
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

	public List<Notice> getmyWriting(String id, String value, int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//page : 페이지
		//limit : 페이지 당 목록의 수
		//board_re_ref desc, board_re_seq asc에 의해 정렬한 것을
		//조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.
		String board_list_sql = "select * "
				+ "   from (select rownum rnum, j.* "
				+ "    	    from (select .*,  nvl(cnt, 0) cnt "
				+ "               from notice left outer join (select comment_board_num, count(*) cnt "
				+ "                                           from comm "
				+ "											  group by comment_board_num) "
				+ "               on board_num = comment_board_num "
				+"				  where board_name = ? " 
				+"				  and (board_subject like ? "
				+"				  or board_content like ? "
				+ "     	      ) order by board_num desc ) j " 
				+ "     	where rownum <= ? "
				+ "			) "
				+ " where rnum>=? and rnum<=?";
		
		List<Notice> list = new ArrayList<Notice>();
		// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지, 3페이지, 4페이지...
		int startrow = (page - 1) * limit + 1;  // 읽기 시작할 row 번호(1 11 21 31 ...)
		int endrow = startrow + limit - 1; 		// 읽을 마지막 row 번호(10 20 30 40 ...)
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(board_list_sql);
			pstmt.setString(1, id);
			pstmt.setString(2, "%"+value+"%");
			pstmt.setString(3, "%"+value+"%"); //'%a%'
			pstmt.setInt(4, endrow);
			pstmt.setInt(5, startrow);
			pstmt.setInt(6, endrow);
			rs = pstmt.executeQuery();
			
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				Notice board = new Notice();
				board.setBoard_num(rs.getInt("BOARD_NUM"));
				board.setBoard_name(rs.getString("BOARD_NAME"));
				board.setBoard_subject(rs.getString("BOARD_SUBJECT"));
				board.setBoard_content(rs.getString("BOARD_CONTENT"));
				board.setBoard_readcount(rs.getInt("BOARD_READCOUNT"));
				board.setBoard_date(rs.getString("BOARD_DATE"));
				board.setCnt(rs.getInt("cnt"));
				list.add(board); // 값을 담은 객체를 리스트에 저장합니다.
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

	public boolean boardModify(Notice c) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update notice "
				   + "set BOARD_SUBJECT=?, BOARD_CONTENT=? "
				   + "where BOARD_NUM=? ";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, c.getBoard_subject());
			pstmt.setString(2, c.getBoard_content());
			pstmt.setInt(3, c.getBoard_num());
			int result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("성공 업데이트");
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

	public boolean boardDelete(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from notice "
				   + "where BOARD_NUM=? ";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			int result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("삭제 완료");
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

	public int getListCount(String field, String value) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select count(*) "
									    + "from notice"
										+ " where " + field + " like ?");
			pstmt.setString(1, "%"+value+"%"); //'%a%'
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

	public List<Notice> getBoardList(String field, String value, int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//page : 페이지
		//limit : 페이지 당 목록의 수
		//board_re_ref desc, board_re_seq asc에 의해 정렬한 것을
		//조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.
		
		String board_list_sql = "select * "
							+ "   from (select rownum rnum, j.* "
							+ "    	    from (select b.* , m.user_name, nvl(cnt, 0) as cnt "
							+ "              from notice b left outer join (select comment_board_num, count(*) cnt "
							+ "                                           from comm "
							+ "											  group by comment_board_num) c "
							+ "              on b.board_num = c.comment_board_num "
							+ "              left join memberall m "
							+ "              on b.board_name = m.email "
							+ "              where "+ field +" like ? "
							+ "     	      order by board_num desc ) j " 
							+ "     	where rownum <= ? "
							+ "			) "
							+ " where rnum>=? and rnum<=?";
		
		List<Notice> list = new ArrayList<Notice>();
		// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지, 3페이지, 4페이지...
		int startrow = (page - 1) * limit + 1;  // 읽기 시작할 row 번호(1 11 21 31 ...)
		int endrow = startrow + limit - 1; 		// 읽을 마지막 row 번호(10 20 30 40 ...)
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(board_list_sql);
			pstmt.setString(1, "%"+value+"%"); //'%a%'
			pstmt.setInt(2, endrow);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, endrow);
			rs = pstmt.executeQuery();
			
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				Notice board = new Notice();
				board.setBoard_num(rs.getInt("BOARD_NUM"));
				board.setBoard_name(rs.getString("BOARD_NAME"));
				board.setBoard_user_name(rs.getString("user_name"));
				board.setBoard_subject(rs.getString("BOARD_SUBJECT"));
				board.setBoard_content(rs.getString("BOARD_CONTENT"));
				board.setBoard_readcount(rs.getInt("BOARD_READCOUNT"));
				board.setBoard_date(rs.getString("BOARD_DATE"));
				board.setCnt(rs.getInt("cnt"));
				list.add(board); // 값을 담은 객체를 리스트에 저장합니다.
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

	public int getListCount(String value) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(" select count(*) "
									    + " from notice "
										+ " where (board_subject like ? or board_content like ?)");
			pstmt.setString(1, "%"+value+"%"); //'%a%'
			pstmt.setString(2, "%"+value+"%"); //'%a%'
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

	public List<Notice> getBoardList(String value, int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//page : 페이지
		//limit : 페이지 당 목록의 수
		//board_re_ref desc, board_re_seq asc에 의해 정렬한 것을
		//조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.
		
		String board_list_sql = "select * "
							+ "   from (select rownum rnum, j.* "
							+ "    	    from (select b.* , m.user_name, nvl(cnt, 0) as cnt "
							+ "              from notice b left outer join (select comment_board_num, count(*) cnt "
							+ "                                           from comm "
							+ "											  group by comment_board_num) c "
							+ "              on b.board_num = c.comment_board_num "
							+ "              left join memberall m "
							+ "              on b.board_name = m.email "
							+ " 			 where (board_subject like ? or board_content like ?) "
							+ "     	     order by board_num desc ) j " 
							+ "     	where rownum <= ? "
							+ "			) "
							+ " where rnum>=? and rnum<=?";
		
		List<Notice> list = new ArrayList<Notice>();
		// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지, 3페이지, 4페이지...
		int startrow = (page - 1) * limit + 1;  // 읽기 시작할 row 번호(1 11 21 31 ...)
		int endrow = startrow + limit - 1; 		// 읽을 마지막 row 번호(10 20 30 40 ...)
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(board_list_sql);
			pstmt.setString(1, "%"+value+"%"); //'%a%'
			pstmt.setString(2, "%"+value+"%"); //'%a%'
			pstmt.setInt(3, endrow);
			pstmt.setInt(4, startrow);
			pstmt.setInt(5, endrow);
			rs = pstmt.executeQuery();
			
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				Notice board = new Notice();
				board.setBoard_num(rs.getInt("BOARD_NUM"));
				board.setBoard_name(rs.getString("user_name"));
				board.setBoard_subject(rs.getString("BOARD_SUBJECT"));
				board.setBoard_content(rs.getString("BOARD_CONTENT"));
				board.setBoard_readcount(rs.getInt("BOARD_READCOUNT"));
				board.setBoard_date(rs.getString("BOARD_DATE"));
				board.setCnt(rs.getInt("cnt"));
				list.add(board); // 값을 담은 객체를 리스트에 저장합니다.
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

	public int getuserListCount(String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			String sql = "select count(*) from notice "
					   + "where board_name =  ?  ";
			System.out.println(sql);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getuserListCount() 에러: " + ex);
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

	public List<Notice> getuserWriting(String name, int page, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//page : 페이지
		//limit : 페이지 당 목록의 수
		//board_re_ref desc, board_re_seq asc에 의해 정렬한 것을
		//조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.
		String board_list_sql   = " select *    "
							    + " from (select rownum rnum, j.*  "
							    + "	      from (select * from  notice "
							    + "	  		    where board_name = ?   "
							    + "	  		    order by board_num desc ) j  "
							    + "	      where rownum <= ?  "
							    + "	      )  "
							    + " where rnum>=? and rnum<=?";
		
		List<Notice> list = new ArrayList<Notice>();
		// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지, 3페이지, 4페이지...
		int startrow = (page - 1) * limit + 1;  // 읽기 시작할 row 번호(1 11 21 31 ...)
		int endrow = startrow + limit - 1; 		// 읽을 마지막 row 번호(10 20 30 40 ...)
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(board_list_sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, endrow);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, endrow);
			rs = pstmt.executeQuery();
			
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				Notice board = new Notice();
				board.setBoard_num(rs.getInt("BOARD_NUM"));
				board.setBoard_name(rs.getString("BOARD_NAME"));
				board.setBoard_user_name(rs.getString("BOARD_USER_NAME"));
				board.setBoard_subject(rs.getString("BOARD_SUBJECT"));
				board.setBoard_readcount(rs.getInt("BOARD_READCOUNT"));
				board.setBoard_date(rs.getString("BOARD_DATE"));
				list.add(board); // 값을 담은 객체를 리스트에 저장합니다.
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getuserWriting() 에러 : " + ex);
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

	public void username_Update(String email, String user_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = " update notice "
				   + " set BOARD_USER_NAME = ? "
				   + " where BOARD_NAME = ? ";
		try{
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_name);
			pstmt.setString(2, email);
			pstmt.executeUpdate();
		}catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("setReadCountUpdate() 에러 : " + ex);
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
	}
	

}
