package com.chensha.universalsurveysystem.controller;

import com.chensha.universalsurveysystem.service.PaperService;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.PaperParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

    /**
     * 修改paper
     *
     * @param authHeader        认证头
     * @param updatePaperParams paper信息
     * @return paper列表
     */
    @PostMapping("updatePaper/{questionnaireId}")
    public Result updatePaper(@RequestHeader("Authorization") String authHeader, @RequestBody String updatePaperParams, @PathVariable("questionnaireId") String questionnaireId) {
        return (paperService.updatePaper(authHeader, updatePaperParams, questionnaireId));
    }


    /**
     * 获取paper列表
     *
     * @param authHeader      认证头
     * @param questionnaireId 问卷id
     * @return paper列表
     */
    @GetMapping("listPaper/{questionnaireId}")
    public Result listPaper(@RequestHeader("Authorization") String authHeader, @PathVariable("questionnaireId") String questionnaireId) {
        return (paperService.listPaper(authHeader, questionnaireId));
    }


}
