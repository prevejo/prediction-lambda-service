package br.ucb.prevejo.shared.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class IOUtil {

    public static byte[] unzip(byte[] bytes) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        GZIPInputStream input = new GZIPInputStream(bais);

        byte[] buffer = new byte[4096];

        int len;
        while ((len = input.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }

        return baos.toByteArray();
    }

}
