package com.example.designate.app;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.designate.client.TranslateQry;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author wangjiameng
 * @date 2023/5/8 10:26
 */
@Slf4j
@Service
public class TranslateService {
    private final static String TRANS_URL = "https://fanyi-api.baidu.com/api/trans/vip/translate";
    private final static String APPID = "20230210001556463";
    private final static String APP_SECRET = "imfaqnWNbYvNAio9TUwx";
    @Autowired
    private OkHttpClient httpClient;

    public String translate(String q) throws IOException {
        TranslateQry translateQry = new TranslateQry();
        translateQry.setQ(q);
        translateQry.setAppid(APPID);
        translateQry.setSalt(System.currentTimeMillis());
        Map<String, Object> paramMap = BeanUtil.beanToMap(translateQry, false, true);
        paramMap.put("sign", this.getSign(translateQry));
        HttpUrl.Builder urlBuilder = HttpUrl.get(TRANS_URL).newBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .tag(paramMap)
                .build();
        Call call = httpClient.newCall(request);
        String result = Objects.requireNonNull(call.execute().body()).string();
        result = Convert.unicodeToStr(result);
        if (result.contains("error_code")){
            throw new RuntimeException("翻译失败");
        }
        log.info(result);
        JSONArray jsonArray = JSON.parseObject(result).getJSONArray("trans_result");
        return jsonArray.getJSONObject(0).getString("dst");
    }

    private String getSign(TranslateQry translateQry) {
        String sb = translateQry.getAppid() + translateQry.getQ() + translateQry.getSalt() + APP_SECRET;
        return SecureUtil.md5(sb);
    }
}
