<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8" />
	<title>Ajax Test</title>
</head>

<style>
	.rs {
		border : 3px solid red;
		margin-top: 3px;
		padding: 20px;
		
		height: 20px;
	}
	.rs-msg {
		border : 3px solid pink;
		margin-top: 3px;
		padding: 20px;
		
		height: 20px;
	}
	.rs-code {
		border : 3px solid green;
		margin-top: 3px;
		padding: 20px;
		
		height: 20px;
	}
</style>

<!-- 제이쿼리 불러오기 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script>
	function callByAjaxV2() {
		var form = document.form1;
		
		var num1 = form.num1.value;
		var num2 = form.num2.value;
		
		var action = form.action;
		
		$.get('./doPlus', {
			num1 : num1,
			num2 : num2
		}, function(data) {
			// 			$('.rs').empty().append(data);
			$('.rs').text(data);
		}, 'html');
	}
	
	function callByAjaxV3() {
		var form = document.form1;
		
		var num1 = form.num1.value;
		var num2 = form.num2.value;
		
		var action = form.action;
		
		$.get('./doPlus', {
			num1 : num1,
			num2 : num2
		}, function(data) {
			data = data.split('/');
			var rs = data[0];
			var msg = data[1];
			
			$('.rs').text(rs);
			$('.rs-msg').text(msg);
		}, 'html');
	}
	
	function callByAjaxV4() {
		var form = document.form1;
		
		var num1 = form.num1.value;
		var num2 = form.num2.value;
		
		var action = form.action;
		
		$.get('./doPlus', {
			num1 : num1,
			num2 : num2
		}, function(data) {
			data = data.split('/');
			var rs = data[0];
			var msg = data[1];
			var rsCode = data[2];
			
			$('.rs').text(rs);
			$('.rs-msg').text(msg);
			$('.rs-code').text(rsCode);
		}, 'html');
	}
	
	function callByAjaxV5() {
		
		var form = document.form1;
		
		var num1 = form.num1.value;
		var num2 = form.num2.value;
		
		var action = './doPlusJson';
		
		$.get(action, {
			num1 : num1,
			num2 : num2
		}, function(data) {
			$('.rs').text(data.rs);
			$('.rs-msg').text(data.msg);
			$('.rs-code').text(data.code);
		}, 'json');
	}
	
</script>

<body>
	<div class="">
		<h2> AJAX test page </h2>
	</div>
	
	<form name="form1" method="get" action="./doPlus">
		<div>
			<input type="text" name="num1" placeholder="정수 입력" />
		</div>
		<div>
			<input type="text" name="num2" placeholder="정수 입력" />
		</div>
		<div>
			<input type="submit" value="더하기" />
		</div>

		<div>
			<input onclick="callByAjaxV2();" type="button" value="더하기v2" />
			<input onclick="callByAjaxV3();" type="button" value="더하기v3" />
			<input onclick="callByAjaxV4();" type="button" value="더하기v4" />
			<input onclick="callByAjaxV5();" type="button" value="더하기v5" />
		</div>
	</form>

	<h4>더한 결과</h4>
	<div class="rs"></div>
	
	<h4>더한 결과 msg</h4>
	<div class="rs-msg"></div>
	
	<h4>더한 결과 code</h4>
	<div class="rs-code"></div>
	
</body>
</html>