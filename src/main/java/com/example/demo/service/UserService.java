package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	@Transactional
    public boolean registerUser(User user) {
        try {
        	userRepository.save(user);
        	return true;
        }catch(DataIntegrityViolationException e) {
        	return false;
        }
    }
	public boolean existsByName(String name) {
        return userRepository.existsByName(name); // ユーザー名の存在チェック
    }

    public Iterable<User> findAllUsers() {
        // すべてのユーザーを取得
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        // IDによるユーザー検索
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteUser(Long id) {
        // ユーザーの削除
        userRepository.deleteById(id);
    }
    public String getUserComment(String username) {
        User user=userRepository.findByName(username);
        if(user!=null && user.getComment()!=null && !user.getComment().isEmpty()) {
        	return user.getComment();
        }else {
        	return "コメントがありません。";
        }
    }

}
