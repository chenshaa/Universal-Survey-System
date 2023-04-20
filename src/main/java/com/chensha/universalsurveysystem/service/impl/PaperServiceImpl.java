package com.chensha.universalsurveysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chensha.universalsurveysystem.dao.mapper.PaperMapper;
import com.chensha.universalsurveysystem.dao.pojo.Paper;
import com.chensha.universalsurveysystem.service.PaperService;
import com.chensha.universalsurveysystem.service.sysUserService;
import com.chensha.universalsurveysystem.vo.ErrorCode;
import com.chensha.universalsurveysystem.vo.Result;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private sysUserService sysUserService;


    /**
     * 根据问卷id查询paper
     *
     * @param questionnaireId 问卷id
     * @return paperList
     */
    @Override
    public Result listPaper(String authHeader, String questionnaireId) {
        //判断是否为管理员
        if (sysUserService.authToken4Admin(authHeader)) {
            return Result.success(_listPaperByQuestionnaireId(questionnaireId));
        }
        return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
    }

    /**
     * 更新问卷的paper
     *
     * @param authHeader        认证头
     * @param updatePaperParams userId List
     * @param questionnaireId   问卷id
     * @return paper列表
     */
    @Override
    public Result updatePaper(String authHeader, String updatePaperParams, String questionnaireId) {
        //判断是否为管理员
        if (sysUserService.authToken4Admin(authHeader)) {
            //删除原有的paper
            LambdaQueryWrapper<Paper> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Paper::getQuestionnaireId, questionnaireId);
            paperMapper.delete(queryWrapper);
            //添加新的paper
            JSONArray jsonArray = new JSONArray(updatePaperParams);
            for (int i = 0; i < jsonArray.length(); i++) {
                Paper paper = new Paper();
                paper.setQuestionnaireId(questionnaireId);
                paper.setRespondentId(jsonArray.getString(i));
                paper.setCreatedAt(new Date());
                paperMapper.insert(paper);
            }
            return Result.success(_listPaperByQuestionnaireId(questionnaireId));
        }
        return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
    }

    /**
     * 根据问卷id查询paper
     *
     * @param questionnaireId 问卷id
     * @return paperList
     */
    public List<Paper> _listPaperByQuestionnaireId(String questionnaireId) {
        LambdaQueryWrapper<Paper> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Paper::getQuestionnaireId, questionnaireId);
        return paperMapper.selectList(queryWrapper);
    }

}
