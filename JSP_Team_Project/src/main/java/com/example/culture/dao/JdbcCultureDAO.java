package com.example.culture.dao;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.culture.vo.CultureVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.common.util.ConnectionUtil.CONN_UTIL;

public class JdbcCultureDAO implements CultureDAO<CultureVO> {

    @Override
    public void insert(CultureVO cultureVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "insert into culture_basic " +
                    "(svc_nm,area_nm,place_nm,tel_no,svc_id) " +
                    "values(?,?,?,?,?)";
            conn = CONN_UTIL.getConnection();
            Objects.requireNonNull(conn).setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cultureVO.getSvc_nm());
            pstmt.setString(2, cultureVO.getArea_nm());
            pstmt.setString(3, cultureVO.getPlace_nm());
            pstmt.setString(4, cultureVO.getTel_no());
//            pstmt.setLong(5, cultureVO.getCno());
            pstmt.setString(5,cultureVO.getSvc_id());
            pstmt.executeUpdate();

            sql = "insert into culture_info " +
                    "(pay_ay_nm,use_tgt_info,svc_url,img_url,dtlcont) " +
                    "values(?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cultureVO.getPay_ay_nm());
            pstmt.setString(2, cultureVO.getUse_tgt_info());
            pstmt.setString(3, cultureVO.getSvc_url());
            pstmt.setString(4, cultureVO.getImg_url());
            pstmt.setString(5, cultureVO.getDtlcont());
//            pstmt.setLong(6, cultureVO.getCno());
            pstmt.executeUpdate();

            sql = "insert into culture_res " +
                    "(capacity,price,revstd_day_nm,revstd_day) " +
                    "values(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cultureVO.getCapacity());
            pstmt.setInt(2, cultureVO.getPrice());
//            pstmt.setLong(3, cultureVO.getCno());
            pstmt.setString(3, cultureVO.getRevstd_day_nm());
            pstmt.setString(4, cultureVO.getRevstd_day());
            pstmt.executeUpdate();

