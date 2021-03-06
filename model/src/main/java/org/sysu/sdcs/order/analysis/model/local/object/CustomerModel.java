package org.sysu.sdcs.order.analysis.model.local.object;

import org.sysu.sdcs.order.analysis.model.database.entity.Sex;

/**
 * Customer Information in Database
 * @author Zhuang Yixin
 */
public class CustomerModel {
	private String name;
	private Sex sex;
	private int age;
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
