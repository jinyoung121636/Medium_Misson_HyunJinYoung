package com.ll.medium.domain.member;

import com.ll.medium.domain.post.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    // 회원가입 템플릿을 불러줌
    @GetMapping("/join")
    public String singup(MemberCreateForm memberCreateForm) {
        return "domain/member/join_form";
    }


    @PostMapping("/join")
    public String signup(@Valid MemberCreateForm memberCreateForm, Model model) {
        String username = memberCreateForm.getUsername();
        String email = memberCreateForm.getEmail();

        // 중복 사용자 이름 또는 이메일 확인
        if (memberService.existsByUsernameOrEmail(username, email)) {
            model.addAttribute("errorMessage", "이미 존재하는 회원입니다.");
            return "redirect:/member/join";
        }

        // 회원 가입
        Member member = memberService.create(username, email, memberCreateForm.getPassword(), memberCreateForm.getIsPaid());
        model.addAttribute("successMessage", "회원가입을 축하합니다.");
        return "redirect:/member/login";
    }

    // 로그인 템플릿을 불러줌
    @GetMapping("/login")
    public String login() {
        return "domain/member/login_form";
    }


    // membership 가입
    @GetMapping("/membership")
    public String membershipJoin(MemberCreateForm memberCreateForm){
        return "domain/member/membership_form";
    }

    // membership 가입
    @PostMapping ("/membership")
    public String membershipJoin(MemberCreateForm memberCreateForm, Model model, Principal principal) {
        String username = principal.getName();
       memberService.processPaymentAndUpgradeMembership(username);
        return "redirect:/";
    }


}