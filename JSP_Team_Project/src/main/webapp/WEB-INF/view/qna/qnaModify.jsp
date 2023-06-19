<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>글 수정</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/reset.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/detail.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/board.css"/>">
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
</script>

<body>
<jsp:include page="/WEB-INF/view/common/header.jsp" flush="true" />
<main id="board_register">

    <div class="sub_tit_line">
        <ul>
            <li class="sub_tit_home"><a href="#">H</a></li>
            <li><a href="#">고객센터</a></li>
            <li><a href="#">QnA 작성</a></li>
        </ul>
    </div>

    <div class="board_wrap">
        <div id="sub_banner">
            <p><strong>문의하기</strong>QnA센터 답변 가능 시간: 월~금 9:00 - 18:00 </p>
        </div>


        <form action="<c:url value="/qnaModify" />" method="post">
            <table class="board_write_table">
                <tr>
                    <td>제목</td>
                    <td align="left">
                        <input type="text" name="title" id="title" value="${qna.getTitle()}">
                    </td>
                </tr>
                <tr>
                    <td>작성자</td>
                    <td align="left">
                        <input type="text" name="id" id="writer" value="${qna.getId()}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td align="left">
                        <textarea name="content" id="content" class="txt_area"><c:out value="${qna.getContent()}"/></textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="hidden" name="qqno" value="${qna.getQqno()}">
                        <input type="hidden" name="page" value="${page}">
                        <button type="submit" class="btn_modify btn_active">수정</button>
                        <button id="delBtn" class="btn_modify btn_delete">삭제</button>
                        <button id="listBtn" class="btn_modify btn_default">목록</button>
                    </td>
                </tr>
            </table>
        </form>
        <p class="board_btns">
            <a href="<c:url value="/qnaList?page="/>${page}">목록으로 돌아가기</a>
        </p>
    </div>
</main>
</body>
<script>
    $(document).ready(function() {
        $("#delBtn").click(function(e) {
            e.stopPropagation();
            self.location = "/project/qnaDelete?qqno="${qna.getQqno()}
        })
        $("#listBtn").click(function(e) {
            e.stopPropagation();
            self.location = "/project/qnaList"
        })
    });

</script>
</html>
