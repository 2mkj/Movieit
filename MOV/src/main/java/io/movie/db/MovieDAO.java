package io.movie.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class MovieDAO {
	private DataSource ds;

	// 생성자에서 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public MovieDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
		}
	}

	// 영화 정보 추가
	public int insert(Movie m) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = ds.getConnection();
			System.out.println("getConnection : insert()");

			pstmt = con.prepareStatement("insert into movie values(movie_seq.nextval,?,?,?,?,?,?)");
			pstmt.setString(1, m.getTitle());
			pstmt.setString(2, m.getDirector());
			pstmt.setString(3, m.getActor());
			pstmt.setString(4, m.getMdate());
			pstmt.setString(5, m.getContent());
			pstmt.setString(6, m.getPoster());
			result = pstmt.executeUpdate();// 삽입 성공시 result는 1

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

	public Movie movie_info(String title, String mdate) {
		Movie m = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from movie where title=? and mdate=?");
			pstmt.setString(1, title);
			pstmt.setString(2, mdate);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				m = new Movie();
				m.setMnum(rs.getInt("mnum"));
				m.setTitle(rs.getString("title"));
				m.setDirector(rs.getString("director"));
				m.setActor(rs.getString("actor"));
				m.setMdate(rs.getString("mdate"));
				m.setContent(rs.getString("content"));
				m.setPoster(rs.getString("poster"));
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
		return m;
	}

	public int update(Movie m, String title) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = ds.getConnection();

			if (m.getPoster() != null) { // 프로필 사진 수정 안함
				pstmt = con.prepareStatement(
						"update movie set title=?,director=?,actor=?,mdate=?,content=?,poster=? where title=?");
				pstmt.setString(1, m.getTitle());
				pstmt.setString(2, m.getDirector());
				pstmt.setString(3, m.getActor());
				pstmt.setString(4, m.getMdate());
				pstmt.setString(5, m.getContent());
				pstmt.setString(6, m.getPoster());
				pstmt.setString(7, title);
				result = pstmt.executeUpdate();
			} else {
				pstmt = con.prepareStatement(
						"update movie set title=?,director=?,actor=?,mdate=?,content=? where title=?");
				pstmt.setString(1, m.getTitle());
				pstmt.setString(2, m.getDirector());
				pstmt.setString(3, m.getActor());
				pstmt.setString(4, m.getMdate());
				pstmt.setString(5, m.getContent());
				pstmt.setString(6, title);
				result = pstmt.executeUpdate();
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
		return result;
	}

	public Movie movie_info(String title) {
		Movie m = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from movie where title=?");
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				m = new Movie();
				m.setMnum(rs.getInt("mnum"));
				m.setTitle(rs.getString("title"));
				m.setDirector(rs.getString("director"));
				m.setActor(rs.getString("actor"));
				m.setMdate(rs.getString("mdate"));
				m.setContent(rs.getString("content"));
				m.setPoster(rs.getString("poster"));
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
		return m;
	}

	public Movie movie_search(String query) {
		Movie m = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from movie where title like ?");
			pstmt.setString(1, "%" + query + "%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				m = new Movie();
				m.setMnum(rs.getInt("mnum"));
				m.setTitle(rs.getString("title"));
				m.setDirector(rs.getString("director"));
				m.setActor(rs.getString("actor"));
				m.setMdate(rs.getString("mdate"));
				m.setContent(rs.getString("content"));
				m.setPoster(rs.getString("poster"));
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
		return m;
	}

	public int insert_2(Movie m) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = ds.getConnection();
			System.out.println("getConnection : insert()");

			pstmt = con.prepareStatement("insert into movie(mnum,title,mdate,poster,director,actor) values(movie_seq.nextval,?,?,?,?,?)");
			pstmt.setString(1, m.getTitle());
			pstmt.setString(2, m.getMdate());
			pstmt.setString(3, m.getPoster());
			pstmt.setString(4, m.getDirector());
			pstmt.setString(5, m.getActor());
			result = pstmt.executeUpdate();

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

	public void moviedel() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();

			String sql = "delete from movie where title='null'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
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
	}

	public void seqmin() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();

			String sql = "update movie set mnum=mnum-1 where title='NULL'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
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
	}

	public int ismovie(String title, String mdate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from movie where title=? and mdate=?");
			pstmt.setString(1, title);
			pstmt.setString(2, mdate);
			rs = pstmt.executeQuery();
				if(rs.next()){	
					result = 0;
				}else{
					result = 1;
				}
			System.out.println("영화 중복체크결과 : "+result);
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
		return result;
	}
}