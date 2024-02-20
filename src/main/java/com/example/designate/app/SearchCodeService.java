package com.example.designate.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangjiameng
 * @date 2023/5/9 15:40
 */
@Slf4j
@Service
public class SearchCodeService {
    private final static String SEARCH_URL = "https://searchcode.com/api/codesearch_I";

    @Autowired
    private OkHttpClient httpClient;

    @Autowired
    private TranslateService translateService;

    public String search(String q) throws IOException {
        String code = translateService.translate(q);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("q", code);
        paramMap.put("p", 1);
        paramMap.put("per_page", 50);
        HttpUrl.Builder urlBuilder = HttpUrl.get(SEARCH_URL).newBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .tag(paramMap)
                .build();
        Call call = httpClient.newCall(request);
        String result = Objects.requireNonNull(call.execute().body()).string();
        JSONArray results = JSON.parseObject(result).getJSONArray("results");
        Set<String> wordSet = new HashSet<>();
        wordSet.add(code);
//        Pattern pattern = Pattern.compile("([\\-\\w\\$]{0,})" + code + "([\\-\\w\\$]{0,})");
        Pattern pattern = Pattern.compile("([\\-_\\w\\d\\/\\$]{0,}){0,1}" + code + "([\\-_\\w\\d\\$]{0,}){0,1}");
        for (Object o : results) {
            JSONObject jsonObject = (JSONObject) o;
            String lines = jsonObject.getString("lines");
            Matcher mat = pattern.matcher(lines);
            while (mat.find()) {
                wordSet.add(mat.group(0));
            }
        }
        log.info(JSON.toJSONString(wordSet));
        return JSON.toJSONString(wordSet);
    }
}
