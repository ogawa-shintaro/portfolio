package com.example.demo.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserInput {
	@NotBlank(groups=Group1.class)
	private String name;
	@NotBlank(groups=Group1.class)
	@Pattern(regexp = "^[A-Za-z0-9]{5,}$",groups=Group2.class)
	private String password;
	
	private String comment;
}
