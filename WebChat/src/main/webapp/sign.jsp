<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>註冊頁面</title>
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	function signUp() {
		if($("input[name=account]").val() === ""){
		      alert('請輸入帳號');
		      return;
		}
		if($("input[name=password]").val() === ""){
		      alert('請輸入密碼');
		      return;
		}
		if($("input[name=nickname]").val() === ""){
		      alert('請輸入暱稱');
		      return;
		}
		var formData = new FormData($("#myForm")[0]);
		
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
<body>
	<form id="myform" enctype="Multipart/Form-Data">
		帳號：<input type="text" name="account"><br>
		密碼：<input type="password" name="password"><br>
		暱稱：<input type="text" name="nickname"><br>
		大頭貼；<input type="file" name="image"><br>
		<input type="button" name="sign" value="註冊" onclick="signUp();">
	</form>
</body>
</html>