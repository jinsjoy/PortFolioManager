package com.portfolio.nodes;

public class Node {
	
	private int data;
	private String name;
	
	public Node(int data, String name){
		this.data=data;
		this.name=name;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
