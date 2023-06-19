<%--
  Created by IntelliJ IDEA.
  User: 502
  Date: 2022-10-27
  Time: 오전 10:54
  캘린더 파일
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
    .calendar {
        grid-area: a;
        text-align:center; border: 1px solid #eaeaea;}
    .calendar td { width:60px;height:50px; line-height: 50px; }
    .calendar td:hover{font-weight: bold;}

    .calendar thead td{font-weight: bold;}
    .calendar .calendar_tit td { }
    .calendar .calendar_days td { }

    .calendar .choiceDay{
        background-color: #af28fe;
        color: #ffffff;
    }
</style>
<script src="javascript/calendar.js"></script>

<div class="calendar_wrap">
    <table class="calendar">
        <thead>
        <tr class="calendar_tit">
            <td onClick="prevCalendar();" style="cursor:pointer;">&#60;&#60;</td>
            <td colspan="5">
                <span id="calYear">YYYY</span>년
                <span id="calMonth">MM</span>월
            </td>
            <td onClick="nextCalendar();" style="cursor:pointer;">&#62;&#62;</td>
        </tr>
        <tr class="calendar_days">
            <td>일</td>
            <td>월</td>
            <td>화</td>
            <td>수</td>
            <td>목</td>
            <td>금</td>
            <td>토</td>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

