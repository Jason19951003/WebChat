<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登入首頁</title>
    
	<script type="text/javascript">
		function goSign() {
			location.href = "./sign.jsp"
		}
		
		function goChat() {
			if ($("input[name=account]").val() === "") {
			      alert('請輸入帳號');
			      return;
			}
			
			if ($("input[name=password]").val() === "") {
			      alert('請輸入密碼');
			      return;
			}
			
			var formData = new FormData($("#myform")[0]);
			
			$.ajax({
				   type : "POST"
				   ,url : "http://127.0.0.1:8088/WebChat/login"
				   ,data: formData
				   ,dataType : "json"
				   ,processData : false
			       ,cache: false
			       ,contentType: false
				   ,success : function (data) {
					   if (data.MESSAGE != "") {
						   alert(data.MESSAGE);
					   } else {
						   $("input[name=nickname]").val(data.NICKNAME);
						   document.getElementById("myform").submit();
					   }
				   }
			});
		}
	</script>
	<style type="text/css">
		body {
			font-size: 16px;
		}
		span{
			font-size : 20px;
			color : yellow;
		}
		
		.head {
			margin : auto;
			position: absolute;
			top : 0px;
			left : 500px;
		}
		.gif1 {
			margin : auto;
			position: absolute;
			top : 500px;
		}
		.gif2 {
			margin : auto;
			position: absolute;
			top : 500px;
			left : 300px;
		}
	</style>
</head>
<body style="background-color: 	black">
	<img class="head" src="image/head.png">
	<div style="width: 500px; height: 500px; margin: auto; position: absolute; bottom: 0; top: 0; left : 0; right : 0;">
		<form id="myform" action="/WebChat/chat.jsp" method="post">
			<input type="hidden" name="nickname">
			<table style="text-align: right">
				<tr>
					<td><span>帳號：</span></td>
					<td style="text-align: left; height: 50px;"><input type="text" name="account" class="form-control"></td>
				</tr>
				<tr>
					<td><span>密碼：</span></td>
					<td style="text-align: left; height: 50px;"><input type="password" name="password" class="form-control"></td>
				</tr>
				<tr>
					<td>
						
					</td>
					<td style="text-align: left">
						<input type="button" name="login" value="登入" onclick="goChat();" class="btn btn-default">
						<input type="button" name="sign" value="註冊" onclick="goSign();" class="btn btn-default">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<img src="image/pleaseLeft.gif" class="gif1">
	<img src="image/pleaseRight.gif" class="gif2">
</body>
</html>