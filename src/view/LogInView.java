package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.User;
import controller.*;
import main.FirstMenuView;


public class LogInView extends JFrame implements ActionListener {
	
	JLabel title, lblUsername, lblPassword;
	
	JTextField txtUsername;
	
	JPasswordField txtPassword;
	
	JButton logInButton, backButton;
	
	String username, password;
	
	User user = new User();
	
	public LogInView() {
		
		getContentPane().setLayout(null);
		setTitle("Swee Library");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		title = new JLabel("Please enter your username and password to continue", JLabel.CENTER);
		title.setBounds(48, 20, 500, 40);
		title.setFont(new Font("Times New Roman", Font.BOLD, 20));
		getContentPane().add(title);
		
		lblUsername = new JLabel("Username :");
		lblUsername.setBounds(110, 120, 100, 40);
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 20));
		getContentPane().add(lblUsername);
		
		lblPassword = new JLabel("Password :");
		lblPassword.setBounds(110, 200, 100, 40);
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 20));
		getContentPane().add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(250, 120, 240, 40);
		getContentPane().add(txtUsername);
		txtPassword = new JPasswordField();
		txtPassword.setBounds(250, 200, 240, 40);
		getContentPane().add(txtPassword);
		
		username = txtUsername.getText();
		password = String.valueOf(txtPassword.getPassword());
		
		
		backButton = new JButton("Back");
		backButton.setBounds(160, 300, 100, 40);
		getContentPane().add(backButton);
		backButton.addActionListener(this);
		
		logInButton = new JButton("Log In");
		logInButton.setBounds(320, 300, 100, 40);
		getContentPane().add(logInButton);
		logInButton.addActionListener(this);
	}
	
	public void goBack() {
		this.dispose();
		FirstMenuView fmv = new FirstMenuView();
		fmv.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == logInButton) {
			
			username = txtUsername.getText();
			password = String.valueOf(txtPassword.getPassword());
			password = User.encryptPassword(password);
			UserHandler userH = new UserHandler();
			user = userH.getByUsername(username); 
			
			if(user.getUsername() == null) {
				JOptionPane.showMessageDialog(null, "Invalid Username!");
				return;
			}else if(user.getPassword().equals(password) == false) {
				JOptionPane.showMessageDialog(null, "Invalid Password!");
				return;
			}
			
			this.dispose();
			
			RoleHandler roleH = new RoleHandler();
			
			if(User.getRoleId().equals(roleH.getByName("Manager").getId()) == true) {
				ManagerMainView managerView = new ManagerMainView();
				managerView.setVisible(true);
			}
			else if(User.getRoleId().equals(roleH.getByName("Human Capital").getId())== true) {
				HumanCapitalMainView humanView = new HumanCapitalMainView();
				humanView.setVisible(true);
			}
			else if(User.getRoleId().equals(roleH.getByName( "Administrator").getId()) == true) {
				AdministratorMainView adminView = new AdministratorMainView();
				adminView.setVisible(true);
			}
			else if(User.getRoleId().equals(roleH.getByName( "Membership").getId()) == true) {
				MemberMainView memberView = new MemberMainView();
				memberView.setVisible(true);
			}
			else if(User.getRoleId().equals(roleH.getByName( "Purchasing").getId()) == true) {
				PurchasingMainView purchaseView = new PurchasingMainView();
				purchaseView.setVisible(true);
			}
		}
		else if(e.getSource() == backButton) {
			goBack();
		}
	}
}
