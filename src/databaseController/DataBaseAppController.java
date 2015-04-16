package databaseController;

import java.awt.Container;
import java.util.ArrayList;

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
		return null;
	}
	
	public void start()
	{
		
		
	}

}
