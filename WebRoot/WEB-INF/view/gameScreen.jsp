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
		    	alert('�u���J�^��r');
		    	player.value = '';
		    	player.focus();
		    	return false;
		    }
		}else{
			alert('�п�J�^���r');
			player.focus();
			return false;
		}
	}
	
	function showMsg(){
		var isFirstEntry = form.isFirstEntry.value;
		if(isFirstEntry == 'Y'){
			form.gameMsg.value = '�w��i�J��u�^���r���s�v�p�C��';
			form.gameMsg.value += '\n���C���O�ѡukiuno�v�һs�@ Ver1.0';
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
		<tr class="tr-1"><td colspan="2" align="center">��H�Ҧ�</td></tr>
		<c:forEach var="i" begin="1" end="${gameInfo.peopleNum}">
  			<tr class="tr-2"><td colspan="2">
  				 ���a${i}�G<input type="text" id="player${i}" name="player${i}" size="15">
  				 ${i==1?'&nbsp<input type="submit" value="�e�X">':''}
  			</td></tr>
		</c:forEach>
		<c:if test="${gameInfo.gameMode=='1'}">
   			<tr class="tr-2"><td colspan="2">�q��1�G${gameInfo.computer}</td></tr>
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
				\h �s�X���O�M��<br>
				\s �Ӧ^�X���a�{��뭰<br>
				\z �i�J��L�H��r�ɥR�t��<br>
				\c �o���H����r���ϴ�<br>
			</td>
		</tr>
	</table>
</form>
</body>
</html>