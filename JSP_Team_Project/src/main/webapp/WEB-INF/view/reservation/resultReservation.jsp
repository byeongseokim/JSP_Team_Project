<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>행사 상세</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/reserveResult.css"/>">
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
<jsp:include page="/WEB-INF/view/common/header.jsp" flush="true"/>
    <main id="reservation">
        <div class="sub_tit_line">
            <ul>
                <li class="sub_tit_home"><a href="#">H</a></li>
                <li><a href="#">행사예약</a></li>
                <li><a href="#"></a></li>
            </ul>
        </div>
        <!--     상세페이지 영역       -->
        <div class="container_wrap">
            <section class="reservation_nav">
                <p>
                    <span>1</span>
                    <strong>나의 예약 정보</strong>
                </p>
                <p>
                    <span>2</span>
                    <strong>예약 완료</strong>
                </p>
            </section>
            <section class="reserve_ok">
                <h1>예약이 완료되었습니다!</h1>
                <p>자세한 내용은 마이페이지에서 확인하실 수 있습니다.</p>
                <p class="btn_reservation"><input id="myPage" type="button" value="마이페이지"><input id="toList" type="button" value="목록으로"></p>
            </section>
            <script>
                $(document).ready(function (){
                    $("#myPage").click(function(){
                       window.self.location="/project/myPage";
                    });
                    $("#toList").click(function(){
                        window.self.location="/project/list?page="+${page};
                    });
                })
            </script>
        </div>
    </main>

</body></html>