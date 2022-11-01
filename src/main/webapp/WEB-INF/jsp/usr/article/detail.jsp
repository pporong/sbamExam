<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>

<!-- 조회수 function -->
<script>
	const params = {};
	params.id = parseInt('${param.id}');
	
/* 	var isAlreadyAddGoodRp = ${isAlreadyAddGoodRp};
	var isAlreadyAddBadRp = ${isAlreadyAddBadRp}; */
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
	});

</script> 

<!-- 댓글 function -->
<script>
	// 댓글 중복 submit 방지
	let ReplyWrite__submitFormDone = false;
	
	function ReplyWrite__submitForm(form) {
		
		if (ReplyWrite__submitFormDone) {
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
		
		ReplyWrite__submitFormDone = true;
		form.submit();
	}

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
						<td class="text-green-600"><span class="badge badge-outline">${article.id }번 글</span></td>
						
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
						<th class="text-indigo-700">현재 추천수</th>
						<td>
							<span class=" gap-2 btn-sm mx-2 btn-like" onclick=""> 👍 좋아요 
								<div class="badge badge-secondary"> ${article.goodReactionPoint}</div>
							</span>
							<span class=" gap-2 btn-sm btn-hate"> 👎 싫어요 
		 						 <div class="badge"> ${article.badReactionPoint}</div>
		 					</span>
						</td>
					</tr>
					<tr>
						<th class="text-indigo-700">제목</th>
						<td>${article.title }</td>
					</tr>
					<tr>
						<th class="text-indigo-700">내용</th>
						<td style="height: 250px;">${article.body }</td>
					</tr>
					
					<tr>
						<th class="text-indigo-700">느낌 남기기</th>
						<td>				
							<!-- 추천 기능 사용 가능? -->
							<c:if test="${actorCanMakeReaction }">		
								<div class="btns my-3 flex justify-center">
									<!-- 추천 버튼 -->
									<a id="" href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" 
									class="btn gap-2 btn-sm mx-2 btn-like btn-outline" onclick="f_clickLikeBtn"> 👍 좋아요 </a>
									<a id="" href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" 
									class="btn gap-2 btn-sm btn-hate btn-outline"> 👎 싫어요 </a>
								</div>
							</c:if>
							
							<!-- 싫어요를 누르고싶다면 좋아요를 취소 해! -->
							<c:if test="${actorCanDelGoodRp }">
						 		<div class="btns my-3 flex justify-center">
									<!-- 추천 버튼 -->
									<a id="" href="/usr/reactionPoint/doDeleteGoodReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" 
									class="btn gap-2 btn-sm mx-2 btn-like btn-warning"> 👍 좋아요 </a>
									<a onclick="alert(this.title); return false;" title="싫어요를 누르고 싶다면 좋아요를 취소 해 주세요!" id="" href="#" 
									class="btn gap-2 btn-sm btn-hate btn-outline"> 👎 싫어요 </a>
								</div> 
						 	</c:if>
						 	
						 	<!-- 좋아요를 누르고싶다면 싫어요를 취소 해! -->
							<c:if test="${actorCanDelBadRp }">
							 	<div class="btns my-3 flex justify-center">
									<!-- 추천 버튼 -->
									<a onclick="alert(this.title); return false;" title="좋아요를 누르고 싶다면 싫어요를 취소 해 주세요!" id="" href="#" 
									class="btn gap-2 btn-sm mx-2 btn-like btn-outline"> 👍 좋아요 </a>
									<a href="/usr/reactionPoint/doDeleteBadReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" 
									class="btn gap-2 btn-sm btn-hate btn-warning"> 👎 싫어요 </a>
								</div> 
						 	</c:if>
						</td>
					</tr>
				</tbody>

			</table>
		</div>

		<!-- 댓글 목록 -->
		<div class="mt-5">
			<div class="text-indigo-700"> 댓글 목록 <span class="badge badge-outline"> ${repliesCount } 개</span></div>
			<c:forEach var="reply" items="${replies }">
				<div class="text-green-600">${reply.id}</div>
				<div class="text-green-600">${reply.regdate}</div>
				<div>${reply.extra__writerName}</div>
				<div>${reply.body}</div>		
			</c:forEach>

		</div>


		<!-- 댓글 입력 -->
		<div class="mt-5 overflow-x-auto">
			<div class="text-indigo-700">댓글 작성</div>
			<c:if test="${rq.logined }">
				<form class="table-box-type-1 overflow-x-auto" method="POST" action="../reply/doWrite" 
					onsubmit="ReplyWrite__submitForm(this); return false;">
					<input type="hidden" name="relTypeCode" value="article"/>
					<input type="hidden" name="relId" value="${param.id }"/>
					  <table class="table table-zebra w-full text-sm">
						<colgroup>
							<col width="100" />
						</colgroup>
						<tbody>
							<tr>
								<th class="text-indigo-700">작성자</th>
								<td>${rq.loginedMember.name }</td>
							</tr>
							<tr>
								<th class="text-indigo-700">내용</th>
								<td><textarea class="w-full input input-bordered" style="height: 100px;" type="text" name="body" placeholder="댓글을 입력해주세요" rows="5" /></textarea></td>
							</tr>
							<tr>
								<th class="text-indigo-700"></th>
								<td class=""><button class="btn btn-ghost btn-sm" type="submit" value="작성">댓글 작성</button></td>
							</tr>
						</tbody>
					</table>
				</form>
			</c:if>
			
			<!-- 댓글 이용시 로그인여부 -->
			<c:if test="${rq.notLogined }">
			로그인 후 이용 할 수 있습니다.  
				<a class="btn-text-link btn btn-ghost" href="/usr/member/login">로그인</a> 해 주세요!
			</c:if>
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

<%@ include file="../common/foot.jspf"%>
