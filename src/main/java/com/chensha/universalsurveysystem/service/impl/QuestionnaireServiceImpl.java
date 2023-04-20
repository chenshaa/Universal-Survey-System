package com.chensha.universalsurveysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chensha.universalsurveysystem.dao.mapper.*;
import com.chensha.universalsurveysystem.dao.pojo.*;
import com.chensha.universalsurveysystem.service.sysUserService;
import com.chensha.universalsurveysystem.service.QuestionnaireService;
import com.chensha.universalsurveysystem.utils.ExcelUtils;
import com.chensha.universalsurveysystem.vo.*;
import com.chensha.universalsurveysystem.vo.papams.QuestionnaireTimeParams;
import com.chensha.universalsurveysystem.vo.papams.UpdateQuestionnaireParams;
import com.chensha.universalsurveysystem.vo.papams.UploadAnswerParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private final sysUserService sysUserService;
    @Autowired
    private final QuestionnaireMapper questionnaireMapper;
    @Autowired
    private final PaperMapper paperMapper;

    @Autowired
    private final OptionMapper optionMapper;

    @Autowired
    private final QuestionMapper questionMapper;

    @Autowired
    private final AnswerMapper answerMapper;

    public QuestionnaireServiceImpl(com.chensha.universalsurveysystem.service.sysUserService sysUserService, com.chensha.universalsurveysystem.dao.mapper.QuestionnaireMapper questionnaireMapper, PaperMapper paperMapper, OptionMapper optionMapper, QuestionMapper questionMapper, AnswerMapper answerMapper) {
        this.sysUserService = sysUserService;
        this.questionnaireMapper = questionnaireMapper;
        this.paperMapper = paperMapper;
        this.optionMapper = optionMapper;
        this.questionMapper = questionMapper;
        this.answerMapper = answerMapper;
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
            List<Questionnaire> questionnaireList = questionnaireMapper.selectList(null);
            return Result.success(copyList(questionnaireList));

        } else {
            //当前非管理员账号，返回属于自己的问卷
            String account = sysUserService.getAccount(authHeader);
            LambdaQueryWrapper<Paper> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Paper::getRespondentId, account);
            List<Paper> paperList = paperMapper.selectList(queryWrapper);
            List<Questionnaire> questionnaireList = new ArrayList<>();
            for (Paper paper : paperList) {
                Questionnaire questionnaire = questionnaireMapper.selectById(paper.getQuestionnaireId());
                questionnaireList.add(questionnaire);
            }
            return Result.success(copyList(questionnaireList));
        }
    }

    /**
     * 更新问卷
     *
     * @param authHeader                认证头
     * @param updateQuestionnaireParams 问卷参数
     * @return Result 成功或失败
     */
    @Override
    public Result updateQuestionnaire(String authHeader, UpdateQuestionnaireParams updateQuestionnaireParams) {
        //判空
        if (updateQuestionnaireParams.getTitle().equals("")) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        String questionnaireId;
        //判断是否为管理员账号
        if (sysUserService.authToken4Admin(authHeader)) {
            if (updateQuestionnaireParams.getId() == null || updateQuestionnaireParams.getId().equals("")) {
                //新增问卷
                Questionnaire questionnaire = new Questionnaire();
                updateQuestionnaireParams.setCreatorId(sysUserService.getUserIdByAccount(sysUserService.getAccount(authHeader)));
                BeanUtils.copyProperties(updateQuestionnaireParams, questionnaire);
                questionnaire.setAnonymousEnabled(false);
                questionnaireMapper.insert(questionnaire);
                questionnaireId = questionnaire.getId();
            } else {
                questionnaireId = updateQuestionnaireParams.getId();
                //更新问卷
                Questionnaire questionnaire = questionnaireMapper.selectById(updateQuestionnaireParams.getId());
                BeanUtils.copyProperties(updateQuestionnaireParams, questionnaire);
                questionnaireMapper.updateById(questionnaire);

                //删除问卷内的所有题目
                LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Question::getQuestionnaireId, updateQuestionnaireParams.getId());
                List<Question> questionList = questionMapper.selectList(queryWrapper);
                //删除选项后删除题目
                for (Question paper : questionList) {
                    LambdaQueryWrapper<Option> optionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    optionLambdaQueryWrapper.eq(Option::getQuestionId, paper.getId());
                    optionMapper.delete(optionLambdaQueryWrapper);
                }
                questionMapper.delete(queryWrapper);
            }

            //QuestionList文本转换成数组
            try {
                JSONArray jsonArray = new JSONArray(updateQuestionnaireParams.getQuestionList());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // 保存问题
                    Question question = new Question();
                    question.setContent(jsonObject.getString("content"));
                    question.setQuestionnaireId(questionnaireId);
                    question.setQuestionType(jsonObject.getInt("questionType"));
                    question.setAnswer(jsonObject.optString("userChoise"));
                    question.setIsRequired(jsonObject.optBoolean("isRequired", false));
                    question.setCreatedAt(new Date());
                    question.setUpdatedAt(new Date());
                    questionMapper.insert(question);

                    // 保存问题选项
                    if (jsonObject.has("options")) {
                        JSONArray optionsArray = jsonObject.getJSONArray("options");
                        for (int j = 0; j < optionsArray.length(); j++) {
                            Option option = new Option();
                            option.setQuestionId(question.getId());
                            option.setContent(optionsArray.getString(j));
                            optionMapper.insert(option);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return Result.success(questionnaireId);
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
            questionnaireMapper.deleteById(qid);
            return Result.success(listQuestionnaire(authHeader));
        } else {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }
    }

    /**
     * 获取问卷详情
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @return QuestionnaireVo
     */
    @Override
    public Result getQuestionnaireDetail(String authHeader, String qid) {
        //判空
        if (qid.equals("")) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //获取问卷信息
        Questionnaire questionnaire = questionnaireMapper.selectById(qid);
        if (questionnaire == null) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }
        QuestionnaireDetailVo questionnaireDetailVo = new QuestionnaireDetailVo();
        BeanUtils.copyProperties(questionnaire, questionnaireDetailVo);

        Boolean isAdmin = true;
        //判断为非管理员账号
        if (authHeader==null || !sysUserService.authToken4Admin(authHeader)) {
            isAdmin = false;
            //判断是否为匿名问卷
            if (questionnaire.getAnonymousEnabled()) {
                //匿名
                //判断用户是否为问卷的回答者
                if (_findUserInPaper(authHeader, questionnaire))
                    return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());

            }
        }

        //2.问卷是否已经结束
        if (questionnaire.getEndTime() != null && questionnaire.getEndTime().before(new Date())) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }
        //3.问卷是否已经开始
        if (questionnaire.getStartTime() == null || questionnaire.getStartTime().after(new Date())) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //获取问卷题目
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getQuestionnaireId, qid);
        List<Question> questionList = questionMapper.selectList(queryWrapper);
        List<QuestionVo> questionVoList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionVo questionVo = new QuestionVo();
            BeanUtils.copyProperties(question, questionVo);
            if (isAdmin) {
                questionVo.setUserChoise(question.getAnswer());
            }

            //获取题目选项
            LambdaQueryWrapper<Option> optionLambdaQueryWrapper = new LambdaQueryWrapper<>();
            optionLambdaQueryWrapper.eq(Option::getQuestionId, question.getId());
            List<Option> optionList = optionMapper.selectList(optionLambdaQueryWrapper);
            List<String> optionStringList = new ArrayList<>();
            for (Option option : optionList) {
                optionStringList.add(option.getContent());
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                String json = mapper.writeValueAsString(optionStringList);
                questionVo.setOptions(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            questionVoList.add(questionVo);
        }

        //将QuestionVoList转换为json
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(questionVoList);
            questionnaireDetailVo.setQuestionList(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Result.success(questionnaireDetailVo);
    }


    /**
     * 判断用户是否为问卷的回答者
     *
     * @param authHeader 认证头
     * @return boolean 是否为问卷的回答者
     */
    private boolean _findUserInPaper(String authHeader, Questionnaire questionnaire) {
        LambdaQueryWrapper<Paper> paperQueryWrapper = new LambdaQueryWrapper<>();
        paperQueryWrapper.eq(Paper::getQuestionnaireId, questionnaire.getId());
        paperQueryWrapper.eq(Paper::getRespondentId, sysUserService.getUserIdByAccount(sysUserService.getAccount(authHeader)));
        Paper paper=paperMapper.selectOne(paperQueryWrapper);

        return paper != null;
    }

    /**
     * 上传问卷答案
     *
     * @param authHeader         认证头
     * @param uploadAnswerParams 上传答案参数
     * @return 成功或失败
     */
    @Override
    public Result uploadAnswer(String authHeader, UploadAnswerParams uploadAnswerParams) {
        //判空
        if (uploadAnswerParams.getQuestionnaireId().equals("")) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //获取问卷信息
        Questionnaire questionnaire = questionnaireMapper.selectById(uploadAnswerParams.getQuestionnaireId());
        if (questionnaire == null) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //判断是否为匿名问卷
        if (questionnaire.getAnonymousEnabled()) {
            //匿名
            if(authHeader==null)
                return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
            //判断用户是否为问卷的回答者
            if (_findUserInPaper(authHeader, questionnaire))
                return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }

        //2.问卷是否已经结束
        if (questionnaire.getEndTime() != null && questionnaire.getEndTime().before(new Date())) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }
        //3.问卷是否已经开始
        if (questionnaire.getStartTime() == null || questionnaire.getStartTime().after(new Date())) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //pass

        //获取用户id
        String userId = "";
        if(authHeader!=null){
            userId = sysUserService.getUserIdByAccount(sysUserService.getAccount(authHeader));
        }

        try {
            System.out.println(uploadAnswerParams.getOptionContext());
            //String userInput = URLDecoder.decode(answerParams, "UTF-8");
            JSONArray jsonArray = new JSONArray(uploadAnswerParams.getOptionContext());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // 保存问题
                if (jsonObject.has("userChoise")) {
                    Answer answer = new Answer();
                    answer.setQuestionId(jsonObject.getString("id"));
                    Object userChoise = jsonObject.get("userChoise");
                    if (userChoise instanceof String) {
                        answer.setOptionContext((String) userChoise);
                    } else {
                        answer.setOptionContext(String.valueOf(userChoise));
                    }
                    System.out.println(answer.getOptionContext());
                    answer.setUserId(userId);
                    answerMapper.insert(answer);
                }

            }
            return Result.success("上传成功");
        } catch (JSONException e) {
            e.printStackTrace();
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }
    }

    /**
     * 设置问卷时间
     *
     * @param authHeader 认证头
     * @param timeParams 问卷时间参数
     * @return
     */
    @Override
    public Result setQuestionnaireTime(String authHeader, QuestionnaireTimeParams timeParams) {
        //判空
        if (timeParams.getQuestionnaireId().equals("")) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }
        if (!sysUserService.authToken4Admin(authHeader)) {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }

        //获取问卷信息
        Questionnaire questionnaire = questionnaireMapper.selectById(timeParams.getQuestionnaireId());
        if (questionnaire == null) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }
        if (timeParams.getStartTime() != null) {
            questionnaire.setStartTime(timeParams.getStartTime());
        }
        if (timeParams.getEndTime() != null) {
            questionnaire.setEndTime(timeParams.getEndTime());
        }
        questionnaireMapper.updateById(questionnaire);
        return Result.success("成功");
    }

    /**
     * 获取问卷报告
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @return
     */
    @Override
    public Result getQuestionnaireReport(String authHeader, String qid) {
        //判空
        if (qid.equals("")) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //获取问卷信息
        Questionnaire questionnaire = questionnaireMapper.selectById(qid);
        if (questionnaire == null) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //查询所有问题
        LambdaQueryWrapper<Question> questionQueryWrapper = new LambdaQueryWrapper<>();
        questionQueryWrapper.eq(Question::getQuestionnaireId, qid);
        List<Question> questionList = questionMapper.selectList(questionQueryWrapper);

        List<ReportVo> reportVoList = new ArrayList<>();
        //查询所有答案
        for (Question question : questionList) {
            //获取所有option并建立ReportOptionVo列表
            LambdaQueryWrapper<Option> optionQueryWrapper = new LambdaQueryWrapper();
            optionQueryWrapper.eq(Option::getQuestionId, question.getId());
            List<Option> optionList = optionMapper.selectList(optionQueryWrapper);
            List<ReportOptionVo> reportOptionVoList = new ArrayList<>();
            for (Option option : optionList) {
                ReportOptionVo reportOptionVo = new ReportOptionVo();
                reportOptionVo.setOptionId(option.getId());
                reportOptionVo.setOptionContext(option.getContent());
                reportOptionVo.setOptionCount(0);
                reportOptionVoList.add(reportOptionVo);
            }

            //获取所有答案
            LambdaQueryWrapper<Answer> answerQueryWrapper = new LambdaQueryWrapper();
            answerQueryWrapper.eq(Answer::getQuestionId, question.getId());
            List<Answer> answerList = answerMapper.selectList(answerQueryWrapper);

            //统计answerList中的答案各种选择的数量，并将结果转换成string存储
            if (question.getQuestionType() == 2) {
                //多选,形如[0,1,2,3]
                for (Answer answer : answerList) {
                    //处理方括号
                    String optionContext = answer.getOptionContext().substring(1, answer.getOptionContext().length() - 1);
                    String[] optionIndex = optionContext.split(",");
                    for (String index : optionIndex) {
                        reportOptionVoList.get(Integer.parseInt(index)).setOptionCount(reportOptionVoList.get(Integer.parseInt(index)).getOptionCount() + 1);
                    }
                }

            } else if(question.getQuestionType() == 1 || question.getQuestionType() == 2 || question.getQuestionType() == 4) {
                //单选,形如0
                for (Answer answer : answerList) {
                    reportOptionVoList.get(Integer.parseInt(answer.getOptionContext())).setOptionCount(reportOptionVoList.get(Integer.parseInt(answer.getOptionContext())).getOptionCount() + 1);
                }
            }else{
                //文本，暂不统计
            }

            //将结果转换成json
            JSONArray jsonArray = new JSONArray();
            for (ReportOptionVo reportOptionVo : reportOptionVoList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("optionId", reportOptionVo.getOptionId());
                jsonObject.put("optionContext", reportOptionVo.getOptionContext());
                jsonObject.put("optionCount", reportOptionVo.getOptionCount());
                jsonArray.put(jsonObject);
            }
            ReportVo reportVo = new ReportVo();
            reportVo.setQuestionId(question.getId());
            reportVo.setQuestionContext(question.getContent());
            reportVo.setQuestionType(question.getQuestionType());
            reportVo.setReport(jsonArray.toString());
            reportVoList.add(reportVo);

        }

        return Result.success(reportVoList);
    }

    /**
     * 下载问卷
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @return
     */
    @Override
    public ResponseEntity<byte[]> downloadQuestionnaire(String authHeader, String qid) {
        //判空
        if (qid.equals("")) {
            return null;
        }

        //获取问卷题目List
        LambdaQueryWrapper<Question> questionQueryWrapper = new LambdaQueryWrapper();
        questionQueryWrapper.eq(Question::getQuestionnaireId, qid);
        List<Question> questionList = questionMapper.selectList(questionQueryWrapper);
        //转换成string list和questionId list
        List<String> questionStringList = new ArrayList<>();
        List<String> questionIdList = new ArrayList<>();
        for (Question question : questionList) {
            questionStringList.add(question.getContent());
            questionIdList.add(question.getId());
        }

        //汇总创建时间
        List<Date> dateList = new ArrayList<>();
        for (Question question : questionList) {
            LambdaQueryWrapper<Answer> answerQueryWrapper = new LambdaQueryWrapper();
            answerQueryWrapper.eq(Answer::getQuestionId, question.getId());
            List<Answer> answerList = answerMapper.selectList(answerQueryWrapper);
            for (Answer answer : answerList) {
                if (!dateList.contains(answer.getCreatedAt())) {
                    dateList.add(answer.getCreatedAt());
                }
            }
        }

        List<ExcelVo> excelVoList = new ArrayList<>();
        int index = 1;
        for (Date date : dateList) {
            ExcelVo excelVo = new ExcelVo();

            List<String> answerStringList = new ArrayList<>();
            for (String questionId : questionIdList) {
                LambdaQueryWrapper<Answer> answerQueryWrapper = new LambdaQueryWrapper();
                answerQueryWrapper.eq(Answer::getQuestionId, questionId);
                answerQueryWrapper.eq(Answer::getCreatedAt, date);
                Answer answer = answerMapper.selectOne(answerQueryWrapper);
                if (answer == null) {
                    answerStringList.add("");
                } else {
                    answerStringList.add(answer.getOptionContext());
                    if (answer.getUserId() != null) {
                        excelVo.setUserId(answer.getUserId());
                    }
                }
            }

            if (excelVo.getUserId() == null || excelVo.getUserId().equals("")) {
                excelVo.setUserName("匿名用户");
            } else {
                excelVo.setUserName(sysUserService.getUserNameById(excelVo.getUserId()));
            }
            excelVo.setIndex(String.valueOf(index++));
            excelVo.setUpdateTime(dateToString(date));
            excelVo.setQuestions(questionStringList);
            excelVo.setAnswers(answerStringList);
            excelVoList.add(excelVo);
        }


        try {
            //将excelVoList转换为excel
            byte[] excelBytes = ExcelUtils.exportExcel(excelVoList);
            //设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", new String("问卷结果.xlsx".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 设置问卷是否匿名
     *
     * @param authHeader 认证头
     * @param qid        问卷id
     * @param anonymous  是否匿名
     * @return
     */
    @Override
    public Result setQuestionnaireAnonymous(String authHeader, String qid, Integer anonymous) {
        //判空
        if (qid.equals("") || anonymous == null) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //判断是否有权限
        if (!sysUserService.authToken4Admin(authHeader)) {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }

        //判断是否存在
        LambdaQueryWrapper<Questionnaire> questionnaireQueryWrapper = new LambdaQueryWrapper();
        questionnaireQueryWrapper.eq(Questionnaire::getId, qid);
        Questionnaire questionnaire = questionnaireMapper.selectOne(questionnaireQueryWrapper);
        if (questionnaire == null) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //设置是否匿名
        questionnaire.setAnonymousEnabled(anonymous == 1 ? true : false);
        questionnaireMapper.updateById(questionnaire);

        return Result.success("成功");
    }

    @Override
    public Result getStatisticsData(String authHeader) {
        //判断是否有权限
        if (!sysUserService.authToken4Admin(authHeader)) {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }

        StatisticsDataVo statisticsDataVo = new StatisticsDataVo();

        //获取问卷数量
        statisticsDataVo.setQuestionnaireNum(String.valueOf(questionnaireMapper.selectCount(null)));

        //获取问题数量
        statisticsDataVo.setQuestionNum(String.valueOf(questionMapper.selectCount(null)));

        //获取答案数量
        statisticsDataVo.setAnswerNum(String.valueOf(answerMapper.selectCount(null)));

        return Result.success(statisticsDataVo);
    }

    /**
     * 将Date转换为String
     *
     * @param date Date
     * @return String
     */
    private String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
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
        System.out.println(questionnaireVoList);
        return questionnaireVoList;
    }


}
