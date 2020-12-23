package jp.co.internous.ecsite.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// modelパッケージには、DBとのやり取りに使用される情報や、フロントとバック間でのやり取りに使用される情報などを役割に応じて格納する。MVCモデルのM
// 中でもEntityは実体を意味し、DBのテーブルとマッピングする役割を持つ（詳しくはJPAを検索）
// 最終的にこのクラスはDBテーブルの１レコードを表すことになる

// @Entityアノテーションを付与すると、Springの機能により当該クラスはEntityとして振る舞うようになる
@Entity
// @Tableアノテーションで、DBのテーブル名とクラス名を紐付ける
@Table(name = "user")
public class User {
	
	// DBのカラムと対応した属性
	// @Idアノテーションはプライマリキーを意味する
	@Id
	// @Columnアノテーションで、テーブルのカラム名と変数名を紐付ける
	@Column(name = "id")
	// @GenerateValueアノテーションは、IDフィールドの振る舞い方を指定する。今回はAuto_Increamentとして振る舞う
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "is_admin")
	private int isAdmin;

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

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
}
