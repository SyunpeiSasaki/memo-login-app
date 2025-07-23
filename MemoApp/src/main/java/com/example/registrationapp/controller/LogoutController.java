package com.example.registrationapp.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        // セッションを無効化
        session.invalidate();
        // メッセージを表示するためにモデルを使用
        model.addAttribute("message", "ログアウトしました。");
        // login.html にリダイレクト
        return "redirect:/login";
    }
}
