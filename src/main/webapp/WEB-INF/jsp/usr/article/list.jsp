<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name } 게시판" />
<%@ include file="../common/head.jspf"%>

<%
%>   

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div>총 게시물 수 ${articlesCount } 개</div>
		<div class="table-box-type-1 overflow-x-auto">
			  <table class="table table-compact w-full">
				<colgroup>
					<col width="80" />
					<col width="140" />
					<col />
					<col width="140" />
				</colgroup>
				<thead>
					<tr class="text-indigo-700">
						<th>번호</th>
						<th>날짜</th>
						<th>제목</th>
						<th>작성자</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="article" items="${articles }">
						<tr>
							<td  class="text-green-600">${article.id}</td>
							<td>${article.regDate.substring(2,16)}</td>
							<td><a class="hover:underline" href="../article/detail?id=${article.id}">${article.title}</a></td>
							<td>${article.extra__writerName}</td>
						</tr>
					</c:forEach>
				</tbody>

			</table>
			
 		<div class="page-menu flex justify-center">
	 		<div class="btn-group mx-0 my-10 grid grid-cols-2">
	 		<button class="btn btn-outline btn-sm"> 이전 </button>
	 		<c:forEach begin="1" end="10" var="i">
	 		  <a href="?page=${i}" class="btn btn-sm ${param.page == i ? 'btn-active' : '' }">${i}</a>
	 		</c:forEach>
	 		<button class="btn btn-outline btn-sm"> 다음 </button>

			</div>
		</div>
		
	</div>
</section>
<%@ include file="../common/foot.jspf"%>