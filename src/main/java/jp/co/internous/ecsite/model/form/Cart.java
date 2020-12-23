package jp.co.internous.ecsite.model.form;

import java.io.Serializable;

// 購入機能の実装
// 購入情報をフロントからJava側に渡すためのCartクラスを作成する
public class Cart implements Serializable{
	private static final long serialVersionUID = 1L;
	
	// カート情報の属性
	private long id;
	private String goodsName;
	private long price;
	private long count;
	
	// 各属性のゲッターとセッター
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
}
