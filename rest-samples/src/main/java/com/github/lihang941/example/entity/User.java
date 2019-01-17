package com.github.lihang941.example.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "\"user\"")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "JDBC")
    private String userId;

    @Column(name = "\"user_name\"")
    private String userName;

    @Column(name = "\"create_time\"")
    private Date createTime;

    @Column(name = "\"user_status\"")
    private Integer userStatus;

    public static final String USER_ID = "userId";

    public static final String USER_NAME = "userName";

    public static final String CREATE_TIME = "createTime";

    public static final String USER_STATUS = "userStatus";
}