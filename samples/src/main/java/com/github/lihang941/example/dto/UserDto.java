package com.github.lihang941.example.dto;

import java.util.Date;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-11-30 18:02
 */
public class UserDto {
    private String userId;
    private String userName;
    private Date createTime;
    private Integer userStatus;
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
