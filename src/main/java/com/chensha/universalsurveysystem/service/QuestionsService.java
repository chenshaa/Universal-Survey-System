package com.chensha.universalsurveysystem.service;

import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.AddQuestionParams;

public interface QuestionsService {
    Result addQuestion(String authHeader, AddQuestionParams addQuestionParams);
}
