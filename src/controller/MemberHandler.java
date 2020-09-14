package controller;

import javax.swing.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import model.*;
import view.CreateMembershipForm;

public class MemberHandler {
	public JFrame showCreateMembershipForm() {
		return new CreateMembershipForm();
	}
	
	public JInternalFrame showViewMembershipForm() {
		return showViewMembershipForm();
	}
	
	public List<Member> getAll(){
		return new Member().all();
	}
	
	public Member insert(HashMap<String, String> inputs) {
		Member member = new Member();
		member.setId(inputs.get("id"));
		member.setAddress(inputs.get("address"));
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		String memberSince = dtf.format(now).toString();
		member.setMemberSince(memberSince);
		
		return member.insert();
	}
	
	public Member createMembership(HashMap<String, String> inputs) {
		//insert ke member
		String id = UUID.randomUUID().toString();
		String roleId =  new RoleHandler().getByName("Membership").getId();
		inputs.put("id", id);
		inputs.put("roleId", roleId);
		
		//insert ke user
		if(new UserHandler().insert(inputs) == null) {
			JOptionPane.showMessageDialog(null, "Create Membership Failed");
			return null;
		}
		
		Member member = insert(inputs);
		JOptionPane.showMessageDialog(null, "Create Membership Success!!");
		return member;
		
	}
}
