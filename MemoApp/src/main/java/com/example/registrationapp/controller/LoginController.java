package com.example.registrationapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.registrationapp.entity.User;
import com.example.registrationapp.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // `login.html` テンプレートを表示
    }

    // emailでログインする処理
    @PostMapping("/login")
    public String handleLogin(@RequestParam("email") String email, 
                              @RequestParam("password") String password,
                              Model model) {
        System.out.println("ログイン試行: email=" + email + ", password=" + password);

        // `email` でデータベースからユーザー情報を検索
        User user = userService.findByEmail(email);

        //  ユーザーが存在しない場合
        if (user == null) {
            model.addAttribute("error", "メールアドレスまたはパスワードが間違っています");
            return "login"; // `login.html` に戻る
        }

        // ハッシュ化されたパスワードと入力値を比較
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "メールアドレスまたはパスワードが間違っています");
            return "login"; // `login.html` に戻る
        }

        // ログイン成功時の処理
        model.addAttribute("user", user);
        return "redirect:/home"; // ログイン成功後に `/home` へリダイレクト
    }
}
