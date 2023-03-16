package com.chensha.universalsurveysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chensha.universalsurveysystem.dao.mapper.PaperMapper;
import com.chensha.universalsurveysystem.dao.mapper.QuestionnaireMapper;
import com.chensha.universalsurveysystem.dao.pojo.Paper;
import com.chensha.universalsurveysystem.dao.pojo.Questionnaire;
import com.chensha.universalsurveysystem.service.sysUserService;
import com.chensha.universalsurveysystem.service.QuestionnaireService;
import com.chensha.universalsurveysystem.vo.ErrorCode;
import com.chensha.universalsurveysystem.vo.QuestionnaireVo;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.AddQuestionnaireParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private final sysUserService sysUserService;
    @Autowired
    private final QuestionnaireMapper QuestionnaireMapper;
    @Autowired
    private final PaperMapper paperMapper;

    public QuestionnaireServiceImpl(com.chensha.universalsurveysystem.service.sysUserService sysUserService, com.chensha.universalsurveysystem.dao.mapper.QuestionnaireMapper questionnaireMapper, PaperMapper paperMapper) {
        this.sysUserService = sysUserService;
        QuestionnaireMapper = questionnaireMapper;
        this.paperMapper = paperMapper;
    }

    /**
     * 获取问卷列表
     *
     * @param authHeader 认证头
     * @return QuestionnaireVo列表
     */
    @Override
    public Result listQuestionnaire(String authHeader) {
        if (sysUserService.authToken4Admin(authHeader)) {
            //当前为管理员账号,返回全部问卷
            List<Questionnaire> questionnaireList = QuestionnaireMapper.selectList(null);
            return Result.success(copyList(questionnaireList));

        } else {
            //当前非管理员账号，返回属于自己的问卷
            String account = sysUserService.getAccount(authHeader);
            LambdaQueryWrapper<Paper> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Paper::getRespondentId, account);
            List<Paper> paperList = paperMapper.selectList(queryWrapper);
            List<Questionnaire> questionnaireList = new ArrayList<>();
            for (Paper paper : paperList) {
                Questionnaire questionnaire = QuestionnaireMapper.selectById(paper.getQuestionnaireId());
                questionnaireList.add(questionnaire);
            }
            return Result.success(copyList(questionnaireList));
        }
    }

    /**
     * 增加问卷
     *
     * @param authHeader 认证头
     * addQuestionnaireParams 问卷参数
     * @return Result 成功或失败
     */
    @Override
    public Result addQuestionnaire(String authHeader, AddQuestionnaireParams addQuestionnaireParams) {
        //判空
        if (addQuestionnaireParams.getTitle().equals("")) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //判断是否为管理员账号
        if (sysUserService.authToken4Admin(authHeader)) {
            Questionnaire questionnaire = new Questionnaire();
            BeanUtils.copyProperties(addQuestionnaireParams, questionnaire);
            QuestionnaireMapper.insert(questionnaire);
            return Result.success("成功");
        } else {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }
    }

    /**
     * 删除问卷
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @return Result 成功或失败
     */
    @Override
    public Result deleteQuestionnaire(String authHeader, String qid) {
        //判空
        if (qid.equals("")) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //判断是否为管理员账号
        if (sysUserService.authToken4Admin(authHeader)) {
            QuestionnaireMapper.deleteById(qid);
            return Result.success(listQuestionnaire(authHeader));
        } else {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }
    }

    /**
     * 将Questionnaire列表转换为QuestionnaireVo列表
     *
     * @param questionnaireList Questionnaire列表
     * @return QuestionnaireVo列表
     */
    public List<QuestionnaireVo> copyList(List<Questionnaire> questionnaireList) {
        List<QuestionnaireVo> questionnaireVoList = new ArrayList<>();
        for (Questionnaire questionnaire : questionnaireList) {
            QuestionnaireVo questionnaireVo = new QuestionnaireVo();
            BeanUtils.copyProperties(questionnaire, questionnaireVo);
            questionnaireVoList.add(questionnaireVo);
        }
        return questionnaireVoList;
    }
}
