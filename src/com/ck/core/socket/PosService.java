package com.ck.core.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.ck.dao.IPropertyConifg;
import com.ck.dao.confImpl.PropertiesConfig;

public class PosService{
	
	private PosService(){
		
	}
	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(PosService.class);
	
	/**
	 * 
	 */
	private ExecutorService servicePool;//线程池
	
	private static PosService instance = null;
	
	/**
	 * 
	 * @return
	 */
	public static synchronized PosService getInstance(){
		if(instance == null){
			instance = new PosService();
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
		int nThreads = conf.getConfigAsInt("sys.pos.server.pool.num", 10);
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			servicePool = Executors.newFixedThreadPool(nThreads);
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
				servicePool.execute(new ReceiverHandler(socket));
			} catch (Exception e) {
				logger.error("failed to deal ", e);
			}finally{
				servicePool.shutdown();
			}
		}
	}

	public static void main(String[] args) {
		PosService t = PosService.getInstance();
		t.start();
	}
}
