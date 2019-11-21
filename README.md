# sample project

## 構成
- Java 11
- Spring Boot
- Spring Security

## 開発環境(推奨)
- Windows10
- IntelliJ(IntelliJ IDEA Community Edition 2019.1.3)

## 起動方法
```sh
./gradlew clean bootRun
```

## 実行可能Jarのビルドと実行

```sh
./gradlew clean build
java -jar build/libs/spring-authentication-sample-0.0.1-SNAPSHOT.jar
```

## 認証
エンドポイント:http://localhost:8080/login

HTTPメソッド：POST

params
```
{
    "username": "test@test.com",
    "password": "password"
}
```

## ログアウト
エンドポイント:http://localhost:8080/logout

HTTPメソッド：POST

## 提供エンドポイント

| エンドポイント | メソッド | 用途 |
|:----------------:|:------------|:------------|
| http://localhost:8080/users  | GET | ユーザー一覧情報取得  |
| http://localhost:8080/users  | GET | ユーザー情報取得  |
| http://localhost:8080/users  | POST | ユーザー情報登録 |
| http://localhost:8080/user/{id}  | PUT| ユーザー情報登録 |
| http://localhost:8080/userst/{id}  | DELETE | ユーザー情報登録 |
