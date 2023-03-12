package org.acme.utils;

import javax.ws.rs.FormParam;

public class FileFormItem {
    @FormParam("file")
    public byte[] file;
}
