package databaseView;

import javax.swing.JFrame;

import databaseController.DataBaseAppController;

/**
 * IT takes the setup of the panel and initializes the Frame through the panel.
 * @author cdaz6661
 *
 */
public class DataBaseFrame extends JFrame
{
	
	private DataBasePanel basePanel;
	public Object queryList;
		
		public DataBaseFrame(DataBaseAppController baseController) 
		{
			basePanel = new DataBasePanel(baseController);
			setupFrame();
		}
		
		/**
		 * This sets the size for the Frame of the application and initiates when it will be visible or not
		 */
		private void setupFrame()
		{
			this.setContentPane(basePanel);
			this.setSize(1000, 1000);
			this.setVisible(true);
		}
}
