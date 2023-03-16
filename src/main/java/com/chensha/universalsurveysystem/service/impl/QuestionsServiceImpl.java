package com.chensha.universalsurveysystem.service.impl;

import com.chensha.universalsurveysystem.dao.mapper.QuestionMapper;
import com.chensha.universalsurveysystem.dao.pojo.Question;
import com.chensha.universalsurveysystem.service.QuestionsService;
import com.chensha.universalsurveysystem.service.sysUserService;
import com.chensha.universalsurveysystem.vo.ErrorCode;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.AddQuestionParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private sysUserService sysUserService;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Result addQuestion(String authHeader, AddQuestionParams addQuestionParams) {
        //判空
        if (addQuestionParams.getContent().equals("") || addQuestionParams.getQuestionnaireId().equals("")) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //判断是否为管理员
        if(!sysUserService.authToken4Admin(authHeader)){
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }

        String account = sysUserService.getAccount(authHeader);
        String userid = sysUserService.getUserIdByAccount(account);

        //添加问题
        Question question = new Question();
        question.setQuestionnaireId(addQuestionParams.getQuestionnaireId());
        question.setContent(addQuestionParams.getContent());
        question.setCreatedAt(new Date());
        question.setUpdatedAt(new Date());
        questionMapper.insert(question);

        return Result.success("添加成功");
    }
}
