<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name } 게시판" />
<%@ include file="../common/head.jspf"%>

<%
int cPage = (int)request.getAttribute("page");
int totalPage = (int)request.getAttribute("totalPage");
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
 		
 		<div class="page">

 		</div>
		
		
	</div>
</section>
<%@ include file="../common/foot.jspf"%>