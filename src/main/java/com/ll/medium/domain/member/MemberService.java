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

    public SiteMember create(
            String membername,
            String emaill,
            String password)
    {
        SiteMember member = new SiteMember();
        member.setMembername(membername);
        member.setEmail(emaill);
        member.setPassword(passwordEncoder.encode(password));
        this.memberRepository.save(member);
        return member;
    }

    public SiteMember getMember (String membername) {
        Optional<SiteMember> siteMember  = this.memberRepository.findBymembername(membername);
        if(siteMember.isPresent()){
            return siteMember.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }
}
