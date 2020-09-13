package model;

import util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Genre {
	private String id;
	private String type;
	
	final String selectString = "SELECT * FROM genres ORDER BY id DESC;";
	final String insertString = "INSERT INTO genres (id,type) VALUES (?, ?);";
	final String findbyTypeString = "SELECT * FROM genres WHERE type=? ;";
	final String findbyIdString = "SELECT * FROM genres WHERE id=? ;";
	
	//constructor 
	
	public Genre() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Genre(String id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	//getter setter 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	//function 
	
	public List<Genre> all(){
		Connection connection = Connect.connect();
		List<Genre> genres = new ArrayList<Genre>();
		
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(selectString);
			while(rs.next()) {
				Genre genre = new Genre();
				genre.setId(rs.getString(1));
				genre.setType(rs.getString(2));
				
				genres.add(genre);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return genres;
	}
	
	public Genre insert() {
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(insertString);
			statement.setString(1, this.id);
			statement.setString(2, this.type);
			
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
	
	public Genre getByType(String type) {
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		Genre genre = new Genre();
		try {
			statement = connection.prepareStatement(findbyTypeString);
			statement.setString(1, type);
			ResultSet rs = statement.executeQuery(); 
			
			if(rs.next()) {
				genre.setId(rs.getString("id"));
				genre.setType(rs.getString("type"));				
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
		
		return genre;
	}
	
	public Genre find(String id) {
		Connection connection = Connect.connect();
		PreparedStatement statement = null;
		Genre genre = new Genre();
		try {
			statement = connection.prepareStatement(findbyIdString);
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery(); 
			
			if(rs.next()) {
				genre.setId(rs.getString("id"));
				genre.setType(rs.getString("type"));				
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
		
		return genre;
	}
}
