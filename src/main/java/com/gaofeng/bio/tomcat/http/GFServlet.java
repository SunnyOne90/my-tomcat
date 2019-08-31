package com.gaofeng.bio.tomcat.http;

public abstract class GFServlet {
    public void service(GFRequest request,GFResponse response)throws Exception{
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }
    //由自类自行实现
    protected abstract void doPost(GFRequest request, GFResponse response)throws Exception;

    protected abstract void doGet(GFRequest request, GFResponse response)throws Exception;
    
}
