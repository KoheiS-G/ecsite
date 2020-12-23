package jp.co.internous.ecsite.model.dto;

import jp.co.internous.ecsite.model.entity.User;

// DTOはData Transfe Objectの略
// modelの中でも、Javaプログラムから画面に送るデータを管理するクラスを格納する
// ここまで書いていて分かるとおり、DTOクラスとフォームクラス、エンティティクラスにはさほど違いはない（プロパティがあって、そのプロパティに対するゲッターとセッターメソッドを持つクラスを総称してBeanと呼ぶ）
// 違うのは用途である
// formはHTTPのPOSTメソッドで送信された値（formタグの中身）を入れることになっている
// entityはDBに登録・更新する値を入れることになっている
// dtoはデータ交換用
public class LoginDto {
	
	// Userエンティティの属性
	private long id;
	private String userName;
	private String password;
	private String fullName;
	
	// LoginDtoクラスはコンストラクタを3つ持つ
	// これは、以下の3パターンでインスタンス化できるようにするためである
	
	// インスタンス化の際に初期設定せず、あとから一つずつSetterを使ってデータをセットするパターン
	public LoginDto() {
		
	}
	
	// Userエンティティをまとめて受け取りデータをセットするパターン
	public LoginDto(User user) {
		this.setId(user.getId());
		this.setUserName(user.getUserName());
		this.setPassword(user.getPassword());
		this.setFullName(user.getFullName());
	}
	
	// 引数を分けて受け取り、データをセットするパターン
	public LoginDto(long id, String userName, String password, String fullName) {
		this.setId(id);
		this.setUserName(userName);
		this.setPassword(password);
		this.setFullName(fullName);
	}

	// 各属性のゲッターとセッター
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
