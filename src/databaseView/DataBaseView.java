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
	
	public void displayInformation(String input)
	{
		JOptionPane.showMessageDialog(null, input);
	}

}
