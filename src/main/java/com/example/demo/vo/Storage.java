package com.example.demo.vo;

import com.example.demo.annotation.NotNull;
import lombok.Data;

/**
 * @author Steven
 */
@Data
public class Storage {

    @NotNull(msg = "storage count must be not null")
    private Integer count;

    private String time;


}
