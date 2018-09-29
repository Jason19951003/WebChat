package com.jason.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.jason.user.UserProfile;

public class DBUtil {
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static Statement st = null;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static UserProfile getUserProfile(Map<String, Object> paramMap) throws SQLException {
		if (paramMap.get("account") != null) {
			return getUserProfile((String)paramMap.get("account"));
		} else {
			return null;
		}
	}
	
	public static UserProfile getUserProfile(String userId) throws SQLException {
		ResultSet rs = null;
		UserProfile userprofile = new UserProfile();
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "123456");
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM mychat where account = '" + userId + "'");
			
			while (rs.next()) {
				userprofile.setAccount(rs.getString("account"));
				userprofile.setPassword(rs.getString("password"));
				userprofile.setNickname(rs.getString("nickname"));
				userprofile.setImage(rs.getBlob("image"));
				return userprofile;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
			if (st != null) {
				st.close();
			}
		}
		
		return null;
	}
	public static boolean checkUserExists(String userId) throws SQLException {
		boolean exist = false;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "123456");
			st = con.createStatement();
			rs = st.executeQuery("SELECT account AS TOTAL FROM mychat where account = '" + userId + "'");
			
			int row = 0;
			while (rs.next()) {
				row++;
			}
			if (row == 0) {
				exist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return exist;
	}
	
	public static boolean checkUserExists(Map<String, Object> paramMap) throws SQLException {
		String accout = (String) paramMap.get("accout");
		if (accout != null) {
			return checkUserExists(accout);
		} else {
			return false;
		}
	}
	
	public static boolean userLogin(Map<String, Object> parmMap) throws SQLException {
		boolean exist = true;
		ResultSet rs = null;
		String accont = (String)parmMap.get("account");
		String password = (String)parmMap.get("password");
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "123456");
			st = con.createStatement();
			rs = st.executeQuery("SELECT account AS TOTAL FROM mychat where account = '" + accont + "' and password = '" + password + "'");
			
			int row = 0;
			while (rs.next()) {
				row++;
			}
			if (row == 0) {
				exist = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return exist;
	}
	public static int insertUser(Map<String, Object> paramMap) throws SQLException, FileNotFoundException {
		int execute = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "123456");
			String sql = "insert into mychat(account,password,nickname,image) values(?,?,?,?)";
			if (paramMap.get("image") == null) {
				sql = "insert into mychat(account,password,nickname) values(?,?,?)";
			}
			
			ps = con.prepareStatement(sql);
			ps.setString(1, (String) paramMap.get("account"));
			ps.setString(2, (String) paramMap.get("password"));
			ps.setString(3, (String) paramMap.get("nickname"));
			if (paramMap.get("image") != null) {
				ps.setBlob(4, new FileInputStream(new File((String)paramMap.get("image"))));
			}
			
			execute = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			execute = 0;
		} finally {
			if (con != null) {
				con.close();
			}
			if (ps != null) {
				ps.close();
			}
		}		
		return execute;
	}
}
