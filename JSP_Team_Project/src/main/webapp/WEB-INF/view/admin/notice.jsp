<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항</title>
    <jsp:include page="/WEB-INF/view/common/commonFiles.jsp" flush="true"/>
    <script src="<c:url value="/resources/javascript/jquery-3.6.0.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/sub.css"/>">
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
    <main id="notice">
        <!--    서브메뉴 타이틀 영역    -->
        <div class="sub_tit_line">
            <ul>
                <li class="sub_tit_home"><a href="/project">H</a></li>
                <li><a href="/project/notice">고객센터</a></li>
                <li><a href="#">공지사항</a></li>
            </ul>
        </div>
        <h2 class="sub_h2_tit">공지사항</h2>
        <!--        게시판 시작   -->
        <div class="container_wrap">
            <!--     공지 카테고리 영역       -->
            <div class="container_inner">
                <ul class="notice_category">
                    <li class="on"><a href="${pageContext.request.contextPath}/WEB-INF/view/admin/notice.jsp">공지사항</a></li>
                    <li><a href="${pageContext.request.contextPath}/qnaList">Q&A 게시판</a></li>
                </ul>
            </div>
             <!--     게시판 목록 영역       -->
            <div id="board_wrap">
                <table id="list_wrap">
                    <tr>
                        <th class="bo_num">번호</th>
                        <th class="bo_tit" width="650">제목</th>
                        <th class="bo_writer">글쓴이</th>
                        <th class="bo_comments">댓글</th>
                        <th class="bo_cnt">조회수</th>
                        <th class="bo_regDate">등록일</th>
                    </tr>
                    <c:forEach items="${requestScope.pageResponse.getPageList()}" var="qna">
                        <tr>
                            <td class="bo_num"><c:out value="${qna.getQqno()}"/></td>
                            <td class="bo_tit"><a href="<c:url value='/qnaDetail?qqno=${qna.getQqno()}&page=${requestScope.pageResponse.getPage()}'/>"><c:out value="${qna.getTitle()}"/></a></td>
                            <td class="bo_writer"><c:out value="${qna.getId()}"/></td>
                            <td class="bo_comments"><c:out value="${qna.getCommentCnt()}"/></td>
                            <td class="bo_cnt"><c:out value="${qna.getCnt()}"/></td>
                            <td class="bo_regDate"><fmt:formatDate value="${qna.getRegDate()}" pattern="yyyy/MM/dd" var="regDate"/>${regDate}</td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="nav">
                    <ul>
                        <c:if test="${requestScope.pageResponse.isShowPrev()}">
                            <li class="nav_prev">
                                <a href="<c:url value="/qnaList?page=${requestScope.pageResponse.page-1}&size=${requestScope.pageResponse.size}"/>">
                                    [PREV]
                                </a>
                            </li>
                        </c:if>
                        <c:forEach begin="${requestScope.pageResponse.start}" end="${requestScope.pageResponse.end}" var="num">
                            <li>
                                <a href="<c:url value="/qnaList?page=${num}&size=${requestScope.pageResponse.size}"/>"
                                   class="${num==requestScope.pageResponse.page?'sel':''}">${num} </a>
                            </li>
                        </c:forEach>
                        <c:if test="${requestScope.pageResponse.isShowNext()}">
                            <li class="nav_next">
                                <a href="<c:url value="/qnaList?page=${requestScope.pageResponse.page+1}&size=${requestScope.pageResponse.size}"/>">
                                    [NEXT]
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </main>
</body>
</html>