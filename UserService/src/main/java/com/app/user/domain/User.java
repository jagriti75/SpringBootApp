package com.app.user.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Employee")
@Entity
public class User {
	
	@GeneratedValue
	@Id
	private int id;
	@Column(name = "employeeName")
	private String username;
	@Column(name = "Salary")
	private BigDecimal amount;

}
