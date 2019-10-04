package com.dev.bss.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "suggestion")
public class Feedback {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sugg_id")
	private int suggestionId;
	@Column(name = "user_id")
	private int userId;
	private String feedback;
	public int getSuggestionId() {
		return suggestionId;
	}
	public void setSuggestionId(int suggestionId) {
		this.suggestionId = suggestionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	@Override
	public String toString() {
		return "Suggestions [suggestionId=" + suggestionId + ", userId=" + userId + ", feedback=" + feedback + "]";
	}
	

}
