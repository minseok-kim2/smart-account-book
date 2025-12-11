<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
		<title>게시글 수정</title>
	</head>
	<body>
		<div class="container w-75 mt-5 mx-auto">
			<h2>게시글 수정</h2>
			<hr>
			<form method="post" action="/board/edit">
				<input type="hidden" name="id" value="${board.id}">
				<div class="mb-3">
					<label class="form-label">제목</label>
					<input type="text" name="title" class="form-control" value="${board.title}" required>
				</div>
				<div class="mb-3">
					<label class="form-label">작성자</label>
					<input type="text" name="writer" class="form-control" value="${board.writer}" readonly>
				</div>
				<div class="mb-3">
					<label class="form-label">내용</label>
					<textarea name="content" rows="10" class="form-control" required>${board.content}</textarea>
				</div>
				<div class="d-flex justify-content-between">
					<a href="/board/${board.id}" class="btn btn-secondary">취소</a>
					<button type="submit" class="btn btn-warning">수정</button>
				</div>
			</form>
		</div>
	</body>
</html>




