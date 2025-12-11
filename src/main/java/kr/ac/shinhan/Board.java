package kr.ac.shinhan;

import lombok.Data;

@Data // getter and Setter Support
public class Board {
	private int id;
	private String title;
	private String writer;
	private String content;
	private String createdDate;
	private int viewCount;
}
/*
CREATE TABLE board (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(200) NOT NULL,
	writer VARCHAR(50) NOT NULL,
	content TEXT NOT NULL,
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
	view_count INT DEFAULT 0
);
*/




