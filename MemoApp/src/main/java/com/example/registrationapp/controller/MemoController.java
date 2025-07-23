package com.example.registrationapp.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.registrationapp.entity.Memo;
import com.example.registrationapp.entity.User;
import com.example.registrationapp.repository.MemoRepository;
import com.example.registrationapp.service.MemoService;
import com.example.registrationapp.service.UserService;

@Controller
public class MemoController {

    @Autowired
    private MemoService memoService;

    @Autowired
    private UserService userService;

    @Autowired
    private MemoRepository memoRepository;

    //  ホームページの表示（emailで認証）
    @GetMapping("/home")
    public String showHomePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Memo> memoList = List.of(); 

        if (userDetails != null) { 
            String email = userDetails.getUsername();  
            User user = userService.findByEmail(email); 
            
            if (user != null) {
                memoList = memoService.findMemosByUser(user);
            }
            
            model.addAttribute("message", "ログイン中: " + user.getEmail());
        } else { 
            model.addAttribute("message", "ログインしてください");
        }

        model.addAttribute("memoList", memoList);
        return "home";
    }

    // メモ作成ページの表示
    @GetMapping("/create")
    public String showCreateMemoPage(Model model) {
        model.addAttribute("memo", new Memo());
        return "create";
    }

    //  メモの登録処理
    @PostMapping("/create")
    public String processCreateMemo(@Valid @ModelAttribute("memo") Memo memo,
                                    BindingResult result,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "タイトルと内容は必須です");
            return "create";
        }

        if (userDetails == null) {
            model.addAttribute("error", "ログイン情報を取得できません。再度ログインしてください。");
            return "login";
        }

        String email = userDetails.getUsername();  
        User user = userService.findByEmail(email); 

        if (user == null) {
            model.addAttribute("error", "ユーザーが見つかりません。再度ログインしてください。");
            return "login";
        }

        memo.setId(null);
        memo.setCreatedAt(LocalDateTime.now());
        memo.setUser(user);

        try {
            memoService.saveMemo(memo);
        } catch (Exception e) {
            model.addAttribute("error", "メモの保存中にエラーが発生しました: " + e.getMessage());
            return "create";
        }

        return "redirect:/home";
    }

    //  メモ詳細ページの表示
    @GetMapping("/show/{id}")
    public String showMemoDetails(@PathVariable Long id, Model model) {
        Memo memo = memoService.findById(id);
        
        if (memo == null) {
            model.addAttribute("error", "指定されたメモが見つかりません");
            return "error"; 
        }

        model.addAttribute("memo", memo);
        return "show";
    }

    //  メモ編集ページの表示
    @GetMapping("/edit/{id}")
    public String showEditMemoPage(@PathVariable Long id, Model model) {
        Memo memo = memoService.findById(id);
        
        if (memo == null) {
            model.addAttribute("error", "指定されたメモが見つかりません");
            return "error"; 
        }

        model.addAttribute("memo", memo);
        return "edit"; 
    }

    // 修正: メモ編集後 `/create` に遷移
    @PostMapping("/edit/{id}")
    public String saveEditedMemo(@PathVariable Long id,
                                 @RequestParam("title") String title,
                                 @RequestParam("content") String content,
                                 @RequestParam("category") String category) { 

        Memo memo = memoService.findById(id);
        if (memo != null) {
            memo.setTitle(title);
            memo.setContent(content);
            memo.setCategory(category);
            memoService.saveMemo(memo);
        } else {
            return "error"; 
        }
        
        return "redirect:/home"; // ✅ `/create` にリダイレクト
    }

    // メモの削除処理
    @PostMapping("/delete/{id}")
    public String deleteMemo(@PathVariable Long id) {
        Memo memo = memoService.findById(id);
        if (memo != null) {
            memoService.deleteMemo(id);
        } else {
            return "error";
        }
        
        return "redirect:/home"; 
    }
}
