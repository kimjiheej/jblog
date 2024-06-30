<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
  <div id="container">
      <div id="header">
    <h1>${blog.title}</h1>
    <ul>
        <sec:authorize access="isAnonymous()">
            <!-- 로그인이 되어 있지 않은 경우 -->
            <li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <!-- 로그인이 되어 있는 경우 -->
            <li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
            <sec:authentication property="principal" var="user"/>
            <li><a href="${pageContext.request.contextPath}/${user.id}/admin/basic">블로그 관리</a></li>
        </sec:authorize>
    </ul>
</div>
		
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li class="selected">기본설정</li>
					<li><a href="${pageContext.request.contextPath}/${user.id }/admin/category">카테고리</a></li>
					<li><a href="${pageContext.request.contextPath}/${user.id }/admin/write">글작성</a></li>
				</ul>
				<form action="${pageContext.request.contextPath}/${user.id }/admin/basic/update" method="post"  enctype="multipart/form-data">
	 		      	<table class="admin-config">
			      		<tr>
			      			<td class="t">블로그 제목</td>
			      			<td><input type="text" size="40" name="title" value="${blog.title }"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">로고이미지</td>
			      			<td><img src="${pageContext.request.contextPath}${blog.logo }"></td>
			      		</tr>      		
			      		<tr>
			      			<td class="t">&nbsp;</td>
			      			<td><input type="file" name="logo-file"></td>      			
			      		</tr>           		
			      		<tr>
			      			<td class="t">&nbsp;</td>
			      			<td class="s"><input type="submit" value="기본설정 변경"></td>      			
			      		</tr>           		
			      	</table>
				</form>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>${blog.title }</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>