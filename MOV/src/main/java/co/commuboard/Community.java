package co.commuboard;

import java.util.Date;

public class Community {
	private int    board_num; 		//글번호
	private String board_name; 		//글 작성자
	private String board_user_name; //글 작성자 닉네임
	private String board_subject; 	//글 제목
	private String board_content; 	//글 내용
	private int    board_readcount; //글의 조회수
	private String board_date;
	private int    cnt;
	
	
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}
	public String getBoard_subject() {
		return board_subject;
	}
	public void setBoard_subject(String board_subject) {
		this.board_subject = board_subject;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public int getBoard_readcount() {
		return board_readcount;
	}
	public void setBoard_readcount(int board_readcount) {
		this.board_readcount = board_readcount;
	}
	public String getBoard_date() {
		return board_date;
	}
	public void setBoard_date(String board_date) {
		Date date = new Date();
		board_date = String.format("%1$tY.%1$tm.%1$td", date);
		this.board_date = board_date.substring(2,10); //년-월-일
		
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getBoard_user_name() {
		return board_user_name;
	}
	public void setBoard_user_name(String board_user_name) {
		this.board_user_name = board_user_name;
	}
	
	
}
