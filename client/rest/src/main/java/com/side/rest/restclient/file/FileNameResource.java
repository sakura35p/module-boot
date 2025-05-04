package com.side.rest.restclient.file;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class FileNameResource extends ByteArrayResource {

    private final String filename;

    public FileNameResource(byte[] byteArray, String imageFullUrl) {
        super(byteArray);
        this.filename = imageFullUrl.substring(imageFullUrl.lastIndexOf('/') + 1);
    }

    public FileNameResource(Resource resource, String imageFullUrl) throws IOException {
        super(resource.getInputStream().readAllBytes());
        this.filename = imageFullUrl.substring(imageFullUrl.lastIndexOf('/') + 1);
    }


    @Override
    public String getFilename() {
        return this.filename;
    }

}
