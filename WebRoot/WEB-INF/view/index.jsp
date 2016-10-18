<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String realPath = request.getRealPath("");
System.out.println("realPath = " + realPath);
realPath = realPath.replaceAll("\\\\", "/");
realPath = realPath.replaceAll("\\\\", "/");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>英文單字接龍小遊戲</title>
<script>
	window.open('menu.do?action=goHeadMenu&path=<%=realPath%>','英文單字接龍小遊戲',"directories=0,titlebar=0,toolbar=0,location=0,status=0,menubar=0,scrollbars=no,resizable=no,width=600,height=350");
	window.open('','_self','');
	window.close();
</script>
</head>
<body></body>
</html>