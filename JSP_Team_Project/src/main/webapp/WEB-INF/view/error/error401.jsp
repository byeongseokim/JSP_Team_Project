<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: kimnamhyun
  Date: 2022/10/27
  Time: 11:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%response.setStatus(HttpServletResponse.SC_OK);%>
<html>
<!DOCTYPE html>

<head>
    <title>Title</title>
    <script src="<c:url value="/resources/javascript/jquery-3.6.0.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/reset.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>">
   <style>
        .error_wrap{text-align: center; 
            padding: 100px 0; margin: 0 auto; max-width: 1200px;
        }
        .error_wrap h1{
            font-size: 4em;
            margin-bottom: 50px;
            color: #793dea;
        }
        
        .error_wrap h2{font-size: 1.7em; margin-bottom: 50px;}
        .error_wrap strong{margin-bottom: 50px; display: block; color: #999;}
        .error_wrap>p{background-color: #ece8ef; padding: 20px 0; margin-bottom: 5px;}
        .error_wrap a{display: block;  font-weight: bold; cursor: pointer; color: #793dea;}
        .error_wrap a:hover{text-decoration: underline;}
        #error_txt{display: none; margin-top: 30px; color: #999;}
    </style>
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
        $(document).ready(function (){
            //common.jsp 에서 실행될땐 바디태그 아래부분이라 로그인시 바로 적용인 안되서 옮김
            let user = '<c:out value="${sessionScope.get('user')}"/>';
            console.log("logined_cookie: : " + getCookie("logined_cookie").valueOf());
            console.log("user: " + user);

        })
        //에러 내용 표시
        function showTxt(){
            document.getElementById("error_txt").style.display="block";
        }
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/view/common/header.jsp" flush="true"/>
<!--<script src="/resources/javascript/header_sub.js"></script>-->
<main>
    <div class="sub_tit_line">
        <ul>
            <li class="sub_tit_home"><a href="/project">H</a></li>
            <li><a href="#">ERROR</a></li>
        </ul>
    </div>
    <div class="error_wrap">
       <div>
          <h1>401 ERROR</h1>
           <h2> UNAUTHORIZED.</h2>
           <strong> 로그인이 필요한 페이지 입니다. <br> 다시 한 번 확인해 주세요.</strong>
       </div>
<%--       <p>--%>
<%--           <a onclick="showTxt()">에러 오류 확인:</a>--%>
<%--           <span id="error_txt">에러 내용 삽입</span>--%>
<%--       </p>--%>
       
       
    </div>

</main>
</body>
</html>

