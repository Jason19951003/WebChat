package com.jason.user;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.jason.util.DBUtil;
import com.jason.util.RequestBean;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		RequestBean requstBean = RequestBean.buildRequestBean(request);
		Map<String, Object> paramMap = requstBean.getRequestMap();
		JSONObject json = new JSONObject();
		try {
			if (DBUtil.userLogin(paramMap)) {
				json.put("MESSAGE", "");
			} else {
				json.put("MESSAGE", "帳號密碼錯誤!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("MESSAGE", e);
			response.getWriter().print(json);
		}
		
		response.getWriter().print(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
