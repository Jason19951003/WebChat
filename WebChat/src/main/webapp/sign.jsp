<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>註冊頁面</title>
<script type="text/javascript">
	function signUp() {
		if ($("input[name=account]").val() === "") {
		      alert('請輸入帳號');
		      return;
		}
		
		if ($("input[name=password]").val() === "") {
		      alert('請輸入密碼');
		      return;
		}
		
		if ($("input[name=nickname]").val() === "") {
		      alert('請輸入暱稱');
		      return;
		}
		
		var formData = new FormData($("#myform")[0]);
		
		$.ajax({
			   type : "POST"
			   ,url : "http://127.0.0.1:8088/WebChat/sign"
			   ,data: formData
			   ,dataType : "json"
			   ,processData : false
		       ,cache: false
		       ,contentType: false
			   ,success : function (data) {
				   if (data.MESSAGE == 'Y') {
					   $("input[name=nickname]").val(data.NICKNAME);
					   document.getElementById("myform").submit();
				   } else {
					   alert(data.MESSAGE);
				   }
			   }
		});
	}
	function clean() {
		document.getElementById("myform").reset();
	}
</script>
<style type="text/css">
	body {
		font-size: 20px;
	}
	span {
		color : yellow;
	}
</style>
</head>
<body style="background-color: 	black">
	<div style="width: 500px; height: 500px; margin: auto; position: absolute; bottom: 0; top: 0; left : 0; right : 0;">
		<form id="myform" action="/WebChat/chat.jsp">
			<input type="hidden" name="nickname">
			<table style="text-align: right">
				<tr>
					<td><span>帳號：</span></td>
					<td style="text-align: left; height: 50px;"><input type="text" name="account"></td>
				</tr>
				<tr>
					<td><span>密碼：</span></td>
					<td style="text-align: left; height: 50px;"><input type="password" name="password"></td>
				</tr>
				<tr>
					<td><span>暱稱：</span></td>
					<td style="text-align: left; height: 50px;"><input type="text" name="nickname"></td>
				</tr>
				<tr>
					<td><span>大頭貼：</span></td>
					<td style="text-align: left; height: 50px;"><input type="file" name="image" style="color: yellow"></td>
				</tr>
				<tr>
					<td>
						
					</td>
					<td style="text-align: left">
						<input type="button" name="sign" value="註冊" onclick="signUp();" class="btn btn-default">
						<input type="button" name="sign" value="清空" onclick="clean();" class="btn btn-default">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>