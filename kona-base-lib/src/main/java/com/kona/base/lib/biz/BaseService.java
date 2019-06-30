package com.kona.base.lib.biz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**
 * @author : Yuan.Pan 2019/6/30 12:20 AM
 */
@Slf4j
@Component
public class BaseService {

    @Autowired
    protected MessageSourceAccessor msg;
}
