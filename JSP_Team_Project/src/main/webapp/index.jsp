<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>축제</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
    <script src="<c:url value="/resources/javascript/jquery-3.6.0.min.js"/>"></script>
</head>
<script>
    $(document).ready(function (){
        let query = window.location.search;
        let param = new URLSearchParams(query);
        let msg = param.get('msg');
        if(msg!=null&&msg.length>0){
            alert(msg);
        }
        //common.jsp 에서 실행될땐 바디태그 아래부분이라 로그인시 바로 적용인 안되서 옮김
        let user = '<c:out value="${sessionScope.get('user')}"/>';
        console.log("logined_cookie: : " + getCookie("logined_cookie").valueOf());
        console.log("user: " + user);

    })
</script>
<body>
    <jsp:include page="/WEB-INF/view/common/header.jsp" flush="true"/>
    <main>
        <div id="main">
            <section id="visual">
               <div class="main_bg">
                   <p class="bg00"></p>
                   <p class="bg01"></p>
                   <p class="bg02"></p>
                   <p class="bg03"></p>
               </div>
               <p class="main_txt">SEOUL FESTIVAL</p>
            </section>
        </div>
    </main>
</body>
</html>
