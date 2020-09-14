package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.BookHandler;
import controller.BorrowTransactionHandler;
import controller.RoleHandler;
import model.Borrow;
import model.BorrowItem;
import model.User;

public class ViewBorrowForm extends JInternalFrame implements ActionListener {
	
	List<Borrow> borrowList = new ArrayList<Borrow>();
	List<BorrowItem> bowItemList = new ArrayList<BorrowItem>();
	BorrowTransactionHandler borrowTH = new BorrowTransactionHandler();
	JLabel title;
	
	DefaultTableModel bowDtm, bookDtm;
	JTable bowTbl, bookTbl;
	JScrollPane bowPane, bookPane;
	
	JPanel titlePnl, tblPnl, btnPnl;
	
	JButton viewBook, acceptButton;

	boolean isMember;
	
	public ViewBorrowForm() {
		// TODO Auto-generated constructor stub
		setSize(340, 335);
		setLocation(225, 10);
		setVisible(true);
		setClosable(true);
		
		title = new JLabel("Pending Borrow Book");
		titlePnl = new JPanel();
		titlePnl.add(title);
		
		
		if(User.getRoleId().compareTo(new RoleHandler().getByName("Membership").getId()) == 0) isMember = true;
		else isMember = false;
		
		borrowList = borrowTH.getPendingStatus(isMember);
		
		getBorrowList();
		
		makeItem();
		
		tblPnl = new JPanel(new GridLayout(2, 1));
		tblPnl.add(bowPane);
		tblPnl.add(bookPane);
		
		viewBook = new JButton("View Item");
		btnPnl = new JPanel();
		btnPnl.add(viewBook);
		viewBook.addActionListener(this);
		
		acceptButton = new JButton("Accept");
		btnPnl.add(acceptButton);
		acceptButton.addActionListener(this);
		if(User.isRoleAdministrator() == false) {
			acceptButton.setEnabled(false);
		}
		
		
		add(titlePnl, BorderLayout.NORTH);
		add(tblPnl, BorderLayout.CENTER);
		add(btnPnl, BorderLayout.SOUTH);
	}
	
	public void refreshTable() {
		bowDtm.setRowCount(0);
		showBorrowList();
	}
	public void getBorrowList() {
		String[] names = {"ID", "Member ID", "Status", "Borrow Time"};
		
		bowDtm = new DefaultTableModel(names, borrowList.size());
		bowTbl = new JTable(bowDtm);
		bowPane = new JScrollPane(bowTbl);
		
		showBorrowList();
	}
	
	public void showBorrowList() {
		borrowList = borrowTH.getPendingStatus(isMember);
		int size = borrowList.size();

		bowDtm.setRowCount(size);
		for (int i = 0; i < size; i++) {
			String id = borrowList.get(i).getId();
			String memberId = borrowList.get(i).getMemberId();
			String status = borrowList.get(i).getStatus();
			String borrowTime = borrowList.get(i).getBorrowTimestamp();
			
			bowDtm.setValueAt(id, i, 0);
			bowDtm.setValueAt(memberId, i, 1);
			bowDtm.setValueAt(status, i, 2);
			bowDtm.setValueAt(borrowTime, i, 3);
		}
	}
	
	public void makeItem() {
		String[] names = {"ID", "Title", "Return Timestamp"};
		
		bookDtm = new DefaultTableModel(names, 0);
		bookTbl = new JTable(bookDtm);
		bookPane = new JScrollPane(bookTbl);
	}
	
	public void showBook() {
		int index = bowTbl.getSelectedRow();
		if(index == -1) {
			JOptionPane.showMessageDialog(null, "Please Choose 1 Transaction!");
			return;
		}
		
		bookDtm.setRowCount(0);
		String curId = borrowList.get(index).getId();
		bowItemList = borrowTH.getBookItem(curId);
		
		int size = bowItemList.size();
		bookDtm.setRowCount(size);
		System.out.println("size pending" + size);
		
		for (int i = 0; i < size; i++) {
			String id = bowItemList.get(i).getId();
			String bookId = bowItemList.get(i).getBookId();
			String retTime = bowItemList.get(i).getReturnTimestamp();
			
			String bookName = new BookHandler().getById(bookId).getName();
			
			bookTbl.setValueAt(id, i, 0);
			bookTbl.setValueAt(bookName, i, 1);
			bookTbl.setValueAt(retTime, i, 2);
		}
	}

	public void acceptBorrow() {
		
		if(User.getRoleId().equals(new RoleHandler().getByName("Administrator").getId()) == false) {
			JOptionPane.showMessageDialog(null, "You can't do this action!!");
			return;
		}
		
		int index = bowTbl.getSelectedRow();
		if(index == -1) {
			JOptionPane.showMessageDialog(null, "Please Choose 1 Transaction!");
			return;
		}
		
		if(borrowTH.acceptBorrowRequest(borrowList.get(index).getId())
				== true) {
			JOptionPane.showMessageDialog(null, "Accept Success");
		}else {
			JOptionPane.showMessageDialog(null, "Accept Failed");
		}
		
		refreshTable();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == viewBook) {
			showBook();
		}else if(e.getSource() == acceptButton) {
			acceptBorrow();
		}
	}
}