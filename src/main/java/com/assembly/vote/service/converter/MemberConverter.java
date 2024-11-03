package com.assembly.vote.service.converter;

import com.assembly.vote.service.domain.Member;
import com.assembly.vote.service.model.request.MemberRequest;
import com.assembly.vote.service.model.response.MemberResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member buildEntity(MemberRequest memberRequest) {
        return Member.builder()
                .cpf(memberRequest.getCpf())
                .name(memberRequest.getName())
                .build();
    }

    public List<MemberResponse> buildMemberResponse(List<Member> members) {
        return members.stream()
                .map(this::buildMemberResponse)
                .toList();
    }

    public Member buildUpdatedMember(String memberId, MemberRequest memberRequest) {
        return Member.builder()
                .id(memberId)
                .cpf(memberRequest.getCpf())
                .name(memberRequest.getName())
                .build();
    }

    public MemberResponse buildMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.id())
                .cpf(member.cpf())
                .name(member.name())
                .build();
    }
}
