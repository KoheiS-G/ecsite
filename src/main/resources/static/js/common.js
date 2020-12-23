/*
	Ajaxとは
	Asynchronous　Javascript + XMLの略である
	すなわちJavascriptとXMLを使用して非同期（Asynchronous）でページ内容を更新する技術のことを指す
	「非同期通信」とはサーバーと通信中であっても別の処理を引き続き行えることを意味する
	GmailやFacebook、Twitterなどでスムーズに操作が行えるのは非同期でサーバーと通信が行われているからである
	逆に、Webサイトなどでリンクをクリックして別のサイトに遷移する時、画面が真っ白になってWebページの情報がサーバーから読み込まれるまで待たなければならないような通信を「同期通信」という
	Ajaxを活用することにより、サーバー側とスムーズなデータのやり取りができるだけでなく、ユーザーに待ち時間を感じさせないレスポンスの高いWeb画面を構築することも可能になるのが大きな特徴である
*/

/*
	RESTとは
	REpresentational State Transferの略であり、Webサービスの設計モデルである
	一般的によく使われる狭義のRESTは、パラメータを指定して特定のURLにHTTPでアクセスすると、XMLやJSON（こちらが主流）などで記述されたメッセージが送られてくるようなシステム、
	および、そのような呼び出し規約（RESTful APIと呼ばれる）のことを指す
	簡単に言うと「リソース（1）」を扱うための考え方である
	システムやセッションの状態に依存せず、同じURLやパラメータの組み合わせからは常に同じ結果が返される
	APIの設計思想をRESTに従って行うのがREST API(RESTful API)である
	
	(1)リソースとは、ブログの記事や写真などというひと固まりの情報のことを指す
	
	
	RESTは設計に際し以下を設計原則とするように提言している
	・アドレス指定可能なURI（Uniform Resource Identifier）で公開されていること
	・インターフェース（HTTPメソッドの利用）の統一がされていること
	・ステートレスであること
	・処理結果がHTTPステータスコードで通知されること
	
	これらの原則に則ったWebサービスをRESTfulなサービスという
	
	アドレス指定可能なURIとは
	https://qiita.com/api/v2/users/TakahiRoyte
	上記URIはhttps://qiita.com/api/v2が提供する/usersのなかの(記事筆者)/TakahiRoyteと指定されている
	RESTでは一つのリソースに対してアドレス指定可能なURIが用意されていることが推奨されている
	リソースのURIを定義するときにはこのようにディレクトリ構造を模すとシンプルかつ直感的にサービスの利用ができる
	例えば以下のlogin関数は、一つのリソースとして数えられ、http://localhost:8080/ecsite/api/loginというURIを与えられている
	
	インターフェースの統一とは
	RESTで用いられるHTTPメソッドはSQLでも学んだCRUD操作と対応付けられる
	登録はPOSTメソッドでCREATEにあたる
	取得はGETメソッドでREADにあたる
	更新はPUTメソッドでUPDATEにあたる
	削除はDELETEメソッドでDELETEにあたる
	RESTではURIで表されたリソースに対して各HTTPメソッドで操作を行う
	リソースをCRUD操作するときにURIをそれぞれのメソッドで分けることはせず、URIはあくまでリソースを表すものとして扱い、そのURIの中で異なるメソッドによる操作を行うというのがRESTである
	
	ステートレスとは
	ステートとは「状態」を意味する。よってステートレスとは「状態がない」ことを意味する
	ステートレスなやり取りにおいてサーバーはクライアントのセッション情報を保持しない。逆にステートフルなやり取りではセッション情報が保持される
	ステートレスなやりとりとは、つまりリクエストのたびに前のリクエストも同時に送信する必要があるということを指す。するとリクエストが重なるごとに要求が冗長になることが予想される
	しかしこれにはメリットがあり、
	・クライアントのリクエストはリソース操作に必要十分な情報になる
	・セッション管理がシンプルになる
	・スケーラブルなシステムにできる
	スケーラビリティとは、機器やソフトウェア、システムなどの拡張性、拡張可能性のことを指す。スケーラブルなシステムとはすなわち拡張性の高いシステムを意味する
	スケーラビリティにはシステムの利用や負荷の増大、用途の拡大などに応じて、どれだけ柔軟に性能や機能を向上、拡張できるかを表したものという意味がある
	ステートフルなやり取りではセッションを使うと書いたが、セッションとは一時的にサーバーに情報を記憶させる仕組みで、当然ながら通信は一つのWebサーバーとの間で行われる
	ここでスケーラビリティの問題が浮上する。ステートフルなやり取りでは一つのサーバーとでしか通信を行えないので、アクセスが集中すればサーバーを増設するしか負荷緩和の方法がない
	システムを拡張すればそれだけ負荷も増大するため、その度にWebサーバーを増設することになりとコストがかかるので、スケーラビリティが低いということになる
	一方でステートレスなやり取りでは、各インタラクションでリクエストを全て繰り返すため、別々のサーバーがクライアントのリクエストにレスポンスすることが出来る
	このように負荷緩和を行うことでスケーラブルなシステムを構築できる
*/


