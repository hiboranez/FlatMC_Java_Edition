package Utils.TCP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ServerLoad {
    public static String[] loadServer(String name) {
        String path = System.getProperty("user.dir") + "/data/server/" + name + ".txt";
        String[] address = new String[2];
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ip = ")) {
                    address[0] = line.substring(5);
                } else if (line.startsWith("port = ")) {
                    address[1] = line.substring(7);
                }
                line = null;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close(); // 在finally块中确保关闭文件流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return address;
    }
}
