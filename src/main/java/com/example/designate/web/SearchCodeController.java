package com.example.designate.web;

import com.example.designate.app.SearchCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiameng
 * @date 2023/5/8 16:40
 */
@RestController
public class SearchCodeController {
    @Autowired
    private SearchCodeService service;

    @GetMapping("/search")
    public String search(String q) {
        try {
            return service.search(q);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
