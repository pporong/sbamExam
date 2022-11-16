<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ADMIN > member list" />
<%@ include file="../common/head.jspf"%>
<%@ page import="com.cwy.exam.demo.util.Ut"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1 overflow-x-auto">
			 <table class="table table-compact w-full center-box">
				<thead>
					<tr class="text-indigo-700">
						<th>회원 번호</th>
						<th>가입 날짜</th>
						<th>아이디</th>
						<th>비밀번호</th>
						<th>회원 분류번호 <br />(3: 일반 / 7 : 관리자)</th>
						<th>이름</th>
						<th>닉네임</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="member" items="${members }">
							<tr class="hover">
								<td class="text-green-600"><a href="#">${member.id}</a></td>
								<td class="">${member.regDate}</td>
								<td class="">${member.loginId}</td>
								<td class="">${member.loginPw}</td>
								<td class="">${member.authLevel}</td>
								<td class=""><a href="#">${member.name}</a></td>
								<td class="">${member.nickname}</td>
							</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</div>
			<div class="container mx-auto btns flex justify-end my-3">
				<button class="btn-text-link btn btn-outline btn-sm" type="button" onclick="history.back();">뒤로가기</button>
			</div>
	</div>


</section>
<%@ include file="../common/foot.jspf"%>
