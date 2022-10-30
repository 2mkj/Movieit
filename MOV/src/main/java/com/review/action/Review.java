package com.review.action;

public class Review {
	private int review_number;
	private String review_id;
	private String review_title;
	private String review_content;
	private String review_date;
	private String review_star;
	private int review_readcount;
	private String review_move_name;

	
	
	public int getReview_number() {
		return review_number;
	}

	public void setReview_number(int review_number) {
		this.review_number = review_number;
	}

	public String getReview_date() {
		return review_date;
	}

	public void setReview_date(String review_date) {
		this.review_date = review_date;
	}

	public String getReview_id() {
		return review_id;
	}

	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}

	public String getReview_title() {
		return review_title;
	}

	public void setReview_title(String review_title) {
		this.review_title = review_title;
	}

	public String getReview_content() {
		return review_content;
	}

	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}

	public String getReview_star() {
		return review_star;
	}

	public void setReview_star(String review_star) {
		this.review_star = review_star;
	}

	public int getReview_readcount() {
		return review_readcount;
	}

	public void setReview_readcount(int review_readcount) {
		this.review_readcount = review_readcount;
	}

	public String getReview_move_name() {
		return review_move_name;
	}

	public void setReview_move_name(String review_move_name) {
		this.review_move_name = review_move_name;
	}

}
