package databaseController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import databaseModel.QueryInfo;

/**
 * The Controller takes the code for both the Frame and Panel and runs its through here 
 * @author cdaz6661
 *
 */
public class DataBaseController
{
	
	private String connectionString;
	private Connection databaseConnection;
	private DataBaseAppController baseController;
	private String query;
	private String currentQuery;
	private long queryTime;
	
	/**
	 * Connects to the String given by the SQL with the controller
	 * @param baseController
	 */
	public DataBaseController(DataBaseAppController baseController)
	{
		connectionString = "jdbc:mysql://10.228.5.150/book_reading?user+%%%%%&password+######";
		this.baseController = baseController;
		checkDriver();
		setupConnection();
	}
	
	/**
	 * This is what connects all the identities to the database server itself, with it being the Server, 
	 * the Name, The users name and the password for access betwene the program and server.
	 * @param pathToDBServer The database Server 
	 * @param databaseName Name of the database
	 * @param userName The name of the User who created the database
	 * @param password password that the user has set to access the database
	 */
	public void connectionStringBuilder(String pathToDBServer, String databaseName, 
			String userName, String password)
	{
		connectionString = "jdbc:mysql://";
		connectionString += pathToDBServer;
		connectionString += "/" + databaseName;
		connectionString += "?user=" + userName;
		connectionString += "&password=" + password;
	}

	/**
	 * Checks the computers Driver and scans for data to use in the database
	 */
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
		
	}
	
	/**
	 * This is what sets up the connection with the database and checks the driver in the computer.
	 */
	private void setupConnection()

	{
		try
		{
			databaseConnection = DriverManager.getConnection(connectionString);
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	/**
	 * This is what closes the connection with the database, when it is checking for set data.
	 */
	public void closeConnection()
	{
		try
		{
			databaseConnection.close();
		}
		catch (SQLException currentExcecption)
		{
			displayErrors(currentExcecption);
		}
	}
	
	/**
	 * Gets information about the types and properties of the columns in a ResultSet Object
	 * Added a startTime and endTime to keep track of the current time it takes to retrieve the data 
	 * @return results All of the tables that have been provided 
	 */
	public String[][] getMetaDataTitles()
	{
		String[][] results;
		String query = "SHOW TABLES";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		
		try 
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			
			
			answers.last();
			int numberOfRows = answers.getRow();
			answers.beforeFirst();
			
			results = new String [numberOfRows][1];
			
			while(answers.next())
			{
				results[answers.getRow()-1][1] = answers.getString(1);
			}
			
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			results = new String [][] {{"empty"}};
			displayErrors(currentException);
		}
		
		queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(query, queryTime));
		
		return results;
	}
	
	
	/**
	 * This is a DDL statement, the drop statement itself is used to destroy an existing database, table, index, or view
	 * distinct between the Delete and Truncate statements and do not remove the table itself
	 */
	public void dropStatement()
	{
		String results;
		try
		{
			if(checkForStructureViolation())
			{
				throw new SQLException("you is no allowed to dropping db's",
									   "duh",
									   Integer.MIN_VALUE);
			}
			
			if(currentQuery.toUpperCase().contains(" INDEX "))
			{
				results = "The index was ";
			}
			else
			{
				results = "The table was ";
			}
			
			Statement dropStatement = databaseConnection.createStatement();
			int affected = dropStatement.executeUpdate(currentQuery);
			
			dropStatement.close();
			
			if(affected == 0)
			{
				results += "dropped";
			}
			JOptionPane.showMessageDialog(baseController.getAppFrame(), results);
		}
		catch(SQLException dropError)
		{
			displayErrors(dropError);
		}
	}
	
	private boolean checkForStructureViolation()
	{
		return false;
	}

	/**
	 * Checks the data member query for a potential violation of database structure/content removal.
	 * Note the space around the SQL commands and the us of .toUpperCase to make the check effective.
	 * @return True if the query could remove/destroy data, false otherwise.
	 */
	private boolean checkQueryForDataViolation()
	{
		if(query.toUpperCase().contains("DROP")
				|| query.toUpperCase().contains("TRUNCATE")
				|| query.toUpperCase().contains("SET")
				|| query.toUpperCase().contains("ALTER"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Generic version of the select query method that will work with any database specified by the current 
	 * connection STring value
	 * @param query THe SELECT query to be turned into a ResultSet object and parsed into the 2D array.
	 * @return The 2D array of results from the select query
	 * @throws A SQLException 
	 */
	public String[][] selectQueryResults(String query)
	{
		String [][] results;
		this.query = query;
		try
		{
			if(checkQueryForDataViolation())
			{
				throw new SQLException("There was an attempt at a data violation", 
										" ( you dont get to mess wit dat data in there!",
										Integer.MIN_VALUE);
			}
			
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			int columnCount = answers.getMetaData().getColumnCount();
			
			answers.last();
			int numberOfRows = answers.getRow();
			answers.beforeFirst();
			
			results = new String [numberOfRows][columnCount];
			
			while(answers.next())
			{
				for(int col = 0; col< columnCount; col++)
				{
					results[answers.getRow() - 1][col] =answers.getString(col + 1);
				}
			}
			
			answers.close();
			firstStatement.close();
		}
		catch(SQLException currentException)
		{
			results = new String [][] { {"The query was unsuccessful. :("},
									 {"You might want to use a better query string. "},
									 {currentException.getMessage()}
									 };
			displayErrors(currentException);
		}
		
		
		return results;
	}
	
	/**
	 * This is what displays the given table in the Comic Heroes database
	 * If there is no table given, then all of the matching tables in the database are shown
	 * @return connectionString The connection with the database
	 */
	public String displayTables()
	{
		@SuppressWarnings("unused")
		String tableNames = "";
		String query = "SHOW TABLES";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			
			while(answers.next())
			{
				tableNames += answers.getString(1) + "\n";
				
			}
			answers.close();
			firstStatement.close();
		}
		catch(SQLException currentError)
		{
			displayErrors(currentError);
		}
		
		return connectionString;
	}
	
	/**
	 * It shows all the rows that have been selected within the database and displays them in the GUI Panel
	 * @return rowsAffected The rows inside each table of the database
	 */
	public int insertSample()
	{
		int rowsAffected = -1;
		String query = "INSERT INTO `gasoline_travel`.`cities`"
				+ "(`name`, `population`) "
				+ "VALUES('Wisty', 6, 1);";
		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(query);
			insertStatement.close();
		}
		catch(SQLException currentError)
		{
			displayErrors(currentError);
		}
		
		return rowsAffected;
	}
	
	/**
	 * Displays any errors that occur during the time of checking the driver inside the database.
	 * @param currentException
	 */
	private void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), "Exception:" + currentException.getMessage());
		if(currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State" + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code" + ((SQLException) currentException).getErrorCode());
		}
	}
	
	/**
	 * It tests the results of the data within the SQL, retrieved by the SQL Server  
	 * @return
	 */

	public Object[][] testResults()
	{
		return null;
	}

}
