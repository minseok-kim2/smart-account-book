<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
		<title>게시글 보기</title>
	</head>
	<body>
		<div class="container w-75 mt-5 mx-auto">
			<h2>${board.title}</h2>
			<hr>
			<div class="card">
				<div class="card-header d-flex justify-content-between">
					<span><strong>작성자:</strong> ${board.writer}</span>
					<span><strong>작성일:</strong> ${board.createdDate} | <strong>조회수:</strong> ${board.viewCount}</span>
				</div>
				<div class="card-body">
					<p class="card-text" style="white-space: pre-wrap; min-height: 200px;">${board.content}</p>
				</div>
			</div>
			<hr>
			<div class="d-flex justify-content-between">
				<a href="/board" class="btn btn-secondary">&laquo; 목록으로</a>
				<div>
					<a href="/board/edit/${board.id}" class="btn btn-warning">수정</a>
					<a href="/board/delete/${board.id}" class="btn btn-danger" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
				</div>
			</div>
		</div>
	</body>
</html>




