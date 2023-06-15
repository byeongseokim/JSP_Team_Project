package com.example;


import com.example.api.ApiProvider;
import com.example.api.ApiRatePolicy;
import com.example.api.CultureJsonApiProvider;
import com.example.api.ProjectApiRatePolicy;
import com.example.common.config.HikariDsConfig;
import com.example.culture.dao.CultureDAO;
import com.example.culture.dao.JdbcCultureDAO;
import com.example.culture.service.CultureService;
import com.example.culture.service.CultureServiceImpl;
import com.example.culture.vo.CultureVO;
import com.example.qna.dao.JdbcQnADAO;
import com.example.qna.dao.QnADAO;
import com.example.qna.service.QnAService;
import com.example.qna.service.QnAServiceImpl;
import com.example.reservation.dao.JdbcReservationDAO;
import com.example.reservation.dao.ReservationDAO;
import com.example.reservation.service.ReservationService;
import com.example.reservation.service.ReservationServiceImpl;
import com.example.review.dao.JdbcReviewDAO;
import com.example.review.dao.ReviewDAO;
import com.example.review.service.ReviewService;
import com.example.review.service.ReviewServiceImpl;
import com.example.user.dao.JdbcUserDAO;
import com.example.user.dao.UserDAO;
import com.example.user.service.UserService;
import com.example.user.service.UserServiceImpl;
import com.google.gson.Gson;

import javax.sql.DataSource;

public final class AppConfig {

    //dataSource
    public DataSource dataSource() {
        return new HikariDsConfig().config();
    }
    //gson
    public Gson gson(){
        return new Gson();
    }

    //userService
    public UserService userService() {
        return new UserServiceImpl(userDAO());
    }
    //userDAO
    public UserDAO userDAO() {
        return new JdbcUserDAO();
    }

    //apiProvider
    public ApiProvider apiProvider(){
        return new CultureJsonApiProvider
                ("6653645678736b6139317441527257","문화행사",cultureDAO());
    }
    //apiRatePolicy
    public ApiRatePolicy apiRatePolicy(){
        return new ProjectApiRatePolicy();
    }

    //cultureService
    public CultureService cultureService(){
            return new CultureServiceImpl(apiProvider(),apiRatePolicy(), cultureDAO());
    }
    //cultureDAO
    public CultureDAO<CultureVO> cultureDAO() {
        return new JdbcCultureDAO();
    }

    //reservationService
    public ReservationService reservationService() {
        return new ReservationServiceImpl(reservationDAO(), cultureDAO(),userDAO());
    }
    //reservationDAO
    public ReservationDAO reservationDAO() {
        return new JdbcReservationDAO();
    }

    //reviewService
    public ReviewService reviewService(){
        return new ReviewServiceImpl(reviewDAO());
    }
    //reviewDAO
    public ReviewDAO reviewDAO() {
        return new JdbcReviewDAO();
    }

    //qnaService
    public QnAService qnAService(){
        return new QnAServiceImpl(qnADAO());
    }
    //qnaDao
    public QnADAO qnADAO() {
        return new JdbcQnADAO();
    }
}
