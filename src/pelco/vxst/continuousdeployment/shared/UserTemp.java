package pelco.vxst.continuousdeployment.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class UserTemp{
	
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";
	
	public void userLoad () {
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		final Button uploadButton = new Button("Upload File");
		final Label errorLabel = new Label();
		final ListBox roleListBox = new ListBox();
		final FormPanel form = new FormPanel();
		final FileUpload fileUpload = new FileUpload();
		
		//Name Field Default Value
		nameField.setText("GWT User");
		
		//Form
		form.setAction(UPLOAD_ACTION_URL);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		
		RootPanel.get("fileUploadContainer").add(fileUpload);
		
		// We can add style names to widgets
		sendButton.addStyleName("sendButton");
		uploadButton.addStyleName("someButton");
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("uploadButtonContainer").add(uploadButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("roleListContainer").add(roleListBox);
	
		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();
		
		uploadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String filename = fileUpload.getFilename();
				if(filename.length() == 0) {
					Window.alert("No File Specified!");
				} else {
					form.submit();
				}
			}
		});
		
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				Window.alert(event.getResults());
			}
		});
	}
}