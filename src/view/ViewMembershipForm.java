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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
 
import controller.MemberHandler;
import model.Member;
 
public class ViewMembershipForm extends JInternalFrame {
 
	List<Member> memberList = new ArrayList<Member>();
 
	TableModel dtm;
	JTable tbl;
	JScrollPane sPane;
 
	JLabel title;
 
	JButton close;
 
	JPanel titlePnl;
 
	public ViewMembershipForm() {
		setSize(700, 400);
		setLocation(225, 10);
		setVisible(true);
		setClosable(true);
 
		title = new JLabel("Member List");
		titlePnl = new JPanel();
		titlePnl.add(title);
 
		getMember();
 
		String[] names = {"Member ID", "Member Address", "Member Since"};
 
		dtm = new DefaultTableModel(names, memberList.size());
		tbl = new JTable(dtm);
		sPane = new JScrollPane(tbl);
 
		showMember();
 
		add(titlePnl, BorderLayout.NORTH);
		add(sPane, BorderLayout.CENTER);
	}
 
	public void getMember() {
		memberList = new MemberHandler().getAll();
	}
 
	public void showMember() {
		
		for (int i = 0; i < memberList.size(); i++) {
			
			String id = memberList.get(i).getId();
			String address = memberList.get(i).getAddress();
			String memberSince = memberList.get(i).getMemberSince();
			
			dtm.setValueAt(id, i , 0);
			dtm.setValueAt(address, i , 1);
			dtm.setValueAt(memberSince, i , 2);
		}
	}
}