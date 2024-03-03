package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("users") // Spring Data JDBCでは@Entityアノテーションは使用しません
public class User {
	
	@Id // 主キーを示す
	private Long id;
	
	@Column("name") // カラム名を明示的に指定することも可能です
	private String name;
	
	@Column("password")
	private String password;
	
	@Column("comment")
	private String comment;
	
	// コンストラクタ、ゲッター、セッター等（Lombok @Dataがこれらを自動生成）
}
