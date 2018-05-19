package io.ride.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class IpAddressUtils {
    /**
     * @param content        请求的参数 格式为：name=xxx&pwd=xxx
     * @param encodingString 服务器端请求编码。如GBK,UTF-8等
     */
    public static Map<String, String> getAddress(String content, String encodingString) {
        // 这里调用pconline的接口
        String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
        String returnStr = getResult(urlStr, content, encodingString);
        if (returnStr != null) {
            // 处理返回的省市区信息
            String[] temp = returnStr.split(",");
            if (temp.length < 3) {
                log.info("局域网ip");
                return null;
            }
            log.info("return json ===> {}", Arrays.toString(temp));

            String country = (temp[2].split(":"))[1].replaceAll("\"", "");
            country = decodeUnicode(country);       // 国家
            String region = (temp[4].split(":"))[1].replaceAll("\"", "");
            region = decodeUnicode(region);// 省份
            String city = (temp[5].split(":"))[1].replaceAll("\"", "");
            city = decodeUnicode(city);//
            String county = (temp[6].split(":"))[1].replaceAll("\"", "");
            county = decodeUnicode(county);// 市区


            Map<String, String> info = new HashMap<>();
            info.put("country", country);
            info.put("region", region);
            info.put("city", city);
            info.put("county", county);

            return info;
        }
        return null;
    }

    /**
     * 得到ip地址归属地
     *
     * @param address ip address
     * @return info
     */
    public static Map<String, String> getAddress(String address) {
        return getAddress("ip=" + address, "UTF-8");
    }


    /**
     * @param urlStr   请求的地址
     * @param content  请求的参数 格式为：name=xxx&pwd=xxx
     * @param encoding 服务器端请求编码。如GBK,UTF-8等
     */
    private static String getResult(String urlStr, String content, String encoding) {
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();  // 新建连接实例
            connection.setConnectTimeout(2000);                     // 设置连接超时时间，单位毫秒
            connection.setReadTimeout(2000);                        // 设置读取数据超时时间，单位毫秒
            connection.setDoOutput(true);                           // 是否打开输出流 true|false
            connection.setDoInput(true);                            // 是否打开输入流true|false
            connection.setRequestMethod("POST");                    // 提交方法POST|GET
            connection.setUseCaches(false);                         // 是否缓存true|false
            connection.connect();                                   // 打开连接端口
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());                            // 打开输出流往对端服务器写数据
            out.writeBytes(content);                                // 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.flush();                                            // 刷新
            out.close();                                            // 关闭输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), encoding));        //  往对端写完数据对端服务器返回数据
            // ,以BufferedReader流来读取
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();// 关闭连接
            }
        }
        return null;
    }

    /**
     * unicode 转换成 中文
     *
     * @author fanhui 2007-3-15
     */
    private static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * 判断是否在同一个网段之中
     *
     * @param ip
     * @param cidr
     * @return
     */
    public static boolean isInRange(String ip, String cidr) {
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24)
                | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
        int mask = 0xFFFFFFFF << (32 - type);
        String cidrIp = cidr.replaceAll("/.*", "");
        String[] cidrIps = cidrIp.split("\\.");
        int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24)
                | (Integer.parseInt(cidrIps[1]) << 16)
                | (Integer.parseInt(cidrIps[2]) << 8)
                | Integer.parseInt(cidrIps[3]);

        return (ipAddr & mask) == (cidrIpAddr & mask);
    }

    public static boolean isInRange(String ip, String startIp, String endIp) {
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24)
                | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        String[] startIps = startIp.split("\\.");
        int startIpAddr = (Integer.parseInt(startIps[0]) << 24)
                | (Integer.parseInt(startIps[1]) << 16)
                | (Integer.parseInt(startIps[2]) << 8) | Integer.parseInt(startIps[3]);
        String[] endIps = endIp.split("\\.");
        int endIpAddr = (Integer.parseInt(endIps[0]) << 24)
                | (Integer.parseInt(endIps[1]) << 16)
                | (Integer.parseInt(endIps[2]) << 8) | Integer.parseInt(endIps[3]);
        return ipAddr >= startIpAddr && ipAddr <= endIpAddr;
    }

    public static void main(String[] args) {
        System.out.println(getAddress("60.12.210.99"));
        System.out.println(getAddress("192.168.3.1"));

        System.out.println(isInRange("192.168.1.10", "192.168.1.1", "192.168.1.100"));

    }
} 