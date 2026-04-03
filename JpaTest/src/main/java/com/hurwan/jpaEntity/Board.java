package com.hurwan.jpaEntity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="BOARD")
public class Board {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CONTENT_ID")
	private Long contentId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="WRITER_ID")
	private User writer;
	
	@Column(name="CONTENT", length=2000)
	private String content;
	
	@Column(name="CONTENT_CREATED_AT", insertable=false, updatable=false)
	private LocalDateTime contentCreatedAt;
	
	protected Board() {
		
	}
	
	public Board(User writer, String content) {
		this.writer = writer;
		this.content = content;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getContentId() {
		return contentId;
	}

	public LocalDateTime getContentCreatedAt() {
		return contentCreatedAt;
	}
}
