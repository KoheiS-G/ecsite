package jp.co.internous.ecsite.model.form;

import java.io.Serializable;

// formパッケージはmodelの中でも、画面からJavaプログラムに送るデータを管理するクラスを格納する役割を持つ

// ログイン後に表示するページを作成する
//　ログインするには、HTMLのformからユーザ情報（ユーザ名とパスワード）を渡す必要があるため、LoginFormから作成する 
public class LoginForm implements Serializable {
	// Serializableインターフェースとは、ファイルにインスタンスを保存・復元することができる仕組みである
	// オブジェクトの状態をStreamの状態（1バイトずつ読み書きできるバイト配列状のデータ構造）に変換することをシリアライズというらしい
	// Spring FrameworkではformパッケージにはSerializableというマーカーインターフェースをつけることがルール付けられている
	// なんらかのライブラリに渡すオブジェクトの場合、Serializableが必要になるが
	// 自分の処理の中だけでオブジェクトをやり取りして保存・復元の必要がないならば不要になる
	private static final long serialVersionUID = 1L;
	
	// ユーザー名とパスワードの属性
	private String userName;
	private String password;
	
	// 各属性のゲッターとセッター
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
	
}
