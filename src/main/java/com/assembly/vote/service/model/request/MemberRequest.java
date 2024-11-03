package com.assembly.vote.service.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberRequest {

    @NotBlank(message = "{cpf_must_not_be_empty}")
    private String cpf;

    @NotBlank(message = "{name_must_not_be_empty}")
    private String name;
}
