package com.github.lihang941.example.resource;

import com.github.lihang941.common.bean.IdDto;
import com.github.lihang941.common.page.OffsetBean;
import com.github.lihang941.common.vertx.RequestTool;
import com.github.lihang941.example.convert.TestConvert;
import com.github.lihang941.example.dto.TestDto;
import com.github.lihang941.example.entity.Test;
import com.github.lihang941.example.service.TestService;
import com.github.lihang941.vertx.rest.*;
import com.github.lihang941.web.autoconfigure.Controller;
import com.github.pagehelper.Page;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.http.HttpServerResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-12-03 10:02
 */
@URL("test")
@Controller
public class TestResource {

    @Autowired
    private TestService testService;

    @POST
    public IdDto add(@Body TestDto testDto) {
        Test test= TestConvert.toTest().apply(testDto);
        testService.insertUseGeneratedKeys(test);
        return new IdDto(test.getId());
    }

    @URL(":id")
    @POST
    public int update(@Path("id") Long id,@Body TestDto testDto) {
        Test test= TestConvert.toTest().apply(testDto);
        test.setId(id);
        return testService.updateByPrimaryKeySelective(test);
    }

    @URL(":id")
    @DELETE
    public int delete(@Path("id") Long id) {
        return testService.deleteByPrimaryKey(id) ;
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
    public void get(@HeaderMap CaseInsensitiveHeaders headers, @Context Serializer serializer, @Context HttpServerResponse response) {
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
