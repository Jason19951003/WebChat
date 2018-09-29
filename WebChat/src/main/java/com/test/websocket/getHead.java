package com.test.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jason.user.UserProfile;
import com.jason.util.DBUtil;

/**
 * Servlet implementation class getHead
 */
@WebServlet("/getHead")
public class getHead extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getHead() {
        super();        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		
		try {
			UserProfile userProfile = DBUtil.getUserProfile(account);
			InputStream in = null;
			
			int len = 0;
			byte buffer[] = new byte[8192];
			
			if (userProfile.getImage() != null) {
				in = userProfile.getImage().getBinaryStream();
			} else {
				in = this.getClass().getClassLoader().getResourceAsStream("NoHead.jpg");
			}
			
			while ((len = in.read(buffer)) > 0) {
				response.getOutputStream().write(buffer, 0, len);
			}
			
			in.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
