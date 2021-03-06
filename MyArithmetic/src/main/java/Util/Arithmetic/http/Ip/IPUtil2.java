package Util.Arithmetic.http.Ip;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 获取服务器IP及Mac导致
 * @author wuyl
 *
 */
public final class IPUtil2 {

	private IPUtil2() {
	}

	/**
	 * 获取服务器IP地址
	 * @return
	 */
	public static String getLocalIP() {
		String ip = "";
		InetAddress inetAddress = null;
		try {
			boolean bFindIP = false;
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				if (bFindIP) {
					break;
				}
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					inetAddress = (InetAddress) ips.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
						bFindIP = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		if (null != inetAddress) {
			ip = inetAddress.getHostAddress();
		}
		return ip;
	}

	/**
	 * 获取服务器IP地址(多网卡)
	 * @return
	 */
	public static List<String> getLocalIPs() {
		InetAddress inetAddress = null;
		List<String> ipList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					inetAddress = (InetAddress) ips.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
						ipList.add(inetAddress.getHostAddress());
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return ipList;
	}

	/**
	 * 获取服务器Mac地址
	 */
	public static String getMacId() {
		String macId = "";
		InetAddress inetAddress = null;
		NetworkInterface ni = null;
		try {
			boolean bFindIP = false;
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				if (bFindIP) {
					break;
				}
				ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					inetAddress = (InetAddress) ips.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
						bFindIP = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		if (null != inetAddress) {
			try {
				macId = getMacFromBytes(ni.getHardwareAddress());
			} catch (SocketException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return macId;
	}

	/**
	 * 获取服务器Mac地址(多网卡)
	 */
	public static List<String> getMacIds() {
		InetAddress inetAddress = null;
		NetworkInterface ni = null;
		List<String> macList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					inetAddress = (InetAddress) ips.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
						macList.add(getMacFromBytes(ni.getHardwareAddress()));
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return macList;
	}

	private static String getMacFromBytes(byte[] bytes) {
		StringBuffer mac = new StringBuffer();
		byte currentByte;
		boolean first = false;
		for (byte b : bytes) {
			if (first) {
				mac.append("-");
			}
			currentByte = (byte) ((b & 240) >> 4);
			mac.append(Integer.toHexString(currentByte));
			currentByte = (byte) (b & 15);
			mac.append(Integer.toHexString(currentByte));
			first = true;
		}
		return mac.toString().toUpperCase();
	}


	/**
	 * 获取登陆端类型
	 *
	 * @param request
	 * @return
	 */
	public static String getTerminal(HttpServletRequest request) {
		String terminal = request.getHeader("User-Agent");
		if (terminal.contains("Windows NT")) {
			terminal = "PC端";
		}
		else {
			terminal = "移动端";
		}
		return terminal;
	}








	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	public static final String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}


	/**
	 * 获取本机Mac地址
	 * @return
	 */
	public static final String getMacAddress() {
		String line="";
		String MacAddress="";
		InputStreamReader is=null;
		LineNumberReader ln=null;
		try {
			Process process = Runtime.getRuntime().exec("ipconfig /all");
			is = new InputStreamReader(process.getInputStream());
			ln = new LineNumberReader(is);
			while ((line = ln.readLine()) != null){
				if (line.indexOf("Physical Address") > 0) {
					MacAddress = line.substring(line.indexOf("-") - 2);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		} finally{
			try{
				if(is!=null){
					is.close();
					is=null;
				}
				if(ln!=null){
					ln.close();
					ln=null;
				}
			}catch(Exception e){
				throw new RuntimeException(e.getMessage(),e);
			}
		}
		return MacAddress;
	}


	/**
	 * 根据IP获取Mac地址
	 * @param ip
	 * @return
	 * @throws IOException
	 */
	public static final String getMacAddress(String ip){
		if("localhost".equals(ip)||"127.0.0.1".equals(ip)){
			return getMacAddress();
		}
		String line="";
		String macAddress = "";
		InputStreamReader is = null;
		LineNumberReader ln = null;
		try{
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			is = new InputStreamReader(p.getInputStream());
			ln = new LineNumberReader(is);
			for (int i = 1; i < 100; i++) {
				line = ln.readLine();
				if (line != null) {
					if (line.indexOf("MAC Address") > 1) {
						macAddress = line.substring(line.indexOf("MAC Address") + 14, line.length());
						break;
					}
				}
			}
		}catch(IOException e){
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			try{
				if(ln!=null){
					ln.close();
					ln=null;
				}
				if(is!=null){
					is.close();
					is=null;
				}
			}catch(Exception e){
				throw new RuntimeException(e.getMessage(),e);
			}
		}
		return macAddress;
	}

}
