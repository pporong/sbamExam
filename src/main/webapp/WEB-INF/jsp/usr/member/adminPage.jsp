<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ADMIN" />
<%@ include file="../common/head.jspf"%>
<%@ page import="com.cwy.exam.demo.util.Ut"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
			 <table class="table table-compact w-full center-box">
				
				<thead>
					<tr class="text-indigo-700">
						<th>회원목록</th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="member" items="${members }">
						<tr class="hover">
							<td class="text-green-600">${member.id}</td>
							<td>${article.forPrintType1RegDate}</td>
							<td><a class="hover:underline" href="${rq.getArticleDetailUriFromArticleList(article) }">${article.title}</a></td>
							<td>${article.extra__writerName}</td>
							<td>${article.hitCount}</td>
							<td>${article.goodReactionPoint}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="container mx-auto btns flex justify-end my-3">
				<button class="btn-text-link btn btn-outline btn-sm" type="button" onclick="history.back();">뒤로가기</button>
			</div>
	</div>



	
</section>
<%@ include file="../common/foot.jspf"%>
