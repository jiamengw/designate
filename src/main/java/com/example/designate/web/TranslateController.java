package com.example.designate.web;

import com.example.designate.app.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiameng
 * @date 2023/5/8 16:40
 */
@RestController
public class TranslateController {
    @Autowired
    private TranslateService service;

    @GetMapping("/translate")
    public String translate(String q) {
        try {
            return service.translate(q);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
