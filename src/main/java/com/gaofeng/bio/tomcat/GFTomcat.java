package com.gaofeng.bio.tomcat;

import com.gaofeng.bio.tomcat.http.GFRequest;
import com.gaofeng.bio.tomcat.http.GFResponse;
import com.gaofeng.bio.tomcat.http.GFServlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GFTomcat {
    private int port = 8080;
    private ServerSocket server;
    private Map<String, GFServlet> servletMapping = new HashMap<String, GFServlet>();
    private Properties webXml = new Properties();
    /**
     * 编写步骤
     * 1.配置好启动端口，默认8080 serverSocket IP:localhost
     * 2.配置web.xml 自己写的servler继承HttpServlet
     * servlet-name
     * servlet-class
     * url-pattern
     * 3.读取配置 url -pattern和servlet建立一个映射关系
     * Map servletMapping
     */

    public void start(){
        init();
        try {
            server = new ServerSocket(this.port);
            System.out.println("高峰的 Tomcat 已启动，监听的端口是：" + this.port);
            while (true){
                Socket chient = server.accept();
                process(chient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Socket chient) throws Exception{
        InputStream is = chient.getInputStream();
        OutputStream os = chient.getOutputStream();
        //7.Request(InputStrean)/Response(OutputStrean)
        GFRequest request = new GFRequest(is);
        GFResponse response = new GFResponse(os);
        //5.从协议内容中获取url,获取与之对应的servlet
        String url = request.getUrl();
        if(servletMapping.containsKey(url)){
            servletMapping.get(url).service(request,response);
        }else {
            response.write("404 - Not Found");
        }
        os.flush();
        os.close();
        is.close();
        chient.close();


    }

    private void init() {
        try {
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF+ "web.properties");
            webXml.load(fis);
            for (Object k : webXml.keySet()) {
                String key = k.toString();
                if(key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$","");
                    String url = webXml.getProperty(key);
                    String className = webXml.getProperty(servletName+".className");
                    //单实例，多线程
                    GFServlet obj = (GFServlet) Class.forName(className).newInstance();
                    servletMapping.put(url,obj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GFTomcat tomcat = new GFTomcat();
        tomcat.start();
    }
}
