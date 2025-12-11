<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
		<title>게시판</title>
		<style>
			.btnn { padding: 8px 16px; margin: 20px; text-decoration: none; border-radius: 4px; }
		</style>
	</head>
	<body>
		<div class="container w-75 mt-5 mx-auto">
			<h2>게시판</h2>
			<hr>
			<c:if test="${error != null}">
				<div class="alert alert-danger alert-dismissible fade show mt-3">
					에러 발생: ${error}
					<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				</div>
			</c:if>
			<table class="table table-hover">
				<thead class="table-dark">
					<tr>
						<th style="width: 10%">번호</th>
						<th style="width: 45%">제목</th>
						<th style="width: 15%">작성자</th>
						<th style="width: 20%">작성일</th>
						<th style="width: 10%">조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="board" items="${boardList}" varStatus="status">
						<tr>
							<td>${board.id}</td>
							<td>
								<a href="/board/${board.id}" class="text-decoration-none">
									${board.title}
								</a>
							</td>
							<td>${board.writer}</td>
							<td>${board.createdDate}</td>
							<td>${board.viewCount}</td>
						</tr>
					</c:forEach>
					<c:if test="${empty boardList}">
						<tr>
							<td colspan="5" class="text-center text-muted">등록된 게시글이 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<hr>
			<div class="d-flex justify-content-between">
				<a href="/mainStart" class="btn btn-secondary">메인으로</a>
				<a href="/board/write" class="btn btn-primary">글쓰기</a>
			</div>
		</div>
	</body>
</html>


