package com.song.flow.boot.test;

import java.util.Set;
import java.util.TreeSet;

public class Test {

	public static void main(String[] args) {

		Set<Parent> set = new TreeSet<>((o1, o2) -> (o1.age == o2.age) ? 0 : (o1.age > o2.age) ? 1 : -1);
		

		Parent p1 = new Parent("songyz1", 18);
		Parent p2 = new Parent("songyz2", 19);
		Parent p3 = new Parent("songyz3", 20);
		Parent p4 = new Parent("songyz3", 20);
		Parent p5 = new Parent("songyz3", 20);
		set.add(p1);
		set.add(p3);
		set.add(p2);
		set.add(p5);
		set.add(p4);
		System.out.println(set);
	}

}

class Parent {

	String name;

	int age;

	public Parent(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String toString() {
		return "Parent [name=" + name + ", age=" + age + "]";
	}

}
