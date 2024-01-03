package com.ll.medium.domain.member;

import com.ll.medium.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public Member create(
            String username,
            String emaill,
            String password)
    {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(emaill);
        member.setPassword(passwordEncoder.encode(password));
        this.memberRepository.save(member);
        return member;
    }
    public Member getUser (String username) {
        Optional<Member> member = this.memberRepository.findByusername(username);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("존재하지 않는 회원입니다.");
        }
    }

    public boolean existsByUsernameOrEmail(String username, String email) {
        return memberRepository.existsByUsernameOrEmail(username, email);
    }
}
