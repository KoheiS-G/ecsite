package jp.co.internous.ecsite.model.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import jp.co.internous.ecsite.model.entity.Purchase;

public class HistoryDto {
	
	// 購入履歴のデータ
	private long id;
	private long userId;
	private long goodsId;
	private String goodsName;
	private long itemCount;
	private long total;
	private String createdAt;
	
	// DTOクラスはコンストラクタを複数持つ（オーバーロード）
	// 引数によって場合分けを行う
	public HistoryDto() {
		
	}
	
	public HistoryDto(Purchase entity) {
		this.setId(entity.getId());
		this.setUserId(entity.getUserId());
		this.setGoodsId(entity.getGoodsId());
		this.setGoodsName(entity.getGoodsName());
		this.setItemCount(entity.getItemCount());
		this.setTotal(entity.getTotal());
		
		Timestamp d = entity.getCreatedAt();
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		this.setCreatedAt(f.format(d));
	}

	// 各属性のゲッターとセッター
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public long getItemCount() {
		return itemCount;
	}

	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
