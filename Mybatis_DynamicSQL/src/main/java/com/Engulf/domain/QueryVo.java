package com.Engulf.domain;

import java.util.List;

public class QueryVo {

    private User user;

    private List<Integer> nums;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getNums() {
        return nums;
    }

    public void setNums(List<Integer> nums) {
        this.nums = nums;
    }
}
