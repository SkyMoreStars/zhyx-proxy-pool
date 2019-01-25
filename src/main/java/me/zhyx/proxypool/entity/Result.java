package me.zhyx.proxypool.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Auther: yssq
 * @Date: 2019/1/25 14:16
 * @Description:
 */
@ToString
@Data
public class Result {
    private String message;
    private int code;
    private List data;
}
