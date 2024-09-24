package home.project.service;

import home.project.domain.Member;
import home.project.dto.MemberDTOWithoutId;
import home.project.dto.TokenDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberService {

    Member convertToEntity(MemberDTOWithoutId memberDTOWithoutId);

    TokenDto join(MemberDTOWithoutId member);

    Optional<Member> findById(Long memberId);

   Optional<Member> findByEmail(String email);

    Page<Member> findAll(Pageable pageable);

    Page<Member> findMembers(String name, String email, String phone, String role, String content, Pageable pageable);

    Optional<Member> update(Member member);

    void deleteById(Long memberId);
}
