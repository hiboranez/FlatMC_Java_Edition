package Utils.TCP;

import Base.GameFrame;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {
    private static Socket socket;
    private static Thread listenerThread;

    public static boolean startListeningToServer(String ip, int port, GameFrame game) {
        try {
            // 创建套接字并连接到服务器
            socket = new Socket(ip, port);

            // 获取输入流，用于读取服务器发送的信息
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            // 启动线程监听服务器发送的信息
            listenerThread = new Thread(() -> {
                try {
                    // 不断监听服务器发送的信息
                    while (true) {
                        String receivedMessage = reader.readLine();
                        if (receivedMessage != null) {
                            Command.ReceivedFromServerCommand(receivedMessage);
                        }
                    }
                } catch (IOException e) {
                    // 检查是否是由于服务器关闭而导致的异常
                    if ("Connection reset".equals(e.getMessage())) {
                        // 服务器关闭时调用serverClosed()方法
                        game.serverClosed();
                    } else {
                        e.printStackTrace();
                    }
                }
            });

            // 启动线程
            listenerThread.start();

            // 返回连接成功的标志
            return true;
        } catch (IOException e) {
            // 连接失败时返回标志
            e.printStackTrace();
            return false;
        }
    }

    public static void sendMessageToServer(String message, String playerName) {
        try {
            // 获取输出流，用于向服务器发送信息
            OutputStream outputStream = socket.getOutputStream();
            // 使用 PrintWriter 包装输出流，并指定字符编码为UTF-8
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
            // 发送消息给服务器
            writer.print(playerName + " " + message);
            writer.flush(); // 立即刷新缓冲区，确保消息被完整发送
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeTCP() {
        // 发送断开连接通知给服务器
        if (listenerThread != null)
            listenerThread.interrupt();
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
