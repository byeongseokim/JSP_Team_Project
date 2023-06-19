<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${culture.getSvc_nm()}</title>

    <link rel="stylesheet" href="<c:url value="/resources/css/sub.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/detail.css"/>">
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
        //오른쪽 퀵메뉴 버튼 기능
        var num;
        var scrollNum;

        function gnbClick(e) {
            e.preventDefault();
            num = $(this).index()
            scrollNum = $('.container_wrap section').eq(num).offset().top
            $('html,body').stop().animate({
                scrollTop: scrollNum
            }, 1000)
        }
        $('.quick-menu li').on('click', gnbClick)
        //common.jsp 에서 실행될땐 바디태그 아래부분이라 로그인시 바로 적용인 안되서 옮김
        let user = '<c:out value="${sessionScope.get('user')}"/>';
        console.log("logined_cookie: : " + getCookie("logined_cookie").valueOf());
        console.log("user: " + user);

        let StringOpn = '<c:out value="${culture.getRcpt_bgn_dt()}"/>';
        let rcpt_bgn_dt = new Date(StringOpn).toLocaleDateString();
        let StringEnd = '<c:out value="${culture.getRcpt_end_dt()}"/>';
        let rcpt_end_dt = new Date(StringEnd).toLocaleDateString();
        $(".detail_date").html('접수기간 : '+rcpt_bgn_dt+' ~ '+rcpt_end_dt);


        let StringOpn1 = '<c:out value="${culture.getSvc_opn_bgn_dt()}"/>';
        let opn_bgn_dt = new Date(StringOpn1).toLocaleDateString();
        let StringEnd1 = '<c:out value="${culture.getSvc_opn_end_dt()}"/>';
        let opn_end_dt = new Date(StringEnd1).toLocaleDateString();
        $(".detail_date2").html(opn_bgn_dt+' ~ '+opn_end_dt);
    })

