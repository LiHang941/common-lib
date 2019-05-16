package com.github.lihang941.example.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "\"test\"")
public class Test {
    @Id
    @Column(name = "\"id\"")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "\"test_name\"")
    private String testName;

    public static final String ID = "id";

    public static final String DB_ID = "id";

    public static final String TEST_NAME = "testName";

    public static final String DB_TEST_NAME = "test_name";
}