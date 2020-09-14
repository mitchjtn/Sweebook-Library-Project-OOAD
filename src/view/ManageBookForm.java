package view;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.BookHandler;
import model.Book;

import java.awt.FlowLayout;
import java.awt.GridLayout;

public class ManageBookForm extends JInternalFrame implements ActionListener{

	 ManageGenreForm manGen= new ManageGenreForm();
	 BookHandler bookHan = new BookHandler();
	
	 List<Book> bookList = new ArrayList<Book>();
	 
	 JTable table;
	 DefaultTableModel tableModel;

	 JScrollPane sPane, sPaneCart;
	 JLabel title,titleCart;
	 JButton restock, delete, viewGen;
	 JPanel titlePnl,titlePnlCart;
	 JSpinner spQuantity;
	 
	 public ManageBookForm() {
		 setSize(340, 335);
		 setLocation(225, 10);
		 setClosable(true);
		 setVisible(true);
	  
		 title = new JLabel("Book List");
		 titlePnl = new JPanel();
		 titlePnl.add(title);
		 
		 
		 //book list
		 bookList = bookHan.getAll();
		 String[] ListBookName = {"ID", "Name", "Genre ID", "ISBN", "Qty"};
		 tableModel = new DefaultTableModel(ListBookName,bookList.size());
		 table = new JTable(tableModel);
		 showBook(); 
		 
		 //button
		 
		 restock = new JButton("Restock Book");
		 delete = new JButton("Delete Book");
		 
		 restock.addActionListener(this);
		 delete.addActionListener(this);
		 
		 restock.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {

				JTextField name = new JTextField();
				JTextField genre = new JTextField();
				JTextField ISBN = new JTextField();
				JSpinner quantity = new JSpinner();
				Object[] message = {
				    "Input Book Name:", name,
				    "Input Genre :", genre,
				    "Input ISBN:", ISBN,
				    "Input Quantity:", quantity,
				};
				int option = JOptionPane.showConfirmDialog(null, message, "Enter Book Data", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION)
				{
				    String valueName = name.getText();
				    String valueGenre = genre.getText();
				    String valueISBN = ISBN.getText();
				    int valueQuantity =  (Integer) quantity.getValue();
				    if(valueName.length()<1 || valueGenre.length()<1 || valueISBN.length()<1 || valueQuantity == 0) {
				    	JOptionPane.showMessageDialog(null, "Please Completed the Data !");
				    	return;
				    }
				    else {
				    	HashMap<String,String> inputs = new HashMap<String,String>();
				    	inputs.put("name",valueName);
				    	inputs.put("type",valueGenre);
				    	inputs.put("isbn",valueISBN);
				    	inputs.put("quantity", Integer.toString(valueQuantity) );
				    	bookHan.restockBook(inputs);

				    	refreshTable();
				    }
				}
			}
		 });
		 
		 delete.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				 if(index == -1) {
					JOptionPane.showMessageDialog(null, "Choose Book to Deleted!");
					return;
				 }
				 else {
					 int ans = JOptionPane.showConfirmDialog(null, "Do you want to delete book?");
					 switch(ans){
			         case JOptionPane.YES_OPTION: 
			             bookHan.delete(bookList.get(index).getId());
			             break;
			         case JOptionPane.NO_OPTION:
			        	 JOptionPane.showMessageDialog(null, "Delete Canceled!!");
			             break;
					 }
				 }
				 refreshTable();
				 
			}
		 });
		 
		 JPanel pnlButton = new JPanel(new FlowLayout());
		 
		 pnlButton.add(restock);
		 pnlButton.add(delete);
	  
		 getContentPane().add(titlePnl, BorderLayout.NORTH);
		 getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		 getContentPane().add(pnlButton, BorderLayout.SOUTH);
	 }
	 
	 public void refreshTable() {
		 tableModel.setRowCount(0);
		 showBook();
	 }
	 
	 public void showBook() {
			bookList = bookHan.getAll();
			int size = bookList.size();
			tableModel.setRowCount(size);
			
			for (int i = 0 ; i < bookList.size() ; i++) {
				String id = bookList.get(i).getId();
				String name = bookList.get(i).getName();
				String genreId = bookList.get(i).getGenreId();
				String isbn = bookList.get(i).getIsbn();
				int qty = bookList.get(i).getQuantity();
	 
				table.setValueAt(id, i, 0);
				table.setValueAt(name, i, 1);
				table.setValueAt(genreId, i, 2);
				table.setValueAt(isbn, i, 3);
				table.setValueAt(qty, i, 4);
			}
	 }
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	 }

}