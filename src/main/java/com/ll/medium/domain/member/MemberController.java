package com.ll.medium.domain.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    // 회원가입 템플릿을 불러줌
    @GetMapping("/join")
    public String singup(MemberCreateForm memberCreateForm){
        return "domain/member/join_form";
    }

    // 회원가입 정보를 저장
    @PostMapping("/join")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "domain/member/join_form";
        }
        if(!memberCreateForm.getPassword().equals(memberCreateForm.getPasswordConfirm())){
            bindingResult.rejectValue(
                    "passwordConfirm",
                    "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "domain/member/join_form";
        }
        memberService.create(
                memberCreateForm.getUsername(),
                memberCreateForm.getEmail(),
                memberCreateForm.getPassword());
        return "redirect:/";
    }

    // 로그인 템플릿을 불러줌
    @GetMapping("/login")
    public String login(){
        return "domain/member/login_form";
    }
}
