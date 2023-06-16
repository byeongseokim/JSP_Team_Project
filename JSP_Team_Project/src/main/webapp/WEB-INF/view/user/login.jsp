<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>로그인</title>
  <link rel="stylesheet" href="<c:url value="/resources/css/login.css"/>">
  <script src="<c:url value="/resources/javascript/jquery-3.6.0.min.js"/>"></script>
  <script src="<c:url value="/resources/javascript/login_cookie.js"/>"></script>
</head>
<script>
  let query = window.location.search;
  let param = new URLSearchParams(query);
  let msg = param.get('msg');
  if(msg!=null){
    alert(msg)
  }
  $(document).ready(function (){
    //common.jsp 에서 실행될땐 바디태그 아래부분이라 로그인시 바로 적용인 안되서 옮김
    let user = '<c:out value="${sessionScope.get('user')}"/>';
    console.log("logined_cookie: : " + getCookie("logined_cookie").valueOf());
    console.log("user: " + user);

  })
</script>
<body>
<jsp:include page="/WEB-INF/view/common/header.jsp" flush="true"/>

<div id="wrap">
  <main id="main">
    <div id="login_bg">

    </div>

    <div id="login">
      <div id="top_wrap">
        <h1><a href="#">SEOUL FESTIVAL</a></h1>
      </div>

      <div id="login_con">
        <div id="login1" class="login_box">
          <form action="<c:url value="/login"/>" method="post">
            <p class="u_txt"><label for="u_id" class="u_id_txt">아이디</label>
              <input type="text" id="u_id" autocomplete="off" name="id" placeholder="">
            </p>
            <p class="u_txt"><label for="u_id">비밀번호</label>
              <label for="u_pwd"></label><input type="password" id="u_pwd" autocomplete="off" name="pwd">
            </p>

            <!--    아이디저장/로그인상태유지     -->
            <p id="chk_wrap1">
              <input type="checkbox" name="chk1" id="chk1" value="1">
              <label for="chk1" class="type01 type02">아이디 저장</label>
              <input type="checkbox" name="chk2" id="chk2" value="2">
              <label for="chk2" class="type01 type03">로그인 상태 유지</label>
            </p>
            <p id="btn_wrap1">
              <input type="submit" id="s_btn1" class="s_btn" value="로그인">
            </p><!-- btn_wrap1 -->

            <p class="login_account">
              <a href="#" class="search1">아이디/비밀번호 찾기</a>
              <a href="<c:url value="/join"/>" class="join1">회원가입</a>
            </p>
          </form>
        </div><!-- 회원로그인, login1 -->
      </div><!-- login_con -->
    </div>
  </main>
</div>
</body>

<script>
  $(document).ready(function() {

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

    //아이디저장, 로그인상태 유지
    $("#chk1,#chk2").change(function() {
      var chk1 = $("#chk1").prop("checked");
      var chk2 = $("#chk2").prop("checked");

      if (chk1) {
        $(".type02").addClass("checked");
      } else {
        $(".type02").removeClass("checked");
      }

      if (chk2) {
        $(".type03").addClass("checked");
      } else {
        $(".type03").removeClass("checked");
      }
    })

  })
</script>
</html>