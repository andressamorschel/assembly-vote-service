package com.assembly.vote.service.service;

import com.assembly.vote.service.domain.Member;
import com.assembly.vote.service.exception.NotFoundException;
import com.assembly.vote.service.repository.MemberRepository;
import com.assembly.vote.service.service.http.CpfValidatorApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final CpfValidatorApi cpfValidatorApi;

    @Value("${cpf.validator.api.token}")
    private String cpfValidatorApiToken;

    public boolean cannotVote(String memberId) {
        var member = findMemberById(memberId);
        return !cpfValidatorApi.validateCpf(member.cpf(), cpfValidatorApiToken).valid();
    }

    private Member findMemberById(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("not_found_member", memberId));
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> listMembers() {
        return memberRepository.findAll();
    }

    public void deleteMember(String memberId) {
        memberRepository.deleteById(memberId);
    }
}
