package model;

import util.*;
import java.util.*;
import controller.*;
import model.*;

public class CartStorage {
	
	private HashMap<String,Book> carts;
	

	public CartStorage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartStorage(HashMap<String, Book> carts) {
		super();
		this.carts = carts;
	}

	public HashMap<String, Book> getCarts() {
		return carts;
	}

	public void setCarts(HashMap<String, Book> carts) {
		this.carts = carts;
	}
	
	public Collection<Book> getCart(){ 
		List<Book> books = new ArrayList<Book>();
		
		for (Book book1 : carts.values()) {
			books.add(book1);
		}
		return books;
	}
	
	public void addCart(Book book) {
		carts.put(book.getId(), book);
	}
	
	public void removeCart(Book book) {
		//search book from cart then remove
		carts.remove(book.getId());
	}
}
