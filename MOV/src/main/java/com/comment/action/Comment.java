package com.comment.action;

public class Comment {
	private int comment_number;
	private String comment_id;
	private String comment_content;
	private String comment_password;
	private String comment_review_name;
	private String comment_date;

	public int getComment_number() {
		return comment_number;
	}

	public void setComment_number(int comment_number) {
		this.comment_number = comment_number;
	}

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public String getComment_password() {
		return comment_password;
	}

	public void setComment_password(String comment_password) {
		this.comment_password = comment_password;
	}

	public String getComment_review_name() {
		return comment_review_name;
	}

	public void setComment_review_name(String comment_review_name) {
		this.comment_review_name = comment_review_name;
	}

	public String getComment_date() {
		return comment_date;
	}

	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}

}
