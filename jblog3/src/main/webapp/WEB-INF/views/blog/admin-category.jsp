<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
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
					<c:when test='${empty authUser }'>
						<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
						  <c:if test="${authUser.id == blogId}">
                <li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">블로그 관리</a></li>
            </c:if>
						
					</c:otherwise>
				</c:choose>
			</ul>
      
        </div>
        <div id="wrapper">
            <div id="content" class="full-screen">
                <ul class="admin-menu">
                    <li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">기본설정</a></li>
                    <li class="selected">카테고리</li>
                    <li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/write">글작성</a></li>
                </ul>
                <table class="admin-cat">
                    <tr>
                        <th>번호</th>
                        <th>카테고리명</th>
                        <th>포스트 수</th>
                        <th>설명</th>
                        <th>삭제</th>                     
                    </tr>
                    <c:forEach var="category" items="${list}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${category.name}</td>
                              <td>${category.postCount}</td>
                            <td>${category.description }</td>
             
             <td>
    <a href="${pageContext.request.contextPath}/${authUser.id}/admin/category/delete/${category.no}">
        <img src="${pageContext.request.contextPath}/assets/images/delete.jpg" alt="삭제">
    </a>
</td>
  
                        
                        </tr>
                    </c:forEach>
                </table>
                
                <h4 class="n-c">새로운 카테고리 추가</h4>
                <form action="${pageContext.request.contextPath}/${authUser.id }/admin/category/register" method="post">
                    <table id="admin-cat-add">
                        <tr>
                            <td class="t">카테고리명</td>
                            <td><input type="text" name="name" required></td>
                        </tr>
                        <tr>
                            <td class="t">설명</td>
                            <td><input type="text" name="description" required></td>
                        </tr>
                        <tr>
                            <td class="s">&nbsp;</td>
                            <td><input type="submit" value="카테고리 추가"></td>
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