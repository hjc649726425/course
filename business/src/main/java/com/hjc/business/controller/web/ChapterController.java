package com.hjc.business.controller.web;

import com.hjc.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/web/chapter")
public class ChapterController {

    @Resource
    private ChapterService chapterService;
}
