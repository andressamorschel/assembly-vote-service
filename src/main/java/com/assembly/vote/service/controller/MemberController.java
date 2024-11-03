package com.assembly.vote.service.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.assembly.vote.service.converter.MemberConverter;
import com.assembly.vote.service.domain.Member;
import com.assembly.vote.service.model.request.MemberRequest;
import com.assembly.vote.service.service.MemberService;
import com.assembly.vote.service.validator.MemberRequestValidator;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    private final MemberConverter memberConverter;

    private final MemberRequestValidator memberRequestValidator;

    @PostMapping
    @ResponseStatus(CREATED)
    public Member createMember(@RequestBody @Valid MemberRequest memberRequest) {
        var memberToSave = memberConverter.buildEntity(memberRequest);

        var savedMember = memberService.save(memberToSave);

        log.info("Successfully created member: {}", savedMember);

        return savedMember;
    }

    @GetMapping
    public List<Member> listMembers() {
        return memberService.listMembers();
    }

    @DeleteMapping("/{memberId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteMember(@PathVariable String memberId) {
        log.info("Member deletion requested for: {}", memberId);
        memberRequestValidator.validateMemberId(memberId);
        memberService.deleteMember(memberId);
    }
}