</script>
<body>
<jsp:include page="/WEB-INF/view/common/header.jsp" flush="true"/>
<jsp:include page="/WEB-INF/view/common/navigation.jsp" flush="true"/>
<main id="festival_detail">
    <div class="sub_tit_line">
        <ul>
            <li class="sub_tit_home"><a href="/project">H</a></li>
            <li><a href="/project/list">행사 예약</a></li>
            <li><a href="#">행사 상세</a></li>
        </ul>
    </div>
    <!--     상세페이지 영역       -->
    <div class="container_wrap">
        <section class="detail_overview">
            <div>
                <h1>${culture.getSvc_nm()} <br>
                    <span class="detail_date">행사 기간 : </span>
                    <%--                        <fmt:formatDate value="${culture.getSvc_opn_bgn_dt()}" pattern="yyyy/MM/dd" var="getSvc_opn_bgn_dt"/>${getSvc_opn_bgn_dt} ~ <fmt:formatDate value="${culture.getSvc_opn_end_dt()}" pattern="yyyy/MM/dd" var="getSvc_opn_end_dt"/>${getSvc_opn_end_dt}--%>
                </h1>
                <table id="detail_table">
                    <tr>
                        <th>대상</th>
                        <td>${culture.getUse_tgt_info()}</td>
                    </tr>
                    <tr>
                        <th>장소</th>
                        <td>${culture.getArea_nm()} ${culture.getPlace_nm()}</td>
                    </tr>
                    <tr>
                        <th>모집정원</th>
                        <td>${culture.getCapacity()}명</td>
                    </tr>
                    <tr>
                        <th>이용기간</th>
                        <td class="detail_date2"></td>
                        <%--                        <td><fmt:formatDate value="${culture.getSvc_opn_bgn_dt()}" pattern="yyyy/MM/dd" var="getSvc_opn_bgn_dt"/>${getSvc_opn_bgn_dt} ~ <fmt:formatDate value="${culture.getSvc_opn_end_dt()}" pattern="yyyy/MM/dd" var="getSvc_opn_end_dt"/>${getSvc_opn_end_dt}</td>--%>
                    </tr>
                    <%--                    <tr>--%>
                    <%--                        <th>취소기간</th>--%>
                    <%--                        <td>${culture.getRevstd_day_nm()} ~ ${culture.getRevstd_day()}</td>--%>
                    <%--                    </tr>--%>
                    <tr>
                        <th>이용요금 유무</th>
                        <td>${culture.getPay_ay_nm()}</td>
                    </tr>
                    <tr>
                        <th>문의전화</th>
                        <td>${culture.getTel_no()}</td>
                    </tr>
                    <tr>
                        <th>기타</th>
                        <td> </td>
                    </tr>
                </table>
            </div>
            <p class="bg"></p>
        </section>
        <section class="detail_select_date">
            <div>
                <h2>날짜 확인</h2>
                <jsp:include page="/WEB-INF/view/common/calendar.jsp" flush="true"/>
                <div class="calendar_desc">
                    <form action="<c:url value="/reservation"/>" method="get">
                        <p class="select_date">
                            <strong><i class="material-icons">date_range</i> 선택한 날짜:</strong>
                            <input type="text" name="sel_y" id="cal_getYear" value=""/> /
                            <input type="text" name="sel_m" id="cal_getMonth" value=""/> /
                            <input type="text" name="sel_d" id="cal_getDay" value=""/>
                        </p>
                        <p class="select_number">
                            <strong><i class="material-icons">people</i> 이용 인원:</strong>
                            <span class="select_number_val">
                                <span id="possibleCnt"></span>
                                <input type='button' onclick='count("minus")' value='-' /><input type="text" id="useNum" name="useNum" value="1" /><input type='button' onclick='count("plus")' value='+' />
                            </span>
                        </p>
                        <input type="hidden" name="cno" value="${culture.getCno()}">
                        <input type="hidden" name="page" value="${requestScope.page}">
                        <p class="btn_reservation">
                            <input id="btn-resSubmit" type="submit" value="예약하기"/>
                        </p>
                        <script>
                            $("#btn-resSubmit").click(function (){
                                let year = Number($("#cal_getYear").val());
                                let month = Number($("#cal_getMonth").val());
                                let day = Number($("#cal_getDay").val());
                                let currentDate = new Date();
                                let curYear = Number(currentDate.getFullYear());
                                let curMonth = Number(currentDate.getMonth())+1;
                                let curDay = Number(currentDate.getDate());

                                if(year===0||month===0||day===0){
                                    alert('예약 날짜를 확인해주세요.');
                                    return false;
                                }
                                if(year<curYear||month<curMonth){
                                    alert('올바른 예약 날짜를 선택해주세요(이미 종료된 행사 날짜입니다.)');
                                    return false;
                                }
                                if(month===curMonth){
                                    if(day<=curDay){
                                        alert('올바른 예약 날짜를 선택해주세요(이미 종료된 행사 날짜입니다.)');
                                        return false;
                                    }
                                }
                            });
                        </script>
                    </form>
                </div>
            </div>
        </section>
        <section class="detail_desc">
            <div>
                <h2>이용 안내</h2>
                <div id="description">
                    ${fn:replace(fn:replace(requestScope.culture.getDtlcont(), "\\r\\n", " "), "\\t", " ")}
                </div>
            </div>
        </section>
        <!--리뷰 작성-->
        <!--댓글작성/수정-->
        <section class="detail_review">
            <div>
                <h2>참여 후기</h2>
                <ul class="review_overview">
                    <li class="review_overview_left">
                        <p><strong>4.6</strong><span>/ 5.0</span></p>
                        <p class="review_stars">
                            <img src="<c:url value="/resources/images/star1.png"/>" alt="star" width="50">
                            <img src="<c:url value="/resources/images/star1.png"/>" alt="star" width="50">
                            <img src="<c:url value="/resources/images/star1.png"/>" alt="star" width="50">
                            <img src="<c:url value="/resources/images/star1.png"/>" alt="star" width="50">
                            <img src="<c:url value="/resources/images/star1.png"/>" alt="star" width="50">
                        </p>
                    </li>
                    <li class="review_overview_right">
                        <ul>
                            <li><span>5점</span><span>0</span><span class="review_count">4</span></li>
                            <li><span>4점</span><span>0</span><span class="review_count">0</span></li>
                            <li><span>3점</span><span>0</span><span class="review_count">0</span></li>
                            <li><span>2점</span><span>0</span><span class="review_count">0</span></li>
                            <li><span>1점</span><span>0</span><span class="review_count">0</span></li>
                        </ul>
                    </li>
                </ul>
                <div class="review_write">
                    <div>
                        <h3>리뷰 쓰기</h3>
                        <p class="review_grade">
                            <label for="grade">평점 </label>
                            <strong class="review_write_star">
                                <span>별</span>
                                <span>별</span>
                                <span>별</span>
                                <span>별</span>
                                <span>별</span>
                            </strong>
                            <input type="text" name="grade" id="grade" placeholder="">
                            <%--                            <span name="grade" id="grade">ff</span>--%>
                        </p>
                    </div>
                    <p class="review_txt">
                        <textarea cols="50" rows="5" name="content" id="content" placeholder="리뷰내용"></textarea>
                        <button id="writeBtn" type="button">리뷰 등록</button>
                        <button id="modBtn" type="button" style="display: none;">리뷰 수정</button>
                    </p>

                </div>

                <!--리뷰 리스트-->
                <p class="review_default">
                    아직 등록된 리뷰가 없습니다. 지금 첫 리뷰를 작성해 보세요.
                </p>
                <div id="reviewList" data-cno="${requestScope.culture.getCno()}" data-id="${sessionScope.user}">
                    <ul>
                        <li>
                            <p class="review_list_id"><img src="<c:url value="/resources/images/user_default.png"/>" alt="사용자프로필" width="35" /><span class="id">jinkyeong1004</span></p>

                            <div>
                                <p class="review_list_grade"><span class="grade"><img src="<c:url value="/resources/images/star1.png"/>" alt="star"></span></p>
                                <p class="review_list_date"><span class="date">2022-10-21</span></p>
                            </div>
                            <p class="review_list_content"><span class="content">아주 좋아요. 재미있어요.</span></p>
                            <p class="btn_wrap">
                                <button class="modBtn">수정</button>
                                <button class="delBtn">삭제</button>
                            </p>
                        </li>
                    </ul>
                </div>
            </div>
        </section>
    </div>
    <script>
        function count(type)  {
            // 결과를 표시할 element
            const resultElement = document.getElementById('useNum');
            let number = resultElement.value;

            //인원이 0~10명 사이에서만 신청 가능하도록 조정
            if(type === 'plus') {
                number = parseInt(number) + 1;
                if(number > 10){number = 10; alert('10인 이상은 단체 문의 부탁드립니다.');}
            }
            else if(type === 'minus')  {
                number = parseInt(number) - 1;
                if(number < 1){number = 1;}
            }

            // 결과 출력
            resultElement.value = number;
        }
        $(document).ready(function(){
            let data_cno = $("#reviewList").attr("data-cno");
            let data_id = $("#reviewList").attr("data-id");
            //u d
            // 시작하면서 해당 cno의 모든 리뷰 리스트를 가져옴
            getReviews(data_cno);

            //리뷰쓰기버튼 클릭이벤트
            $("#writeBtn").click(function(){
                let reviewVO = {id : data_id ,
                    cno : data_cno,
                    content : $("textarea[name=content]").val(),
                    grade : $("input[name=grade]").val()
                }
                if($("input[name=grade]").val()===""||
                    $("textarea[name=content]").val()==""){
                    alert("평점과 내용을 전부 입력해주세요");
                    return;
                }
                writeReview(reviewVO);
            });

            //리뷰수정버튼 클릭이벤트  //parent prev eq(0)
            $("#reviewList").on("click",".modBtn",function(){ //아래의 클래스 modBtn클릭
                if($(this).attr("data-id2")!==$("#reviewList").attr("data-id")){
                    if($("#reviewList").attr("data-id")!=='admin'){
                        alert("자신의 리뷰만 수정할 수 있습니다");
                        return;
                    }
                }
                alert('상단의 입력창에서 수정해주세요')
                //1. writeBtn숨기고 숨겨진 #modBtn 버튼 다시 보이게
                $("#writeBtn").css("display","none");
                $("#modBtn").css("display","block");
                //2. 수정에 필요한 content,grade
                let data_cno = $("#reviewList").attr("data-cno");
                let data_content = $(this).parent().prev().eq(0).text();
                let data_grade = $(this).parent().parent().attr("data-grade");
                //3. 검증에 필요한 re_no
                let data_re_no = $(this).parent().parent().attr("data-re_no");
                //4. 현재 리뷰 내용 textarea에 표시 + 평점도 표시
                $("textarea[name=content]").val(data_content).focus();
                $("input[name=grade]").val(data_grade);

                $("#modBtn").click(function(){
                    let reviewVO = {
                        re_no : data_re_no,
                        cno : data_cno,
                        content : $("textarea[name=content]").val(),
                        grade : $("input[name=grade]").val()
                    }
                    if($("input[name=grade]").val()===""||
                        $("textarea[name=content]").val()==""){
                        alert("평점과 내용을 전부 입력해주세요");
                        return;
                    }
                    updateReview(reviewVO);
                })
            })//리뷰수정버튼 클릭이벤트

            //리뷰삭제버튼 클릭이벤트
            $("#reviewList").on("click",".delBtn",function(){ //아래의 클래스 modBtn클릭
                if($(this).attr("data-id1")!==$("#reviewList").attr("data-id")){
                    if($("#reviewList").attr("data-id")!=='admin'){
                        alert("자신의 리뷰만 삭제할 수 있습니다");
                        return;
                    }
                }
                if(!confirm('정말 삭제하시겠습니까?')){
                    return;
                }
                //1. 삭제,검증에 필요한 re_no
                let re_no = $(this).parent().parent().attr("data-re_no");
                //2. 목록 불러오기에 필요한 cno
                let cno = $("#reviewList").attr("data-cno");
                deleteReview(re_no,cno);
            })//리뷰삭제버튼 클릭이벤트

            $("#reviewList").on("click",".reviewPage",function(){
                //페이지를 cno와 같이 넘겨준다
                let page = $(this).text();
                getReviews2(data_cno,page);
            })//리뷰 페이징

            //별 클릭 이벤트
            $(".review_write_star span").click(function() {
                $(".review_write_star span").removeClass("on");
                $(this).addClass("on");
                $(this).prevAll("span").addClass("on");
                $("#grade").val($(this).index()+1);
            });

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
        let getReviews = function(cno) {
            $.ajax({
                url: '/project/review?cno='+cno,
                type: 'GET',
                headers: {"content-type":"application/json"},

                success : function(result){
                    //result = pageresponse

                    if(result.total === 0){
                        $(".review_default").css("display","block");
                    } else{
                        $(".review_default").css("display","none");
                    }
                    $("#reviewList").html(toHtml(result));
                },
                error: function() {
                    alert("error");
                }
            });//ajax
        }//getReviews


        let getReviews2 = function(cno,page) {
            $.ajax({
                url: '/project/review?cno='+cno+'&page='+page,
                type: 'GET',
                headers: {"content-type":"application/json"},

                success : function(result){
                    //result = pageresponse
                    $("#reviewList").html(toHtml(result));
                },
                error: function() {
                    alert("error");
                }
            });//ajax
        }//getReviews

        let writeReview = function(reviewVO) {
            isAjaxRun2=true;
            $.ajax({
                url: '/project/review',
                type: 'POST',
                headers: {"content-type":"application/json"},
                data: JSON.stringify(reviewVO),

                success : function(result) {
                    alert("리뷰 등록 성공");
                    getReviews(reviewVO.cno);
                },
                error: function() {
                    alert("error");
                }
            })//ajax
        }//writeReivew

        let updateReview = function(reviewVO) {
            $.ajax({
                url: '/project/review/'+reviewVO.re_no,
                type: 'PUT',
                headers: {"content-type":"application/json"},
                data: JSON.stringify(reviewVO),

                success : function(result) {
                    alert("리뷰 수정 성공");
                    $("textarea[name=content]").val('');
                    $("input[name=grade]").val('');
                    $("#writeBtn").css("display","block");
                    $("#modBtn").css("display","none");
                    getReviews(reviewVO.cno);
                },
                error: function() {
                    alert("error");
                }
            })//ajax
        }//updateReview

        let deleteReview = function(re_no,cno) {
            $.ajax({
                url: '/project/review/'+re_no,
                type: 'DELETE',
                headers: {"content-type":"application/json"},

                success : function(result) {
                    alert("삭제 성공");
                    getReviews(cno);
                },
                error: function() {
                    alert("error");
                }
            })//ajax
        }//deleteReview

        //배열로 들어온 (js 객체를 html 문자로) 바꿔주는 함수
        let toHtml = function(pageResponse) {
            let reviews = pageResponse.pageList;
            let prev = pageResponse.showPrev;
            let next = pageResponse.showNext;
            let tmp = "<ul>";
            reviews.forEach(function(review) {
                tmp += '<li data-cno='+review.cno +' data-grade='+review.grade+' data-re_no='+review.re_no+'>'
                tmp += '<p class="review_list_id"><img src="<c:url value="/resources/images/user_default.png"/>" alt="사용자프로필" width="35" /><span class="id">'+review.id+'</span></p>'
                tmp += '<div><p class="review_list_grade"><span class="grade"><img src="<c:url value="/resources/images/star1.png"/>" alt="star">' + review.grade +' / </span></p>'
                tmp += '<p class="review_list_date"><span class="date">'+review.regDate+'</span></p></div>'
                tmp += '<p class="review_list_content"><span class="content">'+review.content+'</span></p>'
                tmp += '<p class="btn_wrap"><button class = "delBtn" data-id1='+review.id+'>삭제</button>'
                tmp += '<button class = "modBtn" data-id2='+review.id+'>수정</button>'
                tmp += '</p></li>'
            })//foreach
            tmp += '</ul>';
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
</main>
</body>
</html>
