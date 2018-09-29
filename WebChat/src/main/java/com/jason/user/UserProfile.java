package com.jason.user;

import java.sql.Blob;

import javax.websocket.Session;

public class UserProfile {
	private Session session;
	private String account;
	private String password;
	private String nickname;
	private Blob image;
	
	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}
	
	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the image
	 */
	public Blob getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Blob image) {
		this.image = image;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserProfile [account=" + account + ", password=" + password + ", nickname=" + nickname + "]";
	}
	
}
