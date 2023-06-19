package com.example.qna.controller;

import com.example.qna.service.QnAService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;

@Slf4j
public class QnAController extends HttpServlet {
    protected final QnAService qnAService= SINGLETON_UTIL.qnAService();
}
