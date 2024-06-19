<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
    <div id="container">
        <div id="header">
            <h1>${blog.title }</h1>
            <ul>
                 <c:choose>
        <c:when test="${check}">
            <li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
            <li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">블로그 관리</a></li>
        </c:when>
        <c:otherwise>
            <li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
        </c:otherwise>
    </c:choose>
            </ul>
        </div>
        <div id="wrapper">
            <div id="content">
                <div class="blog-content">
                    <h4>${firstPage.title }</h4>
                    <p>${firstPage.contents}</p>
                </div>
                <ul class="blog-list">
                    <c:forEach var="posting" items="${postList}">
                        <li>
                            <a href="${pageContext.request.contextPath}/${blogId}/${categoryNo}/${posting.no}">
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
                <img src="${pageContext.request.contextPath}${blog.logo}">
            </div>
        </div>

        <div id="navigation">
            <h2>카테고리</h2>
            <ul>
                <c:forEach var="category" items="${list}">
                    <li><a href="${pageContext.request.contextPath}/${blogId}/${category.no}">
                        ${category.name}
                    </a></li>
                </c:forEach>
            </ul>
        </div>

        <div id="footer">
            <p><strong>${blog.title }</strong> is powered by JBlog (c)2016</p>
        </div>
    </div>
</body>
</html>