package com.github.lihang941.example.entity;

import javax.persistence.*;

public class Test {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "test_name")
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
    public Test setId(Long id){		
        this.id = id;
		return this;
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
    public Test setTestName(String testName){		
        this.testName = testName;
		return this;
	}
}