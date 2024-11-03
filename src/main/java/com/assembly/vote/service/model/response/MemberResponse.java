package com.assembly.vote.service.model.response;

import lombok.Builder;

@Builder
public record MemberResponse(
        String id,
        String name,
        String cpf
) {}
