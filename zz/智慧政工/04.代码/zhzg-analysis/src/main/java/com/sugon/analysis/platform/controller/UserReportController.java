package com.sugon.analysis.platform.controller;

import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.platform.dto.UserReportQuery;
import com.sugon.analysis.platform.service.UserReportService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 用户报告控制层
 * @author: liuwei
 * @create: 2020-05-27 15:56
 **/
@RestController
@RequestMapping("/userReport")
@CrossOrigin
public class UserReportController {
    @Resource
    private UserReportService userReportService;

    /**
     * 访问情况概况
     * @param userReportQuery
     * @return
     */
    @RequestMapping("/summary")
    public ResultModel summary(@RequestBody UserReportQuery userReportQuery){
        return userReportService.summary(userReportQuery);
    }

    /**
     * 访问单位况概况
     * @param userReportQuery
     * @return
     */
    @RequestMapping("/summaryByDept")
    public ResultModel summaryByDept(@RequestBody UserReportQuery userReportQuery){
        return userReportService.summaryByDept(userReportQuery);
    }
    /**
     * 访问人员况概况
     * @param userReportQuery
     * @return
     */
    @RequestMapping("/summaryByPeople")
    public ResultModel summaryByPeople(@RequestBody UserReportQuery userReportQuery){
        return userReportService.summaryByPeople(userReportQuery);
    }

    /**
     * 访问情况概况
     * @param userReportQuery
     * @return
     */
    @RequestMapping("/export")
    public ResultModel export(@RequestBody UserReportQuery userReportQuery, HttpServletResponse response){
        return userReportService.export(userReportQuery,response);
    }
}
