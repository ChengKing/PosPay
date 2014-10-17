package com.ck.core.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.ck.dao.IPropertyConifg;
import com.ck.dao.confImpl.PropertiesConfig;

public class ServiceThread{
	
	/**
	 * 
	 */
	private static ServiceThread instance = null;
	
	private static final Logger logger = Logger.getLogger(ServiceThread.class);
	
	/**
	 * 
	 * @return
	 */
	public static synchronized ServiceThread getInstance(){
		if(instance == null){
			instance = new ServiceThread();
		}
		return instance;
	}
	
	/**
	 * 
	 */
	public void start() {
		IPropertyConifg conf = new PropertiesConfig();
		conf.start("project.properties");
		ServerSocket server = init(conf);
		if(server == null){
			throw new RuntimeException("init serversocket failed by conf ["+conf.toString()+"] , failed start ! ");
		}
		startService(server);
	}

	/**
	 * 
	 * @param conf
	 * @return
	 */
	private ServerSocket init(IPropertyConifg conf) {
		int port = conf.getConfigAsInt("sys.pos.server.port", 36010);
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			logger.error("init socket connect failed !", e);
		}
		return server;
	}

	/**
	 * 
	 * @param server
	 */
	private void startService(ServerSocket server) {
		while(true){
			Socket socket = null;
			try {
				// 一旦有堵塞, 则表示服务器与客户端获得了连接  
				socket = server.accept();
				// 启动独立的线程处理本次连接
			} catch (Exception e) {
				logger.error("failed to deal ", e);
			}finally{
				try {
					socket.close();
				} catch (IOException e) {
					logger.error("failed to colse socket !", e);
				}
			}
		}
	}

	public static void main(String[] args) {
	/*	Thread t = new Thread(new ServiceThread());
		t.start();*/
	}
}
