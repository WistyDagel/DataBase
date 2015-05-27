package databaseController;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import databaseModel.QueryInfo;
import databaseView.DataBaseFrame;

public class DataBaseAppController
{
	private DataBaseFrame appFrame;
	private DataBaseController dataController;
	private ArrayList<QueryInfo> queryList;
	
	
	public DataBaseAppController()
	{
		dataController = new DataBaseController(this);
		queryList = new ArrayList<QueryInfo>();
		appFrame = new DataBaseFrame(this);
	}
	
	public DataBaseFrame getAppFrame()
	{
		return appFrame;
	}
	
	public DataBaseController getDataController()
	{
		return dataController;
		
	}
	
	public ArrayList<QueryInfo> getQueryList()
	{
		return queryList;
	}
	
	public void start()
	{
		
		
	}
	
	/**
	 * It will load how long it takes for the information to be actually displayed from the mySQL 
	 * to the Database project
	 */
	public void loadTimingInformation()
	{
		try
		{
			File loadFile = new File("asdasda.sava");
			if(loadFile.exists())
			{
				queryList.clear();
				Scanner textScanner = new Scanner(loadFile);
				while(textScanner.hasNext())
				{
					String query = textScanner.nextLine();
					long queryTime = Long.parseLong(textScanner.nextLine());
					queryList.add(new QueryInfo(query, queryTime));
				}
				textScanner.close();
				JOptionPane.showMessageDialog(getAppFrame(), queryList.size() + "QueryInfo objects were loaded into the application");
			}
			else
			{
				JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryInfo objects loaded");
			}
		}
		catch(IOException currentError)
		{
			dataController.displayErrors(currentError);
		}
	}
	
	/**
	 * This is what saves the data components into a file and you can store on any drive in your computer
	 */
	
	public void saveTimingInformation()
	{
		try
		{
			File saveFile = new File("asdasda.save");
			PrintWriter writer = new PrintWriter(saveFile);
			if(saveFile.exists())
			{
			for (QueryInfo current : queryList)
				{
				writer.println(current.getQuery());
				writer.println(current.getQueryTime());
				}
				writer.close();
				JOptionPane.showMessageDialog(getAppFrame(), queryList.size() + " QueryInfo objects were saved"); 
			}
			else
			{
				JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryInfo objects saved");
			}
		}
		catch(IOException currentError)
		{
			dataController.displayErrors(currentError);
		}
		
	}

}
