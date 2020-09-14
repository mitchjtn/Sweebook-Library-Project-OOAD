package view;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import controller.MemberHandler;
import main.FirstMenuView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.*; 

public class CreateMembershipForm extends JFrame implements ActionListener{
	private JTextField txtName;
	private JTextField txtUserName;
	private JTextArea txtAddress;
	private JPasswordField txtPassword;
	private JPasswordField txtConfirmPassword;
	
	MemberHandler member = new MemberHandler();
	
	JButton backButton, create;
	ButtonGroup group;
	
	public CreateMembershipForm() {
		setTitle("Register Form");
		setSize(600,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//judul
		JLabel title = new JLabel("                            Create MemberShip");
		title.setFont(new Font("Gabriola", Font.PLAIN, 37));
		
		//name
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName = new JTextField();
		
		//username
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUserName = new JTextField();
		
		//gender
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		//grouping
		group = new ButtonGroup();
		JRadioButton rbMale = new JRadioButton("Male");
		rbMale.setActionCommand("Male");
		JRadioButton rbFemale = new JRadioButton("Female");
		rbFemale.setActionCommand("Female");
		group.add(rbFemale);
		group.add(rbMale);
		rbMale.setSelected(true);
		
		//address
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(10, 203, 60, 14);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		//scroll
		txtAddress = new JTextArea();
		JScrollPane spAddress = new JScrollPane(txtAddress);
		
		//password
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPassword = new JPasswordField();
		
		//confirm password
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtConfirmPassword = new JPasswordField();
		
		//back
		
		backButton = new JButton("Back");
		backButton.setBounds(160, 300, 100, 40);
		getContentPane().add(backButton);
		backButton.addActionListener(this);
		
		//create
		create = new JButton("create");
		create.setFont(new Font("Tahoma", Font.PLAIN, 15));
		create.setBounds(320, 300, 100, 40);
		getContentPane().add(create);
		create.addActionListener(this);
		
		JPanel pnlGender = new JPanel(new GridLayout(1,2));
		pnlGender.add(rbMale);
		pnlGender.add(rbFemale);
		
		JPanel pnlForm = new JPanel(new GridLayout(8,2,5,5));
		pnlForm.add(lblName);
		pnlForm.add(txtName);
		pnlForm.add(lblUserName);
		pnlForm.add(txtUserName);
		pnlForm.add(lblPassword);
		pnlForm.add(txtPassword);
		pnlForm.add(lblConfirmPassword);
		pnlForm.add(txtConfirmPassword);
		pnlForm.add(lblGender);
		pnlForm.add(pnlGender);
		pnlForm.add(lblAddress);
		pnlForm.add(spAddress);
		
		JPanel pnlWithAgree = new JPanel(new BorderLayout());
		pnlWithAgree.add(pnlForm,BorderLayout.CENTER);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(title, BorderLayout.NORTH);
		panel.add(pnlWithAgree, BorderLayout.CENTER);
		panel.add(pnlForm, BorderLayout.CENTER);
		add(panel);
	}

	public void goBack() {
		this.dispose();
		FirstMenuView fmv = new FirstMenuView();
		fmv.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == backButton) {
			goBack();
		}else if(e.getSource() == create) {

			String name = txtName.getText();
			String userName = txtUserName.getText();
			
			String gender = group.getSelection().getActionCommand();
			
			String address = txtAddress.getText();
			String pass = txtPassword.getText();
			String confPass = txtConfirmPassword.getText();
			
			if(name.length()<1 || userName.length()<1 || address.length()<1
					|| pass.length()<1 || confPass.length()<1
					|| gender == null) {
				JOptionPane.showMessageDialog(null, "please complete data");
				return;
			}
			else if(!(pass.equals(confPass))) {
				JOptionPane.showMessageDialog(null, "Please input the same password");
				return;
			}
			HashMap<String,String> inputs = new HashMap<String,String>();
			inputs.put("name", name);
			inputs.put("username", userName);
			inputs.put("gender", gender);
			inputs.put("address", address);
			inputs.put("password", pass);
			member.createMembership(inputs);
			
			goBack();
		}
	}
}