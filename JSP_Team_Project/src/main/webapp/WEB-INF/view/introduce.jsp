<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>SEOUL FESTIVAL</title>
  <style>
    .introduce{
      width: 100%; min-width: 1200px; height: auto; overflow-y: hidden;}
    .introduce img{
      max-width: 100%;
    }
  </style>
    <script src="<c:url value="/resources/javascript/jquery-3.6.0.min.js"/>"></script>
</head>
<script>
    $(document).ready(function (){
        //common.jsp 에서 실행될땐 바디태그 아래부분이라 로그인시 바로 적용인 안되서 옮김
        let user = '<c:out value="${sessionScope.get('user')}"/>';
        console.log("logined_cookie: : " + getCookie("logined_cookie").valueOf());
        console.log("user: " + user);

    })
</script>
<body>
<jsp:include page="/WEB-INF/view/common/header.jsp" flush="true" />
<div class="introduce">
  <img src="<c:url value="/resources/images/introduce.png"/>" alt="">
</div>
</body>
</html>
