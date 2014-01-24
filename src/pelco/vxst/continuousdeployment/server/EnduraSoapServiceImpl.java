package pelco.vxst.continuousdeployment.server;

import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import pelco.vxst.continuousdeployment.client.EnduraSoapService;
import pelco.vxst.continuousdeployment.shared.Role;
import pelco.vxst.continuousdeployment.shared.User;
import pelco.vxst.continuousdeployment.shared.UserAttr;

import org.apache.commons.codec.binary.Base64;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;



/**
 * The server side implementation of the RPC service.
 */

@SuppressWarnings("serial")
public class EnduraSoapServiceImpl extends RemoteServiceServlet 
								   implements EnduraSoapService {
	
	final static String serverURL = "http://192.168.20.10:60001/control/UserAndRole-1";
	
	
	public ArrayList<Role> roleGetAll() {
		try {
			SOAPConnectionFactory factory = 
					SOAPConnectionFactory.newInstance();
			
			SOAPConnection connection =
		            factory.createConnection(); 
			 
			SOAPMessage response = connection.call(soapRoleGetAll(), serverURL);
		      
			connection.close();
			 
		    return parseRoleGetAll(response);
			 
		}	 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static SOAPMessage soapRoleGetAll() throws Exception {
		//Create a SOAPMessage
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart  soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPHeader headers = message.getSOAPHeader();
		
		MimeHeaders mimeheaders = message.getMimeHeaders();
		mimeheaders.addHeader("SOAPAction", "\"urn:schemas-pelco-com:service:UserAndRole:1#RoleGetAll\"");
		   
		QName evheaderQName = new QName("SOAP-ENV","evheader");
		QName userIDQName = new QName("SOAP-ENV","userID");
		   
		SOAPElement soapHeaderElem = headers.addChildElement(evheaderQName);
		SOAPElement soapHeaderElem1 = soapHeaderElem.addChildElement(userIDQName);
		soapHeaderElem1.addTextNode("0");
		envelope.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
		   
		SOAPBody body = envelope.getBody();
		   
		SOAPElement soapBodyElem = body.addChildElement("RoleGetAll");
		soapBodyElem.addNamespaceDeclaration("u", "urn:schemas-pelco-com:service:UserAndRole:1");
		soapBodyElem.setPrefix("u");
		   
		QName pageReqQName = new QName("pageReq");
		   
		soapBodyElem.addChildElement(pageReqQName);       
		   
		message.saveChanges();
		   
		message.writeTo(System.out);
		   
		System.out.println();
		   
		return message;
	}
	
	private static ArrayList<Role> parseRoleGetAll(SOAPMessage soapResponse) throws Exception
	{
		ArrayList<Role> roles = new ArrayList<Role>();
		String temp = soapResponse.getSOAPBody().getTextContent();
		
		temp = temp.replace("\n", ",");
		temp = temp.replace(",,,", ",");
		temp = temp.replace(",,", ",");
		String[] test = temp.split(",");
		     
		for (int i = 1; i < test.length; i+=3) {
			roles.add(new Role(Integer.parseInt(test[i]),test[i+1],Integer.parseInt(test[i+2])));
		}
		     
		return roles;
	}
	
	public ArrayList<User> userGetAll() {
		try {
			SOAPConnectionFactory factory = 
					SOAPConnectionFactory.newInstance();
			
			SOAPConnection connection =
		            factory.createConnection(); 
			 
			SOAPMessage response = connection.call(soapUserGetAll(), serverURL);
		      
			connection.close();
			 
		    return parseUserGetAll(response);
			 
		}	 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static SOAPMessage soapUserGetAll() throws Exception {
		//Create a SOAPMessage
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart  soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPHeader headers = message.getSOAPHeader();
		
		MimeHeaders mimeheaders = message.getMimeHeaders();
		mimeheaders.addHeader("SOAPAction", "\"urn:schemas-pelco-com:service:UserAndRole:1#UserGetAll\"");
		   
		QName evheaderQName = new QName("SOAP-ENV","evheader");
		QName userIDQName = new QName("SOAP-ENV","userID");
		   
		SOAPElement soapHeaderElem = headers.addChildElement(evheaderQName);
		SOAPElement soapHeaderElem1 = soapHeaderElem.addChildElement(userIDQName);
		soapHeaderElem1.addTextNode("0");
		envelope.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
		   
		SOAPBody body = envelope.getBody();
		   
		SOAPElement soapBodyElem = body.addChildElement("UserGetAll");
		soapBodyElem.addNamespaceDeclaration("u", "urn:schemas-pelco-com:service:UserAndRole:1");
		soapBodyElem.setPrefix("u");
		   
		QName pageReqQName = new QName("pageReq");
		   
		soapBodyElem.addChildElement(pageReqQName);       
		   
		message.saveChanges();
		   
		message.writeTo(System.out);
		   
		System.out.println();
		   
		return message;
	}
	
	private static ArrayList<User> parseUserGetAll(SOAPMessage soapResponse) throws Exception
	{
		ArrayList<User> users = new ArrayList<User>();
		String temp = soapResponse.getSOAPBody().getTextContent();
		 
		System.out.println(temp);
		
		temp = temp.replace("\n", ",");
		temp = temp.replace(",,,", ",");
		temp = temp.replace(",,", ",");
		String[] test = temp.split(",");
		     
		for (int i = 1; i < test.length; i+=3) {
			users.add(new User(Integer.parseInt(test[i]),test[i+1],Integer.parseInt(test[i+2])));
		}
		     
		return users;
	}
	
	public ArrayList<UserAttr> userAttrGetAll(String user) {
		try {
			SOAPConnectionFactory factory = 
					SOAPConnectionFactory.newInstance();
			
			SOAPConnection connection =
		            factory.createConnection(); 
			 
			SOAPMessage response = connection.call(soapUserAttrGetAll(user), serverURL);
		      
			connection.close();
			 
		    return parseUserAttrGetAll(response);
			 
		}	 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static SOAPMessage soapUserAttrGetAll(String user) throws Exception {
		//Create a SOAPMessage
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart  soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPHeader headers = message.getSOAPHeader();
		
		MimeHeaders mimeheaders = message.getMimeHeaders();
		mimeheaders.addHeader("SOAPAction", "\"urn:schemas-pelco-com:service:UserAndRole:1#UserAttrGetAll\"");
		   
		QName evheaderQName = new QName("SOAP-ENV","evheader");
		QName userIDQName = new QName("SOAP-ENV","userID");
		   
		SOAPElement soapHeaderElem = headers.addChildElement(evheaderQName);
		SOAPElement soapHeaderElem1 = soapHeaderElem.addChildElement(userIDQName);
		soapHeaderElem1.addTextNode("0");
		envelope.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
		   
		SOAPBody body = envelope.getBody();
		   
		SOAPElement soapBodyElem = body.addChildElement("UserAttrGetAll");
		soapBodyElem.addNamespaceDeclaration("u", "urn:schemas-pelco-com:service:UserAndRole:1");
		soapBodyElem.setPrefix("u");
		
		QName userQName = new QName("user");
		SOAPElement userBodyChildEle = soapBodyElem.addChildElement(userQName);
		
		QName nameQName = new QName("name");
		SOAPElement nameUserBodyChildEle = userBodyChildEle.addChildElement(nameQName);
		
		nameUserBodyChildEle.setValue(user);
		
		QName pageReqQName = new QName("pageReq");
		   
		soapBodyElem.addChildElement(pageReqQName);       
		   
		message.saveChanges();
		   
		message.writeTo(System.out);
		   
		System.out.println();
		   
		return message;
	}
	
	private static ArrayList<UserAttr> parseUserAttrGetAll(SOAPMessage soapResponse) throws Exception
	{
		ArrayList<UserAttr> usersAttr = new ArrayList<UserAttr>();
		String temp = soapResponse.getSOAPBody().getTextContent();
		 
		System.out.println(temp);
		
		temp = temp.replace("\n", ",");
		temp = temp.replace(",,,", ",");
		temp = temp.replace(",,", ",");
		String[] test = temp.split(",");
		     
		for (int i = 1; i < test.length; i+=3) {
			usersAttr.add(new UserAttr());
		}
		     
		return usersAttr;
	}
	
	public ArrayList<Role> userGetRoles(Integer dbId) {
		try {
			SOAPConnectionFactory factory = 
					SOAPConnectionFactory.newInstance();
			
			SOAPConnection connection =
		            factory.createConnection(); 
			 
			SOAPMessage response = connection.call(soapUserGetRoles(dbId), serverURL);
		      
			connection.close();
			 
		    return parseUserGetRoles(response);
			 
		}	 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static SOAPMessage soapUserGetRoles(Integer dbId) throws Exception {
		//Create a SOAPMessage
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart  soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPHeader headers = message.getSOAPHeader();
		
		MimeHeaders mimeheaders = message.getMimeHeaders();
		mimeheaders.addHeader("SOAPAction", "\"urn:schemas-pelco-com:service:UserAndRole:1#UserGetRoles\"");
		   
		QName evheaderQName = new QName("SOAP-ENV","evheader");
		QName userIDQName = new QName("SOAP-ENV","userID");
		   
		SOAPElement soapHeaderElem = headers.addChildElement(evheaderQName);
		SOAPElement soapHeaderElem1 = soapHeaderElem.addChildElement(userIDQName);
		soapHeaderElem1.addTextNode("0");
		envelope.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
		   
		SOAPBody body = envelope.getBody();
		   
		SOAPElement soapBodyElem = body.addChildElement("UserGetRoles");
		soapBodyElem.addNamespaceDeclaration("u", "urn:schemas-pelco-com:service:UserAndRole:1");
		soapBodyElem.setPrefix("u");
		
		QName userQName = new QName("user");
		SOAPElement userBodyChildEle = soapBodyElem.addChildElement(userQName);
		
		QName nameQName = new QName("dbId");
		SOAPElement nameUserBodyChildEle = userBodyChildEle.addChildElement(nameQName);
		
		nameUserBodyChildEle.setValue(Integer.toString(dbId));
		
		QName pageReqQName = new QName("pageReq");
		   
		soapBodyElem.addChildElement(pageReqQName);       
		   
		message.saveChanges();
		   
		message.writeTo(System.out);
		   
		System.out.println();
		   
		return message;
	}
	
	private static ArrayList<Role> parseUserGetRoles(SOAPMessage soapResponse) throws Exception
	{
		ArrayList<Role> roles = new ArrayList<Role>();
		String temp = soapResponse.getSOAPBody().getTextContent();
		 
		System.out.println("SOAP Response :: " + temp);
		
		temp = temp.replace("\n", ",");
		temp = temp.replace(",,,", ",");
		temp = temp.replace(",,", ",");
		String[] test = temp.split(",");
		     
		for (int i = 1; i < test.length; i+=3) {
			try {
				
				roles.add(new Role(Integer.parseInt(test[i]),test[i+1],Integer.parseInt(test[i+2])));
			} catch (Exception e) {
				System.out.println("Exception Caught :: " + e);
			}
			
			
		}
		     
		return roles;
	}
	
	public String userCreate(String name, String pass, String userId) {
		try {
			SOAPConnectionFactory factory = 
					SOAPConnectionFactory.newInstance();
			
			SOAPConnection connection =
		            factory.createConnection(); 
			 
			SOAPMessage response = connection.call(soapUserCreate(name, pass, userId), serverURL);
		      
			connection.close();
			 
		    return response.toString();
			 
		}	 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static SOAPMessage soapUserCreate(String name, String pass, String userId) throws Exception {
		//Create a SOAPMessage
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart  soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPHeader headers = message.getSOAPHeader();
		
		MimeHeaders mimeheaders = message.getMimeHeaders();
		mimeheaders.addHeader("SOAPAction", "\"urn:schemas-pelco-com:service:UserAndRole:1#UserCreate\"");
		   
		QName evheaderQName = new QName("SOAP-ENV","evHeader");
		QName userIDQName = new QName("SOAP-ENV","userID");
		   
		SOAPElement soapHeaderElem = headers.addChildElement(evheaderQName);
		SOAPElement soapHeaderElem1 = soapHeaderElem.addChildElement(userIDQName);
		soapHeaderElem1.addTextNode("366");
		envelope.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
		   
		SOAPBody body = envelope.getBody();
		   
		SOAPElement soapBodyElem = body.addChildElement("UserCreate");
		soapBodyElem.addNamespaceDeclaration("u", "urn:schemas-pelco-com:service:UserAndRole:1");
		soapBodyElem.setPrefix("u");
		
		QName userQName = new QName("user");
		SOAPElement userBodyChildEle = soapBodyElem.addChildElement(userQName);
		
		QName nameQName = new QName("name");
		SOAPElement nameUserBodyChildEle = userBodyChildEle.addChildElement(nameQName);
		
		nameUserBodyChildEle.setValue(name);
		
		QName pswdQName = new QName("pswd");
		SOAPElement pswdUserBodyChildEle = soapBodyElem.addChildElement(pswdQName);
		
		QName typeQName = new QName("type");
		SOAPElement typePswdUserBodyChildEle = pswdUserBodyChildEle.addChildElement(typeQName);
		typePswdUserBodyChildEle.setValue("1");
		
		QName dataQname = new QName("data");
		SOAPElement dataUserBodyChildEle = pswdUserBodyChildEle.addChildElement(dataQname);
		dataUserBodyChildEle.setValue(new String(Base64.encodeBase64(pass.getBytes()))+"=");
		
		QName roleDbIdQName = new QName("roleDbId");
		SOAPElement roleDbIdChildEle = soapBodyElem.addChildElement(roleDbIdQName);
		roleDbIdChildEle.setValue("1");
		
		QName pageReqQName = new QName("pageReq");
		   
		soapBodyElem.addChildElement(pageReqQName);       
		   
		message.saveChanges();
		   
		message.writeTo(System.out);
		   
		System.out.println();
		   
		return message;
	}
	
	public String userDelete(String dbId, String userId) {
		if (dbId != "1") {
			try {
				SOAPConnectionFactory factory = 
						SOAPConnectionFactory.newInstance();
				
				SOAPConnection connection =
			            factory.createConnection(); 
				 
				SOAPMessage response = connection.call(soapUserDelete(dbId, userId), serverURL);
			      
				connection.close();
				 
			    return response.toString();
				 
			}	 
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Window.alert("Can Not Delete Admin");
		}
		
		return null;
	}
	
	private static SOAPMessage soapUserDelete(String dbId, String userId) throws Exception {
		//Create a SOAPMessage
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart  soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPHeader headers = message.getSOAPHeader();
		
		MimeHeaders mimeheaders = message.getMimeHeaders();
		mimeheaders.addHeader("SOAPAction", "\"urn:schemas-pelco-com:service:UserAndRole:1#UserDelete\"");
		   
		QName evheaderQName = new QName("SOAP-ENV","evHeader");
		QName userIDQName = new QName("SOAP-ENV","userID");
		   
		SOAPElement soapHeaderElem = headers.addChildElement(evheaderQName);
		SOAPElement soapHeaderElem1 = soapHeaderElem.addChildElement(userIDQName);
		soapHeaderElem1.addTextNode(userId);
		envelope.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
		   
		SOAPBody body = envelope.getBody();
		   
		SOAPElement soapBodyElem = body.addChildElement("UserDelete");
		soapBodyElem.addNamespaceDeclaration("u", "urn:schemas-pelco-com:service:UserAndRole:1");
		soapBodyElem.setPrefix("u");
		
		QName userQName = new QName("user");
		SOAPElement userBodyChildEle = soapBodyElem.addChildElement(userQName);
		
		QName nameQName = new QName("dbId");
		SOAPElement nameUserBodyChildEle = userBodyChildEle.addChildElement(nameQName);
		nameUserBodyChildEle.setValue(dbId);
		
		QName pswdQName = new QName("pswd");
		SOAPElement pswdUserBodyChildEle = soapBodyElem.addChildElement(pswdQName);
		
		QName typeQName = new QName("type");
		SOAPElement typePswdUserBodyChildEle = pswdUserBodyChildEle.addChildElement(typeQName);
		typePswdUserBodyChildEle.setValue("1");
		
		QName roleDbIdQName = new QName("roleDbId");
		SOAPElement roleDbIdChildEle = soapBodyElem.addChildElement(roleDbIdQName);
		roleDbIdChildEle.setValue("1");
		
		QName pageReqQName = new QName("pageReq");
		   
		soapBodyElem.addChildElement(pageReqQName);       
		   
		message.saveChanges();
		   
		message.writeTo(System.out);
		   
		System.out.println();
		   
		return message;
	}
	
	
}
