package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	boolean existsByName(String name);// ユーザー名の存在チェック用メソッド
	User findByName(String name);
}
