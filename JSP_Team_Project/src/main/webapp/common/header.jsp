<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="../common/commonFiles.jsp" flush="true"/>
<script src="javascript/jquery-3.6.0.min.js"></script>

<header>
    <div id="header">
        <h1 id="logo"><a href="<c:url value="/"/>">SEOUL<br>FESTIVAL</a></h1>
        <nav id="gnb">
            <ul>
                <li><a href="#">축제안내</a></li>
                <li><a href="/project/list">예약하기</a></li>
                <li><a href="/project/notice">공지사항</a></li>
                <li><a href="/project/myPage">마이페이지</a></li>
            </ul>
        </nav>
        <div id="header_login">
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

            //로그인,로그아웃 체크해서 버튼 나오게 하기
            $(document).ready(function (){
                let data = '<c:out value="${sessionScope.get('user')}"/>';
                console.log("getCookie length: " + getCookie("logined_cookie").valueOf().length);
                console.log("data: " + data);
                if(data === ""){
                    //alert('로그아웃 상태');
                    $(".u_out").css("display","none");
                    $(".u_on").css("display","inline-block");
                } else{
                    //alert('로그인 상태');
                    $(".u_out").css("display","inline-block");
                    $(".u_on").css("display","none");
                }
            })
            </script>

            <a href="/project/logout" class="u_out">로그아웃</a>
            <a href="/project/myPage" class="u_out">마이페이지</a>
            <a href="/project/login" class="u_on">로그인</a>
            <a href="/project/join" class="u_on">회원가입</a>
        </div>
        <div class="user_name u_out"><strong>${sessionScope.get('user')}</strong> 님, 안녕하세요!</div>
    </div>
</header>