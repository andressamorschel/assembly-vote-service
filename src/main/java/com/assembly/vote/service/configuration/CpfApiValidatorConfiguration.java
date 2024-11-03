package com.assembly.vote.service.configuration;

import static feign.Logger.Level.BASIC;

import com.assembly.vote.service.service.http.CpfValidatorApi;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CpfApiValidatorConfiguration {

    @Value("${cpf.validator.api.url}")
    private String cpfValidatorApiUrl;

    @Bean
    public CpfValidatorApi cpfValidatorApi() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(CpfValidatorApi.class))
                .logLevel(BASIC)
                .target(CpfValidatorApi.class, cpfValidatorApiUrl);
    }
}
