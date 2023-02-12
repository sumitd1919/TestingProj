package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;


@Data
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {
	private Long id;

	private String name;

	private String mobile;

	private String email;

	private Integer salary;


	public Users(Long id, String name, String mobile, String email, Integer salary) {
		this.id = id;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.salary = salary;
	}

}

