package com.nico.store.store.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ShippingAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String receiver;
	private String streetAddress;
	private String city;
	private String country;
	private String zipCode;
	@OneToOne
	private Order order;
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public String getCity() {
		return city;
	}
	public void setCity(String City) {
		this.city = City;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String ZipCode) {
		this.zipCode = ZipCode;
	}
	public String getreceiver() {
		return receiver;
	}
	public void setName(String receiver) {
		this.receiver = receiver;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
}
