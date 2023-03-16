package com.chensha.universalsurveysystem.controller;

import com.chensha.universalsurveysystem.service.QuestionnaireService;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.AddQuestionnaireParams;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 增加问卷
     *
     * @param authHeader             认证头
     * @param addQuestionnaireParams 问卷信息
     * @return 问卷列表
     */
    @PatchMapping("addQuestionnaire")
    public Result addQuestionnaire(@RequestHeader("Authorization") String authHeader, @RequestBody AddQuestionnaireParams addQuestionnaireParams) {
        return questionnaireService.addQuestionnaire(authHeader, addQuestionnaireParams);
    }

    /**
     * 删除问卷
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @return 问卷列表
     */
    @GetMapping("deleteQuestionnaire")
    public Result deleteQuestionnaire(@RequestHeader("Authorization") String authHeader, @RequestBody String qid) {
        return questionnaireService.deleteQuestionnaire(authHeader, qid);
    }

}
