package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.GenreHandler;
import model.*;


public class ManageGenreForm extends JInternalFrame implements ActionListener{
	
	List<Genre> genreList = new ArrayList<Genre>();
	GenreHandler genreHandler = new GenreHandler();
	
	DefaultTableModel tableModel;
	JTable table;
	
	JScrollPane sPane;
	JLabel title;
	JButton close;
	JButton create;
	JPanel titlePnl, btnPnl;
	JLabel lblType;
	JPanel jp;
	  
	public ManageGenreForm() {
		setSize(340, 335);
		setLocation(225, 10);
		setClosable(true);
		setVisible(true);
		
		title = new JLabel("Genre List");
		titlePnl = new JPanel();
		titlePnl.add(title);
		
		genreList = genreHandler.getAll();
		String[] genreName = {"Genre ID","Genre Type"};
		tableModel = new DefaultTableModel(genreName, genreList.size());
		table = new JTable(tableModel);
		showGenre();
	   
		close = new JButton("Back");
		create = new JButton("create");
		btnPnl = new JPanel(new FlowLayout());
		btnPnl.add(create);
		close.addActionListener(this);
		btnPnl.add(close);
		create.addActionListener(new ActionListener() {   
			@Override
			public void actionPerformed(ActionEvent e) {
				String type = JOptionPane.showInputDialog(null, "Insert Type");
				if(type != null ) {
					if( type.equals("") == false && genreHandler.getByType(type) == null) {
						
						HashMap<String,String> inputs = new HashMap<String,String>();
						inputs.put("type", type);
						genreHandler.insert(inputs);
						JOptionPane.showMessageDialog(null, "Insert Genre Success!!");
						refreshTable();				
						
					}else {
						JOptionPane.showMessageDialog(null, "Insert Genre Failed!!");						
					}
				}
			}
		 });

		
		jp = new JPanel(new GridLayout(2,2));
		
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(new JScrollPane(table), BorderLayout.CENTER);
		pnl.add(jp, BorderLayout.SOUTH);
		
		getContentPane().add(titlePnl, BorderLayout.NORTH);
		getContentPane().add(pnl, BorderLayout.CENTER);
		getContentPane().add(btnPnl, BorderLayout.SOUTH);

	}
	   
	public void refreshTable() {
		tableModel.setRowCount(0);
		showGenre();
	}
	
	public void showGenre() {
		genreList = genreHandler.getAll();
		int size = genreList.size();
		tableModel.setRowCount(size);
		
		for(int i = 0; i < size ; i++) {
			String id = genreList.get(i).getId();
			String type = genreList.get(i).getType();
			
			table.setValueAt(id,i,0);
			table.setValueAt(type, i, 1);
		}
	}
	  
	@Override
	public void actionPerformed(ActionEvent e) {
	   // TODO Auto-generated method stub
		if(e.getSource() == close) {
			setVisible(false);
		}
	}
	
}

