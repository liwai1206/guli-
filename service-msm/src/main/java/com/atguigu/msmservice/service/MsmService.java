package com.atguigu.msmservice.service;

import java.util.Map;

public interface MsmService {
    boolean sendMessage(String phone, Map<String, Object> params);
}
