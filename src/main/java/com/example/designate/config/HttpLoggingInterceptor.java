package com.example.designate.config;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author wangjiameng
 * @date 2023/5/9 10:27
 */
public class HttpLoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
