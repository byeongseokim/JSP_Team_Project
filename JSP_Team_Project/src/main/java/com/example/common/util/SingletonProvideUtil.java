package com.example.common.util;


import com.example.AppConfig;
import com.example.culture.service.CultureService;
import com.example.qna.service.QnAService;
import com.example.reservation.service.ReservationService;
import com.example.review.service.ReviewService;
import com.example.user.service.UserService;
import com.google.gson.Gson;

public final class SingletonProvideUtil {

    public static final SingletonProvideUtil SINGLETON_UTIL
            = new SingletonProvideUtil();

    private final AppConfig appConfig;

    private SingletonProvideUtil() {
        appConfig = new AppConfig();
    }

    ///////////////////gson

    public Gson gson(){
        return appConfig.gson();
    }

    ///////////////////user

    //userService
    public UserService userService() {
        return appConfig.userService();
    }

    ///////////////////culture

    //cultureService
    public CultureService cultureService() {
        return appConfig.cultureService();
    }

    ///////////////////reservation

    //reservationService
    public ReservationService reservationService() {
        return appConfig.reservationService();
    }

    ////////////////////review

    //reviewService
    public ReviewService reviewService(){
        return appConfig.reviewService();
    }

    ////////////////////QnA

    //qnaService
    public QnAService qnAService() {
        return appConfig.qnAService();
    }

}
