<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
	<title>가계부</title>
	<style>
		body { background: #f8f9fa; }
		.summary-card {
			border-radius: 15px;
			padding: 20px;
			color: white;
			text-align: center;
		}
		.income-card { background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); }
		.expense-card { background: linear-gradient(135deg, #eb3349 0%, #f45c43 100%); }
		.balance-card { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
		.summary-card h3 { font-size: 1.8rem; margin-bottom: 5px; }
		.summary-card p { margin: 0; opacity: 0.9; }
		.income-row { background-color: rgba(17, 153, 142, 0.1); }
		.expense-row { background-color: rgba(235, 51, 73, 0.1); }
		.amount-income { color: #11998e; font-weight: bold; }
		.amount-expense { color: #eb3349; font-weight: bold; }
		.main-card {
			background: white;
			border-radius: 20px;
			box-shadow: 0 5px 20px rgba(0,0,0,0.1);
			padding: 30px;
		}
	</style>
</head>
<body>
	<div class="container mt-4 mb-5">
		<!-- 헤더 -->
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2><i class="bi bi-wallet2"></i> 가계부</h2>
			<div>
				<a href="/account/stats?month=${currentMonth}" class="btn btn-outline-primary me-2">
					<i class="bi bi-bar-chart"></i> 통계
				</a>
				<a href="/mainStart" class="btn btn-outline-secondary">
					<i class="bi bi-house"></i> 메인
				</a>
			</div>
		</div>
		
		<!-- 월 선택 -->
		<div class="mb-4">
			<form method="get" action="/account" class="d-flex align-items-center">
				<label class="me-2"><strong>월 선택:</strong></label>
				<input type="month" name="month" value="${currentMonth}" class="form-control" style="width: 200px;" onchange="this.form.submit()">
			</form>
		</div>
		
		<!-- 에러 메시지 -->
		<c:if test="${error != null}">
			<div class="alert alert-danger alert-dismissible fade show">
				<i class="bi bi-exclamation-triangle"></i> ${error}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>
		
		<!-- 요약 카드 -->
		<div class="row mb-4">
			<div class="col-md-4 mb-3">
				<div class="summary-card income-card">
					<p><i class="bi bi-arrow-down-circle"></i> 수입</p>
					<h3><fmt:formatNumber value="${totalIncome}" pattern="#,###"/>원</h3>
				</div>
			</div>
			<div class="col-md-4 mb-3">
				<div class="summary-card expense-card">
					<p><i class="bi bi-arrow-up-circle"></i> 지출</p>
					<h3><fmt:formatNumber value="${totalExpense}" pattern="#,###"/>원</h3>
				</div>
			</div>
			<div class="col-md-4 mb-3">
				<div class="summary-card balance-card">
					<p><i class="bi bi-piggy-bank"></i> 잔액</p>
					<h3><fmt:formatNumber value="${balance}" pattern="#,###"/>원</h3>
				</div>
			</div>
		</div>
		
		<!-- 내역 테이블 -->
		<div class="main-card">
			<div class="d-flex justify-content-between align-items-center mb-3">
				<h5><i class="bi bi-list-ul"></i> ${currentMonth} 내역</h5>
				<a href="/account/write" class="btn btn-primary">
					<i class="bi bi-plus-lg"></i> 내역 추가
				</a>
			</div>
			<table class="table table-hover">
				<thead class="table-dark">
					<tr>
						<th style="width: 12%">날짜</th>
						<th style="width: 10%">구분</th>
						<th style="width: 15%">카테고리</th>
						<th style="width: 18%">금액</th>
						<th style="width: 30%">설명</th>
						<th style="width: 15%">관리</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${accountList}">
						<tr class="${item.type == 'income' ? 'income-row' : 'expense-row'}">
							<td>${item.accountDate}</td>
							<td>
								<c:choose>
									<c:when test="${item.type == 'income'}">
										<span class="badge bg-success">수입</span>
									</c:when>
									<c:otherwise>
										<span class="badge bg-danger">지출</span>
									</c:otherwise>
								</c:choose>
							</td>
							<td>${item.category}</td>
							<td class="${item.type == 'income' ? 'amount-income' : 'amount-expense'}">
								<c:choose>
									<c:when test="${item.type == 'income'}">+</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
								<fmt:formatNumber value="${item.amount}" pattern="#,###"/>원
							</td>
							<td>${item.description}</td>
							<td>
								<a href="/account/edit/${item.id}" class="btn btn-sm btn-outline-primary">
									<i class="bi bi-pencil"></i>
								</a>
								<button class="btn btn-sm btn-outline-danger" onclick="confirmDelete(${item.id})">
									<i class="bi bi-trash"></i>
								</button>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty accountList}">
						<tr>
							<td colspan="6" class="text-center text-muted py-4">
								<i class="bi bi-inbox" style="font-size: 2rem;"></i>
								<p class="mt-2 mb-0">등록된 내역이 없습니다.</p>
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	
	<script>
		function confirmDelete(id) {
			if (confirm('정말 삭제하시겠습니까?')) {
				location.href = '/account/delete/' + id;
			}
		}
	</script>
</body>
</html>






