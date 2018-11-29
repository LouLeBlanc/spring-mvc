/**
 * @author Louis LeBlanc
 * For Homework Assignment 2
 * Expert Software Development in Java
 * Brandeis University
 * Instructed by Vitaly Yurik
 */

package edu.brandeis.rseg105.mvc.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@Size(min=2, max=100, message="{validation.category.Size.message")
	@Column(name="category_name")
	private String category;

	@NotBlank(message="{validation.isbn.NotBlank.message}")
	@Size(min=10, max=13, message="{validation.isbn.Size.message")
	@Column(name="isbn")
	private String isbn;

	@NotBlank(message="{validation.title.NotBlank.message}")
	@Size(min=2, max=200, message="{validation.title.Size.message")
	@Column(name="title")
	private String title;

	@NotBlank(message="{validation.publisher.NotBlank.message}")
	@Size(min=2, max=100, message="{validation.publisher.Size.message")
	@Column(name="publisher")
	private String publisher;

	@NotNull
	@Column(name="price")
	private float  price;

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
	public float getPrice() {
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
	public void setPrice(float price) {
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

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		Book book = (Book) o;
		if (isbn != null ? !isbn.equals(book.isbn) : book.title != null )
			return false;
		return title != null ? !title.equals(book.title) : book.title != null;
	}

	@Override public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
		return result;
	}

}
