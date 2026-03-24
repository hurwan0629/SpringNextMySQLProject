package com.hurwan.jpaEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="USERS")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer age;
	private String hobby;
	
	protected User() {
		
	}
	
	public User(String name, Integer age) {
		this.name=name;
		this.age=age;
	}
	
	public User(String name, Integer age, String hobby) {
		this.name=name;
		this.age=age;
		this.hobby=hobby;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public String getHobby() {
		return hobby;
	}

	
}
