var header_sub = `
        <header>
        <div id="header">
            <h1 id="logo"><a href="/">SEOUL<br>FESTIVAL</a></h1>
            <nav id="gnb">
                <ul>
                    <li><a href="#">축제안내</a></li>
<!--                    <li><a href="/project/list?page=1&size=6">예약하기</a></li>-->
                    <li><a href="/project/list">예약하기</a></li>
                    <li><a href="notice.html">공지사항</a></li>
                    <li><a href="myPage.html">그외</a></li>
                </ul>
            </nav>
            <div id="header_login">
                <a href="/project/login">로그인</a>
                <a href="/project/logout">로그아웃</a>
                <a href="/project/join">회원가입</a>
            </div>
        </div>
    </header>
`;

document.write(header_sub);
