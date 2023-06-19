<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<!DOCTYPE html>

<head>
    <title>Title</title>
    <script src="<c:url value="/resources/javascript/jquery-3.6.0.min.js"/>"></script>
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
</script>
<body>
<jsp:include page="/WEB-INF/view/common/header.jsp" flush="true" />
<main id="board_register">

    <div class="sub_tit_line">
        <ul>
            <li class="sub_tit_home"><a href="/project">H</a></li>
            <li><a href="#">고객센터</a></li>
            <li><a href="#">QnA 작성</a></li>
        </ul>
    </div>

    <div class="board_wrap">
        <div id="sub_banner">
            <p><strong>문의하기</strong>QnA센터 답변 가능 시간: 월~금 9:00 - 18:00 </p>
        </div>

        <table class="board_write_table">
            <tr>
                <td>번호</td>
                <td align="left">
                    <c:out value="${qna.getQqno()}" />
                </td>
            </tr>
            <tr>
                <td>제목</td>
                <td align="left">
                    <a href="<c:url value='/qnaDetail?qqno=${qna.getQqno()}'/>">
                        <c:out value="${qna.getTitle()}" /></a>
                </td>
            </tr>
            <tr>
                <td>작성자</td>
                <td align="left">
                    <c:out value="${qna.getId()}" />
                </td>
            </tr>
            <tr>
                <td>내용</td>
                <td align="left">
                    <textarea name="content" id="content" class="txt_area" readonly style="cursor: auto"><c:out value="${qna.getContent()}"/></textarea>
                </td>
            </tr>
        </table>

        <div class="qna_btn_wrap">
            <a href="<c:url value="/qnaModify?qqno="/>${qna.getQqno()}&page=${page}" class="btn_modify btn_active">수정</a>
            <a href="<c:url value="/qnaDelete?qqno="/>${qna.getQqno()}&page=${page}" onclick="return confirm('정말 삭제 하시겠습니까?')" class="btn_modify btn_delete">삭제</a>
            <a href="<c:url value="/qnaList?page="/>${page}" class="btn_modify btn_default">목록</a>
        </div>

        <!--리뷰-->
        <div class="detail_review">
            <div class="review_write">
                <p class="review_txt">
                    <textarea cols="50" rows="5" name="reviewContent" id="reviewContent" placeholder="답글내용"></textarea>
                    <button id="writeBtn" type="button">답글 등록</button>
                    <button id="modBtn" type="button" style="display: none;">답글 수정</button>
                </p>
                <div id="reviewList" data-qqno="${requestScope.qna.getQqno()}" data-id="${sessionScope.user}">
                    <p class="btn_wrap">
                        <button class="modBtn">수정</button>
                        <button class="delBtn">삭제</button>
                    </p>
                </div>
            </div>
        </div>

    </div>

