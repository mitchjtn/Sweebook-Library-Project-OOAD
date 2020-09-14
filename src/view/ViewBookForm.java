package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.BookHandler;
import model.Book;

public class ViewBookForm extends JInternalFrame {
	
	List<Book> bookList = new ArrayList<Book>();
	
	TableModel dtm;
	JTable tbl;
	JScrollPane sPane;
	
	JLabel title;
	
	JButton close;
	
	JPanel titlePnl;
	
	public ViewBookForm() {
		setSize(340, 335);
		setLocation(225, 10);
		setClosable(true);
		setVisible(true);
		
		title = new JLabel("Book List");
		titlePnl = new JPanel();
		titlePnl.add(title);
		
		getBook();
		
		String[] names = {"ID", "Name", "Genre ID", "ISBN", "Qty"};
		
		dtm = new DefaultTableModel(names, bookList.size());
		tbl = new JTable(dtm);
		sPane = new JScrollPane(tbl);
		
		showBook();
		
		add(titlePnl, BorderLayout.NORTH);
		add(sPane, BorderLayout.CENTER);
	}
	
	public void getBook() {
		bookList = new BookHandler().getAll();
	}
	
	public void showBook() {
		for (int i = 0; i < bookList.size(); i++) {
			String id = bookList.get(i).getId();
			String name = bookList.get(i).getName();
			String genreId = bookList.get(i).getGenreId();
			String isbn = bookList.get(i).getIsbn();
			int qty = bookList.get(i).getQuantity();
			
			dtm.setValueAt(id, i, 0);
			dtm.setValueAt(name, i, 1);
			dtm.setValueAt(genreId, i, 2);
			dtm.setValueAt(isbn, i, 3);
			dtm.setValueAt(qty, i, 4);
			
		}
	}
}