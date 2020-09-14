package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import main.FirstMenuView;

public class HumanCapitalMainView extends JFrame implements ActionListener {
	
	ImageIcon image = new ImageIcon("aaa.png");
	
	JLabel photo;
	
	JButton viewEmployee, logOut;
	
	ManageEmployeeForm manEmpForm;
	
	public HumanCapitalMainView() {
		// TODO Auto-generated constructor stub
		getContentPane().setLayout(null);
		setTitle("Human Capital Main Menu");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Title 1
		 
		JLabel title1 = new JLabel("Human Capital");
		title1.setBounds(20, 22, 434, 54);
		title1.setFont(new Font("Gabriola", Font.PLAIN, 34));
		getContentPane().add(title1);
 
		//Title 2
 
		JLabel title2 = new JLabel("Menu");
		title2.setBounds(66, 48, 434, 54);
		title2.setFont(new Font("Gabriola", Font.PLAIN, 34));
		getContentPane().add(title2);
		
		//View Employee
		
		viewEmployee = new JButton("View Employee");
		viewEmployee.setFont(new Font("Tahoma", Font.PLAIN, 12));
		viewEmployee.setBounds(20, 120, 130, 41);
		getContentPane().add(viewEmployee);
		viewEmployee.addActionListener(this);
		
		//Log Out
		 
		logOut = new JButton("Log Out");
		logOut.setFont(new Font("Tahoma", Font.PLAIN, 12));
		logOut.setBounds(20, 240, 130, 41);
		getContentPane().add(logOut);
		logOut.addActionListener(this);
		
		//Photo
		
		photo = new JLabel("New label");
		photo.setBounds(245, 65, 284, 240);
		getContentPane().add(photo);
		photo.setIcon(image);
	}
	
	public void showManageEmployeeForm() {
		if(manEmpForm == null || manEmpForm.isVisible() == false) {
			manEmpForm = new ManageEmployeeForm();
			add(manEmpForm).setSize(340, 335);
			remove(photo);
		}else {
			manEmpForm.dispose();
			getContentPane().add(photo);
		}
	}

	public void logout() {
		 this.dispose();
		 FirstMenuView fmv = new FirstMenuView();
		 fmv.setVisible(true);
		 
	 }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == viewEmployee) {
			showManageEmployeeForm();
		}else if(e.getSource() == logOut) {
			logout();
		}
	}
}