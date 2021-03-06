package com.sakura.study.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Function {
    private Integer id;

    private String moduleName;

    private String moduleRoute;

    private Date createTime;

    private Boolean deleted;
}