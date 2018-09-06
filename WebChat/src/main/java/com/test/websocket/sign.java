package com.test.websocket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class sign
 */
@WebServlet("/sign")
public class sign extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sign() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		Map<String, String[]> req = request.getParameterMap();
		
		for (String key : req.keySet()) {
			System.out.println(Arrays.toString(req.get(key)));
		}
		
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		
		InputStream in = request.getInputStream();
		OutputStream out = new FileOutputStream("E:/image/test.jpg");
		byte buffer[] = new byte[1024];
		int len = 0;
		
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		
		out.flush();
		out.close();
		in.close();
		JSONObject json = new JSONObject();
		json.put("message", "成功!");
		response.getWriter().println(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