</main>
</body>
<script>
    $(document).ready(function() {
        let data_qqno = $("#reviewList").attr("data-qqno");
        let data_id = $("#reviewList").attr("data-id");
        // 시작하면서 해당 qqno의 모든 답글 리스트를 가져옴
        getReviews(data_qqno);

        //리뷰쓰기버튼 클릭이벤트
        var isRun1 = false;
        $("#writeBtn").click(function() {
            if(isRun1){
                return;
            }
            isRun1=true;
            if(data_id===""){
                alert('먼저 로그인 해주세요');
                return;
            }
            let reviewVO = {
                id: data_id,
                qqno: data_qqno,
                content: $("textarea[name=reviewContent]").val(),
            }
            writeReview(reviewVO);
            isRun1 = false;
        });

        //리뷰수정버튼 클릭이벤트
        var isRun2 = false;
        $("#reviewList").on("click", ".modBtn", function() { //아래의 클래스 modBtn클릭
            if($(this).attr("data-id2")!==$("#reviewList").attr("data-id")) {
                if ($("#reviewList").attr("data-id") !== 'admin') {
                    alert("자신의 리뷰만 수정할 수 있습니다");
                    return;
                }
            }
            if(isRun2){
                return;
            }
            isRun2=true;
            if(data_id===""){
                alert('먼저 로그인 해주세요');
                return;
            }
            //1. writeBtn숨기고 숨겨진 #modBtn 버튼 다시 보이게
            $("#writeBtn").css("display", "none");
            $("#modBtn").css("display", "block");
            //2. 수정에 필요한 content,grade
            let data_qqno = $("#reviewList").attr("data-qqno");
            let data_content = $(this).parent().prev().prev().text();
            //3. 검증에 필요한 qano
            let data_qano = $(this).parent().parent().attr("data-qano");
            //4. 현재 리뷰 내용 textarea에 표시 + 평점도 표시
            $("textarea[name=reviewContent]").val(data_content);

            $("#modBtn").click(function() {
                if(data_id===""){
                    alert('먼저 로그인 해주세요');
                    return;
                }
                let reviewVO = {
                    id: data_id,
                    qano: data_qano,
                    qqno: data_qqno,
                    content: $("textarea[name=reviewContent]").val(),
                }
                updateReview(reviewVO);
            })
            isRun2 = false;
        }) //리뷰수정버튼 클릭이벤트

        //리뷰삭제버튼 클릭이벤트
        var isRun3 = false;
        $("#reviewList").on("click", ".delBtn", function() { //아래의 클래스 modBtn클릭
            if($(this).attr("data-id1")!==$("#reviewList").attr("data-id")){
                if($("#reviewList").attr("data-id")!=='admin'){
                    alert("자신의 리뷰만 삭제할 수 있습니다");
                    return;
                }
            }
            if(isRun3){
                return;
            }
            isRun3=true;
            if(data_id===""){
                alert('먼저 로그인 해주세요');
                return;
            }
            if(!confirm('정말 삭제하시겠습니까?')){
                return;
            }
            //1. 삭제,검증에 필요한 qano
            let qano = $(this).parent().parent().attr("data-qano");
            //2. 목록 불러오기에 필요한 qqno
            let qqno = $("#reviewList").attr("data-qqno");
            deleteReview(qano, qqno);
            isRun3=false;
        }) //리뷰삭제버튼 클릭이벤트

        $("#reviewList").on("click", ".reviewPage", function() {
            //페이지를 qqno와 같이 넘겨준다
            let page = $(this).text();
            let qqno = $("#reviewList").attr("data-qqno");
            getReviews2(qqno, page);
        }) //리뷰 페이징

        //prev
        $("#reviewList").on("click", ".prev", function() {
            let pageStr1 = $(this).attr("data-page");
            let page1 = Number(pageStr1);
            getReviews2(data_cno,page1-1);
        })
        //next
        $("#reviewList").on("click", ".next", function() {
            let pageStr2 = $(this).attr("data-page");
            let page2 = Number(pageStr2);
            getReviews2(data_cno,page2+1);
        })


    }); //document.ready

    //////////////////////////////////////////////
    let getReviews = function(data_qqno) {
        $.ajax({
            url: '/project/qnaReview?qqno=' + data_qqno,
            type: 'GET',
            headers: {
                "content-type": "application/json"
            },

            success: function(result) {
                //result = pageresponse
                $("#reviewList").html(toHtml(result));
            },
            error: function() {
                alert("error");
            }
        }); //ajax
    } //getReviews

    let getReviews2 = function(cno, page) {
        $.ajax({
            url: '/project/qnaReview?qqno=' + qqno + '&page=' + page,
            type: 'GET',
            headers: {
                "content-type": "application/json"
            },

            success: function(result) {
                //result = pageresponse
                $("#reviewList").html(toHtml(result));
            },
            error: function() {
                alert("error");
            }
        }); //ajax
    } //getReviews

    let writeReview = function(reviewVO) {
        $.ajax({
            url: '/project/qnaReview',
            type: 'POST',
            headers: {
                "content-type": "application/json"
            },
            data: JSON.stringify(reviewVO),

            success: function(result) {
                alert("리뷰 등록 성공");
                getReviews(reviewVO.qqno);
            },
            error: function() {
                alert("error");
            }
        }) //ajax
    } //writeReivew

    let updateReview = function(reviewVO) {
        $.ajax({
            url: '/project/qnaReview/' + reviewVO.qano,
            type: 'PUT',
            headers: {
                "content-type": "application/json"
            },
            data: JSON.stringify(reviewVO),

            success: function(result) {
                alert("리뷰 수정 성공");
                $("textarea[name=reviewContent]").val('');
                $("#writeBtn").css("display", "block");
                $("#modBtn").css("display", "none");
                getReviews(reviewVO.qqno);
            },
            error: function() {
                alert("error");
            }
        }) //ajax
    } //updateReview

    let deleteReview = function(qano, qqno) {
        $.ajax({
            url: '/project/qnaReview/' + qano,
            type: 'DELETE',
            headers: {
                "content-type": "application/json"
            },

            success: function(result) {
                alert("삭제 성공");
                getReviews(qqno);
            },
            error: function() {
                alert("error");
            }
        }) //ajax
    } //deleteReview

    //배열로 들어온 (js 객체를 html 문자로) 바꿔주는 함수
    let toHtml = function(pageResponse) {
        let reviews = pageResponse.pageList;
        let prev = pageResponse.showPrev;
        let next = pageResponse.showNext;
        let tmp = "<ul>";
        reviews.forEach(function(review) {
            tmp += '<li data-qqno=' + review.qqno + ' data-qano=' + review.qano + '>'
            tmp += '<p class="review_list_id"><img src="<c:url value="/resources/images/user_default.png"/>" alt="사용자프로필" width="35" /><span class="id">'+review.id+'</span></p>'
            tmp += '<p class="review_list_content"><span class="content">'+review.content+'</span></p>'
            tmp += '<p class="review_list_date"><span class="date">'+new Date(review.regDate).toLocaleDateString()+'</span></p></div>'
            tmp += '<p class="btn_wrap"><button class = "delBtn" data-id1='+review.id+'>삭제</button>'
            tmp += '<button class = "modBtn" data-id2='+review.id+'>수정</button>'
            tmp += '</p></li>'
        }) //foreach
        tmp += '</ul>';
        //page nav
        if (prev) {
            tmp += '<span style="cursor: pointer" class="prev" data-page='+pageResponse.page+'>[PREV]</span>';
        }
        for(var i = pageResponse.start; i<=pageResponse.end ; i++){
            tmp += '<div class="reviewPage" style="display:inline-block; cursor: pointer; margin:3px;">'+i+'</div>';
        }
        if (next) {
            tmp += '<span style="cursor: pointer" class="next" data-page='+pageResponse.page+'>[NEXT]</span>';
        }
        return tmp;
    }

</script>

</html>
