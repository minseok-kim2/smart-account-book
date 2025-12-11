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
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<title>가계부 통계</title>
	<style>
		body { background: #f8f9fa; }
		.main-card {
			background: white;
			border-radius: 20px;
			box-shadow: 0 5px 20px rgba(0,0,0,0.1);
			padding: 30px;
			margin-bottom: 20px;
		}
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
		.chart-container { position: relative; height: 300px; }
		.category-item {
			display: flex;
			justify-content: space-between;
			padding: 10px 0;
			border-bottom: 1px solid #eee;
		}
		.category-item:last-child { border-bottom: none; }
		.progress { height: 25px; border-radius: 10px; }
	</style>
</head>
<body>
	<div class="container mt-4 mb-5">
		<!-- 헤더 -->
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2><i class="bi bi-bar-chart-line"></i> 가계부 통계</h2>
			<div>
				<a href="/account" class="btn btn-outline-primary me-2">
					<i class="bi bi-list-ul"></i> 내역
				</a>
				<a href="/mainStart" class="btn btn-outline-secondary">
					<i class="bi bi-house"></i> 메인
				</a>
			</div>
		</div>
		
		<!-- 월 선택 -->
		<div class="mb-4">
			<form method="get" action="/account/stats" class="d-flex align-items-center">
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
					<p><i class="bi bi-arrow-down-circle"></i> 총 수입</p>
					<h3><fmt:formatNumber value="${totalIncome}" pattern="#,###"/>원</h3>
				</div>
			</div>
			<div class="col-md-4 mb-3">
				<div class="summary-card expense-card">
					<p><i class="bi bi-arrow-up-circle"></i> 총 지출</p>
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
		
		<div class="row">
			<!-- 카테고리별 지출 차트 -->
			<div class="col-md-6 mb-4">
				<div class="main-card">
					<h5 class="mb-4"><i class="bi bi-pie-chart"></i> 카테고리별 지출</h5>
					<c:choose>
						<c:when test="${not empty expenseByCategory}">
							<div class="chart-container">
								<canvas id="expenseChart"></canvas>
							</div>
						</c:when>
						<c:otherwise>
							<p class="text-center text-muted py-5">지출 내역이 없습니다.</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			
			<!-- 카테고리별 지출 목록 -->
			<div class="col-md-6 mb-4">
				<div class="main-card">
					<h5 class="mb-4"><i class="bi bi-list-check"></i> 지출 상세</h5>
					<c:choose>
						<c:when test="${not empty expenseByCategory}">
							<c:forEach var="item" items="${expenseByCategory}">
								<div class="category-item">
									<span>${item.category}</span>
									<strong class="text-danger"><fmt:formatNumber value="${item.total}" pattern="#,###"/>원</strong>
								</div>
								<div class="progress mb-3">
									<div class="progress-bar bg-danger" role="progressbar" 
										style="width: ${totalExpense > 0 ? (item.total / totalExpense * 100) : 0}%">
										<fmt:formatNumber value="${totalExpense > 0 ? (item.total / totalExpense * 100) : 0}" pattern="#"/>%
									</div>
								</div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<p class="text-center text-muted py-5">지출 내역이 없습니다.</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		
		<!-- 월별 추이 차트 -->
		<div class="main-card">
			<h5 class="mb-4"><i class="bi bi-graph-up"></i> 최근 6개월 수입/지출 추이</h5>
			<c:choose>
				<c:when test="${not empty monthlyStats}">
					<div class="chart-container" style="height: 350px;">
						<canvas id="monthlyChart"></canvas>
					</div>
				</c:when>
				<c:otherwise>
					<p class="text-center text-muted py-5">데이터가 없습니다.</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	
	<script>
		// 카테고리별 지출 파이 차트
		<c:if test="${not empty expenseByCategory}">
		const expenseCtx = document.getElementById('expenseChart').getContext('2d');
		new Chart(expenseCtx, {
			type: 'doughnut',
			data: {
				labels: [<c:forEach var="item" items="${expenseByCategory}" varStatus="s">'${item.category}'<c:if test="${!s.last}">,</c:if></c:forEach>],
				datasets: [{
					data: [<c:forEach var="item" items="${expenseByCategory}" varStatus="s">${item.total}<c:if test="${!s.last}">,</c:if></c:forEach>],
					backgroundColor: [
						'#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF',
						'#FF9F40', '#FF6384', '#C9CBCF', '#7BC225', '#E7E9ED'
					],
					borderWidth: 2,
					borderColor: '#fff'
				}]
			},
			options: {
				responsive: true,
				maintainAspectRatio: false,
				plugins: {
					legend: {
						position: 'bottom'
					}
				}
			}
		});
		</c:if>
		
		// 월별 추이 막대 차트
		<c:if test="${not empty monthlyStats}">
		const monthlyCtx = document.getElementById('monthlyChart').getContext('2d');
		new Chart(monthlyCtx, {
			type: 'bar',
			data: {
				labels: [<c:forEach var="item" items="${monthlyStats}" varStatus="s">'${item.month}'<c:if test="${!s.last}">,</c:if></c:forEach>],
				datasets: [{
					label: '수입',
					data: [<c:forEach var="item" items="${monthlyStats}" varStatus="s">${item.income}<c:if test="${!s.last}">,</c:if></c:forEach>],
					backgroundColor: 'rgba(17, 153, 142, 0.7)',
					borderColor: '#11998e',
					borderWidth: 1
				}, {
					label: '지출',
					data: [<c:forEach var="item" items="${monthlyStats}" varStatus="s">${item.expense}<c:if test="${!s.last}">,</c:if></c:forEach>],
					backgroundColor: 'rgba(235, 51, 73, 0.7)',
					borderColor: '#eb3349',
					borderWidth: 1
				}]
			},
			options: {
				responsive: true,
				maintainAspectRatio: false,
				scales: {
					y: {
						beginAtZero: true,
						ticks: {
							callback: function(value) {
								return value.toLocaleString() + '원';
							}
						}
					}
				},
				plugins: {
					legend: {
						position: 'top'
					},
					tooltip: {
						callbacks: {
							label: function(context) {
								return context.dataset.label + ': ' + context.parsed.y.toLocaleString() + '원';
							}
						}
					}
				}
			}
		});
		</c:if>
	</script>
</body>
</html>






