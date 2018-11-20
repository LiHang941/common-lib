package com.github.lihang941.vertx.test;

import com.alibaba.fastjson.JSONObject;
import com.github.lihang941.common.vertx.Controller;
import com.github.lihang941.vertx.rest.GET;
import com.github.lihang941.vertx.rest.Query;
import com.github.lihang941.vertx.rest.URL;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/20
 */
@Controller
public class TestResource {

    @GET
    @URL("/test")
    public JSONObject test(@Query(value = "d", defaultValue = "") String d) {
        return new JSONObject().fluentPut("flag", d.equals("")).fluentPut("d", d);
    }

}
