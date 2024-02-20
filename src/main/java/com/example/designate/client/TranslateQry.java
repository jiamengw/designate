package com.example.designate.client;

import lombok.Data;

/**
 * @author wangjiameng
 * @date 2023/5/8 14:35
 */
@Data
public class TranslateQry {
    private String q;
    private String from = "auto";
    private String to = "en";
    private String appid;
    private Long salt;
}
