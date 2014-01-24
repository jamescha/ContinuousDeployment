package pelco.vxst.continuousdeployment.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ContinuousDeployment implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";


	/**
	 * This is the entry point method.
	 */
	
	private HorizontalPanel headerPanel = new HorizontalPanel();
	
	public final String content = "content";
	
	public void onModuleLoad() {
		
		final HeaderUI headerUI= new HeaderUI();
		
		headerPanel.add(headerUI);
		RootPanel.get("header").add(headerPanel);
		
		
	}
	
}
