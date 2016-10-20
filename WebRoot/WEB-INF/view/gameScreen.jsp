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
	var myTimer1;
	var myTimer2;
	var timer1 = 5000; //電腦思考時間
	var timer2 = 500; //顯示電腦正在思考的時間
	
	function init(){
		showMsg();
		lockOtherPlayer();
		checkIsExitGame();
	}
	
	function lockOtherPlayer(){
		var leader = form.leader.value;
		var peopleNum = Number(form.peopleNum.value);
		for(var i=1; i<=peopleNum; i++){
			if(i.toString() != leader) document.getElementById('player'+i).disabled = true;
			else document.getElementById('player'+i).focus();
		}
		if(leader == '0'){
			myTimer1 = setTimeout("computerAction()",timer1);
			myTimer2 = setTimeout("computerThink()",timer2);
		}
	}
	
	function validate(){
		var leader = form.leader.value;
		var player = document.getElementById('player'+leader);
		if(player.value != ''){
			var regx = /^[a-zA-Z\\]+$/;
		    if (!regx.test(player.value)) {
		    	alert('只能輸入英文字或指令符號');
		    	player.value = '';
		    	player.focus();
		    	return false;
		    }
		}else{
			alert('請輸入英文單字或指令符號');
			player.focus();
			return false;
		}
	}
	
	function showMsg(){
		var isFirstEntry = form.isFirstEntry.value;
		if(isFirstEntry == 'Y'){
			form.gameMsg.value = '歡迎進入到網頁版「英文單字接龍」小遊戲';
			form.gameMsg.value += '\n本遊戲是由「kiuno」所製作 Ver1.0';
			form.gameMsg.value += '\n';
		}else{
			var gameMode = form.gameMode.value;
			var leader = form.leader.value;
			var notifyNextPerson = '';
			var isExit = '${gameInfo.isExit}';
			if(isExit != 'Y'){
				if(gameMode == '1'){
					if(leader == '0') notifyNextPerson = '這回合輪到電腦~~\n電腦正在思考.'
					else notifyNextPerson = '這回合輪到玩家1'
				}else{
					notifyNextPerson = '這回合輪到玩家'+leader;
				}
			}
			form.gameMsg.value = '${gameInfo.msg}\n'+notifyNextPerson;
		}
	}
	
	function computerAction(){
		clearTimeout(myTimer1);
		clearTimeout(myTimer2);
		form.submit();
	}
	
	function computerThink(){
		form.gameMsg.value = form.gameMsg.value + '.';
		myTimer2 = setTimeout("computerThink()",timer2);
	}
	
	function checkIsExitGame(){
		var isExit = '${gameInfo.isExit}';
		if(isExit == 'Y'){
			if(confirm('是否重新一局?')){
				form.action = 'englishGame.do?action=startGame';
				form.target = '_top';
				form.submit();
			}else{
				top.close();
			}
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
  				 玩家${i}：<input type="text" id="player${i}" name="player${i}" value="${gameInfo.hisWord[i]}" size="15">
  				 <c:if test="${gameInfo.gameMode=='2'}">
  				 	${gameInfo.playersSurrender[i-1]==1?"<font color='red'>已認輸</font>":"<font color='green'>還活著</font>"}
  				 </c:if>
  				 ${i==1?'&nbsp<input type="submit" value="送出">':''}
  			</td></tr>
		</c:forEach>
		<c:if test="${gameInfo.gameMode=='1'}">
   			<tr class="tr-2"><td colspan="2">電腦1：${gameInfo.hisWord[0]}</td></tr>
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
				\s 該回合玩家認輸投降<br>
				\z 進入到印象單字補充系統<br>
			</td>
		</tr>
	</table>
</form>
</body>
</html>