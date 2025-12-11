<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
	<title>내역 추가</title>
	<style>
		body { background: #f8f9fa; }
		.main-card {
			background: white;
			border-radius: 20px;
			box-shadow: 0 5px 20px rgba(0,0,0,0.1);
			padding: 40px;
		}
		.type-btn {
			padding: 15px 30px;
			border-radius: 10px;
			font-size: 1.1rem;
			transition: all 0.3s;
		}
		.type-btn.income.active {
			background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
			border-color: #11998e;
			color: white;
		}
		.type-btn.expense.active {
			background: linear-gradient(135deg, #eb3349 0%, #f45c43 100%);
			border-color: #eb3349;
			color: white;
		}
		.category-btn {
			margin: 5px;
			border-radius: 20px;
		}
		.category-btn.active {
			background-color: #667eea;
			border-color: #667eea;
			color: white;
		}
	</style>
</head>
<body>
	<div class="container mt-5 mb-5">
		<div class="row justify-content-center">
			<div class="col-md-8">
				<div class="main-card">
					<h3 class="mb-4"><i class="bi bi-plus-circle"></i> 내역 추가</h3>
					<hr>
					
					<form method="post" action="/account/write" id="accountForm">
						<!-- 수입/지출 선택 -->
						<div class="mb-4">
							<label class="form-label"><strong>구분</strong></label>
							<div class="d-flex gap-3">
								<button type="button" class="btn btn-outline-success type-btn income" onclick="selectType('income')">
									<i class="bi bi-arrow-down-circle"></i> 수입
								</button>
								<button type="button" class="btn btn-outline-danger type-btn expense active" onclick="selectType('expense')">
									<i class="bi bi-arrow-up-circle"></i> 지출
								</button>
							</div>
							<input type="hidden" name="type" id="type" value="expense" required>
						</div>
						
						<!-- 카테고리 선택 -->
						<div class="mb-4">
							<label class="form-label"><strong>카테고리</strong></label>
							<div id="incomeCategories" style="display: none;">
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('급여')">💰 급여</button>
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('용돈')">💵 용돈</button>
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('이자')">🏦 이자</button>
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('부수입')">📈 부수입</button>
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('기타수입')">➕ 기타</button>
							</div>
							<div id="expenseCategories">
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('식비')">🍚 식비</button>
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('교통비')">🚌 교통비</button>
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('쇼핑')">🛒 쇼핑</button>
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('문화생활')">🎬 문화생활</button>
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('통신비')">📱 통신비</button>
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('의료비')">🏥 의료비</button>
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('교육비')">📚 교육비</button>
								<button type="button" class="btn btn-outline-secondary category-btn" onclick="selectCategory('기타지출')">➖ 기타</button>
							</div>
							<input type="hidden" name="category" id="category" required>
						</div>
						
						<!-- 금액 -->
						<div class="mb-4">
							<label class="form-label"><strong>금액</strong></label>
							<div class="input-group">
								<input type="number" name="amount" class="form-control form-control-lg" placeholder="0" required min="1">
								<span class="input-group-text">원</span>
							</div>
						</div>
						
						<!-- 날짜 -->
						<div class="mb-4">
							<label class="form-label"><strong>날짜</strong></label>
							<input type="date" name="accountDate" class="form-control form-control-lg" value="${today}" required>
						</div>
						
						<!-- 설명 -->
						<div class="mb-4">
							<label class="form-label"><strong>설명 (선택)</strong></label>
							<input type="text" name="description" class="form-control" placeholder="내용을 입력하세요">
						</div>
						
						<hr>
						<div class="d-flex justify-content-between">
							<a href="/account" class="btn btn-secondary btn-lg">
								<i class="bi bi-arrow-left"></i> 취소
							</a>
							<button type="submit" class="btn btn-primary btn-lg">
								<i class="bi bi-check-lg"></i> 등록
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<script>
		function selectType(type) {
			document.getElementById('type').value = type;
			
			// 버튼 스타일 변경
			document.querySelectorAll('.type-btn').forEach(btn => btn.classList.remove('active'));
			document.querySelector('.type-btn.' + type).classList.add('active');
			
			// 카테고리 표시 변경
			document.getElementById('incomeCategories').style.display = type === 'income' ? 'block' : 'none';
			document.getElementById('expenseCategories').style.display = type === 'expense' ? 'block' : 'none';
			
			// 카테고리 선택 초기화
			document.getElementById('category').value = '';
			document.querySelectorAll('.category-btn').forEach(btn => btn.classList.remove('active'));
		}
		
		function selectCategory(category) {
			document.getElementById('category').value = category;
			
			// 버튼 스타일 변경
			document.querySelectorAll('.category-btn').forEach(btn => btn.classList.remove('active'));
			event.target.classList.add('active');
		}
		
		// 폼 제출 전 검증
		document.getElementById('accountForm').addEventListener('submit', function(e) {
			if (!document.getElementById('category').value) {
				alert('카테고리를 선택해주세요.');
				e.preventDefault();
			}
		});
	</script>
</body>
</html>






