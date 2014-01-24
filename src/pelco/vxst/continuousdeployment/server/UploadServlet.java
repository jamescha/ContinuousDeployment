package pelco.vxst.continuousdeployment.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pelco.vxst.continuousdeployment.client.UserUI;

public class UploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_DIRECTORY = "C:\\Users\\Tardis\\Documents\\Json\\Uploaded\\";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, 
																					IOException {
		
		if (ServletFileUpload.isMultipartContent(req)) {
			FileItemFactory factory = new DiskFileItemFactory();
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			try {
				List<FileItem> items = upload.parseRequest(req);
				for (FileItem item : items) {
					if (item.isFormField()) continue;
					
					String fileName = item.getName();
					
					if (fileName != null) {
						fileName = FilenameUtils.getName(fileName);
					}
					
					File uploadedFile = new File(UPLOAD_DIRECTORY, fileName);
					if (uploadedFile.createNewFile()) {
						item.write(uploadedFile);
						resp.setStatus(HttpServletResponse.SC_CREATED);
						resp.getWriter().print("The file was crated successfully.");
						uploadUsers(fileName);
						resp.flushBuffer();
						
					} else 
						throw new IOException("File already exists");
				}
			} catch (Exception e) {
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
									"An error has occurred" + e.getMessage());
			}
			
		} else {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, 
							"Request Contents is not supported");			
		}
	}
	
	void uploadUsers (String fileName) {
		JSONParser parser = new JSONParser();
		final EnduraSoapServiceImpl enduraSoapService = new EnduraSoapServiceImpl();
		
		
		try {
			
			FileReader fileReader = new FileReader(UPLOAD_DIRECTORY+fileName);
			Object obj = parser.parse(fileReader);
			
			JSONObject jsonObject = (JSONObject) obj;
			
			JSONArray users = (JSONArray) jsonObject.get("users");
			
			for (Object object : users) {
				jsonObject = (JSONObject) object;
				final String name = (String) jsonObject.get("name");
				final String password = (String) jsonObject.get("password");
				
				enduraSoapService.userCreate(name, password, "366");
			}
			
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(new File(UPLOAD_DIRECTORY+fileName).delete()) {
			System.out.println(fileName + " has been successfully Deleted");
		} else {
			System.out.println("File was not Deleted!");
		}
		
	}
	
	
}
