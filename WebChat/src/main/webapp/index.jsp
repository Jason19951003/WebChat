<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登入首頁</title>
<script type="text/javascript">
function sign() {
	location.href = "./sign.jsp"
}

</script>
</head>
<body>
帳號：<input type="text" name="account"><br>
密碼；<input type="password" name="password"><br>
<input type="button" name="login" value="登入">&nbsp;&nbsp;&nbsp;<input type="button" name="sign" value="註冊" onclick="sign()">

</body>
</html>