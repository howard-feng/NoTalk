package com.notalk.model;

import com.google.gson.Gson;
import com.notalk.MainApp;
import com.notalk.view.MainContentTalkController;
import com.notalk.view.RootLayoutController;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpClientThread extends Thread{
    private RootLayoutController rootLayoutController;
    static private Socket clientSocket;
    public static String HOSTNAME = "112.74.62.166";
//    public static String HOSTNAME = "127.0.0.1";
    public static int PORT = 8888;
    public boolean SENDFLAG = false;
    public PrintWriter pw;

//    public static void main(String[] args) throws Exception {
//        TcpClient client = new TcpClient();
//        client.start();
//    }


    public void setRootLayoutController(RootLayoutController rootLayoutController) {
        this.rootLayoutController = rootLayoutController;
    }

    @Override
    public void run() {
        try {
            System.out.println("connecting……");
            clientSocket = new Socket(TcpClientThread.HOSTNAME, TcpClientThread.PORT);
            Scanner scanner = new Scanner(System.in);
//            setName(scanner);

            // 接收服务器端发送过来的信息的线程启动
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ListenrServser());

            // 建立输出流，给服务端发信息
            this.pw = new PrintWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"), true);
            //首次连接 发送账号
            pw.println(MainApp.Mysid);

        } catch(Exception e) {
            e.printStackTrace();
        }
//        finally {
//            if (clientSocket !=null) {
//                try {
//                    clientSocket.close();
//                } catch(IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }



    private void setName(Scanner scan) throws Exception {
        String name;
        //创建输出流
        PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"),true);
        //创建输入流
        BufferedReader br = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));

        while(true) {
            System.out.println("请创建您的昵称：");
            name = scan.nextLine();
            if (name.trim().equals("")) {
                System.out.println("昵称不得为空");
            } else {
                pw.println(name);
                String pass = br.readLine();
                if (pass != null && (!pass.equals("OK"))) {
                    System.out.println("昵称已经被占用，请重新输入：");
                } else {
                    System.out.println("昵称“"+name+"”已设置成功，可以开始聊天了");
                    break;
                }
            }
        }
    }

    // 循环读取服务端发送过来的信息并输出到客户端的控制台
    class ListenrServser implements Runnable {
        Gson gson = new Gson();
        @Override
        public void run() {
            try {
                BufferedReader br = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
                String msgString;
                while((msgString = br.readLine())!= null) {
                    rootLayoutController.handleMsg(msgString);
                    System.out.println(msgString);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * 发送消息
    * */
    public int sendMsg(String msg){
            this.pw.println(msg);
        return 0;
    }


}
