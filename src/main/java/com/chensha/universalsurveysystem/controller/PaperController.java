package com.chensha.universalsurveysystem.controller;

import com.chensha.universalsurveysystem.service.PaperService;
import com.chensha.universalsurveysystem.vo.papams.AddPaperParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

    /**
     * 添加一张paper
     */
    @PostMapping("addPaper")
    public void addPaper(@RequestHeader("Authorization") String authHeader, @RequestBody AddPaperParams addPaperParams) {
        paperService.addPaper(authHeader, addPaperParams);
    }

    /**
     * 删除一张paper
     */
    @PostMapping("deletePaper")
    public void deletePaper(@RequestHeader("Authorization") String authHeader, @RequestBody String pid) {
        paperService.deletePaper(authHeader, pid);
    }

    /**
     * 获取paper列表
     */
    @GetMapping("listPaper")
    public void listPaper(@RequestHeader("Authorization") String authHeader) {
        paperService.listPaper(authHeader);
    }

    /**
     * 添加paperList
     */
    @PostMapping("addPaperList")
    public void addPaperList(@RequestHeader("Authorization") String authHeader, @RequestBody String pid) {
        paperService.addPaperList(authHeader, pid);
    }

}
