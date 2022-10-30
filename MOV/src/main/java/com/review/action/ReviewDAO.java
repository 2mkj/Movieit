package com.review.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jdbc.connect.ConnectionPool;

public class ReviewDAO {

	// 영화 이름에 따른 리뷰를 가져옵니다.
	public ArrayList<Review> getReviewList(String review_move_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<Review> review_list = null;

		String select_all_sql = "select * from review where review_move_name=?";

		try {
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(select_all_sql);
			pstmt.setString(1, review_move_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				if (review_list == null)
					review_list = new ArrayList<Review>();

				Review review = new Review();
				review.setReview_id(rs.getString("review_id"));
				review.setReview_title(rs.getString("review_title"));
				review.setReview_content(rs.getString("review_content"));
				review.setReview_date(rs.getString("review_date"));
				review.setReview_star(rs.getString("review_star"));
				review.setReview_readcount(rs.getInt("review_readcount"));
				review.setReview_move_name(rs.getString("review_move_name"));
				review_list.add(review);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<ReviewDAO> getReviewList에서 에러");
		} finally {
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
		return review_list;
	}

	// 영화이름에 따른 리뷰 개수를 가져옵니다.
	public int getReviewCount(String review_move_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String review_count_sql = "select count(*) from review where review_move_name=?";

		int review_count = 0;
		try {
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(review_count_sql);
			pstmt.setString(1, review_move_name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				review_count = rs.getInt(1);
			}
		} catch (Exception e) {
			review_count = -1;
			e.printStackTrace();
			System.out.println("<ReviewDAO> getReviewCount 에서 에러");
		} finally {
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

		return review_count;
	}

	// 페이징 처리된 리뷰 리스트를 가져옵니다.
	public ArrayList<Review> getReviewList(String review_move_name, int startList, int endList) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<Review> review_list = null;

		String select_all_sql = "select * " + " from" + "	(select * " + "	from "
				+ "		(select rownum rnum, reviewID.* " + "		from (select * " + "				from review "
				+ "			 	where review_move_name=? order by review_date desc) reviewID) " + "	where rnum <= ?) "
				+ " where rnum >=? ";
		try {
			System.out.println(review_move_name);
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(select_all_sql);
			pstmt.setString(1, review_move_name);
			pstmt.setInt(2, endList);
			pstmt.setInt(3, startList);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				if (review_list == null)
					review_list = new ArrayList<Review>();

				Review review = new Review();
				review.setReview_number(rs.getInt("review_number"));
				review.setReview_id(rs.getString("review_id"));
				review.setReview_title(rs.getString("review_title"));
				review.setReview_content(rs.getString("review_content"));
				review.setReview_date(rs.getString("review_date"));
				review.setReview_star(rs.getString("review_star"));
				review.setReview_readcount(rs.getInt("review_readcount"));
				review.setReview_move_name(rs.getString("review_move_name"));
				review_list.add(review);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<ReviewDAO> getReviewList(page, limit) 에서 에러");
		} finally {
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
		return review_list;
	}

	// 리뷰를 추가합니다.
	public int setReviewItem(Review review) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String insert_sql = "insert into review(review_number,review_id, review_title, review_content, review_star, review_move_name) values(create_review_number.nextval, ?,?,?,?,?)";

		try {
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(insert_sql);
			pstmt.setString(1, review.getReview_id());
			pstmt.setString(2, review.getReview_title());
			pstmt.setString(3, review.getReview_content());
			pstmt.setString(4, review.getReview_star());
			pstmt.setString(5, review.getReview_move_name());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<ReviewDAO> setReviewItem 에서 에러");
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// 아이디와 영화에 매칭되는 리뷰가 있는지 확인합니다.
	public boolean isExistReview(String id, String review_move_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		String insert_sql = "select * from review where review_id=? and review_move_name=?";

		try {
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(insert_sql);
			pstmt.setString(1, id);
			pstmt.setString(2, review_move_name);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<ReviewDAO> isExistReview 에서 에러");
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	// 아이디와 리뷰에 대한 리뷰를 하나 가져옵니다.
	public Review getReview(int review_number) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Review review = null;
		String sql = "select * from review where review_number=?";
		try {
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, review_number);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				review = new Review();
				review.setReview_id(rs.getString("review_id"));
				review.setReview_title(rs.getString("review_title"));
				review.setReview_content(rs.getString("review_content"));
				review.setReview_date(rs.getString("review_date"));
				review.setReview_star(rs.getString("review_star"));
				review.setReview_readcount(rs.getInt("review_readcount"));
				review.setReview_move_name(rs.getString("review_move_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<ReviewDAO> getReview 에서 에러");
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return review;
	}

	public int deleteReview(String review_id, String review_move_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String delete_sql = "delete from review where review_id=? and review_move_name=?";

		try {
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(delete_sql);
			pstmt.setString(1, review_id);
			pstmt.setString(2, review_move_name);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<ReviewDAO> deleteReview 에서 에러");
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public int deleteReview(int review_number) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String delete_sql = "delete from review where review_number=?";

		try {
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(delete_sql);
			pstmt.setInt(1, review_number);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<ReviewDAO> deleteReview 에서 에러");
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public int modifiyReview(Review review) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update review set review_title=?, review_content=?, review_star=? where review_id =? and review_move_name=?";
		try {
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, review.getReview_title());
			pstmt.setString(2, review.getReview_content());
			pstmt.setString(3, review.getReview_star());
			pstmt.setString(4, review.getReview_id());
			pstmt.setString(5, review.getReview_move_name());
			System.out.println("<ReviewDAO> modifiyReview -> " + review.getReview_move_name());
			System.out.println("<ReviewDAO> modifiyReview -> " + review.getReview_id());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<ReviewDAO> modifiyReview 에서 에러");
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// 별점 구하기
	public double getStarCount(String title) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		int star = 0;
		String sql = "select review_star from review where review_move_name=?";
		try {
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result++;
				star += Integer.parseInt(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<ReviewDAO> getStarCount 에서 에러");
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ((double) star / result);
	}

	// 조회수 구하기
	public void setReadCount(int review_number) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update review set review_readcount = review_readcount+1 where review_number=?";
		try {
			conn = ConnectionPool.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, review_number);
			result = pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("<ReviewDAO> getReview 에서 에러");
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
