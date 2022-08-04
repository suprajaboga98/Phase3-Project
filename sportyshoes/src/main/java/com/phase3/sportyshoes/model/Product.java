package com.phase3.sportyshoes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Product {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
	private Long id;
	
    @Column(name = "Name")
	private String name;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id" , referencedColumnName = "category_id")
	private Category category;
	
    @Column(name = "Price")
	private double price;
	
    @Column(name = "Weight")
	private double weight;
	
    @Column(name = "Description")
	private String description;
	
    @Column(name = "Image")
	private String imageName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}  
}
