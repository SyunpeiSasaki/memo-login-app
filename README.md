# memo-login-app
ログイン機能付きメモアプリ

このリポジトリは、Spring Boot を用いたメモ管理アプリのログイン機能開発用プロジェクトです。

## 現在の状態

- Eclipse で開発中のプロジェクトをそのままアップロードしています。
- Spring Security によるログイン画面が表示されますが、まだログイン機能は製作途中です。
- 現状、一部の機能は未実装または動作未確認です  
- パスワードリセット機能はまだありません
  

## 開発環境

- Java 17
- Spring Boot 3.1.1
- Maven プロジェクト
- Eclipse IDE


## 起動手順

1. Eclipse または IntelliJ でプロジェクトをインポート  
   「File > Import > Existing Maven Project」を選び、`MemoApp` フォルダを選択

2. メインクラスを起動  
   `src/main/java/com/example/registrationapp/MemoappApplication.java` を右クリック → Run As → Java Application

3. 起動後、ブラウザで以下にアクセス  
http://localhost:8080/home にアクセスすると、各機能のページへ移動できます。
現在、ログイン認証および新規登録機能は修正中のため、セキュリティは一時的に解除しています。
そのため、現時点では認証なしで全ページにアクセス可能な状態です。

