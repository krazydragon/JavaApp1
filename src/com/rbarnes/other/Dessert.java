//Ronaldo Barnes
//Java 1 week 2
//January 2013
//Full Sail University
package com.rbarnes.other;



public class Dessert implements Inventory {

	
	String name;

	public Dessert(String name){
		setName(name);
	}
	
	@Override
	public boolean setName(String name) {
		this.name = name;
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
}
