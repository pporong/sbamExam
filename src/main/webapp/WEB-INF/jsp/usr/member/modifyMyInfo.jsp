<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원 정보 수정" />
<%@ include file="../common/head.jspf"%>
<%@ page import="com.cwy.exam.demo.util.Ut"%>

<script>
	let MemberModify__submitDone = false;
	function MemberModify__submit(form) {
		
		if (MemberModify__submitDone) {
			return;
		}
		
		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPwConfirm.value.length > 0) {
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
			
			if (form.loginPwConfirm.value.length == 0) {
				alert('비밀번호 확인을 입력해주세요');
				form.loginPwConfirm.focus();
				return;
			}
			
			if (form.loginPw.value != form.loginPwConfirm.value) {
				alert('비밀번호가 일치하지 않습니다');
				form.loginPw.focus();
				return;
			}
		}
		
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value.length == 0) {
			alert('닉네임을 입력해주세요');
			form.nickname.focus();
			return;
		}
		
		form.cellphoneNum.value = form.cellphoneNum.value.trim();
		if (form.cellphoneNum.value.length == 0) {
			alert('전화번호를 입력해주세요');
			form.cellphoneNum.focus();
			return;
		}
		
		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요');
			form.email.focus();
			return;
		}
		 
		MemberModify__submitDone = true;
		form.submit();
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form class="" method="POST" action="../member/doModifyMyInfo" onsubmit="MemberModify__submit(this); return false;">
<%-- 		<input type="hidden" name="replaceUri" value="${param.replaceUri }" /> --%>
<%-- 		<input type="hidden" name="id" value="${member.id }" /> 
		<input type="hidden" name="loginPw" value="${member.loginPw }" /> --%>
			 <table class="table table-compact w-full center-box">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
						<th class=>▶ 아이디 </th>
						<td>${rq.loginedMember.loginId }</td>
					</tr>
									
					<tr>
						<th class="">▶  비밀번호 변경 </th>
					</tr>
					<tr>
						<th class="text-red-600">▷ 새 비밀번호 </th>
						<td><input class="w-96" name="loginPw" type="password" placeholder="새로운 비밀번호를 입력해주세요" /></td>
					</tr>
					<tr>
						<th class="text-red-600">▷ 새 비밀번호 확인</th>
						<td><input class="w-96" name="loginPwConfirm" type="password" placeholder="새로운 비밀번호를 다시 한 번 입력해주세요" /></td>
					</tr> 
					<tr>
						<th class="">▶ 가입 날짜 </th>
						<td>${rq.loginedMember.regDate }</td>
					</tr>
					<tr>
						<th class="">▶ 이름 </th>
						<td>${rq.loginedMember.name }</td>
					</tr>
					<tr>
						<th class="">▶ 현재 닉네임 </th>
						<td>${rq.loginedMember.nickname }</td>
					</tr>
					<tr>
						<th class="text-red-600">▷ 변경 할 닉네임 </th>
						<td><input class="w-96" name="nickname" type="text" placeholder="닉네임을 입력해주세요" /></td>
					</tr>
					<tr>
						<th class="">▶ 현재 전화 번호 </th>
						<td>${rq.loginedMember.cellphoneNum }</td>
					</tr>
					<tr>
						<th class="text-red-600">▷ 변경 할 전화 번호 </th>
						<td><input class="w-96" name="cellphoneNum" type="text" placeholder="전화번호를 입력해주세요" /></td>
					</tr>
					<tr>
						<th class="">▶ 현재 이메일</th>
						<td>${rq.loginedMember.email }</td>
					</tr>
					<tr>
						<th class="text-red-600">▷ 변경 할 이메일 </th>
						<td><input class="w-96" name="email" type="text" placeholder="이메일을 입력해주세요" /></td>
					</tr>

<%-- 					<tr>
						<th></th>
						<td><a href="../member/checkPassword?replaceUri=${Ut.getUriEncoded('../member/modify') }" 
						class="btn btn-outline btn-ghost btn-sm">회원 정보 수정 완료</a></td>
					</tr> --%>
					<tr class="">
						<th></th>
						<td class="">
							<button class="btn btn-ghost btn-sm btn-outline" 
							onclick="if(confirm('변경 내용이 정확합니까?') == false) return false;" 
							type="submit" value="회원 정보 수정">회원 정보 수정 완료</button>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="container mx-auto btns flex justify-end">
				<button class="btn-text-link btn btn-outline btn-sm" type="button" onclick="history.back();">뒤로가기</button>
			</div>
		</form>
	</div>



	
</section>
<%@ include file="../common/foot.jspf"%>
