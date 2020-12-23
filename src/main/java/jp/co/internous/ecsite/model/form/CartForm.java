package jp.co.internous.ecsite.model.form;

import java.io.Serializable;
import java.util.List;

public class CartForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// ユーザーIDとカートの内容のList
	// ここでいうcartListにはcommon.jsに記述したaddCart関数で追加したカートの情報が入る
	// cartListは配列なのでCart型を扱うList型の変数とする
	// Cartクラスには配列の中の要素が属性として追加されている
	private long userId;
	private List<Cart> cartList;
	
	// 各属性のゲッターとセッター
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public List<Cart> getCartList() {
		return cartList;
	}
	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}
	
	
}
