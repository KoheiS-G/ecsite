package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.ecsite.model.dao.GoodsRepository;
import jp.co.internous.ecsite.model.dao.UserRepository;
import jp.co.internous.ecsite.model.entity.Goods;
import jp.co.internous.ecsite.model.entity.User;
import jp.co.internous.ecsite.model.form.GoodsForm;
import jp.co.internous.ecsite.model.form.LoginForm;

// Controllerパッケージには、JavaServletと類似の働きをするJavaクラスを格納する
// 具体的にはページ遷移、model/view間の橋渡しの役割を担う（そのため、対応するフロントエンド（HTML）と交互に作成することで、動作・表示を確認しながら開発を進めることができる）

// このクラスがControllerであることを示し、クライアントからのリクエストを処理し、レスポンスを返す事ができるようにする
@Controller
// 「http://localhost:8080/ecsite/admin」にリクエストが送信されたときにこのControllerを呼び出す
@RequestMapping("/ecsite/admin")
public class AdminController {
	
	// @Autowiredで自動的にインスタンスを生成（メソッドごとにインスタンスを生成する必要がなくなる）
	@Autowired
	private UserRepository userRepos;
	
	@Autowired
	private GoodsRepository goodsRepos;
	
	// 「http://localhost:8080/ecsite/admin/」にリクエストが送信されたときに、トップページ（adminindex.html）に遷移する
	@RequestMapping("/")
	public String index() {
		return "adminindex";
	}
	
	// 「http://localhost:8080/ecsite/admin/welcome」にPOSTメソッドでリクエストが送信されたときにこのメソッドを呼び出す
	// Controllerの仕様により、戻り値のテンプレートに遷移する
	@PostMapping("/welcome")
	public String welcome(LoginForm form, Model m) {
		// おそらくインスタンスを生成した時点で値は挿入されている（自動的に挿入されている？）のでセッターを使う必要はなし
		// ユーザー名とパスワードでユーザーを検索する
		List<User> users = userRepos.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		
		// 検索結果が存在していれば、isAdmin（管理者かどうか）を取得し、管理者だった場合のみ処理を行う
		if (users != null && users.size() > 0) {
			boolean isAdmin = users.get(0).getIsAdmin() != 0;
			if (isAdmin) {
				// goodsテーブルから全てのレコードを抽出
				// findAllメソッドはJpaRepositoryの標準メソッドである（そのためRepositoryインターフェースで改めて書く必要はない）
				List<Goods> goods = goodsRepos.findAll();
				m.addAttribute("userName", users.get(0).getUserName());
				m.addAttribute("password", users.get(0).getPassword());
				m.addAttribute("goods", goods);
			}
		}
		return "welcome";
	}
	
	// welcome.htmlで商品を追加するボタンを押したときに呼び出されるメソッド
	// ログイン情報を持たせてgoodsmst.htmlに遷移
	@RequestMapping("/goodsMst")
	public String goodsMst(LoginForm form, Model m) {
		m.addAttribute("userName", form.getUserName());
		m.addAttribute("password", form.getPassword());
		
		return "goodsMst";
	}
	
	// 商品を追加するメソッド
	// goodsmst.htmlで商品を追加すると、DBが更新されwelcome.htmlに遷移する
	@RequestMapping("/addGoods")
	public String addGoods(GoodsForm goodsForm, LoginForm loginForm, Model m) {
		
		 m.addAttribute("userName", loginForm.getUserName());
		 m.addAttribute("password", loginForm.getPassword());
		
		// Goodsクラスのインスタンスを生成し、goodsmst.htmlのフォームに入力した内容を属性として挿入
		// saveAndFlush（引数のエンティティ（つまりレコード）を保存してデータベースに反映させるメソッド）でDBを更新
		Goods goods = new Goods();
		goods.setGoodsName(goodsForm.getGoodsName());
		goods.setPrice(goodsForm.getPrice());
		goodsRepos.saveAndFlush(goods);
		
		// 戻り値で指定する遷移先に"forward:"をつけると、他の@RequestMappingハンドラメソッドに遷移することができる
		// 遷移先はリクエストパターンを含めたURLを指定する必要がある
		// つまりここでは、welcomeメソッドを再度実行してページを更新するという意味になる（ただ単にページに遷移するわけではない）
		return "forward:/ecsite/admin/welcome";
	}
	
	// 商品マスターから商品を削除する機能を作成する
	// これまでのページ遷移による処理ではなく、ajaxを使用した方式での処理（RESTと呼ばれる）である
	@ResponseBody
	@PostMapping("/api/deleteGoods")
	// @RequestBodyを指定すると、リクエストボディの内容をそのまま取得することができる
	// 具体的には、仮にGoodsFormに「goodsName」に「コート」、「price」に「12000」と入力してリクエストを発行すると
	// 引数の内容は「goodsName="コート"&price=12000」となる
	public String deleteApi(@RequestBody GoodsForm f, Model m) {
		try {
			goodsRepos.deleteById(f.getId());
		} catch(IllegalArgumentException e) {
			return "-1";
		}
		
		return "1";
	}
}
