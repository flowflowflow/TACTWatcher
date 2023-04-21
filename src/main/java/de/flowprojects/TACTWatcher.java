package de.flowprojects;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TACTWatcher {
    public static void main(String[] args) {
        //System.out.println("Hello world!");
        try {
            File file = createTempTACTFile();

            URL url = new URL("http://us.patch.battle.net:1119/wowdev2/versions");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream input = con.getInputStream();

            byte[] buffer = new byte[4096];
            int n;

            OutputStream output = new FileOutputStream( file );
            while ((n = input.read(buffer)) != -1)
            {
                output.write(buffer, 0, n);
            }
            output.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static File createTempTACTFile() throws IOException {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
        String filename = "tactfile_" + date.format(new Date());
        return File.createTempFile(filename, ".txt", new File("temp"));
    }
}