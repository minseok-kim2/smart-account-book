package kr.ac.shinhan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.ac.shinhan.dto.CategorySum;
import kr.ac.shinhan.dto.MonthlyTrend;

@Repository
public class AccountDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 전체 목록 조회
	public List<Account> getAll() {
		// account_date AS accountDate : BeanPropertyRowMapper 는 스네이크_케이스 컬럼을
		// 카멜케이스 프로퍼티로 자동 매핑하지 못한다. 별칭이 없으면 예외 없이 해당 필드만 null 로 채워진다.
		String sql = "SELECT id, type, category, amount, description, " +
				"account_date AS accountDate, created_date AS createdDate " +
				"FROM account ORDER BY account_date DESC, id DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class));
	}

	// 월별 목록 조회
	public List<Account> getByMonth(String yearMonth) {
		// ? 파라미터 바인딩 : 문자열 결합 대신 PreparedStatement 바인딩으로 SQL 인젝션 차단
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

	// 수정 - "0건이면 오류"라는 판단은 DAO 가 아니라 Service 가 하도록 영향 행 수만 반환
	public int updateAccount(Account a) {
		String sql = "UPDATE account SET type = ?, category = ?, amount = ?, description = ?, account_date = ? WHERE id = ?";
		return jdbcTemplate.update(sql, a.getType(), a.getCategory(), a.getAmount(), a.getDescription(), a.getAccountDate(), a.getId());
	}

	// 삭제 - "0건이면 오류"라는 판단은 DAO 가 아니라 Service 가 하도록 영향 행 수만 반환
	public int deleteAccount(int id) {
		String sql = "DELETE FROM account WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	// 월별 총 수입
	public int getTotalIncomeByMonth(String yearMonth) {
		// COALESCE(SUM(amount), 0) : 해당 월에 내역이 없으면 SUM 은 NULL 을 반환한다.
		// COALESCE 로 0 처리해두지 않으면 null 이 그대로 애플리케이션까지 넘어온다.
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
	public List<CategorySum> getExpenseByCategory(String yearMonth) {
		// SUM/GROUP BY 로 DB 에서 집계 : 전체 행을 애플리케이션으로 가져와 Java 에서
		// 순회 집계하는 것보다 네트워크 전송량과 메모리 사용이 적다.
		String sql = "SELECT category, SUM(amount) AS total FROM account " +
				"WHERE type = 'expense' AND DATE_FORMAT(account_date, '%Y-%m') = ? " +
				"GROUP BY category ORDER BY total DESC";
		return jdbcTemplate.query(sql,
				(rs, rowNum) -> new CategorySum(rs.getString("category"), rs.getLong("total")),
				yearMonth);
	}

	// 월별 카테고리별 수입 통계
	public List<CategorySum> getIncomeByCategory(String yearMonth) {
		String sql = "SELECT category, SUM(amount) AS total FROM account " +
				"WHERE type = 'income' AND DATE_FORMAT(account_date, '%Y-%m') = ? " +
				"GROUP BY category ORDER BY total DESC";
		return jdbcTemplate.query(sql,
				(rs, rowNum) -> new CategorySum(rs.getString("category"), rs.getLong("total")),
				yearMonth);
	}

	// 최근 6개월 월별 통계
	public List<MonthlyTrend> getMonthlyStats() {
		// CASE WHEN 으로 DB 에서 수입/지출을 분리 집계 (Java 순회 대비 전송량·메모리 이점)
		String sql = "SELECT DATE_FORMAT(account_date, '%Y-%m') AS yearMonth, " +
				"SUM(CASE WHEN type = 'income' THEN amount ELSE 0 END) AS income, " +
				"SUM(CASE WHEN type = 'expense' THEN amount ELSE 0 END) AS expense " +
				"FROM account " +
				"WHERE account_date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) " +
				"GROUP BY DATE_FORMAT(account_date, '%Y-%m') " +
				"ORDER BY yearMonth";
		return jdbcTemplate.query(sql,
				(rs, rowNum) -> new MonthlyTrend(rs.getString("yearMonth"), rs.getLong("income"), rs.getLong("expense")));
	}

	// 사용 가능한 월 목록 조회
	public List<String> getAvailableMonths() {
		String sql = "SELECT DISTINCT DATE_FORMAT(account_date, '%Y-%m') AS month " +
				"FROM account ORDER BY month DESC";
		return jdbcTemplate.queryForList(sql, String.class);
	}
}
