package com.common.rest;

import com.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController<Biz extends BaseBiz,Entity>{

    @Autowired
    protected Biz baseBiz;
}
