package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.CreateMembershipForm;
import view.LogInView;

public class FirstMenuView extends JFrame implements ActionListener {
	
	JLabel lblTitle;
	
	JButton btnLogIn, btnCreateMember;
	
	LogInView logIn;
	
	CreateMembershipForm createMember;

	public FirstMenuView() {
		setTitle("Swee Library");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	  
		  
		lblTitle = new JLabel("SweeBook Library", JLabel.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 65));
		lblTitle.setPreferredSize(new Dimension(200, 150));
		 
		btnLogIn = new JButton("Log In");
		btnLogIn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnLogIn.addActionListener(this);
		btnCreateMember = new JButton("Create Membership");
		btnCreateMember.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnCreateMember.addActionListener(this);
		  
		JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 15, 15));
		buttonPanel.add(btnLogIn);
		buttonPanel.add(btnCreateMember);
		buttonPanel.setBorder(new EmptyBorder(50, 150, 50, 150));

		JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
		mainPanel.add(lblTitle, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		  
		add(mainPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnLogIn) {
			this.dispose();
			logIn = new LogInView();
			logIn.setVisible(true);
		}
		else if(e.getSource() == btnCreateMember) {
			this.dispose();
			createMember = new CreateMembershipForm();
			createMember.setVisible(true);
		}
	}
}