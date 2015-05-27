package databaseView;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import databaseController.DataBaseAppController;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

/**
 * This is the method call on the DataBasePanel it will extend its parent class 
 * through the JPanel
 * @author cdaz6661
 *
 */
public class DataBasePanel extends JPanel
{
	private DataBaseAppController baseController;
	private JTable comicHeroesTable;
	private SpringLayout baseLayout;
	private JLabel titleLabel;
	private JScrollPane tablePane;
	private JTextArea displayArea;
	private Component displayPane;
	private JTable resultsTable;
	private JButton queryButton;
	private Component samplePassword;
	
	/**
	 * This is the setup for the PAnel to pass through the Controller and it checks 
	 * it through the baseController
	 * @param baseController2
	 */
	public DataBasePanel(DataBaseAppController baseController)
	{
		setBackground(Color.GREEN);
		setForeground(Color.BLACK);
		this.baseController = baseController;
		baseLayout = new SpringLayout();
		queryButton = new JButton("Click here to test the query");
		baseLayout.putConstraint(SpringLayout.NORTH, queryButton, 83, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, queryButton, 116, SpringLayout.WEST, this);
		displayArea= new JTextArea();
		samplePassword = new JTextField();
		displayPane = new JScrollPane();
		titleLabel = new JLabel();
		
		
		/**
		 * This is the setupTable, setupPanel, setupLayout and setupListeners.
		 * This is the structure for all of the panel
		 */
		setupDisplayPane();
		setupPanel();
		setupLayout();
		setupListeners();
			
	}
	
	/**
	 * Allows item on screen display easier to be read and easier to see.
	 */

	private void setupDisplayPane()
	{
		displayArea.setWrapStyleWord(true);
		displayArea.setLineWrap(true);
		displayArea.setEditable(false);
		displayArea.setBackground(Color.GREEN);
	}
	
	/**
	 * setupTable is When your mom tells you its dinner time and then you come down stairs all happy wanting to eat those
	 * sloppy joe's and then you fail to realize that her dinner time and your dinner time are completely opposite 
	 * ideas and she makes you set up the table before eating 
	 */
	
	private void setupTable()
	{
		DefaultTableModel basicData = new DefaultTableModel(baseController.getDataController().testResults(), baseController.getDataController().getMetaDataTitles());
		resultsTable = new JTable(basicData);
		displayPane = new JScrollPane(resultsTable);
	}

	/**
	 * The setupPanel is what initializes all the GUI components within the panel 
	 * and runs them in the interface
	 */
	private void setupPanel()
	{
		this.setBackground(Color.CYAN);
		this.setSize(1000, 1000);
		this.setLayout(baseLayout);
		this.add(displayPane);
		this.add(queryButton);
		
		this.add(samplePassword);
		
		 
	}
	
	/**
	 * The setupListeners is the main GUI component and reacts to the User input.
	 */
	private void setupListeners()
	{
		queryButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String[][] temp = baseController.getDataController().getMetaDataTitles();
				for(String[] current : temp)
				{
				displayArea.setText(displayArea.getText() + "Rows Affected:" + current + "\n");
				}
				
				JFrame popFrame = new JFrame();
				popFrame.setSize(250, 250);
				popFrame.setContentPane(new DynamicDataPanel(baseController, "books"));
				popFrame.setVisible(true);
			}
	});	
		
	}

	/**
	 * This is the grid layout for the panel and it assigns every value set inside the panel 
	 */
	private void setupLayout()
	{
	
		displayArea = new JTextArea(10, 30);
		displayArea.setBackground(Color.GREEN);
		baseLayout.putConstraint(SpringLayout.NORTH, displayArea, 40, SpringLayout.SOUTH, queryButton);
		baseLayout.putConstraint(SpringLayout.WEST, displayArea, 21, SpringLayout.WEST, this);
		add(displayArea);
	}
}
