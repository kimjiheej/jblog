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
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		<ul class="menu">
			  <sec:authorize access="isAnonymous()">
            <!-- 로그인이 되어 있지 않은 경우 -->
            <li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
            <li><a href="${pageContext.request.contextPath}/user/join">회원가입</a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <!-- 로그인이 되어 있는 경우 -->
            <li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
            <li><a href="${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}">내 블로그</a></li>
        </sec:authorize>
		</ul>
		<form class="search-form">
			<fieldset>
				<input type="text" name="keyword" />
				<input type="submit" value="검색" />
			</fieldset>
			<fieldset>
				<input type="radio" name="which" value="blog-title"> <label>블로그 제목</label>
				<input type="radio" name="which" value="tag"> <label>태그</label>
				<input type="radio" name="which" value="blog-user"> <label>블로거</label>
			</fieldset>
		</form>
	</div>
</body>
</html>