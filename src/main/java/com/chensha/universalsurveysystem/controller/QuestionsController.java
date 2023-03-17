package com.chensha.universalsurveysystem.controller;

import com.chensha.universalsurveysystem.service.QuestionsService;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.AddQuestionParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/question")
public class QuestionsController {

    @Autowired
    private QuestionsService questionsService;

    /**
     * 添加问题
     *
     * @param authHeader        认证头
     * @param addQuestionParams 问题信息
     * @return 问题列表
     */
    @PostMapping("addQuestion")
    public Result addQuestion(@RequestHeader("Authorization") String authHeader, @RequestBody AddQuestionParams addQuestionParams) {
        return questionsService.addQuestion(authHeader, addQuestionParams);
    }

    /**
     * 获取问题列表
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @return 问题列表
     */
    @GetMapping("listQuestion/{qid}")
    public Result listQuestion(@RequestHeader("Authorization") String authHeader, @PathVariable("qid") String qid) {
        return questionsService.listQuestion(authHeader, qid);
    }

    /**
     * 删除问题
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @return 问题列表
     */
    @GetMapping("deleteQuestion/{qid}")
    public Result deleteQuestion(@RequestHeader("Authorization") String authHeader, @PathVariable("qid") String qid) {
        return questionsService.deleteQuestion(authHeader, qid);
    }

    /**
     * 修改问题
     *
     * @param authHeader 认证头
     * @return 问题列表
     */
    @PostMapping("updateQuestion")
    public Result updateQuestion(@RequestHeader("Authorization") String authHeader) {
        return null;
    }
}
