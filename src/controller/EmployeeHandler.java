package controller;

import java.util.*;
import javax.swing.*;
import model.*;
import view.*;

public class EmployeeHandler {
	public JInternalFrame showManageEmployeeForm() {
		return new ManageEmployeeForm();
	}
	
	public List<Employee> getAll() {
		return new Employee().all();
	}
	
	public Employee findById(String id) {
		return new Employee().find(id);
	}
	
	public Employee insert(HashMap<String, String> inputs) {
		//create employee
		String id = UUID.randomUUID().toString();
		inputs.put("id", id);
		Employee employee = new Employee(id,Integer.parseInt(inputs.get("salary")),inputs.get("status"));
		
		String userId = User.getId();
		String userRole = User.getRoleId();
		
		//create user
		if(new UserHandler().insert(inputs) == null) {
			JOptionPane.showMessageDialog(null, "Create User failed");
			return null;
		}
		
		
		User.setId(userId);
		User.setRoleId(userRole);
		return employee.insert();
		
	}
	
	public Employee update(HashMap<String, String> inputs) {
		Employee employee = new Employee(inputs.get("id"),Integer.parseInt(inputs.get("salary")),inputs.get("status") );
		
		return employee.update();
	}
	
	public Employee createWithPendingStatus(HashMap<String, String> inputs ) {
		inputs.put("status", "pending");
		return insert(inputs);
	}
	
	public Employee createWithActiveStatus(HashMap<String, String> inputs ) {
		inputs.put("status", "active");
		return insert(inputs);
	}
	
	public Employee createEmployee(HashMap<String,String> inputs) {
		if(User.isRoleManager()) {
			return createWithActiveStatus(inputs);
		}else {
			return createWithPendingStatus(inputs);
		}
	}
	
	public Employee firedEmployee(String id) {
		Employee employee = new Employee().find(id);
		employee.setStatus("fired");
		
		HashMap<String, String> inputs = new HashMap<String,String>();
		inputs.put("id", employee.getId());
		inputs.put("salary", Integer.toString(employee.getSalary()) );
		inputs.put("status", employee.getStatus());
		
		return update(inputs);
	}
	
	public Employee acceptEmployee(String id) {
		Employee employee = new Employee().find(id);
		employee.setStatus("active");
		
		HashMap<String, String> inputs = new HashMap<String,String>();
		inputs.put("id", employee.getId());
		inputs.put("salary", Integer.toString(employee.getSalary()) );
		inputs.put("status", employee.getStatus());
		
		return update(inputs);
	}
	
}
