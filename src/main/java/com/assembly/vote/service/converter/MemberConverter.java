package com.assembly.vote.service.converter;

import com.assembly.vote.service.domain.Member;
import com.assembly.vote.service.model.request.MemberRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member requestToEntity(MemberRequest memberRequest) {
        return Member.builder()
                .cpf(memberRequest.cpf())
                .name(memberRequest.name())
                .build();
    }
}
