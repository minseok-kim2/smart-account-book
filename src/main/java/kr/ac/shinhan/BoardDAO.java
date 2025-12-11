package kr.ac.shinhan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 게시글 목록 조회
	public List<Board> getAll() {
		String sql = "SELECT id, title, writer, content, created_date AS createdDate, view_count AS viewCount FROM board ORDER BY id DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Board.class));
	}
	
	// 게시글 상세 조회
	public Board getBoard(int id) {
		String sql = "SELECT id, title, writer, content, created_date AS createdDate, view_count AS viewCount FROM board WHERE id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Board.class), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	// 게시글 등록
	public void addBoard(Board b) {
		String sql = "INSERT INTO board(title, writer, content) VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, b.getTitle(), b.getWriter(), b.getContent());
	}
	
	// 게시글 수정
	public void updateBoard(Board b) {
		String sql = "UPDATE board SET title = ?, content = ? WHERE id = ?";
		jdbcTemplate.update(sql, b.getTitle(), b.getContent(), b.getId());
	}
	
	// 게시글 삭제
	public void deleteBoard(int id) {
		String sql = "DELETE FROM board WHERE id = ?";
		int result = jdbcTemplate.update(sql, id);
		if (result == 0) {
			throw new RuntimeException("삭제 실패: 해당 게시글이 존재하지 않습니다.");
		}
	}
	
	// 조회수 증가
	public void increaseViewCount(int id) {
		String sql = "UPDATE board SET view_count = view_count + 1 WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}




