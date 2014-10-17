package core;


import java.io.IOException;
import java.util.Date;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISODate;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.channel.PostChannel;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.LogEvent;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

public class JposTest implements LogSource {
	
	private Logger logger;
	private String string;

	public JposTest(Logger logger, String string) {
		this.logger = logger;
		this.string = string;
	}

	public static void main(String[] args) throws IOException, ISOException {
		ISOMsg m = new ISOMsg();
		m.setDirection(2);
		m.set(0, "0200");// 交易类型
		m.set(2, "");
		m.set(3, "310000");
		m.set(11, "123456");
		m.set(12, ISODate.getTime(new Date()));
		m.set(13, ISODate.getDate(new Date()));
		m.set(14, "1249");
		m.set(22, "011");
		m.set(41, "6001201");
		m.set(42, "6001201");
		ISOPackager p = new GenericPackager("conf/ISO/iso93ascii.xml");// xml文件会附在附件中
		m.setPackager(p);

		byte[] b = m.pack();// 这一步仅仅是为了在控制台输出结果而写。可以省略

		Logger logger = new Logger();// 这一步仅仅是为了在控制台输出结果而写。可以省略
		logger.addListener(new SimpleLogListener(System.out));// 这一步仅仅是为了在控制台输出结果而写。可以省略
		JposTest jt = new JposTest(logger, "AAAA");// 这一步仅仅是为了在控制台输出结果而写。可以省略
		
		LogEvent evt = new LogEvent(jt, "Test");// 这一步仅仅是为了在控制台输出结果而写。可以省略
		evt.addMessage(ISOUtil.hexString(b));// 这一步仅仅是为了在控制台输出结果而写。可以省略
		evt.addMessage(m);// 这一步仅仅是为了在控制台输出结果而写。可以省略

		ISOChannel channel = new PostChannel("127.0.0.1", 36010, p);
		channel.connect();
		channel.send(m);// 发送
		ISOMsg r = channel.receive();// 接收
		System.out.println(r.getValue());
		evt.addMessage(r);// 这一步仅仅是为了在控制台输出结果而写。可以省略
		Logger.log(evt);// 这一步仅仅是为了在控制台输出结果而写。可以省略
		channel.disconnect();

	}

	@Override
	public void setLogger(Logger logger, String realm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRealm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

}
