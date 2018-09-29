package com.test.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import com.jason.user.UserProfile;
import com.jason.util.DBUtil;
 
@ServerEndpoint("/websocket")
public class WebSocketEndpointTest {
    //用來存放WebSocket已連接的Socket
    static Set<UserProfile> userList;
    
    @OnMessage
    public void onMessage(String message, Session session) throws IOException,
            InterruptedException, EncodeException {
        JSONObject json = new JSONObject(message);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Iterator<String> it = json.keys();
        while (it.hasNext()) {
        	//
        	String key = it.next();
        	paramMap.put(key, json.getString(key));
        }
        String all = (String)paramMap.get("ALL");
        String one = (String)paramMap.get("ONE");
        //聊天室
        if ("Y".equals(all)) {
        	for (UserProfile user : userList) {
        		Session s = user.getSession();
        		s.getBasicRemote().sendText(message);
        	}
        } else if ("Y".equals(one)) {//個人聊天
        	for (UserProfile user : userList) {
        		String account = json.getString("account");
        		if (account.equals(user.getAccount())) {        			
        			user.getSession().getBasicRemote().sendText(message);
        			break;
        		}
        	}
        }
    } 
    
    @OnOpen
    public void onOpen(Session session) {
        //紀錄連接到sessions中
        if (userList == null) {
        	userList = new HashSet<UserProfile>();
        }
        Map<String, List<String>> paramMap = session.getRequestParameterMap();
        
        String account = paramMap.get("account").get(0);
        
        try {
        	UserProfile userProfile = DBUtil.getUserProfile(account);
        	
			if (userProfile != null) {
				userProfile.setSession(session);
				userList.add(userProfile);
			}
			getUserList();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
 
    @OnClose
    public void onClose(Session session) {
    	//將連接從sessions中移除
        try {
        	UserProfile user = null;
        	for (UserProfile userProfile : userList) {
        		if (session == userProfile.getSession()) {
        			user = userProfile;
        			userProfile.getSession().close();
        			userList.remove(userProfile);
        			break;
        		}
        	}
        	for (UserProfile userProfile : userList) {
        		JSONObject json = new JSONObject();
        		json.put("ALL", "Y");
        		json.put("MESSAGE", "系統：" + user.getNickname() + "離開了聊天室");
        		userProfile.getSession().getBasicRemote().sendText(json.toString());
        	}
        	getUserList();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void getUserList() throws IOException {
    	String li = "";
		Map<String, Object> userMap = new HashMap<String, Object>();
		for (UserProfile user : userList) {
			li = "<li onclick='openChat(this);' account='" + user.getAccount() + 
					"' nickname='" + user.getNickname() + "' isopen='N'><img src='getHead?account=" + user.getAccount() + "'><font>" + user.getNickname() + "</font></li>";
			
			userMap.put(user.getAccount(), li);
		}
		
		for (UserProfile user : userList) {
			String userList = "";
			for (String key : userMap.keySet()) {
				if (!key.equals(user.getAccount())) {
					userList += userMap.get(key) + "\n";
				}
			}
			
			JSONObject json = new JSONObject();
			json.put("IMAGE", "Y");
			json.put("USERLIST", userList);
			user.getSession().getBasicRemote().sendText(json.toString());
		}
    }
}