// login関数
// let login = (event) => {};は旧文法だとlet login = function(event){};と同じである
// 意味はeventが発生した際に、この関数を起動するということ（eventの詳細はindex.htmlに記載）
// letは変数宣言の一つで同じ種類のものでvarやconstが挙げられる
// letは再宣言不可、再代入可でスコープがブロック内に限られる。また、変数宣言が常に関数の先頭で行われたことにされる挙動をホイスティングが無効になっている
let login = (event) => {
	event.preventDefault();
	// ログインフォームに入力された値をlet変数jsonStringに代入する
	let jsonString = {
		// $().val()は$()内の要素のvalueを取得するという意味である
		'userName': $('input[name=userName]').val(),
		'password': $('input[name=password]').val()
	};
	// jqueryのajaxを呼び出し、IndexControllerに追加したloginメソッドにアクセスしている
	// $.ajaxメソッドはjQueryでAjax通信の基本的な機能を担い、具体的にはHTTP通信でページを読み込む
	// 簡易的な構文として$.getや$.postなどのメソッドがあるが、非同期通信の動作をより細かく制御したい状況では$.ajaxメソッドを、という使い分け方をする
	$.ajax({
		//　通信に利用するHTTPメソッド（今回はPOSTメソッド）
		type: 'POST',
		// リクエストを送信する先のURL（省略された場合は呼び出し元のページに送信する）
		url: '/ecsite/api/login',
		// サーバーに送信する値
		//　JSON.stringifyとは
		// JSONはJavaScriptのオブジェクトのように記述するが、中身のデータ形式は文字列に過ぎない
		// そのためエンコードというデータを別の形式に変換する作業をする必要があり、エンコードをして初めてJavaScriptのオブジェクトとして扱えるようになる
		// 逆に、JavaScriptオブジェクトを別の言語で読むためには、オブジェクトをJSONにエンコードする必要がある
		// JavaScriptオブジェクトをJSONにエンコードをするにはJSON.stringifyを使う。これでJavaScriptオブジェクトが文字列になる
		// ここでJSONに値として入っているのはログインフォームに入力したユーザー名とパスワードで、これを/ecsite/api/loginというURLに送信する
		// するとIndexControllerに定義したloginApiメソッドが呼び出される。そのとき、このJSONに格納された値が引数として渡され、LoginFormに同じ変数名の値を設定する
		data: JSON.stringify(jsonString),
		// リクエスト時に利用するContent-Typeヘッダの値
		contentType: 'application/json',
		// サーバーから返されるデータの型を指定する
		datatype: 'json',
		// スクリプトを読み込む際のキャラセットを指定する
		sctiptCharset: 'utf-8'
	})
	// その後、thenメソッドのコールバック関数により、参照した結果を画面に出力している
	// ここではPromiseという仕組みを利用している。Promiseとは簡単に言えば非同期処理を簡潔に実現する仕組みである
	// 同期処理とは前の処理が終わるまで次の処理が行われないということであり、時間のかかる処理（たとえばサーバーからデータを取得するような場合）があると、その処理が終わるまで次の処理は実行できないということになる
	// 一方で非同期処理は結果を待たずにすぐ次の処理を実行できるというメリットがある
	// コールバックとは、非同期処理の結果を元にして何らかの処理を実行する仕組みであり、Promiseではthen()というメソッドがコールバックにあたる
	// この場合だと$.Ajax()メソッドの処理に対するコールバックとなる（つまりJSONオブジェクトを送信する処理の結果を元に処理を行うということ）
	// なお、(result)とはjQueryにおけるイベントオブジェクトの一つであり、直前に実行されたイベントハンドラが返した値を取得できるという機能を持つ
	// 直前のイベントハンドラは$.Ajax()メソッドになるので、これが返した値とはURLにJSONを送信しIndexControllerのloginApiメソッドがさらに戻り値として返したJSONを指す
	.then((result) => {
		// let変数userにIndexControllerから受け取ったJSONをJavaScriptオブジェクトにデコードして格納する（配列として扱われる）
		// JSONをデコードするにはJSON.parse()を使う
		let user = JSON.parse(result);
		// welcomeというidを持つ要素にテキストを挿入する。そのとき、上で宣言したuserという配列の中からfullNameというキーの値を使う
		$('#welcome').text(` -- ようこそ！ ${user.fullName} さん`);
		// hiddenUserIdというidを持つ要素に値を挿入する。値は配列userの中のidというキーの値を使う
		$('#hiddenUserId').val(user.id);
		// userNameとpasswordという名前を持つinputのvalueを空白にする（つまりフォームをクリアするということ）
		$('input[name=userName]').val('');
		$('input[name=password]').val('');
	}, () => {
		// Promiseのエラー処理分岐を行う。上のメソッドが正常に動作しなかった場合、下記のエラー処理を行う
		console.error('Error: ajax connection failed.');
	}
	);
};

