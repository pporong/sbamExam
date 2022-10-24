<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>

<!-- 조회수 function -->
<script>
	const params = {};
	params.id = parseInt('${param.id}');
</script>

<script>
	function ArticleDetail__increaseHitCount(){
		const localStorageKey = 'airtlce__' + params.id + '__alreadyView';
		
		if (localStorage.getItem(localStorageKey)) {
			return;
		}
		localStorage.setItem(localStorageKey, true);
		
		$.get('../article/doIncreaseHitCountRd?', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}
	
	$(function() {
		// 실전코드
 		ArticleDetail__increaseHitCount();
		
		// 연습코드
//		setTimeout(ArticleDetail__increaseHitCount, 1000);
	});
	
</script>


<script>

</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1 overflow-x-auto">
			  <table class="table table-compact w-full">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
						<th class="text-indigo-700">번호</th>
						<td class="text-green-600">${article.id }</td>
					</tr>
					<tr>
						<th class="text-indigo-700">게시판</th>
						<td class="">${article.boardId}</td>
					</tr>
					<tr>
						<th class="text-indigo-700">조회수</th>
						<td><span class="badge article-detail__hit-count">${article.hitCount }</span></td>
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
						<th class="text-indigo-700">제목</th>
						<td>${article.title }</td>
					</tr>
					<tr>
						<th class="text-indigo-700">내용</th>
						<td style="height: 250px;">${article.body }</td>
					</tr>
				</tbody>

			</table>
		</div>


					
		<div class="btns my-3 flex justify-center">
			<!-- 추천 버튼 -->
			<button class="btn gap-2 btn-sm mx-2 btn-like"> 좋아요
	 			<div class="badge badge-secondary ">${article.extra__goodReactionPiont }</div>
			</button>
			<button class="btn gap-2 btn-sm btn-hate"> 싫어요
	 			 <div class="badge">${article.extra__badReactionPiont }</div>
			</button>
		</div>
			<!-- 뒤로가기, 삭제 버튼 -->
		<div class="btns my-3 flex justify-end">
			<button class="btn-text-link btn btn-outline btn-sm" type="button" onclick="history.back();">뒤로가기</button>
			<c:if test="${article.extra__actorCanModify }">
				<a class="btn-text-link btn btn-outline btn-sm" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.extra__actorCanDelete }">
				<a class="btn-text-link btn btn-outline btn-sm" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
					href="../article/doDelete?id=${article.id }"
				>삭제</a>
			</c:if>

		</div>
	</div>
</section>

<!-- <iframe src="http://localhost:8081/usr/article/doIncreaseHitCount?id=1" frameborder="0"></iframe> -->

<%@ include file="../common/foot.jspf"%>
