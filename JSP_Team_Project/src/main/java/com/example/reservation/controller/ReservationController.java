package com.example.reservation.controller;

import com.example.culture.service.CultureService;
import com.example.reservation.service.ReservationService;
import com.example.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;

@Slf4j
public class ReservationController extends HttpServlet {

    protected ReservationService reservationService = SINGLETON_UTIL.reservationService();

    protected CultureService cultureService = SINGLETON_UTIL.cultureService();

    protected UserService userService = SINGLETON_UTIL.userService();
}
