package com.github.lihang941.example.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "\"user\"")
public class User {
    @Id
    @Column(name = "\"user_id\"")
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

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return user_status
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}