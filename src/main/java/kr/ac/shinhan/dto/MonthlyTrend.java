package kr.ac.shinhan.dto;

public record MonthlyTrend(String yearMonth, long income, long expense) {

	public long balance() {
		return income - expense;
	}

	// JSP EL(${item.xxx})이 record 접근자를 프로퍼티로 인식하지 못해 getter 로 노출
	public String getYearMonth() {
		return yearMonth;
	}

	public long getIncome() {
		return income;
	}

	public long getExpense() {
		return expense;
	}

	public long getBalance() {
		return balance();
	}
}
