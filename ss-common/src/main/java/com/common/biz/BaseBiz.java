package com.common.biz;

import com.common.biz.around.ABaseBiz;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseBiz<T> extends ABaseBiz<T> {

}
