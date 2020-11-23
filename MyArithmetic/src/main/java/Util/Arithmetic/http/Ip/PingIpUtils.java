package com.sugon.sgcl.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PingIpUtils {

	private static Logger logger = LoggerFactory.getLogger(PingIpUtils.class);
	/**
	 * @param ipAddress  ip��ַ
	 * @param pingTimes  ����(һ��ping,�Է����ص�ping�Ľ���Ĵ���)
	 * @param timeOut    ��ʱʱ�� ��λms(ping��ͨ,���õĴ˴�ping����ʱ��)
	 * @return
	 */
	public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
		BufferedReader in = null;
		String pingCommand = null;
		Runtime r = Runtime.getRuntime();
		String osName = System.getProperty("os.name");
		logger.info("��Ŀ����ϵͳ��:" +osName);
		if(osName.contains("Windows")){
			//��Ҫִ�е�ping����,��������windows��ʽ������
			pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;
		}else{
			//��Ҫִ�е�ping����,��������Linux��ʽ������
			//-c:����,-w:��ʱʱ��(��λ/ms)  ping -c 10 -w 0.5 192.168.120.206
			pingCommand = "ping " + " -c " + "4" + " -w " + "2 " + ipAddress;
		}
		try {
			//ִ�������ȡ���
			Process p = r.exec(pingCommand);
			if (p == null) {
				return false;
			}
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			int connectedCount = 0;
			String line = null;
			while ((line = in.readLine()) != null) {
				connectedCount += getCheckResult(line,osName);
			}
			//�����������=23 ms ttl=64(TTL=64 Windows)����������,���ֵĴ���=���Դ����򷵻���
			//return connectedCount == pingTimes;
			logger.info("pingͨ�豸IP�Ĵ���Ϊ:" +connectedCount);
			return connectedCount >= 2 ? true : false;
		} catch (Exception ex) {
			ex.printStackTrace(); //�����쳣�򷵻ؼ�
			return false;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//��line����=18 ms ttl=64����,˵���Ѿ�pingͨ,����1,��t����0.
	private static int getCheckResult(String line,String osName) {
		if(osName.contains("Windows")){
			if(line.contains("TTL=")){
				return 1;
			}
		}else{
			if(line.contains("ttl=")){
				return 1;
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		String ipAddress = "www.sigchina.com";
		String osName = System.getProperty("os.name");
		System.out.println(ping("10.10.10.180",3,3000));



	}
}