package com.chensha.universalsurveysystem.service;

import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.AddQuestionnaireParams;

public interface QuestionnaireService {
    Result listQuestionnaire(String authHeader);

    Result addQuestionnaire(String authHeader, AddQuestionnaireParams addQuestionnaireParams);

    Result deleteQuestionnaire(String authHeader, String qid);
}
