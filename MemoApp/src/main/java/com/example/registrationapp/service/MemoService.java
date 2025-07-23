package com.example.registrationapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.registrationapp.entity.Memo;
import com.example.registrationapp.entity.User;
import com.example.registrationapp.repository.MemoRepository;

@Service
public class MemoService {

    @Autowired
    private MemoRepository memoRepository;

    // メモを ID で検索するメソッド
    @Transactional
    public Memo findById(Long id) {
        System.out.println("メモを取得: ID = " + id);
        Optional<Memo> memo = memoRepository.findById(id);
        return memo.orElse(null);
    }

    // `getMemoById(Long)` を統一
    @Transactional
    public Memo getMemoById(Long id) {
        return findById(id);
    }

    // メモを保存するメソッド（デバッグログを追加）
    @Transactional
    public void saveMemo(Memo memo) {
        System.out.println("🔹 メモを保存する前の状態: " + memo);
        System.out.println("🔹 メモのタイトル: " + memo.getTitle());
        System.out.println("🔹 メモの内容: " + memo.getContent());
        System.out.println("🔹 メモのカテゴリ: " + memo.getCategory());
        System.out.println("🔹 メモの作成日時: " + memo.getCreatedAt());
        System.out.println("🔹 メモのユーザー情報: " + (memo.getUser() != null ? memo.getUser().getName() : "null"));



        if (memo.getTitle() == null || memo.getTitle().isEmpty()) {
            System.out.println("❌ `title` が空のため保存できません");
            return;
        }

        if (memo.getUser() == null) {
            System.out.println("❌ `memo.getUser()` が `null` のため保存できません");
            return;
        }

        try {
            System.out.println("📌 メモ保存処理開始: " + memo);
            memoRepository.save(memo);
            System.out.println("✅ メモの保存が完了しました: ID = " + memo.getId());
        } catch (Exception e) {
            System.out.println("❌ メモ保存時の例外発生: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    //  メモを更新するメソッド（タイトルと内容を更新）
    @Transactional
    public void updateMemo(Memo memo) {
        System.out.println("メモを更新: ID = " + memo.getId());

        Memo existingMemo = findById(memo.getId());
        if (existingMemo != null) {
            existingMemo.setTitle(memo.getTitle());
            existingMemo.setContent(memo.getContent());
            existingMemo.setCategory(memo.getCategory());
            memoRepository.save(existingMemo);
            System.out.println("✅ メモ更新完了: ID = " + memo.getId());
        } else {
            System.out.println("❌ メモ更新失敗: ID = " + memo.getId() + " のメモは存在しません");
        }
    }

    // メモを削除するメソッド
    @Transactional
    public void deleteMemo(Long id) {
        System.out.println("削除するメモ ID: " + id);
        try {
            memoRepository.deleteById(id);
            System.out.println("✅ メモ削除が完了しました: ID = " + id);
        } catch (Exception e) {
            System.out.println("❌ メモ削除時の例外発生: " + e.getMessage());
        }
    }

    // ユーザーに紐づくメモ一覧を取得
    @Transactional
    public List<Memo> findMemosByUser(User user) {
        System.out.println("ユーザーに紐づくメモを取得: " + user.getName());
        List<Memo> memos = memoRepository.findByUser(user);
        System.out.println("取得したメモリスト: " + memos);
        return memos;
    }
}
