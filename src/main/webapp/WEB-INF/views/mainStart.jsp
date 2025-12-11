<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
	<title>가계부 프로젝트</title>
	<style>
		body {
			background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
			min-height: 100vh;
		}
		.main-card {
			background: white;
			border-radius: 25px;
			box-shadow: 0 15px 50px rgba(0,0,0,0.3);
			padding: 50px;
		}
		.feature-card {
			background: #f8f9fa;
			border-radius: 15px;
			padding: 25px;
			text-align: center;
			transition: transform 0.3s, box-shadow 0.3s;
		}
		.feature-card:hover {
			transform: translateY(-5px);
			box-shadow: 0 10px 30px rgba(0,0,0,0.1);
		}
		.feature-icon {
			font-size: 3rem;
			margin-bottom: 15px;
		}
		.btn-main {
			background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
			border: none;
			padding: 18px 50px;
			font-size: 1.3rem;
			border-radius: 35px;
			transition: transform 0.3s, box-shadow 0.3s;
		}
		.btn-main:hover {
			transform: translateY(-3px);
			box-shadow: 0 10px 30px rgba(102, 126, 234, 0.5);
		}
		.tech-badge {
			background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
			color: white;
			padding: 5px 15px;
			border-radius: 20px;
			font-size: 0.85rem;
			margin: 3px;
			display: inline-block;
		}
	</style>
</head>
<body>
	<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
		<div class="main-card text-center" style="max-width: 800px;">
			<!-- 헤더 -->
			<div class="mb-4">
				<span style="font-size: 4rem;">💰</span>
				<h1 class="mt-3 mb-2">스마트 가계부</h1>
				<p class="text-muted">Spring Boot 기반 개인 재정 관리 시스템</p>
			</div>
			
			<hr class="my-4">
			
			<!-- 기능 소개 -->
			<div class="row mb-4">
				<div class="col-md-4 mb-3">
					<div class="feature-card">
						<div class="feature-icon">📝</div>
						<h5>수입/지출 관리</h5>
						<p class="text-muted small mb-0">간편한 내역 등록 및 관리</p>
					</div>
				</div>
				<div class="col-md-4 mb-3">
					<div class="feature-card">
						<div class="feature-icon">📊</div>
						<h5>통계 분석</h5>
						<p class="text-muted small mb-0">카테고리별 지출 차트</p>
					</div>
				</div>
				<div class="col-md-4 mb-3">
					<div class="feature-card">
						<div class="feature-icon">📈</div>
						<h5>월별 추이</h5>
						<p class="text-muted small mb-0">수입/지출 추이 그래프</p>
					</div>
				</div>
			</div>
			
			<!-- 기술 스택 -->
			<div class="mb-4">
				<p class="mb-2"><strong>기술 스택</strong></p>
				<span class="tech-badge">Java 21</span>
				<span class="tech-badge">Spring Boot 3.5</span>
				<span class="tech-badge">MySQL</span>
				<span class="tech-badge">JSP</span>
				<span class="tech-badge">Chart.js</span>
				<span class="tech-badge">Bootstrap 5</span>
			</div>
			
			<!-- 메인 버튼 -->
			<a href="/account" class="btn btn-main btn-primary btn-lg">
				<i class="bi bi-wallet2"></i> 가계부 시작하기
			</a>
			
			<hr class="mt-4 mb-3">
			
			<!-- 푸터 -->
			<div class="text-muted">
				<small>
					<i class="bi bi-person"></i> 개발자: 김민석<br>
					<i class="bi bi-clock"></i> <%= java.time.LocalDateTime.now().toString().replace("T", " ").substring(0, 19) %>
				</small>
			</div>
		</div>
	</div>
</body>
</html>
