<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="LOGIN" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8 text-xl ">
	<div class="container mx-auto px-3 ">
		<form class="" method="POST" action="../member/doLogin">
		<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri }" />
			<div class="">
				<table class="table table-compact w-full center-box">
					<colgroup>
						<col width="200" />
					</colgroup>
	
					<tbody>
						<tr class="hover">
							<th>▶ 아이디</th>
							<td><input class="w-96" name="loginId" type="text" placeholder="아이디를 입력해주세요" /></td>
						</tr>
						<tr class="hover">
							<th>▶ 비밀번호</th>
							<td><input class="w-96" name="loginPw" type="text" placeholder="비밀번호를 입력해주세요" /></td>
						</tr>
						<tr class="">
							<th></th>
							<td><button class="btn btn-ghost btn-sm btn-outline" type="submit" value="로그인">로그인</button></td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
	</div>

	<div class="container mx-auto btns flex justify-end">
		<button class="btn-text-link btn btn-outline btn-sm" type="button" onclick="history.back();">뒤로가기</button>
	</div>

</section>
<%@ include file="../common/foot.jspf"%>
