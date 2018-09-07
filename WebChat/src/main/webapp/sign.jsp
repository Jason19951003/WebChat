<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>註冊頁面</title>
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
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
				   alert(data.message);
			   }
		});
	}
</script>
</head>
<body style="background-color: black">
	<div style="width: 500px; height: 500px; margin: auto; position: absolute; bottom: 0; top: 0; left : 0; right : 0;">
		<form id="myform">
			<table>
				<tr>
					<td><span style="color: yellow">帳號：</span></td>
					<td><input type="text" name="account"></td>
				</tr>
				<tr>
					<td><span style="color: yellow">密碼：</span></td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td><span style="color: yellow">暱稱：</span></td>
					<td><input type="text" name="nickname"></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" name="sign" value="註冊" onclick="signUp();">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>