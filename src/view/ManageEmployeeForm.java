package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.EmployeeHandler;
import controller.RoleHandler;
import model.Employee;
import model.Role;
import model.User;

public class ManageEmployeeForm extends JInternalFrame implements ActionListener {
	
	List<Role> roleList = new ArrayList<Role>(); 
	List<Employee> employeeList = new ArrayList<Employee>();
	
	User user = new User();
	
	DefaultTableModel roleDtm, empDtm;
	JTable roleTbl, empTbl;
	JScrollPane rolePane, empPane;
	
	JLabel title;
	
	JButton addEmp, fireEmp, accReq;
	
	JPanel titlePnl, tblPnl, btnPnl;
	
	Employee choosedEmployee;
	
	JLabel lblSalary, lblStatus;
	
	JTextField txtSalary, txtStatus;
	
	AddEmployeeForm addEmploy;
	
	public ManageEmployeeForm() {
		setVisible(true);
		setSize(340, 335);
		setLocation(225, 10);
		setClosable(true);
		
		title = new JLabel("Employee List");
		titlePnl = new JPanel();
		titlePnl.add(title);
		
		
		employeeList = new EmployeeHandler().getAll();
		String[] empNames = {"Employee ID", "Employee Salary", "Employee Status"};
		empDtm = new DefaultTableModel(empNames,employeeList.size());
		empTbl = new JTable(empDtm);
		
		empPane = new JScrollPane(empTbl);
		
		getRole();
		
		showEmployee();
		
		tblPnl = new JPanel(new GridLayout(2, 1));
		tblPnl.add(rolePane);
		tblPnl.add(empPane);
		
		addEmp = new JButton("Add");
		addEmp.addActionListener(this);
		
		fireEmp = new JButton("Fire");
		fireEmp.addActionListener(this);
		
		accReq = new JButton("Accept");
		accReq.addActionListener(this);
		
		if(User.isRoleManager() == false) {
			fireEmp.setEnabled(false);
			accReq.setEnabled(false);
		}
		
		btnPnl = new JPanel(new GridLayout(1, 3));
		btnPnl.add(addEmp);
		btnPnl.add(fireEmp);
		btnPnl.add(accReq);
		
		add(titlePnl, BorderLayout.NORTH);
		add(tblPnl, BorderLayout.CENTER);
		add(btnPnl, BorderLayout.SOUTH);
	}
	
	public void getRole() {
		roleList = new RoleHandler().getAll();
		
		String[] roleNames = {"Role ID", "Role Name"};
		
		roleDtm = new DefaultTableModel(roleNames, roleList.size());
		roleTbl = new JTable(roleDtm);
		rolePane = new JScrollPane(roleTbl);
		
		showRole();
	}

	public void refreshEmployeeTable() {
		empDtm.setRowCount(0);
		
		showEmployee();
	}
	
	public void showRole() {
		for (int i = 0; i < roleList.size(); i++) {
			String id = roleList.get(i).getId();
			String name = roleList.get(i).getName();
	
			roleTbl.setValueAt(id, i , 0);
			roleTbl.setValueAt(name, i , 1);
		}
	}
	
	public void showEmployee() {
		employeeList = new EmployeeHandler().getAll();
		int size = employeeList.size();
		empDtm.setRowCount(size);
		
		for (int i = 0; i < size; i++) {
			String id = employeeList.get(i).getId();
			int salary = employeeList.get(i).getSalary();
			String status = employeeList.get(i).getStatus();
	
			empTbl.setValueAt(id, i, 0);
			empTbl.setValueAt(salary, i, 1);
			empTbl.setValueAt(status, i, 2);
		}
	}
	
	
	public void addEmployee() {
		
		addEmploy = new AddEmployeeForm(this);
		
		refreshEmployeeTable();
	}
	
	public void fireEmployee() {
		int index = empTbl.getSelectedRow();
		if(index == -1) {
			JOptionPane.showMessageDialog(null, "Please Choose 1 Employee!");
			return;
		}
		if(employeeList.get(index).getStatus().compareTo("active") != 0) {
			JOptionPane.showMessageDialog(null, "Please Choose 1 Active Employee!");
			return;
		}
		new EmployeeHandler().firedEmployee(employeeList.get(index).getId());
		JOptionPane.showMessageDialog(null, "Employee Fired!");
		
		refreshEmployeeTable();
	}
	
	public void acceptReqs() {
		int index = empTbl.getSelectedRow();
		if(index == -1) {
			JOptionPane.showMessageDialog(null, "Please Choose 1 Employee!");
			return;
		}
		if(employeeList.get(index).getStatus().compareTo("pending") != 0) {
			JOptionPane.showMessageDialog(null, "Please Choose 1 Pending Employee!");
			return;
		}
		new EmployeeHandler().acceptEmployee(employeeList.get(index).getId());
		JOptionPane.showMessageDialog(null, "Employee Accepted");
		refreshEmployeeTable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == addEmp) {
			addEmployee();
		}
		else if(e.getSource() == fireEmp) {
			fireEmployee();
		}
		else if(e.getSource() == accReq) {
			acceptReqs();
		}
	}
}