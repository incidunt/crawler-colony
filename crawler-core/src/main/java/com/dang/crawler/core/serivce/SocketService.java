package com.dang.crawler.core.serivce;

import com.dang.crawler.resources.mysql.dao.SqlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * Created by dang on 17-6-20.
 * 监听Socket连接  以用于命令行操作crawler相关信息
 */
@Service
public class SocketService implements Runnable {
    private Logger logger = LoggerFactory.getLogger(SocketService.class);
    @Resource
    private SqlMapper sqlMapper;

    public static void main(String[] args) {
        SocketService socketService = new SocketService();
        //1、创建一个服务器端Socket，即SocketService
        socketService.run();
    }

    public void oneServer(ServerSocket server) {
        Socket socket = null;
        try {
            //2、调用accept()方法开始监听，等待客户端的连接
            socket = server.accept();
            //3、获取输入流，并读取客户端信息
            String line;
            logger.info("SocketService start");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //由Socket对象得到输入流，并构造相应的BufferedReader对象
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            //由Socket对象得到输出流，并构造PrintWriter对象
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            //由系统标准输入设备构造BufferedReader对象
            while ((line = in.readLine()) != null) {
                logger.info("<<" + line);
                if (line.equals("exit")) {
                    break;
                }
                try {
                    String res = commandLine(line);
                    writer.append(res + "\n<<");
                } catch (Exception e) {
                    logger.error("", e);
                    writer.append(e.toString() + "\n");
                }
                writer.flush();
            }
            logger.info("SocketService exit");
            in.close();
            writer.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String commandLine(String line) {
        String[] param = line.split(" ");
        if (line.contains("select")||line.contains("SELECT")) {
            StringBuffer buffer = new StringBuffer();
            List<Map<String, Object>> res = sqlMapper.select(line);
            for (Map map : res) {
                buffer.append(map.toString() + "\n");
            }
            return buffer.toString();
        } else if (line.contains("update") || line.contains("delete") || line.contains("insert")||line.contains("UPDATE") || line.contains("DELETE") || line.contains("INSERT")) {
            sqlMapper.delete(line);
            return "ok";
        } else if (param[0].contains("job")) {
            if (param.length == 3) {
                if (param[2].equals("start")) {
                    sqlMapper.delete("update crawler_job set nextStartDate='2005-06-15 11:11:11' ,status =  'standby'  where jobId = '" + param[1] + "';");
                } else {
                    sqlMapper.delete("update crawler_job set status='" + param[2] + "' where jobId = '" + param[1] + "';");
                }
            }
            return "ok";
        } else if (param[0].contains("log")) {
            if (param.length == 2) {
                StringBuffer buffer = new StringBuffer();
                List<Map<String, Object>> res = sqlMapper.select("SELECT * FROM crawler_log  WHERE jobId = '" + param[1] + "' ORDER BY id;");
                for (Map map : res) {
                    buffer.append(map.toString() + "\n");
                }
                return buffer.toString();
            }
            return "error";

        } else if (param[0].contains("ls")) {
            StringBuffer buffer = new StringBuffer();
            List<Map<String, Object>> res = sqlMapper.select("SELECT * FROM crawler_job ORDER BY id;");
            for (Map map : res) {
                buffer.append(map.toString() + "\n");
            }
            return buffer.toString();

        } else if (line.contains("help")) {
            return HELP;
        } else {
            return "";
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(5678);
            while (true) {
                logger.info("SocketService listen");
                oneServer(server);
            }
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    private final String HELP = "\t直接输入sql语句\n" +
            "\tjob [jobId] [start|kill|stop|goOn]     对job进行调度\n" +
            "\tlog [jobId]    查看job 的log\n" +
            "\tls job     查看所有job";
}
