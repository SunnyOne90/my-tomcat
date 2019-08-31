package com.gaofeng.bio.tomcat.http;

import java.io.OutputStream;

public class GFResponse {
    private OutputStream out;
    public GFResponse(OutputStream os) {
        this.out = os;
    }

    public void write(String s) throws Exception {
        //用的是http协议，输出也要遵循HTTP协议，给到有个状态码200
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 200 OK\n")
                .append("Content-Type: text/html;\n")
                .append("\r\n")
                .append(s);
        out.write(sb.toString().getBytes());
    }
}
