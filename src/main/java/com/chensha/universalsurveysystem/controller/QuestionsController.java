package com.chensha.universalsurveysystem.controller;

import com.chensha.universalsurveysystem.service.QuestionsService;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.AddQuestionParams;
import com.chensha.universalsurveysystem.vo.papams.UpdateUserParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/question")
public class QuestionsController {

    @Autowired
    private QuestionsService questionsService;

    /**
     * 添加问题
     */
    @PostMapping("addQuestion")
    public Result addQuestion(@RequestHeader("Authorization") String authHeader, @RequestBody AddQuestionParams addQuestionParams) {
        return questionsService.addQuestion(authHeader, addQuestionParams);
    }


}