// addCart関数
let addCart = (event) => {
	// let変数tdListを宣言し、eventターゲットであるcartBtnクラスを持ったタグの親要素の親要素の中から、td要素を持った全ての子要素を抽出する
	// この場合はindex.htmlの中の、商品一覧を表示するテーブルからtr要素を親要素とし、その中からtd要素を全て抽出する
	// tr要素及びその子要素は、Thymeleafの繰り返し文で商品の数だけ繰り返すようにしているため、この時点でどの商品をカートに入れたかが判別できる
	let tdList = $(event.target).parent().parent().find('td');
	
	// let変数に商品の情報をそれぞれ格納する
	let id = $(tdList[0]).text();
	let goodsName = $(tdList[1]).text();
	let price = $(tdList[2]).text();
	// numberタイプのinput要素の中のvalueを取り出す
	let count = $(tdList[3]).find('input').val();
	
	// もし購入数が0以下もしくは空欄ならアラートを出す
	if(count <= '0' || count === '') {
		alert('注文数が０以下または空欄です');
		return;
	}
	
	// 商品をカートに入れるための情報を配列として代入
	let cart = {
		// 'キー': 値
		'id': id,
		'goodsName': goodsName,
		'price': price,
		'count': count
	};
	// index.htmlで宣言したcartListという配列に要素を追加するpushメソッドを使って、カート内の情報を更新する
	cartList.push(cart);
	
	// cartというidを持つ要素からtbody要素を抽出する
	let tbody = $('#cart').find('tbody');
	// tbody要素の子要素全てを削除する
	$(tbody).children().remove();
	// cartList配列内の要素を使って繰り返し文を作る
	// この場合使用しているのはコールバック関数であり、引数として3つの値を受け取ることが出来る
	// 左から順に、配列データの値、配列のインデックス番号、現在処理している配列を表す
	// 今回は配列データの値をcart、配列のインデックス番号をindexとしている（配列のキーにあたる）。cartList内の全ての配列を処理するため、現在処理している配列は書かない
	cartList.forEach(function(cart, index){
		// let変数trに表の行をひとまとめにするtr要素を代入する
		let tr = $('<tr />');
		
		// セルの要素であり表の値を意味するtd要素の中に、cartListという配列の中にあるそれぞれのキーの値を追加する
		// その後td要素をtr要素に追加する
		// $(A).appendTo(B);でBにAが追加されるという意味
		// また、$('<htmlに追加したい要素>', { 'type': '追加したい要素の型', 'text': '追加したい要素のテキスト' ...etc }).appendToなどでhtmlに要素を作成できる
		// この場合だとtd要素を作成し、そのなかにテキストを挿入する。そのテキストは配列から取り出したもの
		$('<td />', { 'text': cart.id }).appendTo(tr);
		$('<td />', { 'text': cart.goodsName }).appendTo(tr);
		$('<td />', { 'text': cart.price }).appendTo(tr);
		$('<td />', { 'text': cart.count }).appendTo(tr);
		// let変数tdButtonにtd要素を代入する
		let tdButton = $('<td />');
		//　button要素を作成し、テキストに「カート削除」、クラスにremoveBtnを付与。これをtd要素に追加する
		$('<button />', {
			'text': 'カート削除',
			'class': 'removeBtn',
		}).appendTo(tdButton);
		
		// tdButtonに格納された要素をtr要素に追加する
		$(tdButton).appendTo(tr);
		// さらにそのtr要素をtbody要素に追加する
		$(tr).appendTo(tbody);
	});
	// removeBtnクラスを持った要素がクリックされたとき、removeCart関数を呼び出すイベントリスナー
	$('.removeBtn').on('click', removeCart);
};

