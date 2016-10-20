<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>英文單字接龍小遊戲</title>
<style>
table, tr, td {
   border: 1px solid black;
}

.button {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 20px;
}
</style>
<script>
	var width = 800;
	var height = 600;
	window.resizeTo(width, height);
	window.moveTo(((screen.width - width) / 2), ((screen.height - height) / 2))
	
	function setGameMode(mode){
		var result = true;
		var peopleNum = 1;
		var maxUsers = Number('${maxUsers}');
		if(mode == 2){
			peopleNum = prompt("請輸入遊戲人數(2-"+maxUsers+"人): ", "2");
			var regx = /^[0-9]+$/;
		    if (!regx.test(peopleNum)) {
		    	alert('請輸入正確的數值!!');
		    	result = false;
		    }
		}
		if(result){
			var param = 'action=startGame&gameMode='+mode+'&peopleNum='+peopleNum;
			window.open('englishGame.do?'+param,'英文單字接龍小遊戲',"directories=0,titlebar=0,toolbar=0,location=0,status=0,menubar=0,scrollbars=no,resizable=no,width=800,height=600");
		}
	}
</script>
</head>
<body>
<table width="95%" align="center">
	<tr><td align="center">進入遊戲前請於下方選擇「遊戲模式」</td></tr>
	<tr><td align="center"><input type="button" class="button" value="單人模式" onclick="setGameMode(1)"></td></tr>
	<tr><td align="center"><input type="button" class="button" value="多人模式" onclick="setGameMode(2)"></td></tr>
	<tr><td align="center"><input type="button" class="button" value="離開遊戲" onclick="window.close()"></td></tr>
</table>
</body>
</html>