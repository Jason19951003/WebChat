package com.jason.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class RequestBean {

	private Map<String, Object> paramMap = new HashMap<String, Object>();
	
	public RequestBean (Map<String, Object> requestMap) {
		this.paramMap = requestMap;
	}
	
	public Map<String, Object> getRequestMap() {
		return paramMap;
	}
	
	public static RequestBean buildRequestBean(HttpServletRequest request) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			return buildMultiPartRequest(request);
		} else {
			return buildNormalRequest(request);
		}
	}
	
	public static RequestBean buildMultiPartRequest(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		// Create a factory for disk-based file items
		FileItemFactory factory = new DiskFileItemFactory();

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// Parse the request
			List items = upload.parseRequest(request);
			Iterator iterator = items.iterator();
			while (iterator.hasNext()) {
				FileItem item = (FileItem) iterator.next();
				if (!item.isFormField()) {
					String fileName = item.getName();
					if (!"".equals(fileName)) {
						File path = new File("E:/image");
						if (!path.exists()) {
							path.mkdirs();
						}
						
						File uploadedFile = new File(path + "/" + fileName);
						item.write(uploadedFile);
						paramMap.put(item.getFieldName(), uploadedFile.getAbsolutePath().replace("\\", "/"));

					}
				} else {
					paramMap.put(item.getFieldName(), item.getString());
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new RequestBean(paramMap);
	}
	
	public static RequestBean buildNormalRequest(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String[]> requestMap = request.getParameterMap();
		
		for (String key : requestMap.keySet()) {
			String value[] = requestMap.get(key);
			if (value.length == 1) {
				paramMap.put(key, value[0]);
			} else {
				paramMap.put(key, value);
			}
		}
		return new RequestBean(paramMap);
	}
}
