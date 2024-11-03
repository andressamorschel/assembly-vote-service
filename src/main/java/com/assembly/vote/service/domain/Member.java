package com.assembly.vote.service.domain;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
public record Member(
        @Id
        String id,
        String name,
        String cpf,
        boolean deleted
) {}
