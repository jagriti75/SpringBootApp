package com.app.user.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
	
	private int id;
	private String userId;
	@JsonProperty(value = "name")
	private String username;
	private BigDecimal amount;

}
