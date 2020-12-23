package jp.co.internous.ecsite.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.ecsite.model.dao.GoodsRepository;
import jp.co.internous.ecsite.model.dao.PurchaseRepository;
import jp.co.internous.ecsite.model.dao.UserRepository;
import jp.co.internous.ecsite.model.dto.HistoryDto;
import jp.co.internous.ecsite.model.dto.LoginDto;
import jp.co.internous.ecsite.model.entity.Goods;
import jp.co.internous.ecsite.model.entity.Purchase;
import jp.co.internous.ecsite.model.entity.User;
import jp.co.internous.ecsite.model.form.CartForm;
import jp.co.internous.ecsite.model.form.HistoryForm;
import jp.co.internous.ecsite.model.form.LoginForm;

// このクラスをControllerに指定
@Controller
// 「http://localhost:8080/ecsite」のURLでこのクラスを呼び出す
@RequestMapping("/ecsite")
public class IndexController {

	// UserエンティティからuserテーブルにアクセスするDAOをインスタンス化
	@Autowired
	private UserRepository userRepos;
	
	// GoodsエンティティからgoodsテーブルにアクセスするDAOをインスタンス化
	@Autowired
	private GoodsRepository goodsRepos;
	
	// 
	@Autowired
	private PurchaseRepository purchaseRepos; 
	
	// WebサービスAPIとして作成するため、JSON形式を扱えるようにGsonをインスタンス化しておく
	private Gson gson = new Gson();
	
	// トップページに遷移するメソッド
	@RequestMapping("/")
	// Spring Frameworkでは、Controllerの処理メソッドに指定された引数は、自動的にModelにaddされるという仕様になっている
	// 要するにmodel.addAttribute("オブジェクト名", 変数名）;も実行されているということである
	// これをしない場合には、Modelクラスを処理メソッドの引数にして、明示的にaddしなければならない
	// ここでは後者の方法を取っている
	public String index(Model m) {
		// goodsテーブルから取得した商品エンティティの一覧を、フロントに渡すModelに追加している
		// Goods型のListであるgoods変数を宣言し、JpaRepository標準メソッドであるfindAllメソッドでテーブル内のレコードを全て取得している
		List<Goods> goods = goodsRepos.findAll();
		// goods変数（イメージ的には配列）をModelに挿入
		// そもそもModelとはSpringが用意するMapオブジェクト（連想配列に近い）のことであり、Viewに渡すオブジェクトを設定する事ができる
		// addAttributeメソッドとは、Modelにキーと値を指定して挿入するということを意味する
		m.addAttribute("goods", goods);
		
		// Controllerクラスでは、戻り値はデフォルトで遷移先のテンプレートを表す
		// すなわちこのメソッドは、indexというViewに遷移し、同時にModelに格納されたオブジェクトを渡すということを行っている
		return "index";
	}
	
	// 戻り値をボディとして返す
	@ResponseBody
	// 「...ecsite/api/login」にPOSTメソッドでリクエストが送信されたときにこのメソッドを呼び出す
	@PostMapping("/api/login")
	// @RequestBodyでフォームに入力された内容を取得できる
	// この場合だとcommon.jsから送信されたJSONオブジェクトからLoginForm.javaに入力されたユーザー名とパスワードを取得することになる
	// またControllerの処理メソッドに引数として指定された値は自動的にModelにaddされる
	public String loginApi(@RequestBody LoginForm form) {
		// DBテーブルからレポジトリクラスを使って、フォームに入力されたユーザー名とパスワードでDBレコードを取得
		List<User> users = userRepos.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		
		// その後、DTOをゲストの情報で初期化し、検索結果が存在していた場合のみ、実在のユーザ情報をDTOに積み込む
		LoginDto dto = new LoginDto(0, null, null, "ゲスト");
		if(users.size() > 0) {
			// DBレコードから生成したListの0番目の値でインスタンスを生成
			dto = new LoginDto(users.get(0));
		}
		
		// 最終的に、DTOをJSONオブジェクトとして画面側に返す
		// RESTでは特定のURLにHTTPリクエストを送信すると、XMLまたはJSONなどで記述されたメッセージを返す原則になっているので、やはりここでもJSONがViewに返される
		// ちなみにJSONとは「JavaScript Object Notation」の略で、「JavaScriptのオブジェクトの書き方を元にしたデータ定義方法」のことである
		// つまり、ここでやっているのはDBから取得したレコードをJavaScriptで扱えるデータに変換して送信するということである
		return gson.toJson(dto);
	}
	
	@ResponseBody
	@PostMapping("/api/purchase")
	public String puchaseApi(@RequestBody CartForm f) {
		
		// 拡張for文（forEach文）の文法
		// コレクション名（Listなどの変数名）.forEach（引数　-> 繰り返し行う処理（引数））
		// forEachメソッドにはアロー関数（ラムダ式）が使える
		// この場合はCartForm内のCartList変数の中の要素を繰り返し処理する
		f.getCartList().forEach((c) -> {
			// 合計金額を計算する
			long total = c.getPrice() * c.getCount();
			// DBのpurchaseテーブルにデータを入力するために、purchaseレポジトリのメソッドを購入情報を引数にして実行する
			purchaseRepos.persist(f.getUserId(), c.getId(), c.getGoodsName(), c.getCount(), total);
		});
		
		// String.valueOfとは、様々な型の値をString型に変換するメソッドである
		// （）内には変換する値が入る
		// 今回はCartList内の要素数をString型に変換している
		return String.valueOf(f.getCartList().size());
	}
	
	@ResponseBody
	@PostMapping("/api/history")
	public String historyApi(@RequestBody HistoryForm form) {
		// HistoryFormインスタンスのgetUserId()メソッドでuserIdを取得
		String userId = form.getUserId();
		// userIdでDBのpurchaseテーブルを検索し、Purchase型のListに格納
		// jsファイルから送信されたJSONは文字列のため、HistoryFormでのuserIdの型もString型になっている
		// String型をLong型に変換するためにparseLongメソッドを使う
		List<Purchase> history = purchaseRepos.findHistory(Long.parseLong(userId));
		// HistoryDto型のListであるhistoryDtoListインスタンスを、可変長配列として生成する
		// 購入履歴の配列をまとめて格納する
		List<HistoryDto> historyDtoList = new ArrayList<>();
		// DBのレコードを格納したList型historyで、要素全てをhistoryDtoListに格納するループ処理を行う
		history.forEach((v) -> {
			HistoryDto dto = new HistoryDto(v);
			historyDtoList.add(dto);
		});
		
		// 購入履歴が可変長配列として格納されたhistoryDtoListをJSONとして返す
		return gson.toJson(historyDtoList);
	}
}
