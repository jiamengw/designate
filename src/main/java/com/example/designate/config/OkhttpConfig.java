package com.example.designate.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author wangjiameng
 * @date 2023/5/9 10:42
 */
@Configuration
public class OkhttpConfig {

    private static final int connectTimeout = 10;
    private static final int readTimeout = 10;

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(connectTimeout , TimeUnit.SECONDS)
                .readTimeout(readTimeout , TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool())
                .addInterceptor(createHttpLoggingInterceptor())
                .build();
    }
    private okhttp3.logging.HttpLoggingInterceptor createHttpLoggingInterceptor() {
        okhttp3.logging.HttpLoggingInterceptor loggingInterceptor = new okhttp3.logging.HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
}
