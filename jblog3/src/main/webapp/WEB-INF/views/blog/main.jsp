<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
			<h1>${blog.title }</h1>
			<ul>
			
				
		
             
                    <li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
             
                  <li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
				<li><a href="${pageContext.request.contextPath}/${authUser.id }/admin/basic">블로그 관리</a></li>
                
			</ul>
		</div>
	<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${firstPage.title }</h4>
					<p>
						${firstPage.contents }
					<p>
				</div>
				<ul class="blog-list">
				 <c:forEach var="posting" items="${postList}">
         <li>
    <a href="${pageContext.request.contextPath}/${authUser.id}/${categoryNo}/${posting.no}">
        ${posting.title}
    </a>
    <span style="margin-left: 10px;">${posting.reg_date}</span>
</li>
        </c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<td><img src="${pageContext.request.contextPath}${blog.logo }"></td>
			</div>
		</div>


<div id="navigation">
    <h2>카테고리</h2>
    <ul>
        <%-- 카테고리 리스트를 반복하며 각 카테고리의 이름을 링크로 출력 --%>
        <c:forEach var="category" items="${list}">
            <li><a href="<%= request.getContextPath() %>/${authUser.id }/${category.no}">
                ${category.name}
            </a></li>
        </c:forEach>
    </ul>
</div>

		
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>