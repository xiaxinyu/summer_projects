package com.account.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.account.core.tool.DateTool;
import com.account.core.tool.StringTool;
import com.account.persist.model.CreditRecord;
import com.account.service.exception.AppServiceException;
import com.account.service.face.ICreditRecordService;
import com.account.service.face.ICreditService;
import com.account.web.exception.AppFileUploadException;


/**
 * Created by Summer.Xia on 9/6/2015.
 */
public class CreditUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CreditUpload.class);
	private String customerName = StringTool.EMPTY;
	private CreditRecord creditRecord = new CreditRecord();
	
	private ICreditService creditService;
	
	private ICreditRecordService creditRecordService;
	
	 
	public void init() throws ServletException {
		ServletContext servletContext = this .getServletContext();   
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);   
        creditService = (ICreditService) ctx.getBean("creditService" );
        creditRecordService = (ICreditRecordService) ctx.getBean("creditRecordService" );
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean flag = true;
		String message = StringTool.EMPTY;
		//set request encoding
        request.setCharacterEncoding("UTF-8");    
        try {    
              String fileRealPath = saveFile(request);
              File uploadFile = new File(fileRealPath);
              if(uploadFile.exists()){
            	  List<String[]> rowDatas = readData(fileRealPath, ",");
            	  creditRecordService.addCreditRecord(creditRecord);
            	  creditService.addCredits(rowDatas,customerName,creditRecord.getId());
              }else{
            	  throw new AppFileUploadException("Can't file upload file in the server,upload file fail");
              }
        } catch (AppFileUploadException e) { 
        	flag = false;
        	message = "upload file fail";
        	logger.error(message,e);
        } catch (AppServiceException e) {
        	flag = false;
        	message = "save credit data fail";
        	logger.error(message,e);
		}   
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag", flag);
        jsonObject.put("message", message);
       response.getWriter().write(jsonObject.toString());    
	}	

	public List<String[]> readData(String strFullPath, String strPrefix) throws AppFileUploadException {
		List<String[]> list = new ArrayList<String[]>();
		try {
			String textData = StringTool.EMPTY;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(strFullPath), "UTF-8"));
			String rowData = StringTool.EMPTY;
			int counter = 0;
			while ((rowData = br.readLine()) != null) {
				rowData = StringTool.trim(rowData);
				if (!StringTool.isNullOrEmpty(rowData)) {
					textData += rowData + "\n"; 
					if(counter != 0){
						String[] values = rowData.split(strPrefix);
						if (values != null && values.length > 0) {
							list.add(values);
						}
					}
					counter ++;
				}
			}
			br.close();
			//fetch data to Credit Record
			creditRecord.setBillData(textData);
			creditRecord.setBillItemsNumber(counter);
		} catch (IOException e) {
			throw new AppFileUploadException(e);
		}
		return list;
	}
	
	private String saveFile(HttpServletRequest request) throws AppFileUploadException {
		String fileRealPath = null; 
		try {
			//declare the folder of upload file 
			String savePath = this.getServletConfig().getServletContext().getRealPath("/") + "uploads/"; 
			File file = new File(savePath);    
	        if (!file.isDirectory()) {    
	            file.mkdirs();    
	        } 
	        
			DiskFileItemFactory fac = new DiskFileItemFactory();    
            ServletFileUpload upload = new ServletFileUpload(fac);    
            upload.setHeaderEncoding("UTF-8");    
            // parse request params   
            List fileList = fileList = upload.parseRequest(request);    
            Iterator it = fileList.iterator();    
            while (it.hasNext()) {    
                Object obit = it.next();  
                if(obit instanceof DiskFileItem){
                	DiskFileItem item = (DiskFileItem) obit;
                	//parse file name
                    String fileName = item.getName();    
                    if(!StringTool.isNullOrEmpty(fileName)){
                    	String firstFileName= fileName.substring(fileName.lastIndexOf("/")+1);  
                    	customerName = firstFileName.substring(0, firstFileName.lastIndexOf("."));
                    	String suffix = firstFileName.substring(firstFileName.lastIndexOf("."));
                    	fileRealPath = savePath + customerName + DateTool.changeDateToString(new Date(), DateTool.DF_YYYYMMDDHHMMSS) + suffix;  
                        //copy file to destination folder   
                        BufferedInputStream in = new BufferedInputStream(item.getInputStream()); 
                        BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(fileRealPath)));    
                        Streams.copy(in, outStream, true);
                        //fetch data
                        creditRecord.setId(StringTool.generateID());
                        creditRecord.setCreateuser(customerName);
                        creditRecord.setUpdateuser(customerName);
                        creditRecord.setBillFileName(fileRealPath);
                        break;
                    }
                }  
            }   
		} catch (Exception e) {
			throw new AppFileUploadException(e);
		}
		return fileRealPath;
	}
}
