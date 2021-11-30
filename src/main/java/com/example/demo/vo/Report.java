package com.example.demo.vo;

import com.example.demo.annotation.NotNull;
import com.example.demo.annotation.Valid;
import lombok.Data;

import java.util.List;

/**
 * @author Steven
 */
@Data
public class Report {

    @NotNull(msg = "reportId 不能为空")
    private String reportId;

    @Valid
    private Order order;

    private List<String> list;

    private List<@Valid Storage> storages;
}
