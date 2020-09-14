package view;
 
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.BookHandler;
import controller.BorrowTransactionHandler;
import controller.RoleHandler;
import model.Borrow;
import model.BorrowItem;
import model.User;
 
//import com.sun.tools.javac.util.List;
 
public class BorrowHistoryForm extends JInternalFrame implements ActionListener {
 
	List<Borrow> borrowList = new ArrayList<Borrow>();
	List<BorrowItem> borrowItem = new ArrayList<BorrowItem>();
	BorrowTransactionHandler borrowTH = new BorrowTransactionHandler();
 
	DefaultTableModel bowDtm, detDtm;
	JTable bowTbl, detTbl;
	JScrollPane bowPane, detPane;
	JLabel title;
	JDialog getDate;
	JButton viewDetail, monthAndYear, returnBut;
	JPanel titlePnl, tblPnl, btnPnl;
	LocalDate now;
	JSpinner Smonth , Syear;
	Date date = new Date();
	
	public BorrowHistoryForm() {
		setVisible(true);
		setSize(340, 335);
		setLocation(225, 10);
		setClosable(true);
 
		title = new JLabel("Borrow History");
		titlePnl = new JPanel();
		titlePnl.add(title);
 
		getBorrowHistory();
 
		makeDetail();
 
		tblPnl = new JPanel(new GridLayout(2, 1));
		tblPnl.add(bowPane);
		tblPnl.add(detPane);
 
		viewDetail = new JButton("Borrow Detail");
		viewDetail.addActionListener(this);
		
		monthAndYear = new JButton("Filter");
		monthAndYear.addActionListener(this);
		
		returnBut = new JButton("Return Book");
		returnBut.addActionListener(this);
		if(User.isRoleMember() == false) {
			returnBut.setEnabled(false);
		}
		
		btnPnl = new JPanel(new FlowLayout());
		btnPnl.add(viewDetail);
		btnPnl.add(monthAndYear);
		btnPnl.add(returnBut);
 
		add(titlePnl, BorderLayout.NORTH);
		add(tblPnl, BorderLayout.CENTER);
		add(btnPnl, BorderLayout.SOUTH);
	}
 
	public void getBorrowHistory() {
		//Get Date

		now = LocalDate.now();
		Date newdate = java.sql.Date.valueOf(now);
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(newdate));
		int month =Integer.parseInt(new SimpleDateFormat("MM").format(newdate));
		date.setMonth(month);
		date.setYear(year);
		
		String[] names = {"ID", "Member ID", "Status","Borrow Time"};
 
		bowDtm = new DefaultTableModel(names, 0);
		bowTbl = new JTable(bowDtm);
		bowPane = new JScrollPane(bowTbl);
 
		showBorrowHistory();
	}
 
	public void showBorrowHistory() {
		borrowList = borrowTH.getAcceptStatus(date);
		int size = borrowList.size();
		bowDtm.setRowCount(size);
		
		for (int i = 0; i < size; i++) {
			String id = borrowList.get(i).getId();
			String name = borrowList.get(i).getMemberId();
			String status = borrowList.get(i).getStatus();
			String bowTime = borrowList.get(i).getBorrowTimestamp();
 
			bowDtm.setValueAt(id, i, 0);
			bowDtm.setValueAt(name, i, 1);
			bowDtm.setValueAt(status, i, 2);
			bowDtm.setValueAt(bowTime, i, 3);
		}
	}
 
	public void makeDetail() {
		String[] names = {"ID", "Book ID", "Return Time"};
 
		detDtm = new DefaultTableModel(names, 0);
		detTbl = new JTable(detDtm);
		detPane = new JScrollPane(detTbl);
	}
 
	public void viewDetail() {
		int index = bowTbl.getSelectedRow();
		if(index == -1) {
			JOptionPane.showMessageDialog(null, "Please Choose 1 Transaction!");
			return;
		}
 
		String curId = borrowList.get(index).getId();
 
		borrowItem = new BorrowTransactionHandler().getBookItem(curId);
		int size = borrowItem.size();
		detDtm.setRowCount(size);
		for (int i = 0; i < size; i++) {
			String id = borrowItem.get(i).getId();
			String bookId = borrowItem.get(i).getBookId();
			String retTime = borrowItem.get(i).getReturnTimestamp();
			if(retTime == null) retTime = "-";
			String bookName = new BookHandler().getById(bookId).getName();
			
			detDtm.setValueAt(id, i, 0);
			detDtm.setValueAt(bookName, i, 1);
			detDtm.setValueAt(retTime, i, 2);
		}
	}
	
	public void returnBook() {
		// TODO Auto-generated method stub
		int index = detTbl.getSelectedRow(); 
		 if(index == -1) {
			 JOptionPane.showMessageDialog(null, "Choose Book to returned!");
			return;
		 }
	 
		 int ans = JOptionPane.showConfirmDialog(null, "Do you want to return book?");
		 switch(ans){
         case JOptionPane.YES_OPTION: 	             
             
             //return book
             HashMap<String, String> inputs = new HashMap<String, String>();
             inputs.put("id", borrowItem.get(index).getId());
             inputs.put("bookId", borrowItem.get(index).getBookId());
             long fine = new BorrowTransactionHandler().returnBook(inputs);
             
             if( fine > 0) {
            	 String paymentString = null;
            	 long payment = 0;
            	 do {
            		 paymentString = JOptionPane.showInputDialog(null,"Input Payment", "Total Fine = " + fine, JOptionPane.INFORMATION_MESSAGE);
                	 payment = Long.parseLong(paymentString);
            	 }while(paymentString == null && paymentString.length() == 0 &&  payment < fine);
            	 
            	 if(payment > fine) {
            		 long change = payment - fine;
            		 JOptionPane.showMessageDialog(null, "Total Change = " + change);
            	 }	 
             }if(fine < 0)
            	 break;
             
        	 JOptionPane.showMessageDialog(null, "Return Book Success");
             
             
             break;
         case JOptionPane.NO_OPTION:
        	 JOptionPane.showMessageDialog(null, "Return Book Canceled");
             break;
		 }
		 
		 
	}
	
	public void refreshTableHistory() {
		bowDtm.setRowCount(0);
		showBorrowHistory();
	}
	
	public void filter() {
		JLabel tit = new JLabel();
		
	    
	    String months[] = { "January", "February", "March", 
	            "April", "May", "June", "July", "August",  
	            "September", "October", "November", "December" };
	    Smonth = new JSpinner(new SpinnerListModel(months));
	    
	    Syear = new JSpinner(new SpinnerNumberModel(2020, 1, 9999, 1));
	    
		Object[] message = {
				"Filter History at : ", tit,
				"Month", Smonth,
				"Year", Syear
			};
	    int option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION)
		{
			
			int valueMonth = 0;
			for (int i = 0; i<12 ; i++) {
				if(Smonth.getValue().equals(months[i])== true) {
					valueMonth = i+1;
					break;
				}
			}
		    int valueYear =  (Integer) Syear.getValue();
		    
		    date.setMonth(valueMonth);
		    date.setYear(valueYear);
		    
		    refreshTableHistory();
		    detDtm.setRowCount(0);
		}

	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == viewDetail) {
			viewDetail();
		}
		if(e.getSource() == monthAndYear) {
			filter();
		}
		if(e.getSource() == returnBut) {
			returnBook();
		}
	}
 
}