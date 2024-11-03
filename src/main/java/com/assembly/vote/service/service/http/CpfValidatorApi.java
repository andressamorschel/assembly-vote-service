package com.assembly.vote.service.service.http;

import com.assembly.vote.service.model.response.CpfValidatorResponse;
import feign.Param;
import feign.RequestLine;

public interface CpfValidatorApi {

    @RequestLine("GET /v1/validator?value={value}&type=cpf&token={token}")
    CpfValidatorResponse validateCpf(@Param("value") String value, @Param("token") String token);
}
