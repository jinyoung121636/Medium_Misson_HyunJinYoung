package com.ll.medium.domain.member;

import com.ll.medium.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public Member create(
            String username,
            String emaill,
            String password,
            boolean isPaid)
    {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(emaill);
        member.setPassword(passwordEncoder.encode(password));
        member.setPaid(isPaid);
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

    @Transactional
    public void processPaymentAndUpgradeMembership(String username){
        Optional<Member> memberOptional = memberRepository.findByusername(username);
        memberOptional.ifPresent(member -> {
            if(!member.isPaid()) {
                member.setPaid(true);
                memberRepository.save(member);
            }
        });
    }

}
