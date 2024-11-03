package com.assembly.vote.service.service;

import com.assembly.vote.service.domain.Member;
import com.assembly.vote.service.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> listMembers() {
        return memberRepository.findAll();
    }

    public void deleteMember(String memberId) {
        memberRepository.deleteById(memberId);
    }

    public boolean memberDoesNotExist(String memberId) {
        return !memberRepository.existsById(memberId);
    }
}
