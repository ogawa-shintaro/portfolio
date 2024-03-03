package com.example.demo.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.input.UserFormInput;
import com.example.demo.input.UserInput;
import com.example.demo.input.ValidGroupOrder;
import com.example.demo.service.UserService;

@Controller
public class LoginController {

	private final UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("userInput", new UserInput());
		return "login";
	}
	@GetMapping(value="/login",params="failure")
	public String loginFail(Model model) {
		model.addAttribute("failureMessage","ログインに失敗しました");
		model.addAttribute("userInput",new UserInput());
		return "login";
	}
//	@PostMapping("/login")
//	public String validateInput(@Validated(ValidGroupOrder.class) UserInput userInput, BindingResult bindingResult,
//			Model model) {
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("userInput", userInput);
//			return "login";
//		}
//		return "userComment";
//	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("userFormInput", new UserFormInput());
		return "register";
	}

	@PostMapping("/register")
	public String validateRegister(@Validated(ValidGroupOrder.class) UserFormInput userFormInput,
			BindingResult bindingResult, Model model) {
		if (!userFormInput.getPassword().equals(userFormInput.getPasswordConfirm())) {
			bindingResult.rejectValue("passwordConfirm", "error.userForm", "パスワードが一致しません");
		}
		if (bindingResult.hasErrors()) {
			return "register";
		}
		return "confirm";
	}

	@PostMapping(value = "/register/confirm", params = "register")
	public String confirmRegister(UserFormInput userFormInput, Model model) {
		if (userService.existsByName(userFormInput.getName())) {
			model.addAttribute("registrationError", "このユーザー名は既に使用されています。");
			model.addAttribute("userFormInput", userFormInput);
			return "register";
		}
		User user = new User();
		user.setName(userFormInput.getName());
		user.setPassword(userFormInput.getPassword());
		user.setComment(userFormInput.getComment());
		userService.registerUser(user);
		return "redirect:/registerSuccess";
	}

	@PostMapping(value = "/register/confirm", params = "back")
	public String backToRegister(UserFormInput userFormInput, Model model) {
		//		model.addAttribute("userFormInput",userFormInput);
		return "register";
	}

	@GetMapping("/registerSuccess")
	public String registerSuccess() {
		return "registerSuccess";
	}

	@GetMapping("/userComment")
	public String showComment(Model model, Principal principal) {
		String username = principal.getName(); // Spring Securityからログインユーザー名を取得
		String comment = userService.getUserComment(username);
		model.addAttribute("username",username);
		model.addAttribute("comment", comment);
		return "userComment"; // コメントを表示するビュー名
	}
	@GetMapping("/")
	public String index() {
		return "index";
	}
}
