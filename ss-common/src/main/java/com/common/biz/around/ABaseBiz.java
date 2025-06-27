package com.common.biz.around;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class ABaseBiz<T> implements IBaseBiz<T>{

    @Autowired
    private IBaseBiz<T> baseBiz;
    @Override
    public int insert(T entity) {
        return baseBiz.insert(entity);
    }

    @Override
    public int deleteById(T entity) {
        return baseBiz.deleteById(entity);
    }

    @Override
    public int delete(Wrapper<T> queryWrapper) {
        return baseBiz.delete(queryWrapper);
    }

    @Override
    public int updateById(T entity) {
        return baseBiz.updateById(entity);
    }

    @Override
    public int update(T entity, Wrapper<T> updateWrapper) {
        return baseBiz.update(entity, updateWrapper);
    }

    @Override
    public T selectById(Serializable id) {
        return baseBiz.selectById(id);
    }

    @Override
    public List<T> selectByIds(Collection<? extends Serializable> idList) {
        return baseBiz.selectByIds(idList);
    }

    @Override
    public void selectByIds(Collection<? extends Serializable> idList, ResultHandler<T> resultHandler) {
        baseBiz.selectByIds(idList, resultHandler);
    }

    @Override
    public Long selectCount(Wrapper<T> queryWrapper) {
        return baseBiz.selectCount(queryWrapper);
    }

    @Override
    public List<T> selectList(Wrapper<T> queryWrapper) {
        return baseBiz.selectList(queryWrapper);
    }

    @Override
    public void selectList(Wrapper<T> queryWrapper, ResultHandler<T> resultHandler) {
        baseBiz.selectList(queryWrapper, resultHandler);
    }

    @Override
    public List<T> selectList(IPage<T> page, Wrapper<T> queryWrapper) {
        return baseBiz.selectList(page, queryWrapper);
    }

    @Override
    public void selectList(IPage<T> page, Wrapper<T> queryWrapper, ResultHandler<T> resultHandler) {
        baseBiz.selectList(page, queryWrapper, resultHandler);
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper) {
        return baseBiz.selectMaps(queryWrapper);
    }

    @Override
    public void selectMaps(Wrapper<T> queryWrapper, ResultHandler<Map<String, Object>> resultHandler) {
        baseBiz.selectMaps(queryWrapper, resultHandler);
    }

    @Override
    public List<Map<String, Object>> selectMaps(IPage<? extends Map<String, Object>> page, Wrapper<T> queryWrapper) {
        return baseBiz.selectMaps(page, queryWrapper);
    }

    @Override
    public void selectMaps(IPage<? extends Map<String, Object>> page, Wrapper<T> queryWrapper, ResultHandler<Map<String, Object>> resultHandler) {
        baseBiz.selectMaps(page, queryWrapper, resultHandler);
    }

    @Override
    public <E> List<E> selectObjs(Wrapper<T> queryWrapper) {
        return baseBiz.selectObjs(queryWrapper);
    }

    @Override
    public <E> void selectObjs(Wrapper<T> queryWrapper, ResultHandler<E> resultHandler) {
        baseBiz.selectObjs(queryWrapper, resultHandler);
    }
}
