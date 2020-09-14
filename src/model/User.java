package model;
import util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import controller.*;


public class User {
	private static String id;
	private static String roleId;
	private String name;
	private String username;
	private String password;
	private String gender;
	
	final String insertString = "INSERT INTO users (id,role_id, name, username, password, gender) VALUES (?, ?, ?, ?, SHA1(?), ?);";
	final String findIdString = "SELECT * FROM users WHERE username=? ;";
	
	//constructor
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String id, String roleId, String name, String username, String password, String gender) {
		super();
		User.id = id;
		User.roleId = roleId;
		this.name = name;
		this.username = username;
		this.password = password;
		this.gender = gender;
	}
	
	//getter setter
	
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		User.id = id;
	}
	public static String getRoleId() {
		return roleId;
	}
	public static void setRoleId(String roleId) {
		User.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	//function
	
	public static boolean isRoleMember() {
		String membershipId = new RoleHandler().getByName("Membership").getId();
		if(membershipId.equals(User.getRoleId()) == true) 
			return true;
		return false;
	}
	
	public static boolean isRoleManager() {
		String managerId = new RoleHandler().getByName("Manager").getId();
		if(managerId.equals(User.getRoleId()) == true) 
			return true;
		return false;
	}
	
	public static boolean isRoleAdministrator() {
		String adminId = new RoleHandler().getByName("Administrator").getId();
		if(adminId.equals(User.getRoleId()) == true) 
			return true;
		return false;
	}
	
	public User insert() {
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		int inserted = 1;
		try {
			statement = connection.prepareStatement(insertString);
			statement.setString(1, id);
			statement.setString(2, roleId);
			statement.setString(3, this.name);
			statement.setString(4, this.username);
			statement.setString(5, this.password);
			statement.setString(6, this.gender);
			
			inserted = statement.executeUpdate();
			
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		if(inserted == 0) return null;
		return this;
	}
	
	
	public User getByUsername(String username) {
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		User user = new User();
		
		try {
			statement = connection.prepareStatement(findIdString);
			
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery(); 
			while(rs.next()) {
				User.setId(rs.getString(1));
				User.setRoleId(rs.getString(2));
				user.setName(rs.getString(3));
				user.setUsername(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setGender(rs.getString(6));
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				statement.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return user;
	}
	
	public static String encryptPassword(String password) {
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		String result = "";
		String encryptString = "SELECT SHA1(?) AS 'password' ";
		try {
			statement = connection.prepareStatement(encryptString);
			
			statement.setString(1, password);
			ResultSet rs = statement.executeQuery(); 
			while(rs.next()) {
				result = rs.getString(1);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				statement.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}
}
