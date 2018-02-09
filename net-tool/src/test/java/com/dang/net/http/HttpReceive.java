package com.dang.net.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Date Create in 2018/2/7
 */
public class HttpReceive  implements Runnable{

    public static void main(String[] args) {
        HttpReceive receive = new HttpReceive();
        Thread thread = new Thread(receive);
        thread.start();
    }

    @Override
    public void run() {
        ServerSocket server;
        Socket socket;
        try{
            server=new ServerSocket(80);
            System.out.println("正在等待80端口的请求");
            while(true){
                socket=server.accept();
                if(socket!=null){
                    new Thread(new TestReveiveThread(socket)).start();
                }
            }
        }catch (Exception e) {
            System.err.println("异常");
        }
    }
}

class TestReveiveThread implements Runnable{
    Socket socket;
    public TestReveiveThread(Socket s) {
        socket=s;
    }
    public void run() {
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(),"utf-8");
            String line=null;
            StringBuffer stringBuffer = new StringBuffer();
            int contentLength = -1;
            int length = 0;
            while((line=bufferedReader.readLine())!=null){
                System.out.println(line);
                if(line.contains("Content-Length")){
                   length = Integer.parseInt(line.substring(16).trim());
                    System.out.println("find length : " + length);
                }
                stringBuffer.append(line + "\r\n");
                System.out.println(stringBuffer.length());
                if(line.equals("")){
                    break;
                }

            }
            //模拟http请求到网站，然后把内容转发给当前的http请求  AOP  service   | Interceptor Contren
            //          String path = "/";
            //          String host = "www.oschina.net";
            //          int port = 80;
            //          Socket socket2 = new Socket(host, port);
            //          OutputStreamWriter osw2 = new OutputStreamWriter(socket2.getOutputStream(),"utf-8");
            //          osw2.write("GET " + path + " HTTP/1.1\r\n");
            //          osw2.write("Host: " + host + " \r\n");
            //          osw2.write("\r\n");
            //          osw2.flush();
            //          BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(socket2.getInputStream(), "utf-8"));
            //          String line2 = null;
            //          while ((line2 = bufferedReader2.readLine()) != null) {
            //              osw.write(line2+"\r\n");
            //          }
            //          bufferedReader2.close();
            //          osw2.close();
            //          socket2.close();
            osw.write("HTTP/1.1 200 OK\r\n");
            osw.write("Server: Apache-Coyote/1.1\r\n");
            osw.write("Set-Cookie: JSESSIONID=03493794995CE31A0F131787B6C6CBB2; Path=/; HttpOnly\r\n");
            osw.write("Content-Type: text/html;charset=UTF-8\r\n");
//            osw.write("Transfer-Encoding: chunked\r\n");
//            osw.write("Transfer-Encoding: chunked\r\n");
            osw.write("Date: Tue, 19 May 2015 02:48:27 GMT\r\n");
            osw.write("\r\n");
            osw.write( Integer.toHexString(stringBuffer.length() + 146)+"\r\n");
            osw.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
            osw.write("<HTML>\r\n");
            osw.write("  <HEAD><TITLE>A Servlet</TITLE></HEAD>\r\n");
            osw.write("  <BODY>\r\n");
            osw.write("\r\n");
            osw.write(stringBuffer.toString());
            osw.write("  </BODY>\r\n");
            osw.write("</HTML>\r\n");
            osw.write("\r\n");
            osw.write("0");
            osw.write("\r\n");
            osw.write("\r\n");
            osw.flush();
            bufferedReader.close();
            osw.close();
            socket.close();
        }catch (Exception e) {
            System.out.println("客户端接受异常"+e.getMessage());
        }
    }
}
