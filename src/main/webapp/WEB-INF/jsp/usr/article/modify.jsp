<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE MODIFY" />
<%@ include file="../common/head.jspf"%>

<script>
	// 댓글 중복 submit 방지
	let ArticleModify__submitFormDone = false;
	
	function ArticleModify__submitForm(form) {
		
		if (ArticleModify__submitFormDone) {
			return;
		}
		
		form.body.value = form.body.value.trim();
		// 		if (form.body.value.length == 0) {
		// 			alert('댓글을 입력해주세요');
		// 			form.body.focus();
		// 			return;
		// 		}
		
		if (form.body.value.length < 2) {
			alert('2글자 이상 입력해주세요');
			form.body.focus();
			return;
		}
		
		ArticleModify__submitFormDone = true;
		form.submit();
	}

</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1 overflow-x-auto" method="POST" action="../article/doModify"
			onsubmit="ArticleModify__submit(this); return false;">
			<input type="hidden" name="id" value="${article.id }" />
			  <table class="table table-compact w-full">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
						<th class="text-indigo-700">번호</th>
						<td class="text-green-600"><span class="badge badge-outline">${article.id }</span></td>
					</tr>
					<tr>
						<th class="text-indigo-700">작성날짜</th>
						<td>${article.regDate }</td>
					</tr>
					<tr>
						<th class="text-indigo-700">수정날짜</th>
						<td>${article.updateDate }</td>
					</tr>
					<tr>
						<th class="text-indigo-700">작성자</th>
						<td>${article.extra__writerName }</td>
					</tr>
					<tr>
						<th class="text-indigo-700">추천수</th>
						<td><span class="badge badge-outline">${article.goodReactionPoint}</span></td>
					</tr>
					<tr>
						<th class="text-indigo-700">제목</th>
						<td><input class="w-full input input-bordered" type="text" name="title" placeholder="제목을 입력해주세요" value="${article.title }" /></td>
					</tr>
					<tr>
						<th class="text-indigo-700">내용</th>
						<td><textarea class="w-full input input-bordered" style="height: 400px;" type="text" name="body" placeholder="내용을 입력해주세요" />${article.body }</textarea></td>
					</tr>
					<tr>
						<th class="text-indigo-700"></th>
						<td><button type="submit" value="수정" />수정</button></td>
					</tr>
				</tbody>

			</table>
		</form>

		<div class="btns flex justify-end">
			<button class="btn-text-link btn btn-outline btn-sm" type="button" onclick="history.back();">뒤로가기</button>
			<c:if test="${article.extra__actorCanModify }">
				<a class="btn-text-link btn btn-outline btn-sm" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.extra__actorCanDelete }">
				<a class="btn-text-link btn btn-outline btn-sm" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
				   href="../article/doDelete?id=${article.id }">삭제</a>
			</c:if>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>

