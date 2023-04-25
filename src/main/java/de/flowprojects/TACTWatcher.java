package de.flowprojects;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

public class TACTWatcher {
    public static void main(String[] args) throws MalformedURLException {
        final String WOWDEV2_TACT_CONTENT_URL = "http://us.patch.battle.net:1119/wowdev2/versions";
        File tempTACTFile = getTACTFileContent(WOWDEV2_TACT_CONTENT_URL);

        if(tempTACTFile != null) {
            readTempTACTFile(tempTACTFile);
        }
    }


    private static File createTempTACTFile() throws IOException {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
        String filename = "tactfile_" + date.format(new Date());
        File tempFile = File.createTempFile(filename, ".txt", new File("temp"));
        tempFile.deleteOnExit();

        return tempFile;
    }

    /**
     * Get content from TACT URL and write it into temporary file
     * (see <a href="https://wowdev.wiki/TACT#HTTP_URLs">here</a>)
     * @param tactContentURL TACT URL
     */
    private static File getTACTFileContent(String tactContentURL) {
        try {
            File file = createTempTACTFile();

            URL url = new URL(tactContentURL);
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

            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void readTempTACTFile(File tactFile) {
        try(Stream<String> lines = Files.lines(tactFile.toPath())) {
            System.out.println("Reading file content:\n");
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}