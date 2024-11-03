package com.assembly.vote.service.converter;

import com.assembly.vote.service.domain.Member;
import com.assembly.vote.service.model.request.MemberRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member buildEntity(MemberRequest memberRequest) {
        return Member.builder()
                .cpf(memberRequest.getCpf())
                .name(memberRequest.getName())
                .build();
    }
}
