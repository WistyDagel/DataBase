package databaseController;

import databaseController.DataBaseAppController;

public class DataBaseRunner
{

	/**
	 * Allows the database name to be Established as new database variable
	 * @param args
	 */
	public static void main(String[]args)
	{
		DataBaseAppController appController = new DataBaseAppController();
		appController.start();
	}
}
