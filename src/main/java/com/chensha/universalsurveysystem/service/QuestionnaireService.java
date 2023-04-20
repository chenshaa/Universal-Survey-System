package com.chensha.universalsurveysystem.service;

import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.QuestionnaireTimeParams;
import com.chensha.universalsurveysystem.vo.papams.UpdateQuestionnaireParams;
import com.chensha.universalsurveysystem.vo.papams.UploadAnswerParams;
import org.springframework.http.ResponseEntity;

public interface QuestionnaireService {
    Result listQuestionnaire(String authHeader);

    Result updateQuestionnaire(String authHeader, UpdateQuestionnaireParams addQuestionnaireParams);

    Result deleteQuestionnaire(String authHeader, String qid);

    Result getQuestionnaireDetail(String authHeader, String qid);

    Result uploadAnswer(String authHeader, UploadAnswerParams uploadAnswerParams);

    Result setQuestionnaireTime(String authHeader, QuestionnaireTimeParams timeParams);

    Result getQuestionnaireReport(String authHeader, String qid);

    ResponseEntity<byte[]> downloadQuestionnaire(String authHeader, String qid);

    Result setQuestionnaireAnonymous(String authHeader, String qid, Integer anonymous);

    Result getStatisticsData(String authHeader);
}
