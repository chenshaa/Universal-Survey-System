package com.chensha.universalsurveysystem.service;

import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.PaperParams;

public interface PaperService {

    Result listPaper(String authHeader, String questionnaireId);

    Result updatePaper(String authHeader, String updatePaperParams, String questionnaireId);

}
