package kr.ac.shinhan;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 전체 목록 조회
	public List<Account> getAll() {
		String sql = "SELECT id, type, category, amount, description, " +
				"account_date AS accountDate, created_date AS createdDate " +
				"FROM account ORDER BY account_date DESC, id DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class));
	}
	
	// 월별 목록 조회
	public List<Account> getByMonth(String yearMonth) {
		String sql = "SELECT id, type, category, amount, description, " +
				"account_date AS accountDate, created_date AS createdDate " +
				"FROM account WHERE DATE_FORMAT(account_date, '%Y-%m') = ? " +
				"ORDER BY account_date DESC, id DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class), yearMonth);
	}
	
	// 상세 조회
	public Account getAccount(int id) {
		String sql = "SELECT id, type, category, amount, description, " +
				"account_date AS accountDate, created_date AS createdDate " +
				"FROM account WHERE id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Account.class), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	// 등록
	public void addAccount(Account a) {
		String sql = "INSERT INTO account(type, category, amount, description, account_date) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, a.getType(), a.getCategory(), a.getAmount(), a.getDescription(), a.getAccountDate());
	}
	
	// 수정
	public void updateAccount(Account a) {
		String sql = "UPDATE account SET type = ?, category = ?, amount = ?, description = ?, account_date = ? WHERE id = ?";
		jdbcTemplate.update(sql, a.getType(), a.getCategory(), a.getAmount(), a.getDescription(), a.getAccountDate(), a.getId());
	}
	
	// 삭제
	public void deleteAccount(int id) {
		String sql = "DELETE FROM account WHERE id = ?";
		int result = jdbcTemplate.update(sql, id);
		if (result == 0) {
			throw new RuntimeException("삭제 실패: 해당 내역이 존재하지 않습니다.");
		}
	}
	
	// 월별 총 수입
	public int getTotalIncomeByMonth(String yearMonth) {
		String sql = "SELECT COALESCE(SUM(amount), 0) FROM account " +
				"WHERE type = 'income' AND DATE_FORMAT(account_date, '%Y-%m') = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, yearMonth);
	}
	
	// 월별 총 지출
	public int getTotalExpenseByMonth(String yearMonth) {
		String sql = "SELECT COALESCE(SUM(amount), 0) FROM account " +
				"WHERE type = 'expense' AND DATE_FORMAT(account_date, '%Y-%m') = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, yearMonth);
	}
	
	// 월별 카테고리별 지출 통계
	public List<Map<String, Object>> getExpenseByCategory(String yearMonth) {
		String sql = "SELECT category, SUM(amount) AS total FROM account " +
				"WHERE type = 'expense' AND DATE_FORMAT(account_date, '%Y-%m') = ? " +
				"GROUP BY category ORDER BY total DESC";
		return jdbcTemplate.queryForList(sql, yearMonth);
	}
	
	// 월별 카테고리별 수입 통계
	public List<Map<String, Object>> getIncomeByCategory(String yearMonth) {
		String sql = "SELECT category, SUM(amount) AS total FROM account " +
				"WHERE type = 'income' AND DATE_FORMAT(account_date, '%Y-%m') = ? " +
				"GROUP BY category ORDER BY total DESC";
		return jdbcTemplate.queryForList(sql, yearMonth);
	}
	
	// 최근 6개월 월별 통계
	public List<Map<String, Object>> getMonthlyStats() {
		String sql = "SELECT DATE_FORMAT(account_date, '%Y-%m') AS month, " +
				"SUM(CASE WHEN type = 'income' THEN amount ELSE 0 END) AS income, " +
				"SUM(CASE WHEN type = 'expense' THEN amount ELSE 0 END) AS expense " +
				"FROM account " +
				"WHERE account_date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) " +
				"GROUP BY DATE_FORMAT(account_date, '%Y-%m') " +
				"ORDER BY month";
		return jdbcTemplate.queryForList(sql);
	}
	
	// 사용 가능한 월 목록 조회
	public List<String> getAvailableMonths() {
		String sql = "SELECT DISTINCT DATE_FORMAT(account_date, '%Y-%m') AS month " +
				"FROM account ORDER BY month DESC";
		return jdbcTemplate.queryForList(sql, String.class);
	}
}






