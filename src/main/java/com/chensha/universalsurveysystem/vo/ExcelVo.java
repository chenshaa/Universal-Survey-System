package com.chensha.universalsurveysystem.vo;

import lombok.Data;

import java.util.List;

@Data
public class ExcelVo {
    /**
     * 序号
     */
    private String index;

    /**
     * 回答时间
     */
    private String updateTime;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 问题列表
     */
    private List<String> questions;

    /**
     * 回答列表
     */
    private List<String> answers;

}
