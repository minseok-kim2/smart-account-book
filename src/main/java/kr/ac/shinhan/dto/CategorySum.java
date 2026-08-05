package kr.ac.shinhan.dto;

public record CategorySum(String category, long total) {

	// JSP EL(${item.total})이 record 접근자 total() 을 프로퍼티로 인식하지 못해 getter 로 노출
	public String getCategory() {
		return category;
	}

	public long getTotal() {
		return total;
	}
}
