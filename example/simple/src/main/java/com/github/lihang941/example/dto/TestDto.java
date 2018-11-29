package com.github.lihang941.example.dto;

import javax.persistence.*;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-11-29 17:54
 */
public class TestDto {


    private Long id;


    private String testName;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return test_name
     */
    public String getTestName() {
        return testName;
    }

    /**
     * @param testName
     */
    public void setTestName(String testName) {
        this.testName = testName;
    }
}
