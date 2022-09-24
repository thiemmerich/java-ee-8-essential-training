package com.linkedin.jsf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.jboss.logging.Logger;

@ServerEndpoint("/socket/help")
public class HelpServerSocketEndPoint {

	private static final Logger LOG = Logger.getLogger(HelpServerSocketEndPoint.class);
	private static List<Session> sessions = new ArrayList<>();

	@OnOpen
	public void onOpen(Session session) {
		LOG.info("Connect to the client");
		sessions.add(session);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		sessions.stream().forEach(s -> {
			try {
				s.getBasicRemote().sendText(message);
			} catch (IOException e) {
				LOG.error(e);
			}
		});
	}

	@OnClose
	public void onClose() {
		LOG.info("Connection closed.");
	}
}
