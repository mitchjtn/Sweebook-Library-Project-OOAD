package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import controller.*;
import model.*;

public class BorrowBookForm extends JInternalFrame implements ActionListener {
	BorrowBookHandler bbh = new BorrowBookHandler();
	 
	 //list

	 List<Book> bookList = new ArrayList<Book>();
	 List<Book> cartList = new ArrayList<Book>();

	 BookHandler bookH = new BookHandler();

	 //create ui

	 DefaultTableModel tableModelBook, tableModelCart;
	 JTable tbl;
	 JTable tblCart;
	 JScrollPane sPane;
	 JScrollPane sPaneCart;
	 JLabel title;
	 JLabel titleCart;
	 JButton close,borrow,addCart,removeCart;
	 JPanel titlePnl;
	 JPanel titlePnlCart;
	 JTable tableBook,tableCart;



	 public BorrowBookForm() {
		 setVisible(true);
		 setSize(340, 335);
		 setLocation(225, 10);
		 setClosable(true);

		 //book list
		 bookList = bookH.getAll();

		 String[] names = {"ID", "Name", "Genre ID", "ISBN", "Qty"};
		 tableModelBook = new DefaultTableModel(names, bookList.size());
		 tableBook = new JTable(tableModelBook);
		 sPane = new JScrollPane(tableBook);

		 showBookList();

		 //cart List
		 cartList = bbh.getCart();

		 String[] namesCart = {"ID", "Name", "Genre ID", "ISBN", "Qty"};

		 tableModelCart = new DefaultTableModel(namesCart, cartList.size());
		 tableCart = new JTable(tableModelCart);
		 sPaneCart = new JScrollPane(tableCart);		 

		 showCartList();

		 //button
		 borrow = new JButton("Borrow");
		 addCart = new JButton("add to cart");
		 removeCart = new JButton("remove from cart");

		 borrow.addActionListener(this);
		 addCart.addActionListener(this);
		 removeCart.addActionListener(this);

		 JPanel pnlBook = new JPanel(new BorderLayout());
		 pnlBook.add(new JLabel("Book List"), BorderLayout.NORTH);
		 pnlBook.add(sPane, BorderLayout.CENTER);

		 JPanel pnlCart = new JPanel(new BorderLayout());
		 pnlCart.add(new JLabel("Cart List"), BorderLayout.NORTH);
		 pnlCart.add(sPaneCart, BorderLayout.CENTER);

		 JPanel pnlList = new JPanel(new GridLayout(2,1));
		 pnlList.add(pnlBook);
		 pnlList.add(pnlCart);

		 JPanel pnlButton = new JPanel(new FlowLayout());
//		 pnlButton.add(close);
		 pnlButton.add(borrow);
		 pnlButton.add(addCart);
		 pnlButton.add(removeCart);

		 getContentPane().add(pnlList, BorderLayout.CENTER);
		 getContentPane().add(pnlButton, BorderLayout.SOUTH);


	 }
	 
	 public void refreshBookTable() {
		 tableModelBook.setRowCount(0);
		 showBookList();
	 }
	 
	 public void refreshCartTable() {
		 tableModelCart.setRowCount(0);
		 showCartList();
	 }
	 
	 private void showBookList(){
		bookList = bookH.getAll();
		int size = bookList.size();
		tableModelBook.setRowCount(size);
		for (int i = 0 ; i < size ; i++) {
			String id = bookList.get(i).getId();
			String name = bookList.get(i).getName();
			String genreId = bookList.get(i).getGenreId();
			String isbn = bookList.get(i).getIsbn();
			int qty = bookList.get(i).getQuantity();
 
			tableBook.setValueAt(id, i, 0);
			tableBook.setValueAt(name, i, 1);
			tableBook.setValueAt(genreId, i, 2);
			tableBook.setValueAt(isbn, i, 3);
			tableBook.setValueAt(qty, i, 4);
		}
	 }
	 
	 private void showCartList() {
		cartList = bbh.getCart();
		int size = cartList.size();
		tableModelCart.setRowCount(size);
		for (int i = 0 ; i < size ; i++) {
			String id = cartList.get(i).getId();
			String name = cartList.get(i).getName();
			String genreId = cartList.get(i).getGenreId();
			String isbn = cartList.get(i).getIsbn();
			int qty = cartList.get(i).getQuantity();
 
			tableCart.setValueAt(id, i, 0);
			tableCart.setValueAt(name, i, 1);
			tableCart.setValueAt(genreId, i, 2);
			tableCart.setValueAt(isbn, i, 3);
			tableCart.setValueAt(qty, i, 4);
			}
	}
	 
	 @Override
	 public void actionPerformed(ActionEvent e) {
		 // TODO Auto-generated method stub
		 if(e.getSource() == close ) {
			 this.dispose();
		 }else if(e.getSource() == addCart) {
			 int index = tableBook.getSelectedRow();
				if(index == -1) {
					JOptionPane.showMessageDialog(null, "Choose Book You Want Add to Cart!");
					return;
				}
				
				if(bbh.addToCart(bookList.get(index)) ) {						
					JOptionPane.showMessageDialog(null, "Add to cart success");
					refreshCartTable();
					refreshBookTable();
				}
				
		 }else if(e.getSource() == removeCart) {
			 int index = tableCart.getSelectedRow();
				if(index == -1) {
					JOptionPane.showMessageDialog(null, "Choose Book Do You Want remove!");
					return;
				}
				else {
					int ans = JOptionPane.showConfirmDialog(null, "Do You Want to Remove This Book?");
					 switch(ans){
				        case JOptionPane.YES_OPTION: 
				        	bbh.removeCart(cartList.get(index));
				        	JOptionPane.showMessageDialog(null, "Remove Success !");
				        	refreshCartTable();
							refreshBookTable();
				            break;
				        case JOptionPane.NO_OPTION:
				            break;
				    }
				}
		 }else if(e.getSource() == borrow) {
			 if(bbh.borrowBook() ) {
				 JOptionPane.showMessageDialog(null, "Borrow Book Success!!");
				 refreshCartTable();
			 }
			 
		 }
	 }
}