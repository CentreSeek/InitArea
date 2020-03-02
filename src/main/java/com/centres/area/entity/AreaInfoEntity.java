package com.centres.area.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: init_area
 * @description:
 * @author: CentreS
 * @create: 2020-03-02 14:38:47
 **/
@Data
@Accessors(chain = true)
public class AreaInfoEntity {
    private Integer id;
    private Integer type;
    private String name;
    private String code;
    private Integer parentId;
}
