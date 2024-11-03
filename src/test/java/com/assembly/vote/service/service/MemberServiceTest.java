package com.assembly.vote.service.service;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.assembly.vote.service.domain.Member;
import com.assembly.vote.service.exception.NotFoundException;
import com.assembly.vote.service.model.response.CpfValidatorResponse;
import com.assembly.vote.service.repository.MemberRepository;
import com.assembly.vote.service.service.http.CpfValidatorApi;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CpfValidatorApi cpfValidatorApi;

    @InjectMocks
    private MemberService memberService;

    @Test
    void shouldCannotVoteSuuccessfullyWhenApiReturnsTrue() {
        var apiToken = "token";
        setField(memberService, "cpfValidatorApiToken", apiToken);
        var memberId = "member-id";
        var memberCpf = "member-cpf";
        var member = mock(Member.class);
        var cpfValidatorResponse = mock(CpfValidatorResponse.class);
        given(member.cpf()).willReturn(memberCpf);
        given(cpfValidatorResponse.valid()).willReturn(true);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
        given(cpfValidatorApi.validateCpf(memberCpf, apiToken)).willReturn(cpfValidatorResponse);

        var result = memberService.cannotVote(memberId);

        assertThat(result).isFalse();
        verify(memberRepository).findById(memberId);
        verify(cpfValidatorApi).validateCpf(memberCpf, apiToken);
    }


    @Test
    void shouldCannotVoteSuuccessfullyWhenApiReturnsFalse() {
        var apiToken = "token";
        setField(memberService, "cpfValidatorApiToken", apiToken);
        var memberId = "member-id";
        var memberCpf = "member-cpf";
        var member = mock(Member.class);
        var cpfValidatorResponse = mock(CpfValidatorResponse.class);
        given(member.cpf()).willReturn(memberCpf);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
        given(cpfValidatorApi.validateCpf(memberCpf, apiToken)).willReturn(cpfValidatorResponse);

        var result = memberService.cannotVote(memberId);

        assertThat(result).isTrue();
        verify(memberRepository).findById(memberId);
        verify(cpfValidatorApi).validateCpf(memberCpf, apiToken);
    }

    @Test
    void shouldCannotVoteThrowsNotFoundExceptionWhenMemberDoesNotExists() {
        var memberId = "member-id";
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        assertThatCode(() -> memberService.cannotVote(memberId))
                .isInstanceOf(NotFoundException.class);

        verify(memberRepository).findById(memberId);
        verifyNoInteractions(cpfValidatorApi);
    }

    @Test
    void shouldSaveSuccessfully() {
        var member = mock(Member.class);
        given(memberRepository.save(member)).willReturn(member);

        var result = memberService.save(member);

        assertThat(result).isEqualTo(member);
        verify(memberRepository).save(member);
    }

    @Test
    void shouldListMembersSuccessfully() {
        var member = mock(Member.class);
        given(memberRepository.findAll()).willReturn(singletonList(member));

        var result = memberService.listMembers();

        assertThat(result).isNotEmpty();
        verify(memberRepository).findAll();
    }

    @Test
    void shouldDeleteMember() {
        var memberId = "member-id";

        memberService.deleteMember(memberId);

        verify(memberRepository).deleteById(memberId);
    }

}