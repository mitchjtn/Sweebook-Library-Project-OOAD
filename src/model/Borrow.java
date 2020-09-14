package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.*;
import java.util.*;

public class Borrow {
	private String id;
	private String memberId;
	private String status;
	private String borrowTimestamp;
	
	final String insertString = "INSERT INTO borrows (id,member_id, status, borrow_timestamp) VALUES (?, ?, ?, ?);";
    final String updateString = "UPDATE borrows SET status=? WHERE id=? ;";
    final String findIdString = "SELECT * FROM borrows WHERE id=? ;";
    final String countBookString = "SELECT COUNT(*) AS 'total' " + 
    		"FROM borrow_items JOIN borrows " +
    		"ON borrow_items.borrow_id = borrows.id " + 
    		"WHERE borrows.member_id = ? AND borrow_items.return_timestamp IS NULL";
    final String isStillBorrowString =
    		"SELECT COUNT(*) AS 'total' " + 
    		"FROM borrow_items JOIN borrows " +
    		"ON borrow_items.borrow_id = borrows.id " + 
    		"WHERE borrows.member_id = ? AND borrow_items.book_id = ? AND borrow_items.return_timestamp IS NULL";
    
	//constructor
	public Borrow() {
		super();
	}
	
	public Borrow(String id, String memberId, String status, String borrowTimestamp) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.status = status;
		this.borrowTimestamp = borrowTimestamp;
	}
	
	//getter setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBorrowTimestamp() {
		return borrowTimestamp;
	}
	public void setBorrowTimestamp(String borrowTimestamp) {
		this.borrowTimestamp = borrowTimestamp;
	}
	
	
	//function
	
	public Borrow find(String id) {
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		Borrow borrow = new Borrow();
		try {
			statement = connection.prepareStatement(findIdString);
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery(); 
			
			if(rs.next()) {				
				borrow.setId(rs.getString(1));
				borrow.setMemberId(rs.getString(2));
				borrow.setStatus(rs.getString(3));
				borrow.setBorrowTimestamp(rs.getString(4));
			}
				
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return borrow;
	}
	
	public Borrow insert() {
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(insertString);
			statement.setString(1, this.id);
			statement.setString(2, this.memberId);
			statement.setString(3, this.status);
			statement.setString(4, this.borrowTimestamp);
			
			statement.executeUpdate();
				
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return this;
	}
	
	public Borrow update() {
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(updateString);
			statement.setString(1, this.status);
			statement.setString(2, this.id);

			
			statement.executeUpdate();
				
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return this;
	}
	
	public boolean isBookStillBorrowing(String userId, String bookId) {
		
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		
		int count = 0;
		boolean result = false;
		try {
			statement = connection.prepareStatement(isStillBorrowString);
			statement.setString(1, userId);
			statement.setString(2, bookId);
			ResultSet rs = statement.executeQuery(); 
			
			while(rs.next()) {
				count = rs.getInt("total");
			}
			
			if(count > 0)
				result = true;
				
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	public int getCountBookStillBorrowing(String userId) {
		
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		
		int count = 0;
		
		try {
			statement = connection.prepareStatement(countBookString);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery(); 
			
			while(rs.next()) {
				count = rs.getInt("total");
			}
				
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return count;
	}
	
	public List<Borrow> getPendingStatus(boolean isOnlyCurrentMember) {
		String query = "SELECT * FROM `borrows` "
				+ "WHERE `status` = 'pending' ";
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		
		List<Borrow> borrows = new ArrayList<Borrow>();
		try {
			if(isOnlyCurrentMember) {
				query += "AND member_id = ?";
				statement = connection.prepareStatement(query);
				statement.setString(1, User.getId());
			}else {
				statement = connection.prepareStatement(query);
			}
			
			ResultSet rs = statement.executeQuery(); 
			
			while(rs.next()) {
				Borrow borrow = new Borrow();
				borrow.setId(rs.getString(1));
				borrow.setMemberId(rs.getString(2));
				borrow.setStatus(rs.getString(3));
				borrow.setBorrowTimestamp(rs.getTimestamp(4).toString());
				
				borrows.add(borrow);
			}
				
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return borrows;
	}
	
	public List<Borrow> getAcceptStatus(Date date, boolean isOnlyCurrentMember){
		String query = "SELECT * FROM `borrows` "
				+ "WHERE MONTH(borrow_timestamp) = ? AND YEAR(borrow_timestamp) = ? AND `status` = 'accept' ";
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		
		List<Borrow> borrows = new ArrayList<Borrow>();
		try {
			int year = date.getYear();
			int month = date.getMonth();
			
			if(isOnlyCurrentMember) {
				
				query += "AND member_id = ?";
				
				statement = connection.prepareStatement(query);
				statement.setInt(1, month);
				statement.setInt(2, year);
				statement.setString(3, User.getId());
			}else {
				statement = connection.prepareStatement(query);
				statement.setInt(1, month);
				statement.setInt(2, year);
				
			}
			ResultSet rs = statement.executeQuery(); 
			
			while(rs.next()) {
				Borrow borrow = new Borrow();
				borrow.setId(rs.getString(1));
				borrow.setMemberId(rs.getString(2));
				borrow.setStatus(rs.getString(3));
				borrow.setBorrowTimestamp(rs.getTimestamp(4).toString());
				
				borrows.add(borrow);
			}
				
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return borrows;
	}
}
