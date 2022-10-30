package com.comment.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jdbc.connect.ConnectionPool;
import com.review.action.Review;

public class CommentDAO {

	public ArrayList<Comment> getCommentList(int review_number, String comment_review_name, int startList,
			int endList) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<Comment> comment_list = null;

		String select_all_sql = "";

		select_all_sql += "select * from ";
		select_all_sql += "(select * from ";
		select_all_sql += "(select  rownum rnum, commentID.* from ";
		select_all_sql += "(select comment_table.* from comment_table ";
		select_all_sql += "where comment_review_name=? and review_number_fk = ? ";
		select_all_sql += "order by comment_date desc) commentID ) ";
		select_all_sql += "where rnum <= ?) ";
		select_all_sql += "where rnum >=? ";

		
		
		try {
			System.out.println(comment_review_name);
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(select_all_sql);
			pstmt.setString(1, comment_review_name);
			pstmt.setInt(2, review_number);
			pstmt.setInt(3, endList);
			pstmt.setInt(4, startList);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				if (comment_list == null)
					comment_list = new ArrayList<Comment>();

				Comment comment = new Comment();
				comment.setComment_id(rs.getString("comment_id"));
				comment.setComment_content(rs.getString("comment_content"));
				comment.setComment_number(rs.getInt("comment_number"));
				comment.setComment_password(rs.getString("comment_password"));
				comment.setComment_review_name(rs.getString("comment_review_name"));
				comment.setComment_date(rs.getString("comment_date"));
				comment_list.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<ReviewDAO> getCommentList(page, limit) 에서 에러");
		} finally {

			System.out.println("<getCOmmentList> comment_review_name = " + comment_review_name);
			System.out.println("<getCOmmentList> start, end = " + startList + " " + endList);
			System.out.println("");

			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return comment_list;
	}

	// 영화 일음에 따른 댓글의 개수를 가져옵니다.
	public int getCommentCount(int review_number, String comment_review_name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from comment_table where comment_review_name = ? and review_number_fk = ?";
		int result = -1;
		try {
			con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, comment_review_name);
			pstmt.setInt(2, review_number);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<CommentDAO> getCommentCount에서 에러");
		} finally {

			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// 댓글 번호에 따른 댓글을 가져옵니다.
	public Comment getComment(int comment_number) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Comment comment = null;
		String sql = "select * from comment_table where comment_number=?";
		try {
			con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, comment_number);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				comment = new Comment();
				comment.setComment_content(rs.getString("comment_content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<CommentDAO> getComment에서 에러");
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return comment;
	}

	// 댓글을 등록합니다.
	public int setComment(Comment comment, int review_number) {
		Connection con = null;
		PreparedStatement pstmt = null;

		String sql = "insert into comment_table(comment_number, review_number_fk, comment_id, comment_content, comment_password, comment_review_name ) values(create_comment_number.nextval, ?, ?, ?, ?, ?)";
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, review_number);
			pstmt.setString(2, comment.getComment_id());
			pstmt.setString(3, comment.getComment_content());
			pstmt.setString(4, comment.getComment_password());
			pstmt.setString(5, comment.getComment_review_name());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<CommentDAO> setComment에서 에러");
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// 댓글번호, 사용자의 패스워드입력, 사옹자의 바뀔 댓글내용으로 댓글을 수정합니다.
	public int modifiyComment(int comment_number, String password, String comment_content) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;

		System.out.println(comment_number);
		String get_comment = "select * from comment_table where comment_number =?";
		String sql = "update comment_table set comment_content=? where comment_number=?";

		int result = -1;
		try {
			con = ConnectionPool.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt2 = con.prepareStatement(get_comment);

			pstmt.setString(1, comment_content);
			pstmt.setInt(2, comment_number);

			pstmt2.setInt(1, comment_number);

			rs = pstmt2.executeQuery();
			if (rs.next()) {
				String get_password = rs.getString("comment_password");
				System.out.println("<modifiyComment>에서 DB에서 가져온 password: " + get_password);
				System.out.println("<modifiyComment>에서 사용자가 입력한 password: " + password);
				System.out.println("");
				if (!get_password.equals(password)) {
					result = -2;
				} else {
					result = pstmt.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<CommentDAO> modifiyComment에서 에러");
		} finally {
			try {
				pstmt2.close();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// 댓글번호, 사용자의 패스워드입력으로 댓글을 삭제합니다.
	public int deleteComment(int comment_number, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;

		System.out.println(comment_number);
		String get_comment = "select * from comment_table where comment_number =?";
		String sql = "delete from comment_table where comment_number = ?";

		int result = -1;
		try {
			con = ConnectionPool.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt2 = con.prepareStatement(get_comment);

			pstmt.setInt(1, comment_number);
			pstmt2.setInt(1, comment_number);

			rs = pstmt2.executeQuery();
			if (rs.next()) {
				String get_password = rs.getString("comment_password");
				System.out.println("<deleteComment>에서 DB에서 가져온 password: " + get_password);
				System.out.println("<deleteComment>에서 사용자가 입력한 password: " + password);
				System.out.println("");
				if (!get_password.equals(password) ) {
					result = -2;
				} else {
					result = pstmt.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<CommentDAO> deleteComment에서 에러");
		} finally {
			try {
				pstmt2.close();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
