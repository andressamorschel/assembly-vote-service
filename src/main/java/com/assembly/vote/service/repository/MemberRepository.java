package com.assembly.vote.service.repository;

import com.assembly.vote.service.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {

    @Query("{ _id: ?0, deleted: false }")
    Optional<Member> findById(String memberId);

    @Query("{ deleted: false }")
    List<Member> findAll();
}
