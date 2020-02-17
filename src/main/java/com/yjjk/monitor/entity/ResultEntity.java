package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.ss.formula.functions.T;

/**
 * @program: monitor
 * @description: ${description}
 * @author: CentreS
 * @create: 2020-02-06 15:10:29
 **/
@Data
@Accessors(chain = true)
public class ResultEntity<T> {

    private boolean success;
    private String message;
    private T data;
}
