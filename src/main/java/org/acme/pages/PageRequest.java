package org.acme.pages;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class PageRequest {
    @QueryParam("pageCurrent")
    @DefaultValue("0")
    public int pageCurrent;

    @QueryParam("pageSize")
    @DefaultValue("5")
    public int pageSize;
}
