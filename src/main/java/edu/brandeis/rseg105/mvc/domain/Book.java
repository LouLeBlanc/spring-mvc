/**
 * @author Louis LeBlanc
 * For Homework Assignment 2
 * Expert Software Development in Java
 * Brandeis University
 * Instructed by Vitaly Yurik
 */

package edu.brandeis.rseg105.mvc.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * Book Entity Class.
 * The id and version fields are defined by the superclass.
 *
 */
@Entity
@Table(name="book")
public class Book implements Serializable {

	/**
	 * Implementing Serializable
	 */
	private static final long serialVersionUID = -1606836550546687511L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private int version;

	@NotBlank(message="{validation.category.NotBlank.message}")
	@Column(name="category_name")
	private String category;

	@NotBlank(message="{validation.isbn.NotBlank.message}")
	@Column(name="isbn")
	private String isbn;

	@NotBlank(message="{validation.title.NotBlank.message}")
	@Column(name="title")
	private String title;

	@NotBlank(message="{validation.publisher.NotBlank.message}")
	@Column(name="publisher")
	private String publisher;

	/* We've used a float here in the past, but for this project we need to
	 * demonstrate use of the @NotNull annotation.
	 * Because a float is a value, and not a reference, we cannot use @NotNull.
	 * We could use other annotations to restrict this float to a valid range,
	 * but if we're to use @NotNull, we have to make this a Float so we can
	 * validate it against a null reference.
	 */
	@NotNull
	@Column(name="price")
	private Float  price;

	/**
	 * @return the Book ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @return the category name
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @return the price
	 */
	public Float getPrice() {
		return price;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Float price) {
		this.price = price;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Book - Id: " + this.id +
				", Category Name: " + this.category +
				", ISBN: " + this.isbn +
				", Title: " + this.title +
				", Publisher: " + this.publisher +
				", Price: " + this.price);
	}
}
