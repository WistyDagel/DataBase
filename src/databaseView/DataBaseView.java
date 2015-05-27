package databaseView;

import javax.swing.JOptionPane;

import databaseController.DataBaseAppController;

public class DataBaseView
{
	private DataBaseAppController baseController; 
	
	public DataBaseView(DataBaseAppController baseController)
	{
		this.baseController = baseController; 
	}
	
	/**
	 * Allows item on screen display information easier to be read and to see better.
	 * @param input
	 */
	public void displayInformation(String input)
	{
		JOptionPane.showMessageDialog(null, input);
	}

}
