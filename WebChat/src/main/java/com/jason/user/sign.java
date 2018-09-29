package com.jason.user;

import java.io.IOException;
import java.sql.SQLException;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		RequestBean requestBean = RequestBean.buildMultiPartRequest(request);
		Map<String, Object> paramMap = requestBean.getRequestMap();
		JSONObject json = new JSONObject();
		
		String account = (String) paramMap.get("account");
		try {
			if (DBUtil.checkUserExists(account)) {
				int execute = DBUtil.insertUser(paramMap);
				
				if (execute > 0) {
					UserProfile userProfile = DBUtil.getUserProfile(paramMap);
					json.put("NICKNAME", userProfile.getNickname());
					json.put("MESSAGE", "Y");
				} else {
					json.put("MESSAGE", "失敗!");
				}
			} else {
				json.put("MESSAGE", "該用戶已存在!");
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		response.getWriter().println(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
