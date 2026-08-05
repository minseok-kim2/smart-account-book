package kr.ac.shinhan.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.ac.shinhan.Account;
import kr.ac.shinhan.AccountDAO;
import kr.ac.shinhan.dto.CategorySum;
import kr.ac.shinhan.dto.MonthlySummary;
import kr.ac.shinhan.dto.MonthlyTrend;

/*
 * 이 계층을 만든 이유:
 * 월 기본값 결정과 잔액 계산이 목록 화면과 통계 화면에 중복되어 있었는데,
 * 둘 다 화면을 어떻게 그릴지가 아니라 "월이 비어 있으면 이번 달로 본다",
 * "잔액 = 수입 - 지출" 같은 도메인 규칙이다. 이런 규칙이 Controller 에 있으면
 * 화면마다 중복되고, HTTP 요청 없이는 테스트할 수도 없다.
 */
@Service
public class AccountService {
	private static final DateTimeFormatter YEAR_MONTH = DateTimeFormatter.ofPattern("yyyy-MM");

	private final AccountDAO dao;

	public AccountService(AccountDAO dao) {
		this.dao = dao;
	}

	// 월이 비어 있으면 현재 월(yyyy-MM)을 기본값으로 사용
	public String resolveMonth(String month) {
		if (month == null || month.isEmpty()) {
			return LocalDate.now().format(YEAR_MONTH);
		}
		return month;
	}

	public MonthlySummary getMonthlySummary(String month) {
		String resolved = resolveMonth(month);
		long totalIncome = dao.getTotalIncomeByMonth(resolved);
		long totalExpense = dao.getTotalExpenseByMonth(resolved);
		return new MonthlySummary(resolved, totalIncome, totalExpense);
	}

	public List<Account> getByMonth(String month) {
		return dao.getByMonth(resolveMonth(month));
	}

	public Account getAccount(int id) {
		return dao.getAccount(id);
	}

	public void addAccount(Account account) {
		dao.addAccount(account);
	}

	public void modifyAccount(Account account) {
		int affected = dao.updateAccount(account);
		if (affected == 0) {
			throw new IllegalArgumentException("수정 실패: 해당 내역이 존재하지 않습니다. id=" + account.getId());
		}
	}

	public void removeAccount(int id) {
		int affected = dao.deleteAccount(id);
		if (affected == 0) {
			throw new IllegalArgumentException("삭제 실패: 해당 내역이 존재하지 않습니다. id=" + id);
		}
	}

	public List<CategorySum> getExpenseByCategory(String month) {
		return dao.getExpenseByCategory(resolveMonth(month));
	}

	public List<CategorySum> getIncomeByCategory(String month) {
		return dao.getIncomeByCategory(resolveMonth(month));
	}

	public List<MonthlyTrend> getMonthlyStats() {
		return dao.getMonthlyStats();
	}

	public List<String> getAvailableMonths() {
		return dao.getAvailableMonths();
	}
}
