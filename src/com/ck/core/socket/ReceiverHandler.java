package com.ck.core.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.PostChannel;
import org.jpos.iso.packager.GenericPackager;

import com.ck.core.IPosRequest;
import com.ck.core.exception.POSException;
import com.common.util.StringHelper;


public class ReceiverHandler implements Runnable {
	
	private Socket socket;

	private static final Logger logger = Logger.getLogger(ReceiverHandler.class);
	
	public ReceiverHandler(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			doHandler();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (POSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					logger.error("failed to colse socket["+socket+"]", e);
				}
				socket = null;
			}
		}
	}
	/**
	 * 接收支付终端发过来的请求，并调用银行通道完成支付
	 * 
	 * @throws IOException
	 * @throws POSException 
	 * @throws ISOException 
	 */
	private void doHandler() throws IOException, POSException{
		DataInputStream ins = new DataInputStream(socket.getInputStream());
		// 1-1）接收数据
		String data = ins.readUTF();
		if(StringHelper.isEmpty(data)){
			//TODO
		}
		// 1-2）完成平台数据组装
		IPosRequest request = buildPosRequest(data);
		// 1-3）外发银行
		ISOMsg resFromBank = sendISOPackage(request);
		// 1-4）重组数据，返回支付终端完成支付
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		String result = reloadISOPackage(resFromBank);
		out.writeUTF(result);
        out.flush();  
        out.close(); 
		ins.close();
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	private IPosRequest buildPosRequest(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws POSException 
	 */
	private ISOMsg sendISOPackage(IPosRequest request) throws POSException {
		ISOMsg msg = request.getISOMsg();
		// ISO报文定义格式文件
		ISOPackager p = null;
		try {
			p = new GenericPackager("conf/ISO/iso93ascii.xml");
		} catch (ISOException e) {
			throw new POSException(0,"",e);// TODO
		}
		msg.setPackager(p);
		ISOChannel channel = new PostChannel("127.0.0.1", 36010, p); //TODO
		channel.setName(""); // 调用的具体银行通道名 TODO
		ISOMsg res = null;
		try {
			channel.connect();
			channel.send(msg); // 发送
			res = channel.receive();// 接收
		} catch (IOException e) {
			logger.error("failed to send msg["+msg.toString()+"] by channel ["+channel+"]", e);
			throw new POSException(0, "", e); //TODO
		} catch (ISOException e) {
			logger.error("failed to send msg["+msg.toString()+"] by channel ["+channel+"]", e);
			throw new POSException(0, "", e); //TODO
		}finally{
			if(channel != null){
				try {
					channel.disconnect();
				} catch (IOException e) {
					logger.error("failed to disconnect channel ["+channel+"]",e);
				}
			}
		}
		System.out.println(res.getValue());
		return res;
	}

	private String reloadISOPackage(ISOMsg resFromBank) {
		// TODO Auto-generated method stub
		return null;
	}
}
