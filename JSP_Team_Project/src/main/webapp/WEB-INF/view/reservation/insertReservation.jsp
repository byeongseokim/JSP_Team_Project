<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>행사 상세</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/reservation.css"/>">
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
                <h2>제목</h2>
            </section>
            <section class="reservation_info">
                <form id="resForm" action="<c:url value="/reservation"/>" method="post">
                    <input type="hidden" name="page" value="${requestScope.page}">
                    <input type="hidden" name="id" value="${requestScope.id}">
                    <input type="hidden" name="resDate" value="${requestScope.resDate}">
                    <input type="hidden" name="resCnt" value="${requestScope.resCnt}">
                    <input type="hidden" name="cno" value="${requestScope.cno}">
                    <input type="hidden" name="resPrice" value="${requestScope.resPrice}">

                    <ul class="reservation_wrap">
                        <li class="reservation_date">
                            <h3>예약 날짜</h3>
                            <ul class="caution">
                                <li>유의사항</li>
                                <li>- 원하는 날짜를 선택하세요.</li>
                                <li>- 10명 이상은 상담센터를 통해 따로 예약 신청 부탁드립니다.</li>
                            </ul>
                            <%--     캘린더      --%>
                            <jsp:include page="/WEB-INF/view/common/calendar.jsp" flush="true"/>
                            <p>
                                <strong>선택한 날짜:</strong>
                                <span id="cal_getDate">
                                    <fmt:formatDate value="${requestScope.reservationVO.getResDate()}" pattern="yyyy/MM/dd" var="regDate"/>${regDate}
                                </span>
                            </p>
                        </li>
                        <li class="reservation_num">
                            <h3>신청할 인원: <c:out value="${requestScope.reservationVO.getResCnt()}명"/></h3>
                            <p class="select_number">
                                <span>
                                   <input type="hidden" id="useNum" value="${requestScope.reservationVO.getResCnt()}" />
                                </span>
                            </p>
                        </li>
                        <li class="reservation_user_info">
                            <h3>신청자 정보</h3>
                            <table>
                                <tr><td>이름</td><td><input type="text" value="${requestScope.userInfo.getName()}" readonly></td></tr>
                                <tr><td>전화번호</td><td><input type="text" value="${requestScope.userInfo.getPhone()}" readonly></td></tr>
                                <tr><td>이메일</td><td><input type="text" value="${requestScope.userInfo.getEmail()}" readonly></td></tr>
                                <tr><td>결제요금</td><td><input type="text"
                                                            value="${requestScope.price==0?'무료':requestScope.reservationVO.getResCnt()*requestScope.price}" readonly></td></tr>
                            </table>
                        </li>
                    </ul>
                </form>
            </section>
            <section id="myInfo">
               <div>
                    <h3>나의 예약 정보</h3>
                    <ul>
                        <li class="title">한강야생탐사센터</li>
                        <li><span>이용일자</span><strong><fmt:formatDate value="${requestScope.reservationVO.getResDate()}" pattern="yyyy/MM/dd" var="regDate"/>${regDate}</strong></li>
                        <li><span>신청인원</span><strong><c:out value="${requestScope.reservationVO.getResCnt()}명"/></strong></li>
                        <li><span>결제요금</span><strong><c:out value="${requestScope.price==0?'무료':requestScope.reservationVO.getResCnt()*requestScope.price}" /></strong></li>
                    </ul>
                   <p class="btn_reservation"><input id="resBtn" value="예약하기" /></p>
                   <script>
                       $(document).ready(function(){
                          $("#resBtn").click(function(){
                              $("#resForm").submit();
                          });
                       });
                   </script>
                </div>
            </section>
        </div>
    </main>

</body></html>