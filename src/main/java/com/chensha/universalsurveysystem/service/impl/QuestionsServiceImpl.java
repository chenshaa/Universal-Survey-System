package com.chensha.universalsurveysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chensha.universalsurveysystem.dao.mapper.OptionMapper;
import com.chensha.universalsurveysystem.dao.mapper.PaperMapper;
import com.chensha.universalsurveysystem.dao.mapper.QuestionMapper;
import com.chensha.universalsurveysystem.dao.mapper.QuestionnaireMapper;
import com.chensha.universalsurveysystem.dao.pojo.Option;
import com.chensha.universalsurveysystem.dao.pojo.Paper;
import com.chensha.universalsurveysystem.dao.pojo.Question;
import com.chensha.universalsurveysystem.dao.pojo.Questionnaire;
import com.chensha.universalsurveysystem.service.QuestionsService;
import com.chensha.universalsurveysystem.service.sysUserService;
import com.chensha.universalsurveysystem.utils.Base64Utils;
import com.chensha.universalsurveysystem.vo.ErrorCode;
import com.chensha.universalsurveysystem.vo.QuestionVo;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.AddQuestionParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private sysUserService sysUserService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private OptionMapper optionMapper;

    @Autowired
    private PaperMapper paperMapper;

    /**
     * 添加问题
     *
     * @param authHeader        token
     * @param addQuestionParams 问题参数
     * @return
     */
    @Override
    public Result addQuestion(String authHeader, AddQuestionParams addQuestionParams) {
        //判空
        if (addQuestionParams.getContent().equals("") || addQuestionParams.getQuestionnaireId().equals("")) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //判断是否为管理员
        if (!sysUserService.authToken4Admin(authHeader)) {
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

        //删除已有选项
        LambdaQueryWrapper<Option> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Option::getQuestionId, question.getId());
        optionMapper.delete(queryWrapper);

        //添加选项
        List<String> optionsList = _string2List(addQuestionParams.getOptions());
        for (String option : optionsList) {
            Option option1 = new Option();
            option1.setContent(option);
            option1.setCreatedAt(new Date());
            option1.setUpdatedAt(new Date());
            optionMapper.insert(option1);
        }

        return Result.success("成功");
    }

    /**
     * string转list
     *
     * @param str 字符串
     * @return list
     */
    private List<String> _string2List(String str) {
        String temp = str.substring(1, str.length() - 1); // remove brackets
        List<String> list = Arrays.asList(temp.split(", "));
        return list;
    }

    /**
     * 获取问题列表
     *
     * @param authHeader token
     * @param qid        问卷id
     * @return 问题列表
     */
    @Override
    public Result listQuestion(String authHeader, String qid) {
        //判空
        if (qid.equals("")) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //是否匿名问卷
        LambdaQueryWrapper<Questionnaire> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Questionnaire::getId, qid);
        queryWrapper.eq(Questionnaire::getAnonymousEnabled, 1);
        queryWrapper.last("limit 1");
        Questionnaire questionnaire = questionnaireMapper.selectOne(queryWrapper);
        boolean isAnonymous = questionnaire != null;

        //普通用户仅可获取被分配的问卷和匿名问卷
        if (!isAnonymous && !sysUserService.authToken4Admin(authHeader)) {
            //普通用户
            //是否被分配问卷
            String account = sysUserService.getAccount(authHeader);
            String userid = sysUserService.getUserIdByAccount(account);

            LambdaQueryWrapper<Paper> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(Paper::getQuestionnaireId, qid);
            queryWrapper2.eq(Paper::getRespondentId, userid);
            queryWrapper2.last("limit 1");
            Paper paper = paperMapper.selectOne(queryWrapper2);

            if (paper == null) {
                return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
            }
        }

        //通过
        try {
            return _getQuestion(qid);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除问题
     *
     * @param authHeader token
     * @param qid        问题id
     * @return
     */
    @Override
    public Result deleteQuestion(String authHeader, String qid) {
        //判空
        if (qid.equals("")) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //判断是否为管理员
        if (!sysUserService.authToken4Admin(authHeader)) {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }

        //删除问题
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getQuestionnaireId, qid);
        questionMapper.delete(queryWrapper);

        return Result.success("成功");
    }

    /**
     * 获取问题列表
     *
     * @param qid 问卷id
     * @return 问题列表
     * @throws JsonProcessingException json转换异常
     */
    public Result _getQuestion(String qid) throws JsonProcessingException {
        //获取所有问题
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getQuestionnaireId, qid);
        List<Question> questions = questionMapper.selectList(queryWrapper);

        List<QuestionVo> questionVos = _copyList(questions);

        //获取问题选项
        for (QuestionVo questionVo : questionVos) {
            LambdaQueryWrapper<Option> optionQueryWrapper = new LambdaQueryWrapper<>();
            optionQueryWrapper.eq(Option::getQuestionId, questionVo.getId());
            List<Option> questionOptions = optionMapper.selectList(optionQueryWrapper);

            List<String> options = new ArrayList<>();
            for (Option questionOption : questionOptions) {
                options.add(Base64Utils.encode(questionOption.getContent()));
            }

            questionVo.setOptions(options.toString());
        }

        return Result.success(questionVos);
    }

    /**
     * 复制问题列表
     *
     * @param questions 问题列表
     * @return 问题列表
     */
    public List<QuestionVo> _copyList(List<Question> questions) {
        List<QuestionVo> questionVos = new ArrayList<>();
        for (Question question : questions) {
            QuestionVo questionVo = new QuestionVo();
            questionVo.setId(question.getId());
            questionVo.setContent(question.getContent());
            questionVo.setQuestionnaireId(question.getQuestionnaireId());
            questionVos.add(questionVo);
        }
        return questionVos;
    }
}
