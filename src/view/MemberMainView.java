package view;

import javax.swing.JFrame;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import main.FirstMenuView;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MemberMainView extends JFrame implements ActionListener{
	 BorrowBookForm borrowBookForm ;
	 BorrowHistoryForm borrowHisForm ;
	 ViewBookForm viewBookForm ;
	 ViewBorrowForm viewBorForm ;
  
	 JDesktopPane viewer = new JDesktopPane();
	 
	 JLabel title;
	 JButton borrowBook, viewBook, viewBorrow, borrowHistory;
	 private JButton btnLogOut;
	 JLabel photo;
	 ImageIcon image = new ImageIcon("aaa.png");
	 
	 public MemberMainView() {
		getContentPane().setLayout(null);
		setTitle("Membership Main Menu");
		setSize(600,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		  
		//title
		title = new JLabel("Membership Menu");
		title.setBounds(20, 31, 227, 54);
		title.setFont(new Font("Gabriola", Font.PLAIN, 34));
		getContentPane().add(title);
		  
		//borrow book
		borrowBook = new JButton("Borrow Book");
		borrowBook.setFont(new Font("Tahoma", Font.PLAIN, 12));
		borrowBook.setBounds(20, 84, 130, 41);
		getContentPane().add(borrowBook);
		borrowBook.addActionListener(this);
		
		//view book
		viewBook = new JButton("View Book");
		viewBook.setFont(new Font("Tahoma", Font.PLAIN, 12));
		viewBook.setBounds(20, 136, 130, 41);
		getContentPane().add(viewBook);
		viewBook.addActionListener(this);
		  
		//view borrow
		viewBorrow = new JButton("View Borrow");
		viewBorrow.setFont(new Font("Tahoma", Font.PLAIN, 12));
		viewBorrow.setBounds(20, 188, 130, 41);
		getContentPane().add(viewBorrow);
		viewBorrow.addActionListener(this);
		  
		//borrow history
		borrowHistory = new JButton("Borrow History");
		borrowHistory.setFont(new Font("Tahoma", Font.PLAIN, 12));
		borrowHistory.setBounds(20, 240, 130, 41);
		getContentPane().add(borrowHistory);
		borrowHistory.addActionListener(this);
		  
		//log out
		btnLogOut = new JButton("Log Out");
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLogOut.setBounds(20, 292, 130, 41);
		getContentPane().add(btnLogOut);
		btnLogOut.addActionListener(this);
		  
		//Photo
	
		photo = new JLabel("New label");
		photo.setBounds(245, 65, 284, 240);
		getContentPane().add(photo);
		photo.setIcon(image);
	 }
	
	 public void showBorrowBookForm() {
		if(borrowBookForm == null || borrowBookForm.isVisible() == false) {
	    	 if(viewBookForm != null) viewBookForm.dispose();
	    	 if(viewBorForm != null) viewBorForm.dispose();
	    	 if(borrowHisForm != null) borrowHisForm.dispose();
	    	 
	    	 borrowBookForm = new BorrowBookForm();
		     add(borrowBookForm).setSize(340, 335);
		     remove(photo);
	     }else {
	    	 borrowBookForm.dispose();
	    	 getContentPane().add(photo);
	     }
	     
	 }
	 
	 public void showViewBookForm() {
		 if(viewBookForm == null || viewBookForm.isVisible() == false) {
	    	 if(borrowBookForm != null) borrowBookForm.dispose();
	    	 if(viewBorForm != null) viewBorForm.dispose();
	    	 if(borrowHisForm != null) borrowHisForm.dispose();
	    	 
	    	 viewBookForm = new ViewBookForm();
		     add(viewBookForm).setSize(340, 335);
		     remove(photo);
	     }else {
			 viewBookForm.dispose();
			 getContentPane().add(photo);
	     }
	    
	 }
	 
	 public void showViewBorrowForm() {
		 if(viewBorForm == null || viewBorForm.isVisible() == false){
	    	 if(borrowBookForm != null) borrowBookForm.dispose();
	    	 if(viewBookForm != null) viewBookForm.dispose();
	    	 if(borrowHisForm != null) borrowHisForm.dispose();
	    	 
	    	 viewBorForm = new ViewBorrowForm();
			 add(viewBorForm).setSize(340, 335);
			 remove(photo);
	     }else {
			 viewBorForm.dispose();
			 getContentPane().add(photo);
	     }
		 
	 }
	 
	 public void showBorrowHistoryForm() {
		 if(borrowHisForm == null || borrowHisForm.isVisible() == false){
	    	 if(borrowBookForm != null) borrowBookForm.dispose();
	    	 if(viewBookForm != null) viewBookForm.dispose();
	    	 if(viewBorForm != null) viewBorForm.dispose();
	    	 
	    	 borrowHisForm = new BorrowHistoryForm();
			 add(borrowHisForm).setSize(340, 335);
			 remove(photo);
	     }else if(borrowHisForm.isVisible() == true) {
			 borrowHisForm.dispose();
			 getContentPane().add(photo);
	     }
		 
	 }
	 
	 public void logout() {
		 this.dispose();
		 FirstMenuView fmv = new FirstMenuView();
		 fmv.setVisible(true);
		 
	 }
	 
	 public void actionPerformed(ActionEvent e){
		 if(e.getSource() == borrowBook) {
			 showBorrowBookForm();
		 }
	     else if(e.getSource() == viewBook) {
	    	 showViewBookForm();
	     }
	     else if(e.getSource() == viewBorrow) {
	    	 showViewBorrowForm();
	     }
	     else if(e.getSource() == borrowHistory) {
	    	 showBorrowHistoryForm();
	     }
	     else if(e.getSource() == btnLogOut) {
	    	 logout();
	     }
		 
	 }
}