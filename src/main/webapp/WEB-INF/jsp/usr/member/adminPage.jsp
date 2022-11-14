<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ADMIN" />
<%@ include file="../common/head.jspf"%>
<%@ page import="com.cwy.exam.demo.util.Ut"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
			 <table class="table table-compact w-full center-box">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr class="hover">
						<th class=>▶ 아이디 </th>
						<td>${rq.loginedMember.loginId }</td>
					</tr>
					<tr class="hover">
						<th class="">▶ 가입 날짜</th>
						<td>${rq.loginedMember.regDate }</td>
					</tr>
					<tr class="hover">
						<th class="">▶ 이름</th>
						<td>${rq.loginedMember.name }</td>
					</tr>
					<tr class="hover">
						<th class="">▶ 닉네임</th>
						<td>${rq.loginedMember.nickname }</td>
					</tr>
					<tr class="hover">
						<th class="">▶ 전화 번호</th>
						<td>${rq.loginedMember.cellphoneNum }</td>
					</tr>
					<tr class="hover">
						<th class="">▶ 이메일</th>
						<td>${rq.loginedMember.email }</td>
					</tr>
					<tr>
						<th></th>
						<td><a href="../member/checkPassword?replaceUri=${Ut.getUriEncoded('../member/modifyMyInfo') }" 
						class="btn btn-outline btn-ghost btn-sm">회원 정보 수정</a></td>
					</tr>
				</tbody>
			</table>
			<div class="container mx-auto btns flex justify-end my-3">
				<button class="btn-text-link btn btn-outline btn-sm" type="button" onclick="history.back();">뒤로가기</button>
			</div>
	</div>



	
</section>
<%@ include file="../common/foot.jspf"%>
