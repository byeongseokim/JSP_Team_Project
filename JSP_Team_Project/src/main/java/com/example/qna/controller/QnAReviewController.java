package com.example.qna.controller;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.qna.vo.QnA_A_VO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;
import static com.example.common.util.Validation.validateUser;

@WebServlet(name="qnAReviewController",value="/qnaReview/*")
@Slf4j
public class QnAReviewController extends QnAController {
//    private final QnAService qnAService = SINGLETON_UTIL.qnAService();
    private final Gson gson = SINGLETON_UTIL.gson();

    //자바객체를 json 객체로 응답하는 메서드
    private void sendAsJson(HttpServletResponse response,
                            Object obj) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        String json = gson.toJson(obj);

        PrintWriter out = response.getWriter();

        out.print(json);
        out.flush();
    }

    @Override //get // /qnaReview?qqno=1&page=1&size=12
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("QnAReviewController.doGet");
        try {
            Long qqno = Long.valueOf(req.getParameter("qqno"));
            String paramPage = req.getParameter("page");
            String paramSize = req.getParameter("size");
            PageRequestVO pageRequestVO;
            //쿼리스트링으로 받아온 page size를 검증후 그에 맞는 PageRequest생성
            if(paramPage==null&&paramSize==null){
                pageRequestVO = PageRequestVO.builder().build();
            }
            else if (paramPage==null||"".equals(paramPage)){
                pageRequestVO = PageRequestVO.builder()
                        .size(Integer.parseInt(paramSize))
                        .build();
            }
            else if (paramSize==null|| "".equals(paramSize)) {
                pageRequestVO = PageRequestVO.builder()
                        .page(Integer.parseInt(paramPage))
                        .build();
            }
            else{ //paramPage!=null && paramSize!=null
                pageRequestVO = PageRequestVO.builder()
                        .page(Integer.parseInt(paramPage))
                        .size(Integer.parseInt(paramSize))
                        .build();
            }
            PageResponseVO<QnA_A_VO> pageResponseVO = qnAService.getQnAAList(qqno, pageRequestVO);
            //고의 예외발생 테스트
//            throw new Exception("고의 발생 예외");
            sendAsJson(resp, pageResponseVO);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            log.error("리뷰조회 실패");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"권한이 필요합니다"); //400에러
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("리뷰조회 예외");
        }
    }//review get

    @Override // /review
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
//            validateAdmin(req.getSession());
            //req 로부터 io스트림을 가져온다
            BufferedReader reader = req.getReader();
            //io로부터 읽어올 변수
            String line;
            //읽어온 변수를 저장할 변수 (json이라 문자열)
            StringBuilder stringBuilder = new StringBuilder();
            //더이상 읽어올 문자가 없을때까지 읽어온다
            while((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }
            //읽어온 데이터를 playload에 저장
            String payload = stringBuilder.toString();
            //읽어온 문자열(json)을 자바 객체로 파싱
            QnA_A_VO qnaA = gson.fromJson(payload, QnA_A_VO.class);
            //읽어오지 못했다면 400에러 리턴
            if(qnaA==null) {
                log.error("qnaA 를 가져오지 못함");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //400에러
                return;
            }
            //권한인증
            validateUser(req,req.getSession(),qnaA.getId());
            //qnaA!=null && 중복id x
            qnAService.writeQnAA(qnaA);
            resp.setStatus(200); //200 OK
        } catch(IllegalStateException e){
            e.printStackTrace();
            log.error("리뷰작성 실패");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"권한이 필요합니다"); //400에러
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("리뷰 작성 예외");
        }
    }//post

    @Override // /review/{re_no}  -> /{re_no} -> /27 == pathInfo
    //doput 리팩토링 해야함
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("QnAReviewController.doPut");
//            validateAdmin(req.getSession());
            String pathInfo = req.getPathInfo();

            if(pathInfo == null || pathInfo.equals("/")){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] splits = pathInfo.split("/");

            if(splits.length!=2){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Long qano = null;
            try {
                qano = Long.valueOf(splits[1]);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            QnA_A_VO qnaA = qnAService.getQnAA(qano);
            //null체크
            if(qnaA==null){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            //권한인증
            validateUser(req,req.getSession(),qnaA.getId());
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while((line = reader.readLine())!=null) {
                stringBuilder.append(line);
            }

            String payload = stringBuilder.toString();

            QnA_A_VO qnAAO1 = gson.fromJson(payload, QnA_A_VO.class);

            //업데이트할 칼럼만 가져와서 저장후 업데이트
            String content = qnAAO1.getContent();

            qnaA.setContent(content);

            qnAService.modify(qnaA);
            resp.setStatus(200);
        } catch(IllegalStateException e){
            e.printStackTrace();
            log.error("리뷰수정 실패");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"권한이 필요합니다"); //400에러
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("리뷰 수정 예외");
        }
    }

    @Override // /review/{re_no}  -> /{re_no} -> /27 == pathInfo
    //dodelete 리팩토링 해야함
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("QnAReviewController.doDelete");
            String pathInfo = req.getPathInfo();

            if(pathInfo == null || pathInfo.equals("/")){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] splits = pathInfo.split("/");

            if(splits.length!=2){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Long qano = null;
            try {
                qano = Long.valueOf(splits[1]);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            //권한인증
            validateUser(req,req.getSession(),qnAService.getQnAA(qano).getId());
            qnAService.removeQnAA(qano);
            resp.setStatus(200);
        } catch(IllegalStateException e){
            e.printStackTrace();
            log.error("리뷰삭제 실패");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"권한이 필요합니다"); //400에러
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("리뷰 삭제 예외");
        }
    }
}
