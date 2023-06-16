<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/join.css" />">
    <script src="<c:url value="/resources/javascript/jquery-3.6.0.min.js"/>"></script>
<%--        <script src="<c:url value="/resources/javascript/agreetxt.js"/>"></script>--%>

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
    <script>
        let query = window.location.search;
        let param = new URLSearchParams(query);
        let msg = param.get('msg');
        if (msg != null && msg.length > 0) {
            alert(msg)
        }

    </script>

    <div id="wrap">

<%--        <script src="<c:url value="/resources/javascript/header_sub.js" />"></script> 안지우면 회원가입화면에 헤더2개뜸 --%>

        <main id="main">
            <!--    서브메뉴 타이틀 영역    -->
            <div class="sub_tit_line">
                <ul>
                    <li class="sub_tit_home"><a href="/project">H</a></li>
                    <li><a href="/project/login">로그인</a></li>
                    <li><a href="/project/join">회원가입</a></li>
                </ul>
            </div>
            <div id="join_wrap">
                <div id="sub_banner">
                    <p><strong>회원 가입</strong></p>
                </div>
                <form action="<c:url value="/join" />" method="post" id="join_form">
                <div class="personal_info">
                    <strong class="join_tit">1) <br>개인정보 <br>입력</strong>
                    <p>
                        <label for="u_id">아이디<span> (영문소문자/숫자, 4~16자)</span></label>
                        <input type="text" id="u_id" name="id">
                        <input type="button" id="u_id_check" name="id_check" value="중복 확인" onclick="checkID()">
                    </p>
                    <p>
                        <label for="u_pw1">비밀번호<span>(영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 8~25자)</span></label>
                        <input type="password" id="u_pw1" name="pwd">
                    </p>
                    <p>
                        <label for="u_pw2">비밀번호 확인<span id="pwCheckLabel"></span></label>
                        <input type="password" id="u_pw2">
                    </p>
                    <p>
                        <label for="u_name">이름</label>
                        <input type="text" id="u_name" name="name">
                    </p>
                    <p class="tel_wrap">
                        <label for="u_tel1">전화번호</label>
                        <input type="tel" id="u_tel1" name="phone1"> -
                        <input type="tel" id="u_tel2" name="phone2"> -
                        <input type="tel" id="u_tel3" name="phone3">
                    </p>
                    <p>
                        <label for="email_l">이메일</label>
                        <input type="email" id="email_l" name="email">
                    </p>

                </div>
                <div id="agree">
                    <strong class="join_tit">2) <br>이용약관 <br>동의</strong>
                    <div class="agreebox1">
                        <p class="agreebox_tit">
                            <input type="checkbox" name="chk" id="chk2">
                            <label for="chk2" class="type01 type03">[필수] 이용약관 동의</label>
                        </p>
                        <p class="agreebox_txt">
                            <textarea class="agree_txt1" cols="100" rows="10" readonly></textarea>
                        </p>
                    </div>
                    <div class="agreebox2">
                        <p class="agreebox_tit">
                            <input type="checkbox" name="chk" id="chk3">
                            <label for="chk3" class="type01 type04">[필수] 개인정보 수집 및 이용 동의</label>
                        </p>
                        <p class="agreebox_txt">
                            <textarea class="agree_txt2" cols="100" rows="10" readonly></textarea>
                        </p>
                    </div>
                    <p class="chk_all">
                        <input type="checkbox" id="chk1">
                        <label for="chk1" class="type01 type02">이용약관 및 개인정보수집 및 이용에 모두 동의합니다.</label>
                    </p>
                </div>
                <div id="login_btn">
                    <button type="submit" class="login_submit join_submit">회원가입</button>
                    <button type="reset" class="cancel">취소</button>
                </div>
                </form>
            </div>
        </main>
    </div>
    <script>
        //유효성 검사에 사용될 변수들
        let id = document.getElementById("u_id");
        let pw1 = document.getElementById("u_pw1");
        let pw2 = document.getElementById("u_pw2");
        let pwLabel = document.getElementById("pwCheckLabel");

        //비밀번호 영문+숫자+특수조합(8~25자리) 정규식
        let pwdCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*_+=])(?=.*[0-9]).{8,25}$/;



        //아이디 중복 확인 ajax로 db 호출
        function checkID() {
            let id = $("#u_id").val();
            checkDuplicateId(id);
        }
        let checkDuplicateId = function(id){
            $.ajax({
                url: '/project/check?id='+id,
                type: 'GET',
                headers: {"content-type":"application/json"},

                success : function(result){
                    if(result.id==id){
                        alert(id+"는 중복된 아이디입니다.");
                        //css오류
                        // id.style.outline = "2px solid #eb9494";
                        return;
                    }
                    alert(id+"는 사용 가능한 아이디입니다.");
                    // id.style.outline = "2px solid #a3ea92";
                },
                error: function() {
                    alert(id+"는 중복된 아이디입니다.");
                    // id.style.outline = "2px solid #eb9494";
                }
            });//ajax
        }


        //아이디/비밀번호에 포커스하면 텍스트 효과
        $(".u_txt input").focus(function() {
            $(this).siblings("label").addClass("focus");
        }).blur(function() {
            if ($(this).val() == "") {
                $(this).siblings("label").removeClass("focus");
            } else {
                $(this).siblings("label").addClass("focus");
            }
        })

        //id,pwd 체크
        $(".join_submit").click(function (){
            let id = $("#u_id").val();
            let pwd = $("#u_pw1").val();
            if(id===""||id.length<4||pwd===""||pwd.length<8){
                alert("올바른 값을 입력해주세요.");
                return false;
            }
            return true;
        })

        // $(pw1).blur(function() {
        //     //비밀번호 정규식 확인
        //     if (!pwdCheck.test(pw1.value)) {
        //         alert("비밀번호는 영문+숫자+특수문자 조합으로 8-25자리 사용해야 합니다.");
        //         pw1.focus();
        //     }
        // })

        //비밀번호확인 유효성 검사            
        function checkPw(inputVal) {
            $(inputVal).blur(function() {
                if (pw1.value == pw2.value) {
                    pwLabel.innerHTML = "비밀번호 일치";
                    pwLabel.style.color = "#6edb54";
                } else {
                    pwLabel.innerHTML = "비밀번호가 일치하지 않습니다.";
                    pwLabel.style.color = "#e76060";
                }
                console.log("pw1: " + pw1.value);
                console.log("pw2: " + pw2.value);
            })
        }
        checkPw(pw1);
        checkPw(pw2);


        //이용약관,개인정보 취급방침 체크버튼
        $("#chk1").change(function() {
            var chk1 = $("#chk1").prop("checked");
            if (chk1 == true) {
                $("#chk2, #chk3").prop("checked", true);
                $(".type01").addClass("checked");
            } else {
                $("#chk2, #chk3").prop("checked", false);
                $(".type01").removeClass("checked");
            }
        })
        $("#chk2, #chk3").change(function() {
            var chk2 = $("#chk2").prop("checked");
            var chk3 = $("#chk3").prop("checked");
            if (chk2 == true) {
                $(".type03").addClass("checked");
            } else {
                $(".type03").removeClass("checked");
            }

            if (chk3 == true) {
                $(".type04").addClass("checked");
            } else {
                $(".type04").removeClass("checked");
            }
            if (chk2 == true && chk3 == true) {
                $("#chk1").prop("checked", true);
                $(".type02").addClass("checked");
            } else {
                $("#chk1").prop("checked", false);
                $(".type02").removeClass("checked");
            }
        })

    </script>
</body>

</html>
