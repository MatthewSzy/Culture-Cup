package com.CultureCup.Services.Movie.ImageDownloadClass;

import com.CultureCup.Exceptions.RequestErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloadClass {

    static Logger logger = LoggerFactory.getLogger(ImageDownloadClass.class);

    public static byte[] getImage(String path) {

        try {
            URL url = new URL(path);
            InputStream inputStream = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024]; int n;
            while (-1 != (n = inputStream.read(buffer))) byteArrayOutputStream.write(buffer, 0, n);

            byteArrayOutputStream.close();
            inputStream.close();
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            logger.error("The query to the movie database could not be completed");
            throw new RequestErrorException("Nie można zrealizować zapytania do bazy filmów!");
        }
    }
}
