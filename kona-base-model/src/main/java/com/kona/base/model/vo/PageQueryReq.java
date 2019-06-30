package com.kona.base.model.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 * @author : Yuan.Pan 2019/6/29 1:27 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQueryReq extends BaseReq {

    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNum = 1;

    @Min(value = 1, message = "每页查询数量不能小于1")
    private Integer pageSize = 10;
}
