package com.jason.data;

import com.jason.Interface.IScript;

public class MyData implements IScript {
	private String name;
	private int id;
	private int age;

	public String getName() {
		return "卧槽跟换成功了";
	}

	public int getId() {
		return 9527;
	}

	public int getAge() {
		return 13;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
