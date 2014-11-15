package com.aahmedse.presto.pojo;

public class Employee {

	private int id;
	private String name;
	private boolean active;
	private double sales;
	
	
	public Employee(int id, String name, boolean active, double sales) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
		this.sales = sales;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @return the sales
	 */
	public double getSales() {
		return sales;
	}
	/**
	 * @param sales the sales to set
	 */
	public void setSales(double sales) {
		this.sales = sales;
	}
	
	
	
}
