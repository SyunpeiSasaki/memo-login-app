package com.example.registrationapp.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.registrationapp.entity.User;
import com.example.registrationapp.service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        // バリデーションエラーの確認
        if (result.hasErrors()) {
            model.addAttribute("error", "入力内容に誤りがあります。");
            return "registration";
        }

        // パスワード確認の一致チェック
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("error", "パスワードが一致しません。");
            return "registration";
        }

        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("message", "登録が完了しました: " + user.getName());
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("error", "システムエラーが発生しました。再試行してください。");
            return "registration";
        }
    }
}
