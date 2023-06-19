
document.addEventListener("DOMContentLoaded", function () {
    buildCalendar();
});

var today = new Date(); // @param 전역 변수, 오늘 날짜 / 내 컴퓨터 로컬을 기준으로 today에 Date 객체를 넣어줌
var date = new Date(); // @param 전역 변수, today의 Date를 세어주는 역할

/* @brief   이전달 버튼 클릭 */
function prevCalendar() {
    this.today = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
    buildCalendar(); // @param 전월 캘린더 출력 요청
}

/* @brief   다음달 버튼 클릭 */
function nextCalendar() {
    this.today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
    buildCalendar(); // @param 명월 캘린더 출력 요청
}

/**
 * @brief   캘린더 오픈
 * @details 날짜 값을 받아 캘린더 폼을 생성하고, 날짜값을 채워넣는다.
 */
function buildCalendar() {
    let doMonth = new Date(today.getFullYear(), today.getMonth(), 1);
    let lastDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);

    let tbCalendar = document.querySelector(".calendar > tbody");

    document.getElementById("calYear").innerText = today.getFullYear(); // @param YYYY월
    document.getElementById("calMonth").innerText = autoLeftPad((today.getMonth() + 1), 2); // @param MM월


    // @details 이전 캘린더의 출력결과가 남아있다면, 이전 캘린더를 삭제한다.
    while (tbCalendar.rows.length > 0) {
        tbCalendar.deleteRow(tbCalendar.rows.length - 1);
    }

    // @param 첫번째 개행
    let row = tbCalendar.insertRow();

    // @param 날짜가 표기될 열의 증가값
    let dom = 1;

    // @details 시작일의 요일값( doMonth.getDay() ) + 해당월의 전체일( lastDate.getDate())을  더해준 값에서
    //               7로 나눈값을 올림( Math.ceil() )하고 다시 시작일의 요일값( doMonth.getDay() )을 빼준다.
    let daysLength = (Math.ceil((doMonth.getDay() + lastDate.getDate()) / 7) * 7) - doMonth.getDay();
    let query = window.location.search;
    let param = new URLSearchParams(query);
    let cno = param.get('cno');
    let resDates = getResDates(cno);

    // @param 달력 출력
    // @details 시작값은 1일을 직접 지정하고 요일값( doMonth.getDay() )를 빼서 마이너스( - )로 for문을 시작한다.
    for (let day = 1 - doMonth.getDay(); daysLength >= day; day++) {

        let column = row.insertCell();
        //db에서 예약 가능 날짜를 받아와야 한다..
        //get으로 조회를 보내서 날짜 객체를 받아보자......
        let from = new Date(resDates.from);
        let to = new Date(resDates.to);
        // @param 평일( 전월일과 익월일의 데이터 제외 )
        if (Math.sign(day) == 1 && lastDate.getDate() >= day) {

            // @param 평일 날짜 데이터 삽입
            column.innerText = autoLeftPad(day, 2);

            // @param 일요일인 경우
            if (dom % 7 == 1)
                column.style.color = "#FF4D4D";

            // @param 토요일인 경우
            if (dom % 7 == 0) {
                column.style.color = "#4D4DFF";
                row = tbCalendar.insertRow(); // @param 토요일이 지나면 다시 가로 행을 한줄 추가한다.
            }
        }

        // @param 평일 전월일과 익월일의 데이터 날짜변경
        else {
            let exceptDay = new Date(doMonth.getFullYear(), doMonth.getMonth(), day);
            column.innerText = autoLeftPad(exceptDay.getDate(), 2);
            column.style.color = "#A9A9A9";
        }
        // console.log(from.getFullYear())
        // console.log(from.getMonth())
        // console.log(from.getDay())
        // console.log(from.getDay()===day)
        // console.log('-----------------')
        // console.log(today)
        // console.log(today.getMonth())
        // console.log('----------------')
        // console.log(date)
        // console.log('---------------')


        // @brief   전월, 명월 음영처리
        // @details 예약 연도 비교
        if (today.getFullYear() === from.getFullYear() && today.getFullYear()===to.getFullYear()) {

            /////////////////////임시///////////////////////////////////
            if(today.getMonth()==from.getMonth()&&today.getMonth()==to.getMonth()){
                // @details to보다 이후인 경우이면서 이번달에 포함되는 일인경우
                if ((to.getDate() < day || from.getDate()>day) && Math.sign(day) == 1)
                    column.style.backgroundColor = "#c5c5c5";
                else { //예약가능일
                    if(Math.sign(day) == 1){
                        column.style.backgroundColor = "#FFFFFF";
                        column.style.cursor = "pointer";
                        column.onclick = function() {calendarChoiceDay(this);}
                    }
                }
            }
            // @details 예약 월이 to 일때
            else if (today.getMonth()==to.getMonth()) {

                // @details to보다 이후인 경우이면서 이번달에 포함되는 일인경우
                if (to.getDate() < day && Math.sign(day) == 1)
                    column.style.backgroundColor = "#c5c5c5";
                else { //예약가능일
                    if(Math.sign(day) == 1){
                        column.style.backgroundColor = "#FFFFFF";
                        column.style.cursor = "pointer";
                        column.onclick = function() {calendarChoiceDay(this);}
                    }
                }
            }
            else if (today.getMonth()>to.getMonth()){
                // if (to.getDate() < day && Math.sign(day) == 1)
                //     column.style.backgroundColor = "#c5c5c5";
                if (Math.sign(day) == 1)
                    column.style.backgroundColor = "#c5c5c5";
            }

            // @details 예약 월이 from 일때
            else if (today.getMonth()==from.getMonth()) {
                // @details from보다 이전인 경우이면서 이번달에 포함되는 일인경우
                if (from.getDate() > day && Math.sign(day) == 1)
                    column.style.backgroundColor = "#c5c5c5";
                else { //예약가능일
                    if(Math.sign(day) == 1 && lastDate.getDate() >= day){
                        column.style.backgroundColor = "#FFFFFF";
                        column.style.cursor = "pointer";
                        column.onclick = function () {calendarChoiceDay(this);}
                    }
                }
            }
            else if(today.getMonth()<from.getMonth()){
                // if (to.getDate() < day && Math.sign(day) == 1)
                //     column.style.backgroundColor = "#c5c5c5";
                if (Math.sign(day) == 1)
                    column.style.backgroundColor = "#c5c5c5";
            }
            ////////////////////////////임시////////////////////////////













            // // @details 예약 월이 to 일때
            // if (today.getMonth()==to.getMonth()) {
            //
            //     // @details to보다 이후인 경우이면서 이번달에 포함되는 일인경우
            //     if (to.getDate() < day && Math.sign(day) == 1)
            //         column.style.backgroundColor = "#c5c5c5";
            //     else { //예약가능일
            //         if(Math.sign(day) == 1){
            //             column.style.backgroundColor = "#FFFFFF";
            //             column.style.cursor = "pointer";
            //             column.onclick = function() {calendarChoiceDay(this);}
            //         }
            //     }
            //
            //     // @details 예약 월이 from 일때
            // } else if (today.getMonth()==from.getMonth()) {
            //
            //     // @details from보다 이전인 경우이면서 이번달에 포함되는 일인경우
            //     if (from.getDate() > day && Math.sign(day) == 1)
            //         column.style.backgroundColor = "#c5c5c5";
            //     else { //예약가능일
            //         if(Math.sign(day) == 1 && lastDate.getDate() >= day){
            //             column.style.backgroundColor = "#FFFFFF";
            //             column.style.cursor = "pointer";
            //             column.onclick = function () {calendarChoiceDay(this);}
            //         }
            //     }
            // }
            //
            // else if (today.getMonth() < from.getMonth()) {
            //     if (Math.sign(day) == 1 && day <= lastDate.getDate())
            //         column.style.backgroundColor = "#c5c5c5";
            // } else if (today.getMonth() > to.getMonth()) {
            //     if (Math.sign(day) == 1 && day <= lastDate.getDate())
            //         column.style.backgroundColor = "#c5c5c5";
            // }

        }
        else { // 다른 연도
            column.style.backgroundColor = "#c5c5c5";
        }
        dom++;
    }
}

