package jp.co.internous.ecsite.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.internous.ecsite.model.entity.User;

// DAOとはData Transfer Objectの略である
// DAOパッケージは、modelの中でもJavaプログラムから画面に送るデータを管理するクラスを格納する役割を持つ

// そしてRepositoryインターフェースは、DBへの基本的なアクセスをサポートする役割を持つ
public interface UserRepository extends JpaRepository<User, Long> {
	// ユーザー名とパスワードでDBのテーブルを参照するメソッド
	List<User> findByUserNameAndPassword(String userName, String password);
}