// buy関数
let buy = (event) => {
	// Ajax構文を使って指定のURLにPOSTメソッドで、userId変数とcartList変数というJavaScriptオブジェクトをJSONに変換した上で送信する
	$.ajax({
		type: 'POST',
		url: '/ecsite/api/purchase',
		data: JSON.stringify({
			"userId": $('#hiddenUserId').val(),
			"cartList": cartList
		}),
		contentType: 'application/json',
		datatype: 'json',
		scriptCharset: 'utf-8'
	})
	// その後「購入しました」というアラートを出す
	.then((result) => {
		alert('購入しました。');
	}, () => {
		console.error('Error: ajax connection failed.');
	}
	);
};

// removeCart関数
let removeCart = (event) => {
	const tdList = $(event.target).parent().parent().find('td');
	let id = $(tdList[0]).text();
	// filterは配列の内容を特定の条件で絞り込むメソッドである
	cartList = cartList.filter(function(cart){
		return cart.id !== id;
	});
	$(event.target).parent().parent().remove();
}

// showHistory関数
let showHistory = () => {
	// jqueryのajaxメソッドを使い、userIdを指定のURLに送信し、IndexControllerを呼び出す
	$.ajax({
		type: 'POST',
		url: '/ecsite/api/history',
		data: JSON.stringify({
			"userId": $('#hiddenUserId').val()
		}),
		contentType: 'application/json',
		datatype: 'json',
		scriptCharset: 'utf-8'
	})
	// コールバック関数で通信の結果として購入履歴の配列を格納したJSONを受け取る
	.then((result) => {
		// JSONをJavaScriptオブジェクトにデコードし、中の配列をhistoryListに格納する
		let historyList = JSON.parse(result);
		let tbody = $('#historyTable').find('tbody');
		$(tbody).children().remove();
		historyList.forEach((history, index) => {
			let tr = $('<tr />');
			
			$('<td />', { 'text': history.goodsName }).appendTo(tr);
			$('<td />', { 'text': history.itemCount }).appendTo(tr);
			$('<td />', { 'text': history.createdAt }).appendTo(tr);
			
			$(tr).appendTo(tbody);
		});
		$("#history").dialog("open")
	}, () => {
		console.error('Error: ajax connection failed.');
		}
	);
};