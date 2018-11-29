package com.github.lihang941.example.resource;

import com.github.lihang941.common.vertx.Controller;
import com.github.lihang941.example.service.TestService;
import com.github.lihang941.example.convert.TestConvert;
import com.github.lihang941.example.dto.TestDto;
import com.github.lihang941.example.entity.Test;
import com.github.lihang941.vertx.rest.*;
import com.github.lihang941.common.page.OffsetBean;
import com.github.lihang941.common.vertx.RequestTool;
import com.github.lihang941.common.bean.IdDto;
import com.github.pagehelper.Page;
import io.vertx.core.http.HttpServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-11-29 17:54
 */
@URL("test")
@Controller
public class TestResource {

    @Autowired
    private TestService testService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @POST
    public IdDto add(@Body TestDto testDto) {
        Test test= TestConvert.toTest().apply(testDto);
        testService.insertSelective(test);
        return new IdDto(test.getId());
    }

    @URL(":id")
    @POST
    public void update(@Path("id") Long id,@Body TestDto testDto) {
        Test test= TestConvert.toTest().apply(testDto);
        test.setId(id);
        testService.updateByPrimaryKeySelective(test);
    }

    @URL(":id")
    @DELETE
    public void delete(@Path("id") Long id) {
        testService.deleteByPrimaryKey(id);
    }

    @GET
    @URL(":id")
    public TestDto get(@Path("id") Long id) {
        Test test = testService.selectByPrimaryKey(id);
        if(test == null){
            throw new NoSuchElementException();
        }
        return TestConvert.toTestDto().apply(test);
    }


    @URL("list")
    @GET
    public void get(@HeaderMap Map<String, String> headers, @Context Serializer serializer, @Context HttpServerResponse response) {
        OffsetBean offsetBean = RequestTool.toOffsetBean(headers);
        Page<Test> pageList = testService.offsetList(offsetBean);
        RequestTool.pageEnd(pageList.getTotal(),
                        pageList.getResult()
                        .stream()
                        .map(TestConvert.toTestDto())
                        .collect(Collectors.toList())
                    , response, serializer);
    }

}
