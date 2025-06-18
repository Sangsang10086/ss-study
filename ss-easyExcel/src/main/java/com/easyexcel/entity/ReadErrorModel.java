package com.easyexcel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ReadErrorModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 错误信息
     */
    private String message;
    /**
     * 行号
     */
    private Integer rowIndex;

    /**
     * 列号
     */
    private Integer columnIndex;

    /**
     * 单元格数据
     */
    private String cellData;


}