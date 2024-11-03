package com.assembly.vote.service.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.assembly.vote.service.converter.MemberConverter;
import com.assembly.vote.service.model.request.MemberRequest;
import com.assembly.vote.service.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    private final MemberConverter memberConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    public void test(@RequestBody MemberRequest memberRequest) {
        var memberToSave = memberConverter.requestToEntity(memberRequest);

        var savedMember = memberService.save(memberToSave);
    }
}
