package com.tuling.crypto;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @Author:zhgw
 * @Date:15:34,2023/10/26
 */
public class PipedDemo {

    public static void main(String[] args) throws IOException {
        PipedReader in = new PipedReader();
        PipedWriter out = new PipedWriter();
        out.connect(in);
        Thread thread = new Thread(new Print(in));
        thread.start();
        int receive = 0;

        while((receive = System.in.read()) != -1) {
            out.write(receive);
        }
        out.close();
    }

    static class Print implements Runnable {

        private PipedReader reader;
        public Print(PipedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            int re = 0;
            try {
                while ((re = reader.read()) != -1) {
                    System.out.println((char)re);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
