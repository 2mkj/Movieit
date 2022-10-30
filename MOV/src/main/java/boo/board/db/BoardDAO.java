package boo.board.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class BoardDAO {

	
private DataSource ds;
	
	//생성자에서 JSDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public BoardDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
		}catch(Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
		}
	}
	
	public int getListCount() {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0; // DB에 해당 id가 없습니다.
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select count(*) from community");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				x = rs.getInt(1);
			}
			
		}catch(Exception ex) {
			System.out.println("getListCount() 에러 : " + ex);
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
		return x;
	}

	// 글 목록 보기
	public List<BoardBean> getBoardList(int page, int limit) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// page : 페이지
		// limit : 페이지 당 목록 수
		// board_re_ref desc, board_re_seq asc에 의해 정렬한 것을
		// 조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.
		
		String board_list_sql = " select * "
						   	  + " from (select rownum rnum, j.* "
							  + "	  from ("
							  + "	  		select community.*,  nvl(cnt, 0) as cnt "
							  + "			from community left outer join (select comment_board_num, count(*) cnt "
							  + "				 						from comm "
							  + "				 						group by comment_board_num) "
							  + "			on board_num = comment_board_num "
							  + "	 		order by board_num desc ) j "
							  + "	  where rownum <= ?  "
							  + "	  ) "
							  + " where rnum>=? and rnum<=?";
				
		List<BoardBean> list = new ArrayList<BoardBean>();
		// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지, 3페이지, 4페이지 ...
		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호(1 11 21 31 ..
		int endrow = startrow + limit - 1;	   // 읽을 마지막 row 번호(10 20 30 40 ..
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(board_list_sql);
			pstmt.setInt(1, endrow);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			rs = pstmt.executeQuery();
			
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while(rs.next()) {
				BoardBean board = new BoardBean();
				board.setBoard_num(rs.getInt("board_num"));
				board.setBoard_name(rs.getString("board_name"));
				board.setBoard_subject(rs.getString("board_subject"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_readcount(rs.getInt("board_readcount"));
				board.setBoard_date(rs.getString("board_date"));
				board.setCnt(rs.getInt("cnt"));
				list.add(board); // 값을 담은 객체를 리스트에 저장합니다.
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("getBoardLisr() 에러 : " + ex);
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
		return list;
	} // getBoardList()메서드 end

	// 글 등록하기
	public boolean boardInsert(BoardBean board) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			
			con = ds.getConnection();
			
			String max_sql = "(select nvl(max(board_num),0)+1 from memberall)";

			// 원문글의 BOARD_RE_REF 필드는 자신의 글번호 입니다.
			String sql = "insert into memberall " 
			            + "(BOARD_NUM,     BOARD_NAME,  BOARD_SUBJECT,"
					    + " BOARD_CONTENT, BOARD_RE_REF," 
			            + " BOARD_READCOUNT)"
					    + " values(" + max_sql + ",?,?," 
			            + "        ?,?,? )"; 

			// 새로운 글을 등록하는 부분입니다.
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, board.getBoard_name());
			pstmt.setString(2, board.getBoard_subject());
			pstmt.setString(3, board.getBoard_content());

			result = pstmt.executeUpdate();

			if(result == 1) {
				System.out.println("데이터 삽입이 모두 완료되었습니다.");
				return true;
			}
				
		}catch(Exception ex) {
			System.out.println("boardInsert() 에러: " + ex);
		}finally {
			if(pstmt != null)
				try {
					pstmt.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			try {
				if(con != null)
					con.close();
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
		} // finally end
		return false;
		// return null;
	}

	public void setReadCountUpdate(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String sql = "update memberall "
				+ "set board_readcount=board_readcount+1 "
				+ "where board_num = ?";
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			
		}catch(Exception ex) {
			System.out.println("setReadCountUpdate() 에러 : " + ex);
		}finally {
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
	}

	public BoardBean getDetail(int num) {
		BoardBean board = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from memberall where board_num = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board = new BoardBean();
				board.setBoard_num(rs.getInt("board_num"));
				board.setBoard_name(rs.getString("board_name"));
				board.setBoard_subject(rs.getString("board_subject"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_readcount(rs.getInt("board_readcount"));
				board.setBoard_date(rs.getString("board_date"));
			}
		
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("getDetail() 에러 : " + ex);
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
		return board;
	}

	public BoardBean getModify() {
		
		return null;
	}

	public boolean isBoardWriter(int num, String pass) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		String board_sql = "select board_pass from memberall where board_num = ? ";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(board_sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(pass.equals(rs.getString("board_pass"))) {
					result = true;
				}
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.out.println("isBoardWriter() 에러 : " + ex);
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
	
	public boolean boardModify(BoardBean modifyboard) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update memberall "
						 + "set board_subject=?, board_content=?, board_file=?"
						 + "where board_num=?	";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, modifyboard.getBoard_subject());
			pstmt.setString(2, modifyboard.getBoard_content());
			pstmt.setInt(3, modifyboard.getBoard_num());
			int result = pstmt.executeUpdate();
			if(result == 1) {
				System.out.println("성공 업데이트");
				return true;
			}
		
		}catch(Exception ex) {
			System.out.println("boardModify() 에러: " +ex);
		}finally {
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
		return false;
	}
	public boolean boardDelete(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String select_sql = "select board_re_ref, board_re_lev, board_re_seq "
							+ " from board "
							+ " where board_num=?";
		
		String board_delete_sql = "delete from board "
				+ "			 where  board_re_ref = ? "
				+ "			 and    board_re_lev >= ? "
				+ "			 and    board_re_seq >= ? "
				+ "			 and    board_re_seq <= (  "
				+ "								nvl((select min(board_re_seq) -1 "
				+ "                             	 from   board "
				+ "                             	 where  board_re_ref = ? "
				+ "                             	 and    board_re_lev = ? "
				+ "                             	 and    board_re_seq > ?) , "
				+ "                            	 	 (select max(board_re_seq) "
				+ "                             	 from   board "
				+ "			                		 where  board_re_ref = ? )) "
                + "                       		 )";
		boolean result_check = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(select_sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				pstmt2 = con.prepareStatement(board_delete_sql);
				pstmt2.setInt(1, rs.getInt("board_re_ref"));
				pstmt2.setInt(2, rs.getInt("board_re_lev"));
				pstmt2.setInt(3, rs.getInt("board_re_seq"));
				pstmt2.setInt(4, rs.getInt("board_re_ref"));
				pstmt2.setInt(5, rs.getInt("board_re_lev"));
				pstmt2.setInt(6, rs.getInt("board_re_seq"));
				pstmt2.setInt(7, rs.getInt("board_re_ref"));
				
				int count = pstmt2.executeUpdate();
				
				if(count >= 1)
					result_check = true; // 삭제가 안된 경우에는 false를 반환합닏.
				
			}
 		}catch(Exception ex) {
 			System.out.println("boardDelete() 에러:" + ex);
 			ex.printStackTrace();
 		}
		finally {
			if(rs!=null)
				try {
				   rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if(pstmt2 != null)
				try {
					pstmt2.close();
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
		return result_check;
	}//boardDelete()메서드 end
}
	