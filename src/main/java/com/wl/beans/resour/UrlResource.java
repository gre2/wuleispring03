package com.wl.beans.resour;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlResource implements Resource {

    private  URL url;

    public  UrlResource(URL url){
        this.url=url;
    }

    public InputStream getInputStream() {

        try {
            URLConnection connection=url.openConnection();
            connection.connect();
            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
