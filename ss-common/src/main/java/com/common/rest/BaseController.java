package com.common.rest;

import com.common.biz.BaseBiz;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BaseController<T> extends BaseBiz<T> {

}
