package com.github.lihang941.example.entity;

import javax.persistence.*;

@Table(name = "test_2")
public class Test2 {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "test_name")
    private String testName;

    public static final String ID = "id";

    public static final String TEST_NAME = "testName";

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public Test2 setId(Long id){		
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
    public Test2 setTestName(String testName){		
        this.testName = testName;
		return this;
	}
}