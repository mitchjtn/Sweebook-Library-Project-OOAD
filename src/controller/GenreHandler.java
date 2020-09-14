package controller;

import view.*;
import model.*;
import java.util.*;
import javax.swing.*;

public class GenreHandler {
	
	/**
	 * This method is used to show ManageGenreForm (view)
	 * 
	 * @return JinternalFrame
	 * @see ManageGenreForm
	 */
	public JInternalFrame showManageGenreForm() {
		return new ManageGenreForm();
	}
	
	
	/**
	 * This method is used to get all List Genre from database. 
	 * It calls method all() from Genre (model)
	 * @return a list of genre
	 * @see Genre
	 */
	public List<Genre> getAll(){
		return new Genre().all();
	}
	
	/**
	 * This method will generate an Id for Genre.
	 * It will create Genre (model) then call method insert from model
	 *  to insert the data to database
	 * @param inputs hashmap(string, string) that has a key "type"
	 * @return Genre that already created
	 * @see Genre
	 */
	public Genre insert(HashMap<String,String> inputs) {
		String id =  UUID.randomUUID().toString();
		Genre newGenre = new Genre(id, inputs.get("type"));
		return newGenre.insert();
	}
	
	/**
	 * This method will call method getByType from  Genre (model) and search a type from database
	 * @return genre if found <br>
	 * 		   null if not found
	 * @param type (String)
	 * @see Genre
	 */
	public Genre getByType(String type) {
		return new Genre().getByType(type);
	}
}


