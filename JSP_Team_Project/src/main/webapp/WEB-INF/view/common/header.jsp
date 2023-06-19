<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/view/common/commonFiles.jsp" flush="true"/>

<header>
    <script>
        function getCookie(cookieName) {
            cookieName = cookieName + '=';
            var cookieData = document.cookie;
            var start = cookieData.indexOf(cookieName);
            var cookieValue = '';
            if (start != -1) {
                start += cookieName.length;
                var end = cookieData.indexOf(';', start);
                if (end == -1) end = cookieData.length;
                cookieValue = cookieData.substring(start, end);
            }
            return unescape(cookieValue);
        }
    </script>
    <div id="header">
        <h1 id="logo"><a href="<c:url value="/"/>">SEOUL<br>FESTIVAL</a></h1>
        <nav id="gnb">
            <ul>
                <li><a href="/project/fesAnno">축제안내</a></li>
                <li><a href="/project/list">예약하기</a></li>
                <li><a href="/project/notice">공지사항</a></li>
                <li><a href="/project/myPage">마이페이지</a></li>
            </ul>
        </nav>
        <div id="header_login">
            <c:choose>
                <c:when test="${sessionScope.get('user')==null}">
                    <a href="/project/login" class="u_on">로그인</a>
                    <a href="/project/join" class="u_on">회원가입</a>
                </c:when>
                <c:when test="${sessionScope.get('user').equals('admin')}">
                    <a href="/project/logout" class="u_admin">로그아웃</a>
                    <a href="/project/admin" class="u_admin">관리자페이지</a>
                </c:when>
                <c:otherwise>
                    <a href="/project/logout" class="u_out">로그아웃</a>
                    <a href="/project/myPage" class="u_out">마이페이지</a>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="user_name u_out"><strong>${sessionScope.get('user')}</strong> 님, 안녕하세요!</div>
    </div>
</header>