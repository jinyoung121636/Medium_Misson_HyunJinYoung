package com.ll.medium.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteMember create(
            String username,
            String emaill,
            String password){
        SiteMember user = new SiteMember();
        user.setUsername(username);
        user.setEmail(emaill);
        user.setPassword(passwordEncoder.encode(password));
        this.memberRepository.save(user);
        return user;
    }
}
