package com.chensha.universalsurveysystem.controller;

import com.chensha.universalsurveysystem.service.QuestionnaireService;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.QuestionnaireTimeParams;
import com.chensha.universalsurveysystem.vo.papams.UpdateQuestionnaireParams;
import com.chensha.universalsurveysystem.vo.papams.UploadAnswerParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/questionnaire")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    /**
     * 获取问卷列表
     *
     * @param authHeader 认证头
     * @return QuestionnaireVo列表
     */
    @GetMapping("listQuestionnaire")
    public Result listQuestionnaire(@RequestHeader("Authorization") String authHeader) {
        return questionnaireService.listQuestionnaire(authHeader);
    }


    /**
     * 更新问卷
     *
     * @param authHeader                认证头
     * @param updateQuestionnaireParams 问卷信息
     * @return 问卷列表
     */
    @PostMapping("updateQuestionnaire")
    public Result updateQuestionnaire(@RequestHeader("Authorization") String authHeader, @RequestBody UpdateQuestionnaireParams updateQuestionnaireParams) {
        return questionnaireService.updateQuestionnaire(authHeader, updateQuestionnaireParams);
    }

    /**
     * 删除问卷
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @return 问卷列表
     */
    @GetMapping("deleteQuestionnaire/{qid}")
    public Result deleteQuestionnaire(@RequestHeader("Authorization") String authHeader, @PathVariable("qid") String qid) {
        return questionnaireService.deleteQuestionnaire(authHeader, qid);
    }

    /**
     * 获取问卷详情
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @return 问卷详情
     */
    @GetMapping("getQuestionnaireDetail/{qid}")
    public Result getQuestionnaireDetail(@RequestHeader(name = "Authorization", required = false) String authHeader, @PathVariable("qid") String qid) {
        return questionnaireService.getQuestionnaireDetail(authHeader, qid);
    }

    /**
     * 上传答案
     *
     * @param authHeader   认证头
     * @param answerParams 答案信息
     * @return paper列表
     */
    @PostMapping("uploadAnswer")
    public Result uploadAnswer(@RequestHeader(name = "Authorization", required = false) String authHeader, @RequestBody UploadAnswerParams answerParams) {
        return questionnaireService.uploadAnswer(authHeader, answerParams);
    }

    /**
     * 设置问卷开始结束时间
     *
     * @param authHeader 认证头
     * @param timeParams 时间信息
     * @return 成功或失败
     */
    @PostMapping("setQuestionnaireTime")
    public Result setQuestionnaireTime(@RequestHeader("Authorization") String authHeader, @RequestBody QuestionnaireTimeParams timeParams) {
        return questionnaireService.setQuestionnaireTime(authHeader, timeParams);
    }

    /**
     * 获取问卷分析报告
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @return 问卷分析报告
     */
    @GetMapping("getQuestionnaireReport/{qid}")
    public Result getQuestionnaireReport(@RequestHeader("Authorization") String authHeader, @PathVariable("qid") String qid) {
        return questionnaireService.getQuestionnaireReport(authHeader, qid);
    }

    /**
     * 下载问卷
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @return excel文件
     */
    @GetMapping(value = "downloadQuestionnaire/{qid}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadQuestionnaire(@RequestHeader("Authorization") String authHeader, @PathVariable("qid") String qid) {
        return questionnaireService.downloadQuestionnaire(authHeader, qid);
    }

    /**
     * 设置问卷匿名状态
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @param anonymous  匿名状态
     */
    @GetMapping("setQuestionnaireAnonymous/{qid}/{anonymous}")
    public Result setQuestionnaireAnonymous(@RequestHeader("Authorization") String authHeader, @PathVariable("qid") String qid, @PathVariable("anonymous") Integer anonymous) {
        return questionnaireService.setQuestionnaireAnonymous(authHeader, qid, anonymous);
    }

    /**
     * 获取统计数据
     *
     * @param authHeader 认证头
     *
     */

    @GetMapping("getStatisticsData")
    public Result getStatisticsData(@RequestHeader("Authorization") String authHeader) {
        return questionnaireService.getStatisticsData(authHeader);
    }
}
