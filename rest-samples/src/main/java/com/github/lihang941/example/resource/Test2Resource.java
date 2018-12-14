package com.github.lihang941.example.resource;

import com.github.lihang941.common.bean.IdDto;
import com.github.lihang941.common.page.OffsetBean;
import com.github.lihang941.common.vertx.Controller;
import com.github.lihang941.common.vertx.RequestTool;
import com.github.lihang941.example.convert.Test2Convert;
import com.github.lihang941.example.dto.Test2Dto;
import com.github.lihang941.example.entity.Test2;
import com.github.lihang941.example.service.Test2Service;
import com.github.lihang941.vertx.rest.*;
import com.github.pagehelper.Page;
import io.vertx.core.http.HttpServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-12-03 10:02
 */
//@OpenAPIDefinition(info =
//@Info(
//        title = "the title",
//        version = "0.0",
//        description = "My API"
//)
//)
@URL("test2")
@Controller
public class Test2Resource {

    @Autowired
    private Test2Service test2Service;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


//    @Operation(summary = "Get user by user name",
//            responses = {
//                    @ApiResponse(description = "The user",
//                            content = @Content(mediaType = "application/json",
//                                    schema = @Schema(implementation = Test2Dto.class))),
//                    @ApiResponse(responseCode = "400", description = "User not found")})
    @POST
    public IdDto add(@Body Test2Dto test2Dto) {
        Test2 test2 = Test2Convert.toTest2().apply(test2Dto);
        test2Service.insertSelective(test2);
        return new IdDto(test2.getId());
    }


    @URL(":id")
    @POST
    public void update(@Path("id") Long id, @Body Test2Dto test2Dto) {
        Test2 test2 = Test2Convert.toTest2().apply(test2Dto);
        test2.setId(id);
        test2Service.updateByPrimaryKeySelective(test2);
    }

    @URL(":id")
    @DELETE
    public void delete(@Path("id") Long id) {
        test2Service.deleteByPrimaryKey(id);
    }

    @GET
    @URL(":id")
    public Test2Dto get(@Path("id") Long id) {
        Test2 test2 = test2Service.selectByPrimaryKey(id);
        if (test2 == null) {
            throw new NoSuchElementException();
        }
        return Test2Convert.toTest2Dto().apply(test2);
    }


    @URL("list")
    @GET
    public void get(@HeaderMap Map<String, String> headers, @Context Serializer serializer, @Context HttpServerResponse response) {
        OffsetBean offsetBean = RequestTool.toOffsetBean(headers);
        Page<Test2> pageList = test2Service.offsetList(offsetBean);
        RequestTool.pageEnd(pageList.getTotal(),
                pageList.getResult()
                        .stream()
                        .map(Test2Convert.toTest2Dto())
                        .collect(Collectors.toList())
                , response, serializer);
    }

}
