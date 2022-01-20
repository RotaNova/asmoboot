package com.rotanava.framework.util;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: ntp工具类
 * @author: richenLi
 * @create: 2021-03-29 15:04
 **/
public class NtpUtil {

    public static boolean testNtp(String address, Integer port) {
        try {


            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress timeServerAddress = InetAddress.getByName(address);
            TimeInfo timeInfo = timeClient.getTime(timeServerAddress,port);
            TimeStamp timeStamp = timeInfo.getMessage().getTransmitTimeStamp();
            Date ntpdate = timeStamp.getDate();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean testNtp(String address) {
       return testNtp(address,123);
    }
}