/**
 * @brief   날짜 선택
 * @details 사용자가 선택한 날짜에 체크표시를 남긴다.
 */
function calendarChoiceDay(column) {
    let selYear = document.getElementById("calYear").innerText;
    let selMonth = document.getElementById("calMonth").innerText;
    let selDay = column.innerText;


    // @param 기존 선택일이 존재하는 경우 기존 선택일의 표시형식을 초기화 한다.
    if (document.getElementsByClassName("choiceDay")[0]) {
        document.getElementsByClassName("choiceDay")[0].style.backgroundColor = "#FFFFFF";
        document.getElementsByClassName("choiceDay")[0].style.color = "#000000";
        document.getElementsByClassName("choiceDay")[0].classList.remove("choiceDay");
    }


    // @param 선택일 체크 표시
    column.style.backgroundColor = "#af28fe";
    column.style.color = "#ffffff";

    // @param 선택일 클래스명 변경
    column.classList.add("choiceDay");

    document.getElementById("cal_getYear").value =
        selYear;
    document.getElementById("cal_getMonth").value =
        selMonth;
    document.getElementById("cal_getDay").value =
        selDay;


    let query = window.location.search;
    let param = new URLSearchParams(query);
    let cno = param.get('cno');

    getCultureCnt(cno,selYear,selMonth,selDay);
}

let getCultureCnt = function(cno,selYear,selMonth,selDay) {
    $.ajax({
        url: '/project/resCnt?selYear='+selYear+'&selMonth='+selMonth+'&selDay='+selDay+'&cno='+cno,
        type: 'GET',
        headers: {"content-type":"application/json; charset=UTF-8"},

        success : function(result){
            //possibleCnt
            // alert('선택하신 날짜에 '+result.currentResCnt+'명이 예약중입니다.');
            // document.getElementById("possibleCnt").innerText =
            //     '( '+result.currentResCnt+' / '+result.capacity+' 명)';
            // alert('선택하신 날짜에 '+result.currentResCnt+'명이 예약중입니다.');
            document.getElementById("possibleCnt").innerText =
                '( '+result.currentResCnt+' / '+result.capacity+' )';
        },
        error: function() {
            alert("예약인원 조회 실패");
        }
    });//ajax
}//getReviews

/**
 * @brief   숫자 두자릿수( 00 ) 변경
 * @details 자릿수가 한자리인 ( 1, 2, 3등 )의 값을 10, 11, 12등과 같은 두자리수 형식으로 맞추기위해 0을 붙인다.
 * @param   num     앞에 0을 붙일 숫자 값
 * @param   digit   글자의 자릿수를 지정 ( 2자릿수인 경우 00, 3자릿수인 경우 000 … )
 */
function autoLeftPad(num, digit) {
    if (String(num).length < digit) {
        num = new Array(digit - String(num).length + 1).join("0") + num;
    }
    return num;
}


let getResDates = function (cno) {
    var result;
    $.ajax({
        url: '/project/cultureCal?cno=' + cno,
        type: 'GET',
        headers: {"content-type": "application/json"},
        async: false,

        success: function (data) {
            result = data;
        },
        error: function () {
            alert("error")
        }
    });//ajax
    return result;
}