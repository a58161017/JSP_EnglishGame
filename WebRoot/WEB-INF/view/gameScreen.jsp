<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>�^���r���s�p�C��</title>
<style>
.tr-1 {background-color:#006600;color:#FFFFFF;font-size:20pt;word-spacing:-3px;letter-spacing:0px;-letter-spacing:-1px;}
.tr-2 {background-color:#ffbb99;color:#000000;font-size:15pt;word-spacing:-3px;letter-spacing:0px;-letter-spacing:-1px;}
.tr-3 {background-color:#993300;color:#FFFFFF;font-size:12pt;word-spacing:-3px;letter-spacing:0px;-letter-spacing:-1px;}
</style>
<script>
	var myTimer1;
	var myTimer2;
	var timer1 = 5000; //�q����Үɶ�
	var timer2 = 500; //��ܹq�����b��Ҫ��ɶ�
	
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
		    	alert('�u���J�^��r�Ϋ��O�Ÿ�');
		    	player.value = '';
		    	player.focus();
		    	return false;
		    }
		}else{
			alert('�п�J�^���r�Ϋ��O�Ÿ�');
			player.focus();
			return false;
		}
	}
	
	function showMsg(){
		var isFirstEntry = form.isFirstEntry.value;
		if(isFirstEntry == 'Y'){
			form.gameMsg.value = '�w��i�J��������u�^���r���s�v�p�C��';
			form.gameMsg.value += '\n���C���O�ѡukiuno�v�һs�@ Ver1.0';
			form.gameMsg.value += '\n';
		}else{
			var gameMode = form.gameMode.value;
			var leader = form.leader.value;
			var notifyNextPerson = '';
			var isExit = '${gameInfo.isExit}';
			if(isExit != 'Y'){
				if(gameMode == '1'){
					if(leader == '0') notifyNextPerson = '�o�^�X����q��~~\n�q�����b���.'
					else notifyNextPerson = '�o�^�X���쪱�a1'
				}else{
					notifyNextPerson = '�o�^�X���쪱�a'+leader;
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
			if(confirm('�O�_���s�@��?')){
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
		<tr class="tr-1"><td colspan="2" align="center">��H�Ҧ�</td></tr>
		<c:forEach var="i" begin="1" end="${gameInfo.peopleNum}">
  			<tr class="tr-2"><td colspan="2">
  				 ���a${i}�G<input type="text" id="player${i}" name="player${i}" value="${gameInfo.hisWord[i]}" size="15">
  				 <c:if test="${gameInfo.gameMode=='2'}">
  				 	${gameInfo.playersSurrender[i-1]==1?"<font color='red'>�w�{��</font>":"<font color='green'>�٬���</font>"}
  				 </c:if>
  				 ${i==1?'&nbsp<input type="submit" value="�e�X">':''}
  			</td></tr>
		</c:forEach>
		<c:if test="${gameInfo.gameMode=='1'}">
   			<tr class="tr-2"><td colspan="2">�q��1�G${gameInfo.hisWord[0]}</td></tr>
		</c:if>
		<tr class="tr-3">
			<td>�C���T��:</td>
			<td>���O����:</td>
		</tr>
		<tr class="tr-3">
			<td><textarea name="gameMsg" rows="12" cols="70"></textarea></td>
			<td>
				${menuInfo.gameMode=='1'?'\\r �}��/���� �O���q���Ҩϥγ�r<br>':''}
				\c �o���H����r���ϴ�<br>
				\e ���}�o���什<br>
				\s �Ӧ^�X���a�{��뭰<br>
				\z �i�J��L�H��r�ɥR�t��<br>
			</td>
		</tr>
	</table>
</form>
</body>
</html>