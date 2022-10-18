<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE WRITE" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1 overflow-x-auto" method="POST" action="../article/doWrite">
			  <table class="table table-compact w-full">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
						<th class="text-indigo-700">작성자</th>
						<td>${rq.loginedMember.name }</td>
					</tr>
					<tr>
						<th class="text-indigo-700">게시판 선택</th>
				   <!-- <td> 
							<input type="radio" id="notice" name="boardId" value="1">
  							<label>공지사항</label> &nbsp;&nbsp;&nbsp;
	  						<input type="radio" id="free1" name="boardId" value="2">
							<label>자유게시판</label><br>
						</td> -->
						
						<td>
							<select name="boardId">
								<option disabled> 게시판 선택 </option>
								<option value="1"> 공 지 사 항 </option>
								<option value="2"> 자 유 게 시 판 </option>
							</select>
						
						</td>
						
					</tr>
					<tr>
						<th class="text-indigo-700">제목</th>
						<td><input required="required" class="w-full input input-bordered" type="text" name="title" placeholder="제목을 입력해주세요"" /></td>
					</tr>
					<tr>
						<th class="text-indigo-700">내용</th>
						<td><textarea required="required" class="w-full input input-bordered" style="height: 400px;" type="text" name="body" placeholder="내용을 입력해주세요" /></textarea></td>
					</tr>
					<tr>
						<th class="text-indigo-700"></th>
						<td class=""><button class="btn btn-ghost btn-sm" type="submit" value="등록" />등록</button></td>
					</tr>
				</tbody>

			</table>
		</form>

		<div class="btns flex justify-end">
			<button class="btn-text-link btn btn-outline btn-sm" type="button" onclick="history.back();">뒤로가기</button>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>

