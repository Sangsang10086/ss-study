package com.common.redis;

import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class Redis {
    private String key;
    private Object value;
    private long timeout;
    private TimeUnit unit;
    private boolean hasKey;
}
