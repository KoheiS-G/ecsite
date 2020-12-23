package jp.co.internous.ecsite.model.form;

import java.io.Serializable;

public class HistoryForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// 購入履歴を表示したいユーザーのID
	// JSONファイルには文字列として格納されているので、String型として定義する
	private String userId;

	// 各属性のゲッターとセッター
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
