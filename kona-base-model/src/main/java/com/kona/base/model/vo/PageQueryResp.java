package com.kona.base.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author : Yuan.Pan 2019/6/29 1:21 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageQueryResp<T> {

    /** 总共的页数 */
    private Integer totalPage;

    /** 总个数 */
    private Long totalSize;

    /** 数据 */
    private Collection<T> list;

    public void page(Integer totalPage, Long totalSize, Collection<T> list) {
        this.totalPage = totalPage;
        this.totalSize = totalSize;
        this.list = list;
    }
}
