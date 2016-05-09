package com.exmyth.wechat.test.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

//获取以太网ip/mac
public class XAddressUtil {  
  
    public static String loadFileAsString(String filePath) throws java.io.IOException{  
        StringBuffer fileData = new StringBuffer(1000);  
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024]; int numRead=0;   
        while((numRead=reader.read(buf)) != -1){   
            String readData = String.valueOf(buf, 0, numRead);   
            fileData.append(readData);   
        }   
        reader.close();   
        return fileData.toString();  
    }/** Get the STB MacAddress*/  
      
    public static String getMacAddress(){  
        try {   
            return loadFileAsString("/sys/class/net/eth0/address") .toUpperCase().substring(0, 17);  
            } catch (IOException e) {
                e.printStackTrace();   
                return null;  
            }  
    }  
      
    public static String getLocalIpAddress() {  
        try {  
                for (Enumeration<NetworkInterface> en = NetworkInterface
                                .getNetworkInterfaces(); en.hasMoreElements();) {  
                        NetworkInterface intf = en.nextElement();   
                        for (Enumeration<InetAddress> enumIpAddr = intf
                                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {  
                                InetAddress inetAddress = enumIpAddr.nextElement();  
                                if (!inetAddress.isLoopbackAddress()
                                        && isIPv4Address(inetAddress.getHostAddress())) {
                                        return inetAddress.getHostAddress().toString();  
                                }  
                        }  
                }  
        } catch (SocketException ex) {
                System.out.println("WifiPreference IpAddress" + ex.toString());
        }  
        return null;  
    }

    private static final String IPV4_BASIC_PATTERN_STRING =
            "(([1-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){1}" + // initial first field, 1-255
                    "(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){2}" + // following 2 fields, 0-255 followed by .
                    "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])"; // final field, 0-255

    private static final Pattern IPV4_PATTERN =
            Pattern.compile("^" + IPV4_BASIC_PATTERN_STRING + "$");

    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

}  