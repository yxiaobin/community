package com.newcoder.community;

import java.io.IOException;

public class WKTest {
    public static void main(String[] args) {
        String cmd = "d:/java/wkhtmltopdf/bin/wkhtmltoimage --quality 75 https://www.nowcoder.com D:/java/workspace/data/wk-image/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
