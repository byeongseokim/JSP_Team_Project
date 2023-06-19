package com.example.culture.controller;

import com.example.common.util.SingletonProvideUtil;
import com.example.culture.service.CultureService;

import javax.servlet.http.HttpServlet;

public class CultureController extends HttpServlet {
    protected final CultureService cultureService = SingletonProvideUtil.SINGLETON_UTIL.cultureService();
}
