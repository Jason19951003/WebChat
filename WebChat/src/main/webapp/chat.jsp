<%@ page import="java.util.Map"%>
<%@ page import="com.jason.util.RequestBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<%
	RequestBean requestBean = RequestBean.buildRequestBean(request);	
	Map<String, Object> paramMap = requestBean.getRequestMap();
	String account = (String)paramMap.get("account");
	String nickname = (String)paramMap.get("nickname");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聊天頁面</title>
<script type="text/javascript">
	var webSocket = null;
	
	$(document).ready(function() {
		setWebSocket();		
	});
	
	function setWebSocket() {
		var account = '<%=account%>';
		var nickname = '<%=nickname%>';
		webSocket = new WebSocket('ws://localhost:8088/WebChat/websocket?account=' + account);
		
		//onerror , 連線錯誤時觸發  
        webSocket.onerror = function (event) {
            $(".chat").html("載入聊天頁面發生錯誤!!");
        };
 
        //onopen , 連線成功時觸發
        webSocket.onopen = function (event) {
            //送一個登入聊天室的訊息
            var firstLoginInfo = {
                ALL : "Y",
                MESSAGE : "系統：" + nickname + "登入了聊天室"
            };
            webSocket.send(JSON.stringify(firstLoginInfo));
        };
 
        //onmessage , 接收到來自Server的訊息時觸發
        webSocket.onmessage = function (event) {
            var messageObject = JSON.parse(event.data);
            //發送給共用聊天室
            if (messageObject.ALL != undefined && messageObject.ALL == 'Y') {
            	var chat = $(".chat").html();
            	$(".chat").html(chat + "<br/>" + messageObject.MESSAGE);
            }
            //上線列表
            if (messageObject.IMAGE != undefined && messageObject.IMAGE == 'Y') {
            	$(".uesrList ul").find("li").remove();
            	$(".uesrList ul").append(messageObject.USERLIST);            	
            }
            //發私人訊息
            if (messageObject.ONE != undefined && messageObject.ONE == 'Y') {            	
            	
            	//開啟視窗
           		for (var i = 0; i < $(".uesrList li").length; i++) {
           			if ($($(".uesrList li")[i]).attr("account") == messageObject.from) {
           				$($(".uesrList li")[i]).click();
           				$(".content").each(function(){
           					var id = $(this).siblings(".title").children("font").attr("account");
           					if (id == messageObject.from) {
           						//寫入訊息
           						$(this).children("ul").append("<li id='receive'><img src='getHead?account=" + messageObject.from + "'><font>" + messageObject.MESSAGE +"</font></li>");
           					}
           				});
           			}
           		}
            }
        };
	}
	
	function send() {
		var nickname = '<%=nickname%>';
		if (webSocket != null && webSocket.readyState == 1) {
			var message =  {
				ALL : "Y",
	            MESSAGE : nickname + "說：" + $("input[name=message]").val()
			}
			$("input[name=message]").val('');
			webSocket.send(JSON.stringify(message));
		}
	}
	
	function keyEnter(event) {
		var keyCode = event.keyCode || event.which;
		if (keyCode == '13') {
			send();
		}
	}
	
	function openChat(_this) {
		if ($(_this).attr("isopen") == "N") {
			$(_this).attr("isopen","Y");
			var str = "<li style='border: 1px solid #F5F6F7;'><div class='title'>" +
			"<font account='" + $(_this).attr("account") + "'>" + $(_this).attr("nickname") + "</font><img src='image/close.png' onclick='closeChat(this);' alt='關閉視窗'></div>" +
			"<div class='content'><ul class='myChat'></ul></div>" +
			"<input type='text' name='secretMessage' style='width: 100%; height: 35px' onkeyup='sendMessage(this,event);'></li>";		
			$(".secretChat > ul").append(str);
		}
	}
	
	function closeChat(_this) {
		var name = $(_this).siblings("font").text();		
		
		//不知道為啥不能用.each寫暫時用這方法
		for (var i = 0; i < $(".uesrList li").length; i++) {
			if ($($(".uesrList li")[i]).attr("nickname") == name) {
				$($(".uesrList li")[i]).attr("isopen","N");
			}
		}
		
		$(_this).parent().parent().remove();
	}
	//傳送訊息
	function sendMessage(_this,event) {
		var keyCode = event.keyCode || event.which;
		if (keyCode == '13') {			
			if (webSocket != null && webSocket.readyState == 1) {
				var account = '<%=account%>';
				var message =  {
					ONE : "Y",
		            MESSAGE : $(_this).val(),
		            account : $(_this).siblings(".title").children("font").attr("account"),
		            from : account
				}
				$(_this).siblings(".content").children("ul").append("<li id='send'><font>" + $(_this).val() + "</font></li>");
				$(_this).val('');
				webSocket.send(JSON.stringify(message));
			}
		}
	}
	
	function test() {
		$(".content").each(function(){
			console.log($(this).html());
		});
	}
