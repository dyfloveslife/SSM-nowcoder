package com.nowcoder.community;

import java.io.IOException;

public class WkTests {
    public static void main(String[] args) {
        String cmd = "d:/work/wkhtmltopdf/bin/wkhtmltoimage --quality 75 https://www.nowcoder.com d:/work/data/wk-images/1.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("Ok!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
