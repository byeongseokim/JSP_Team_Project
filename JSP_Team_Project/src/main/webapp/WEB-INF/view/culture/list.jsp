<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>행사 목록</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/sub.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/list.css"/>">
    <script src="<c:url value="/resources/javascript/jquery-3.6.0.min.js"/>"></script>
</head>
<script>
    window.onpageshow = function(event) {
        //back 이벤트 일 경우
        if (event.persisted) {
            location.reload(true);
        }
    }
    $(document).ready(function (){
        //common.jsp 에서 실행될땐 바디태그 아래부분이라 로그인시 바로 적용인 안되서 옮김
        let user = '<c:out value="${sessionScope.get('user')}"/>';
        console.log("logined_cookie: : " + getCookie("logined_cookie").valueOf());
        console.log("user: " + user);

    })
    let query = window.location.search;
    let param = new URLSearchParams(query);
    let msg = param.get('msg');
    if(msg!=null){
        alert(msg)
    }
</script>
<body>
    <jsp:include page="/WEB-INF/view/common/header.jsp" flush="true"/>

    <main id="festival_list">
        <div class="sub_tit_wrap">
            <div class="sub_tit_inner">
                <h2>2023 행사 예약</h2>
            </div>
        </div>
        <!--     게시판 검색 영역       -->
        <div class="container_wrap">
            <div id="search_wrap">
                <input type="text" id="list_search" placeholder="검색어 입력" class="list_search">
                <button type="submit" id="list_search_btn">검색</button>
                <script>
                    $(document).ready(function (){
                        $("#list_search_btn").click(function (){
                            // let keyword = $("input[name=List_search]").val();
                            let keyword = $("#list_search").val();
                            window.self.location="/project/cultureSearch?keyword="+keyword;
                        });
                    });
                </script>
            </div>
            <ul id="list_wrap">
<%--                <c:forEach items="${requestScope.pageResponse.getPageList()}" var="culture">--%>
<%--                    <script>--%>
<%--                        $(function(){--%>
<%--                            let StringOpn = '<c:out value="${culture.getSvc_opn_bgn_dt()}"/>';--%>
<%--                            let opn_bgn_dt = new Date(StringOpn).toLocaleDateString();--%>
<%--                            let StringEnd = '<c:out value="${culture.getSvc_opn_end_dt()}"/>';--%>
<%--                            let opn_end_dt = new Date(StringEnd).toLocaleDateString();--%>
<%--                            $(".detail_date2").html(opn_bgn_dt+' ~ '+opn_end_dt);--%>


<%--                            let StringOpn3 = '<c:out value="${culture.getRcpt_bgn_dt()}"/>';--%>
<%--                            let opn_bgn_dt3 = new Date(StringOpn3).toLocaleDateString();--%>
<%--                            let StringEnd3 = '<c:out value="${culture.getRcpt_end_dt()}"/>';--%>
<%--                            let opn_end_dt3 = new Date(StringEnd3).toLocaleDateString();--%>
<%--                            $(".detail_date3").html(opn_bgn_dt3+' ~ '+opn_end_dt3);--%>
<%--                        })--%>
<%--                    </script>--%>
<%--                </c:forEach>--%>
                <c:forEach items="${requestScope.pageResponse.getPageList()}" var="culture">
                    <li>
                        <a href="<c:url value="/detail?cno=${culture.getCno()}&page=${requestScope.pageResponse.page}"/>">
                            <p id="list_img">
                                <img src="${culture.getImg_url()}" alt=""> </p>
                            <div class="list_box">
                                <p id="list_pro_name">${culture.getSvc_nm()}</p>
                                <ul id="list_pro_info">
                                    <li><strong>장소명</strong><span>${culture.getPlace_nm()}</span></li>
                                    <li><strong>이용대상</strong><span>${culture.getUse_tgt_info()}</span></li>
                                    <li><strong>접수기간</strong><span>${culture.getRcpt_bgn_dt().substring(0,10)} ~ ${culture.getRcpt_end_dt().substring(0,10)}</span></li>
                                    <li><strong>이용기간</strong><span>${culture.getSvc_opn_bgn_dt().substring(0,10)} ~ ${culture.getSvc_opn_end_dt().substring(0,10)}</span></li>
                                </ul>
                            </div>
                        </a>
                    </li>
                </c:forEach>
                <div class="nav">
                    <ul>
                        <c:if test="${requestScope.pageResponse.isShowPrev()}">
                            <li class="nav_prev">
                                <a href="<c:url value="/list?page=${requestScope.pageResponse.page-1}&size=${requestScope.pageResponse.size}"/>">
                                    [PREV]
                                </a>
                            </li>
                        </c:if>
                        <c:forEach begin="${requestScope.pageResponse.start}" end="${requestScope.pageResponse.end}" var="num">
                            <li>
                                <a href="<c:url value="list?page=${num}&size=${requestScope.pageResponse.size}"/>"
                                   class="${num==requestScope.pageResponse.page?'sel':''}">${num} </a>
                            </li>
                        </c:forEach>
                        <c:if test="${requestScope.pageResponse.isShowNext()}">
                            <li class="nav_next">
                                <a href="<c:url value="list?page=${requestScope.pageResponse.page+1}&size=${requestScope.pageResponse.size}"/>">
                                    [NEXT]
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </ul>
        </div>

    </main>
</body></html>