</script>
<style type="text/css">
	.chat {
		width : 1000px;
		height : 500px;
		/*border : 1px solid white;*/
		color : white;
		position: absolute;
		left : 20px;
		top : 20px;
		overflow: auto;/*自動出現滾輪條*/
	}
	.send {
		position: absolute;
		left: 20px;
		top: 525px;
	}
	.uesrList {
		position: absolute;
		left : 1600px;
		top : 20px;
		border : 1px solid white;
		width: 200px;
		height : 900px;
		overflow: auto;/*自動出現滾輪條*/
	}
	.uesrList ul {
		padding: 0px;
		list-style-type: none;
		position: absolute;
		width: 100%;
	}
	.uesrList li {		
		float: left;
		width: 100%;
		height : 40px;
		-webkit-user-select: none; /*不能反白*/
		-khtml-user-select: none;
		-moz-user-select: none;
		-ms-user-select: none;
		user-select: none;
	}
	.uesrList li:hover {
		background-color: gray;
		cursor: pointer;
	}
	.uesrList img {
		width: 30px;
		height: 30px;
		position: relative;
		top: 3px;
		-moz-border-radius: 15px;
     	-webkit-border-radius: 15px;
     	border-radius: 15px;
	}
	.uesrList font {
		color : yellow;
		position: relative;
		top : 3px;
		left : 10px;
	}
	.secretChat {
		position: absolute;
		left: 20px;
		top : 570px;
		width : 1480px;
		height : 355px;		
	}
	.secretChat ul {
		padding: 0px;
		list-style-type: none;		
		width: 100%;
		height : 100%;		
	}
	.secretChat li {
		float: right;
		margin-left : 1%;		
		width : 19%;
		height : 100%;		
	}
	.title {
		background-color: black;		
		color : white;
		box-shadow : 1px 1px 1px 1px white;
		height : 30px;
		width: 100%;
		/*text-align: right;*/
	}
	.title font {
		position: relative;
		top : 3px;
		left : 3px;
		text-align: left;
	}
	.title img {
		width: 28px;
		height: 28px;
		cursor: pointer;
		position: relative;
		top : 2px;
		float: right;
	}
	.content {
		width: 100%;
		height: 82%;
		box-shadow : 0px 0px 1px 1px white;
		overflow: auto;/*自動出現滾輪條*/
	}
	.myChat {
		padding: 0px;
		list-style-type: none;		
		width: 100%;
		height : 100%;
	}
	.myChat li {
		float : left;
		width: 100%;
		height: 10%;
		margin: 0px;
	}
	.myChat img {
		width: 24px;
		height: 24px;
		position: relative;
		top: 3px;
		-moz-border-radius: 12px;
     	-webkit-border-radius: 12px;
     	border-radius: 12px;
	}
	.myChat font {
		color: white;
		width: 100px;
		position: relative;
		top: 3px;
	}
	#receive {
		text-align: left;
	}
	#receive font{
		left : 3px;
	}
	#send {
		text-align: right;
	}
	#send font {
		right : 3px;
	}
</style>
</head>
<body style="background-color: black">
	<div class="chat"></div>
	<div class="send"> 
		<input type="text" name="message" style="width: 1000px; height : 30px" onkeyup="keyEnter(event);">
		<input type="button" name="send" value="發送" onclick="send();" class="btn btn-default" >
		<!-- <input type="button" name="send" value="測試用" onclick="test();" class="btn btn-default" > -->
	</div>
	<div class="uesrList">
		<ul></ul>
	</div>
	<div class="secretChat">
		<ul></ul>
	</div>
</body>
</html>