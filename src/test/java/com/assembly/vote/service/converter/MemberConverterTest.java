package com.assembly.vote.service.converter;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.assembly.vote.service.model.request.MemberRequest;
import org.junit.jupiter.api.Test;

class MemberConverterTest {

    private final MemberConverter memberConverter = new MemberConverter();

    @Test
    void shouldBuildEntitySuccessfully() {
        var request = mock(MemberRequest.class);
        given(request.getCpf()).willReturn("member-cpf");
        given(request.getName()).willReturn("member-name");

        var result = memberConverter.buildEntity(request);

        assertThat(result.cpf()).isEqualTo("member-cpf");
        assertThat(result.name()).isEqualTo("member-name");
    }
}
