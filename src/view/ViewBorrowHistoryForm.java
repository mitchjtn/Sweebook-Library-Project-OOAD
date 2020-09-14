package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import controller.BorrowTransactionHandler;
import controller.RoleHandler;
import model.Borrow;
import model.BorrowItem;
import model.User;

//import com.sun.tools.javac.util.List;

public class ViewBorrowHistoryForm extends JInternalFrame implements ActionListener {
	
	List<Borrow> borrowList = new ArrayList<Borrow>();
	List<BorrowItem> borrowItem = new ArrayList<BorrowItem>();
	
	DefaultTableModel bowDtm, detDtm;
	JTable bowTbl, detTbl;
	JScrollPane bowPane, detPane;
	
	JLabel title;
	
	JDialog getDate;
	
	JButton viewDetail;
	
	JPanel titlePnl, tblPnl, btnPnl;
	
	LocalDate now;
	
	public ViewBorrowHistoryForm() {
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
		
		viewDetail = new JButton("View Borrow Detail");
		btnPnl = new JPanel();
		btnPnl.add(viewDetail);
		viewDetail.addActionListener(this);
		
		add(titlePnl, BorderLayout.NORTH);
		add(tblPnl, BorderLayout.CENTER);
		add(btnPnl, BorderLayout.SOUTH);
	}
	
	public void getBorrowHistory() {
		//Get Date
		
		now = LocalDate.now();
		Date date = java.sql.Date.valueOf(now);
		
		//IsMember?
		
		boolean isMember;
		
		if(User.getRoleId().compareTo(new RoleHandler().getByName("Membership").getId()) == 0) isMember = true;
		else isMember = false;
		
		borrowList = new BorrowTransactionHandler().getAcceptStatus(date);
		
		String[] names = {"ID", "Member ID", "Status","Borrow Time"};
		
		bowDtm = new DefaultTableModel(names, borrowList.size());
		bowTbl = new JTable(bowDtm);
		bowPane = new JScrollPane(bowTbl);
		
		showBorrowHistory();
	}

	public void showBorrowHistory() {
		for (int i = 0; i < borrowList.size(); i++) {
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
		
		for (int i = 0; i < borrowItem.size(); i++) {
			String id = borrowItem.get(i).getId();
			String bookId = borrowItem.get(i).getBookId();
			String retTime = borrowItem.get(i).getReturnTimestamp();
			
			detDtm.setValueAt(id, i, 0);
			detDtm.setValueAt(bookId, i, 1);
			detDtm.setValueAt(retTime, i, 2);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == viewDetail) {
			viewDetail();
		}
	}

}