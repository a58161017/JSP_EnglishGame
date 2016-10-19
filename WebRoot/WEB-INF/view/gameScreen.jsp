<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>英文單字接龍小遊戲</title>
<style>
.tr-1 {background-color:#006600;color:#FFFFFF;font-size:20pt;word-spacing:-3px;letter-spacing:0px;-letter-spacing:-1px;}
.tr-2 {background-color:#ffbb99;color:#000000;font-size:15pt;word-spacing:-3px;letter-spacing:0px;-letter-spacing:-1px;}
.tr-3 {background-color:#993300;color:#FFFFFF;font-size:12pt;word-spacing:-3px;letter-spacing:0px;-letter-spacing:-1px;}
</style>
<script>
	function init(){
		showMsg();
		lockOtherPlayer();
	}
	
	function lockOtherPlayer(){
		var leader = form.leader.value;
		var peopleNum = Number(form.peopleNum.value);
		for(var i=1; i<=peopleNum; i++){
			if(i.toString() != leader) document.getElementById('player'+i).disabled = true;
		}
	}
	
	function validate(){
		var leader = form.leader.value;
		var player = document.getElementById('player'+leader);
		if(player.value != ''){
			var regx = /^[a-zA-Z]+$/;
		    if (!regx.test(player.value)) {
		    	alert('只能輸入英文字');
		    	player.value = '';
		    	player.focus();
		    	return false;
		    }
		}else{
			alert('請輸入英文單字');
			player.focus();
			return false;
		}
	}
	
	function showMsg(){
		var isFirstEntry = form.isFirstEntry.value;
		if(isFirstEntry == 'Y'){
			form.gameMsg.value = '歡迎進入到「英文單字接龍」小遊戲';
			form.gameMsg.value += '\n本遊戲是由「kiuno」所製作 Ver1.0';
			form.gameMsg.value += '\n';
		}else{
			form.gameMsg.value = '${gameInfo.msg}';
		}
	}
</script>
</head>
<body onload="init()">
<form name="form" action="englishGame.do?action=checkAlph" method="post" onsubmit="return validate()">
	<input type="hidden" name="gameMode" value="${gameInfo.gameMode}">
	<input type="hidden" name="peopleNum" value="${gameInfo.peopleNum}">
	<input type="hidden" name="leader" value="${gameInfo.leader}">
	<input type="hidden" name="isFirstEntry" value="${gameInfo.isFirstEntry}">
	<input type="hidden" name="isFirstInput" value="${gameInfo.isFirstInput}">
	<input type="hidden" name="computer" value="${gameInfo.computer}">
	
	<table width="100%">
		<tr class="tr-1"><td colspan="2" align="center">單人模式</td></tr>
		<c:forEach var="i" begin="1" end="${gameInfo.peopleNum}">
  			<tr class="tr-2"><td colspan="2">
  				 玩家${i}：<input type="text" id="player${i}" name="player${i}" size="15">
  				 ${i==1?'&nbsp<input type="submit" value="送出">':''}
  			</td></tr>
		</c:forEach>
		<c:if test="${gameInfo.gameMode=='1'}">
   			<tr class="tr-2"><td colspan="2">電腦1：${gameInfo.computer}</td></tr>
		</c:if>
		<tr class="tr-3">
			<td>遊戲訊息:</td>
			<td>指令說明:</td>
		</tr>
		<tr class="tr-3">
			<td><textarea name="gameMsg" rows="12" cols="70"></textarea></td>
			<td>
				${menuInfo.gameMode=='1'?'\\r 開啟/關閉 記錄電腦所使用單字<br>':''}
				\c 得到隨機單字的救援<br>
				\e 離開這場對局<br>
				\h 叫出指令清單<br>
				\s 該回合玩家認輸投降<br>
				\z 進入到印象單字補充系統<br>
				\c 得到隨機單字的救援<br>
			</td>
		</tr>
	</table>
</form>
</body>
</html>