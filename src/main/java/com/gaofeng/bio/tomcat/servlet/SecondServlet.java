package com.gaofeng.bio.tomcat.servlet;

import com.gaofeng.bio.tomcat.http.GFRequest;
import com.gaofeng.bio.tomcat.http.GFResponse;
import com.gaofeng.bio.tomcat.http.GFServlet;

public class SecondServlet extends GFServlet {

    protected void doPost(GFRequest request, GFResponse response) throws Exception {
        this.doPost(request,response);
    }

    protected void doGet(GFRequest request, GFResponse response) throws Exception {
        response.write("This is Second Serlvet");
    }
}