            sql = "insert into culture_schedule " +
                    "(svc_opn_bgn_dt,svc_opn_end_dt,v_min,v_max,rcpt_bgn_dt,rcpt_end_dt) " +
                    "values(?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cultureVO.getSvc_opn_bgn_dt());
            pstmt.setString(2, cultureVO.getSvc_opn_end_dt());
            pstmt.setString(3, cultureVO.getV_min());
            pstmt.setString(4, cultureVO.getV_max());
            pstmt.setString(5, cultureVO.getRcpt_bgn_dt());
            pstmt.setString(6, cultureVO.getRcpt_end_dt());
//            pstmt.setLong(7, cultureVO.getCno());
            pstmt.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                Objects.requireNonNull(conn).rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException("culture insert를 롤백에 실패했습니다");
            }
            e.printStackTrace();
            throw new RuntimeException("롤백합니다 cultrue 등록에 실패했습니다");
        } finally {
            CONN_UTIL.close(pstmt,conn);
        }
    }

    //작성중
    @Override
    public PageResponseVO<CultureVO> selectAll(PageRequestVO pageRequestVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PageResponseVO<CultureVO> pageResponseVO = null;
        try {
            String sql = "select basic.cno,basic.svc_id,basic.svc_nm,basic.area_nm,basic.place_nm,basic.tel_no," +
                    "info.pay_ay_nm,info.use_tgt_info,info.svc_url,info.img_url,info.dtlcont," +
                    "res.capacity,res.price,res.revstd_day,res.revstd_day_nm," +
                    "sch.svc_opn_bgn_dt,sch.svc_opn_end_dt,sch.v_min,sch.v_max,sch.rcpt_bgn_dt,sch.rcpt_end_dt\n" +
                    "from " +
                    "(culture_basic as basic inner join culture_info as info on basic.cno=info.cno " +
                    "inner join culture_res as res on info.cno=res.cno " +
                    "inner join culture_schedule as sch on res.cno=sch.cno) " +
                    "where svc_opn_end_dt > now() and rcpt_end_dt > now() "+
                    "order by basic.cno desc " +
                    "limit ? , ?;";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setInt(1, pageRequestVO.getSkip());
            pstmt.setInt(2, pageRequestVO.getSize());
            rs = pstmt.executeQuery();
            List<CultureVO> cultureVOS = new ArrayList<>();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            while(rs.next()){
                CultureVO cultureVO = CultureVO.builder()
                        .cno(rs.getLong("cno"))
                        .svc_id(rs.getString("svc_id"))
                        .svc_nm(rs.getString("svc_nm"))
                        .area_nm(rs.getString("area_nm"))
                        .place_nm(rs.getString("place_nm"))
                        .tel_no(rs.getString("tel_no"))
                        .pay_ay_nm(rs.getString("pay_ay_nm"))
                        .use_tgt_info(rs.getString("use_tgt_info"))
                        .svc_url(rs.getString("svc_url"))
                        .img_url(rs.getString("img_url"))
                        .dtlcont(rs.getString("dtlcont"))
                        .svc_opn_bgn_dt(rs.getString("svc_opn_bgn_dt"))
                        .svc_opn_end_dt(rs.getString("svc_opn_end_dt"))
                        .v_min(rs.getString("v_min"))
                        .v_max(rs.getString("v_max"))
                        .rcpt_bgn_dt(rs.getString("rcpt_bgn_dt"))
                        .rcpt_end_dt(rs.getString("rcpt_end_dt"))
                        .capacity(rs.getInt("capacity"))
                        .price(rs.getInt("price"))
                        .revstd_day_nm(rs.getString("revstd_day_nm"))
                        .revstd_day(rs.getString("revstd_day"))
                        .build();
                cultureVOS.add(cultureVO);
            }
             pageResponseVO = PageResponseVO.<CultureVO>withAll()
                .pageRequestVO(pageRequestVO)
                .total(selectCount())
                .pageList(cultureVOS)
                .build();
            return pageResponseVO;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("cultrue 조회(리스트)에 실패했습니다");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
    }

    @Override
    public List<CultureVO> selectAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CultureVO> cultureVOList = new ArrayList<>();
        try {
            String sql = "select basic.cno,basic.svc_id,basic.svc_nm,basic.area_nm,basic.place_nm,basic.tel_no," +
                    "info.pay_ay_nm,info.use_tgt_info,info.svc_url,info.img_url,info.dtlcont," +
                    "res.capacity,res.price,res.revstd_day,res.revstd_day_nm," +
                    "sch.svc_opn_bgn_dt,sch.svc_opn_end_dt,sch.v_min,sch.v_max,sch.rcpt_bgn_dt,sch.rcpt_end_dt\n" +
                    "from " +
                    "(culture_basic as basic inner join culture_info as info on basic.cno=info.cno " +
                    "inner join culture_res as res on info.cno=res.cno " +
                    "inner join culture_schedule as sch on res.cno=sch.cno)";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                CultureVO cultureVO = CultureVO.builder()
                        .cno(rs.getLong("cno"))
                        .svc_id(rs.getString("svc_id"))
                        .svc_nm(rs.getString("svc_nm"))
                        .area_nm(rs.getString("area_nm"))
                        .place_nm(rs.getString("place_nm"))
                        .tel_no(rs.getString("tel_no"))
                        .pay_ay_nm(rs.getString("pay_ay_nm"))
                        .use_tgt_info(rs.getString("use_tgt_info"))
                        .svc_url(rs.getString("svc_url"))
                        .img_url(rs.getString("img_url"))
                        .dtlcont(rs.getString("dtlcont"))
                        .svc_opn_bgn_dt(rs.getString("svc_opn_bgn_dt"))
                        .svc_opn_end_dt(rs.getString("svc_opn_end_dt"))
                        .v_min(rs.getString("v_min"))
                        .v_max(rs.getString("v_max"))
                        .rcpt_bgn_dt(rs.getString("rcpt_bgn_dt"))
                        .rcpt_end_dt(rs.getString("rcpt_end_dt"))
                        .capacity(rs.getInt("capacity"))
                        .price(rs.getInt("price"))
                        .revstd_day_nm(rs.getString("revstd_day_nm"))
                        .revstd_day(rs.getString("revstd_day"))
                        .build();
                cultureVOList.add(cultureVO);
            }
            return cultureVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("cultrue 조회(list)에 실패했습니다");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
    }

    @Override
    public int selectCount() {
        String sql = "select count(*) from culture_schedule " +
                "where svc_opn_end_dt > now() and rcpt_end_dt > now()";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = CONN_UTIL.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("cultrue 조회(count)에 실패했습니다");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "delete basic,info,res,sch\n" +
                "from (culture_basic as basic inner join culture_info as info on basic.cno=info.cno\n" +
                "                    inner join culture_res as res on info.cno=res.cno\n" +
                "                    inner join culture_schedule as sch on res.cno=sch.cno)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = CONN_UTIL.getConnection();

            String sql_setFk = "set foreign_key_checks=0";
            pstmt = conn.prepareStatement(sql_setFk);
            pstmt.execute();

            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

            sql_setFk = "set foreign_key_checks=1";
            pstmt = conn.prepareStatement(sql_setFk);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("culture 삭제에 실패했습니다");
        } finally {
            CONN_UTIL.close(pstmt,conn);
        }
    }

    @Override
    public CultureVO selectOne(Long cno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select basic.cno,basic.svc_id,basic.svc_nm,basic.area_nm,basic.place_nm,basic.tel_no," +
                    "info.pay_ay_nm,info.use_tgt_info,info.svc_url,info.img_url,info.dtlcont," +
                    "res.capacity,res.price,res.revstd_day,res.revstd_day_nm," +
                    "sch.svc_opn_bgn_dt,sch.svc_opn_end_dt,sch.v_min,sch.v_max,sch.rcpt_bgn_dt,sch.rcpt_end_dt\n" +
                    "from " +
                    "(culture_basic as basic inner join culture_info as info on basic.cno=info.cno " +
                    "inner join culture_res as res on info.cno=res.cno " +
                    "inner join culture_schedule as sch on res.cno=sch.cno) " +
                    "where basic.cno = ?";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,cno);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return CultureVO.builder()
                        .cno(rs.getLong("cno"))
                        .svc_id(rs.getString("svc_id"))
                        .svc_nm(rs.getString("svc_nm"))
                        .area_nm(rs.getString("area_nm"))
                        .place_nm(rs.getString("place_nm"))
                        .tel_no(rs.getString("tel_no"))
                        .pay_ay_nm(rs.getString("pay_ay_nm"))
                        .use_tgt_info(rs.getString("use_tgt_info"))
                        .svc_url(rs.getString("svc_url"))
                        .img_url(rs.getString("img_url"))
                        .dtlcont(rs.getString("dtlcont"))
                        .svc_opn_bgn_dt(rs.getString("svc_opn_bgn_dt"))
                        .svc_opn_end_dt(rs.getString("svc_opn_end_dt"))
                        .v_min(rs.getString("v_min"))
                        .v_max(rs.getString("v_max"))
                        .rcpt_bgn_dt(rs.getString("rcpt_bgn_dt"))
                        .rcpt_end_dt(rs.getString("rcpt_end_dt"))
                        .capacity(rs.getInt("capacity"))
                        .price(rs.getInt("price"))
                        .revstd_day_nm(rs.getString("revstd_day_nm"))
                        .revstd_day(rs.getString("revstd_day"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("cultrue 조회(단일)에 실패했습니다");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
        return null;
    }

    @Override
    public PageResponseVO<CultureVO> selectSearchedCultures_noType(String keyword, PageRequestVO pageRequestVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CultureVO> cultureVOList = new ArrayList<>();
        try {
            String sql = "select basic.cno,basic.svc_id,basic.svc_nm,basic.area_nm,basic.place_nm,basic.tel_no," +
                    "info.pay_ay_nm,info.use_tgt_info,info.svc_url,info.img_url,info.dtlcont," +
                    "res.capacity,res.price,res.revstd_day,res.revstd_day_nm," +
                    "sch.svc_opn_bgn_dt,sch.svc_opn_end_dt,sch.v_min,sch.v_max,sch.rcpt_bgn_dt,sch.rcpt_end_dt\n" +
                    "from " +
                    "(culture_basic as basic inner join culture_info as info on basic.cno=info.cno " +
                    "inner join culture_res as res on info.cno=res.cno " +
                    "inner join culture_schedule as sch on res.cno=sch.cno)" +
                    "where (basic.svc_nm like ? or info.dtlcont like ?) and " +
                    "(svc_opn_end_dt > now() and rcpt_end_dt > now()) " +
                    "order by basic.cno desc " +
                    "limit ? , ?;";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setString(1,"%"+keyword+"%");
            pstmt.setString(2,"%"+keyword+"%");
            pstmt.setInt(3,pageRequestVO.getSkip());
            pstmt.setInt(4,pageRequestVO.getSize());
            rs = pstmt.executeQuery();
            while(rs.next()){
                CultureVO cultureVO = CultureVO.builder()
                        .cno(rs.getLong("cno"))
                        .svc_id(rs.getString("svc_id"))
                        .svc_nm(rs.getString("svc_nm"))
                        .area_nm(rs.getString("area_nm"))
                        .place_nm(rs.getString("place_nm"))
                        .tel_no(rs.getString("tel_no"))
                        .pay_ay_nm(rs.getString("pay_ay_nm"))
                        .use_tgt_info(rs.getString("use_tgt_info"))
                        .svc_url(rs.getString("svc_url"))
                        .img_url(rs.getString("img_url"))
                        .dtlcont(rs.getString("dtlcont"))
                        .svc_opn_bgn_dt(rs.getString("svc_opn_bgn_dt"))
                        .svc_opn_end_dt(rs.getString("svc_opn_end_dt"))
                        .v_min(rs.getString("v_min"))
                        .v_max(rs.getString("v_max"))
                        .rcpt_bgn_dt(rs.getString("rcpt_bgn_dt"))
                        .rcpt_end_dt(rs.getString("rcpt_end_dt"))
                        .capacity(rs.getInt("capacity"))
                        .price(rs.getInt("price"))
                        .revstd_day_nm(rs.getString("revstd_day_nm"))
                        .revstd_day(rs.getString("revstd_day"))
                        .build();
                cultureVOList.add(cultureVO);
            }
            return PageResponseVO.<CultureVO>withAll()
                    .pageRequestVO(pageRequestVO)
                    .total(selectSearchedCnt(keyword))
                    .pageList(cultureVOList)
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("cultrue 조회(list)에 실패했습니다");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
    }

    @Override
    public int selectSearchedCnt(String keyword) {
            String sql = "select count(*) " +
                "from " +
                "(culture_basic as basic inner join culture_info as info on basic.cno=info.cno " +
                "inner join culture_res as res on info.cno=res.cno " +
                "inner join culture_schedule as sch on res.cno=sch.cno)" +
                "where (basic.svc_nm like ? or info.dtlcont like ?) and " +
                "(svc_opn_end_dt > now() and rcpt_end_dt > now())";
//        String sql = "select count(*) from culture_schedule " +
//                "where (svc_opn_end_dt > now() and rcpt_end_dt > now()) and " +
//                "((basic.svc_nm like ? or info.dtlcont like ?))";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = CONN_UTIL.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"%"+keyword+"%");
            pstmt.setString(2,"%"+keyword+"%");
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("cultrue 조회(count)에 실패했습니다");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
    }
}
