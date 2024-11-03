package com.assembly.vote.service.validator;

import com.assembly.vote.service.exception.NotFoundException;
import com.assembly.vote.service.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberRequestValidator {

    private final MemberService memberService;

    public void validateMemberId(String memberId) {
        if (memberService.memberDoesNotExist(memberId)) {
            throw new NotFoundException("not_found_member", memberId);
        }
    }
}
