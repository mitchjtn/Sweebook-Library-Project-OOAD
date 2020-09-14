package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import controller.EmployeeHandler;
import controller.RoleHandler;
import controller.UserHandler;
import model.User;

public class AddEmployeeForm extends JDialog implements ActionListener {
	
	ManageEmployeeForm manEmpForm;
	
	JLabel title, lblRole, lblName, lblConfirm, lblConfirmP, lblUsername, lblPassword, lblGender, lblSalary;
	
	JComboBox<String> cbRole;
	
	JTextField txtName, txtUsername;
	
	JPasswordField txtPassword, txtConfirm;
	
	JRadioButton rbMale, rbFemale;
	
	JSpinner spnSalary;
	SpinnerModel snmSalary;
	
	JButton addEmployee;
	ManageEmployeeForm manageEmployeeF;
	public AddEmployeeForm(ManageEmployeeForm form) {
		this.manageEmployeeF = form;
		getContentPane().setLayout(null);
		setVisible(true);
		setLocation(500, 140);
		setSize(340, 450);
 
		title = new JLabel("Add New Employee");
		title.setBounds(88, 6, 200, 20);
		title.setFont(new Font("Times New Roman", Font.BOLD, 17));
		getContentPane().add(title);
 
		lblRole = new JLabel("Role :");
		lblRole.setBounds(20, 45, 100, 15);
		lblRole.setFont(new Font("Times New Roman", Font.BOLD, 15));
		getContentPane().add(lblRole);
 
		lblName = new JLabel("Name :");
		lblName.setBounds(20, 95, 100, 15);
		lblName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		getContentPane().add(lblName);
 
		lblUsername = new JLabel("Username :");
		lblUsername.setBounds(20, 145, 100, 15);
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 15));
		getContentPane().add(lblUsername);
 
		lblPassword = new JLabel("Password :");
		lblPassword.setBounds(20, 195, 100, 15);
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));
		getContentPane().add(lblPassword);
 
		lblConfirm = new JLabel("Confirm");
		lblConfirm.setBounds(20, 240, 100, 15);
		lblConfirm.setFont(new Font("Times New Roman", Font.BOLD, 15));
		getContentPane().add(lblConfirm);
		
		lblConfirmP = new JLabel("Password :");
		lblConfirmP.setBounds(20, 260, 100, 15);
		lblConfirmP.setFont(new Font("Times New Roman", Font.BOLD, 15));
		getContentPane().add(lblConfirmP);
 
		lblGender = new JLabel("Gender :");
		lblGender.setBounds(20, 295, 100, 15);
		lblGender.setFont(new Font("Times New Roman", Font.BOLD, 15));
		getContentPane().add(lblGender);
 
		lblSalary = new JLabel("Salary :");
		lblSalary.setBounds(20, 345, 100, 15);
		lblSalary.setFont(new Font("Times New Roman", Font.BOLD, 15));
		getContentPane().add(lblSalary);
 
		cbRole = new JComboBox<String>(new String[]{
				"purchasing", "administrator", "manager", "human capital"});
		cbRole.setBounds(110, 45, 200, 25);
		getContentPane().add(cbRole);
 
		txtName = new JTextField();
		txtName.setBounds(110, 95, 200, 25);
		getContentPane().add(txtName);
 
		txtUsername = new JTextField();
		txtUsername.setBounds(110, 145, 200, 25);
		getContentPane().add(txtUsername);
 
		txtPassword = new JPasswordField();
		txtPassword.setBounds(110, 195, 200, 25);
		getContentPane().add(txtPassword);
 
		txtConfirm = new JPasswordField();
		txtConfirm.setBounds(110, 245, 200, 25);
		getContentPane().add(txtConfirm);
 
		rbMale = new JRadioButton("Male");
		rbMale.setBounds(120, 295, 70, 25);
		getContentPane().add(rbMale);
		rbFemale = new JRadioButton("Female");
		rbFemale.setBounds(220, 295, 70, 25);
		getContentPane().add(rbFemale);
 
		ButtonGroup genderButton = new ButtonGroup();
		genderButton.add(rbMale);
		genderButton.add(rbFemale);
 
		snmSalary = new SpinnerNumberModel();
		spnSalary = new JSpinner(snmSalary);
		spnSalary.setBounds(110, 345, 200, 25);
		getContentPane().add(spnSalary);
 
		addEmployee = new JButton("Add");
		addEmployee.setBounds(110, 380, 90, 25);
		getContentPane().add(addEmployee);
		addEmployee.addActionListener(this);
	}
	
	public String getGender()
	{
	    if(rbMale.isSelected())
	    {
	        return "Male";
	    }
	    else if(rbFemale.isSelected())
	    {
	        return "Female";
	    }
	    else
	    {
	        return null;
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == addEmployee) {
			
			
			//Role
			
			String role = cbRole.getSelectedItem().toString();
			role = new RoleHandler().getByName(role).getId();
			//Name
			
			String name = txtName.getText();
			if(name.compareTo("") == 0) {
				JOptionPane.showMessageDialog(null, "Please input name!!");
				return;
			}
			
			//Username
			
			String username = txtUsername.getText();
			if(username.compareTo("") == 0) {
				JOptionPane.showMessageDialog(null, "Please input username!!");
				return;
			}
			
			//Password
			
			String password = new String(txtPassword.getPassword());
			String confirm = new String(txtConfirm.getPassword());
			
			if(password.compareTo("") == 0 || confirm.compareTo("") == 0) {
				JOptionPane.showMessageDialog(null, "Please input password!!");
				return;
			}
			
			if(password.equals(confirm) == false) {
				JOptionPane.showMessageDialog(null, "Password didn't match!!");
				return;
			}
			
			
			//Gender
			
			String gender = getGender();
			if(getGender() == null) {
				JOptionPane.showMessageDialog(null, "Please choose gender!!");
				return;
			}
			
			Integer salary = (Integer)(snmSalary.getValue());
			if(salary < 10000) {
				JOptionPane.showMessageDialog(null, "Salary must be greater than 10000!!");
				return;
			}
			
			//create hashmap to store employee data
			HashMap<String, String> inputs = new HashMap<String, String>();
			inputs.put("name", name);
			inputs.put("roleId", role);
			inputs.put("username", username);
			inputs.put("gender", gender);
			inputs.put("password", password);
			inputs.put("salary", salary.toString());
				
			new EmployeeHandler().createEmployee(inputs);
			manageEmployeeF.refreshEmployeeTable();
			dispose();
			
		}
	}

}