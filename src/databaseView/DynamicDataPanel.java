package databaseView;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import databaseController.DataBaseAppController;

public class DynamicDataPanel extends JPanel
{
	private DataBaseAppController baseController;
	private JButton queryButton;
	private SpringLayout baseLayout;
	private String table;
	private ArrayList<JTextField> fieldList;
	private Component columnField;
	private Component columnLabel;
	private String fields;
	
	public DynamicDataPanel(DataBaseAppController baseController, String table)
	{
		this.baseController = baseController;
		this.table = table;
		queryButton = new JButton("Submit query");
		baseLayout = new SpringLayout();
		

		fieldList = new ArrayList<JTextField>();
		
		setupPanel(table);
		setupLayout();
		setupListeners();
	}

	/**
	 * Where the baseLayout is located in the GUI of the project 
	 */
	
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.NORTH, queryButton, 70, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, queryButton, 49, SpringLayout.WEST, this);
	}
	
	/**
	 * Like a soccer field you find a place for a goal and then you just yell goal 
	 * until you pass out drunk at the Stadium. Oh and by the way it actually initializes 
	 * the spot of a value and displays it within its field
	 * @return
	 */
	
	private String getFields()
	{
		String fields = "(";
		
		for(int spot = 0; spot < fieldList.size(); spot++)
		{
			fields += "`" + fieldList.get(spot).getName() + "`";
			if(spot == fieldList.size()-1)
			{
				fields += ")";
			}
			else
			{
				fields += ", ";
			}
		}
		
		return fields;
	}
	
	/**
	 * Gets the values from the fieldList and displays them in the table in the project itself
	 * @return
	 */
	
	private String getValues()
	{
		String values = "(";
		for(int spot = 0; spot < fieldList.size(); spot++)
		{
			values += "'" + fieldList.get(spot).getText() + "'";
			if(spot == fieldList.size()-1)
			{
				values += ");";
			}
			else
			{
				values += ", ";
			}
		}
		
		return values;
	}

	/**
	 * This is what establishes the panel itself and what sets up the design of the project
	 * @param selectedTable
	 */
	
	private void setupPanel(String selectedTable)
	{
		this.setLayout(baseLayout);
		this.add(queryButton);
		int spacing = 50;
		
		String [] columns = baseController.getDataController().getDatabaseColumnNames(selectedTable);
		
		for(int spot = 0; spot < columns.length; spot++)
		{
			if(!columns[spot].equalsIgnoreCase("id"))
			{	
				JLabel columnedLabel = new JLabel(columns[spot]);
				JTextField columnFIeld = new JTextField(20);
			
				this.add(columnLabel);
				this.add(columnField);
				columnField.setName(columns[spot]);
				
				baseLayout.putConstraint(SpringLayout.NORTH, queryButton, 122, SpringLayout.NORTH, this);
				baseLayout.putConstraint(SpringLayout.WEST, queryButton, 69, SpringLayout.WEST, columnLabel);
				
				spacing += 50;
			}
			
			
		}
		
	}
	
	/**
	 * This is what actually allows anything to happen in the GUI itself, checks for the button being touched 
	 * and then it works!
	 */
	
	private void setupListeners()
	{
		final String query = "INSERT INTO" + "`" + table + "` " + getFields() + "VALUES" + getValues();
		
		queryButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				baseController.getDataController().submitUpdateQuery(query);
			}
		});
	}
	
}
