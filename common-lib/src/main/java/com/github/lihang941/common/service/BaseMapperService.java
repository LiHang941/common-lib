package com.github.lihang941.common.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;
import com.github.lihang941.common.mapper.BaseMapper;
import com.github.lihang941.common.page.OffsetBean;
import com.github.lihang941.common.page.PageNumBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/3
 */
public abstract class BaseMapperService<T, F, PK extends Serializable> implements BaseService<T, PK> {

    private BaseMapper<T, F, PK> baseMapper;

    public BaseMapperService(BaseMapper<T, F, PK> baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public Page<T> pageList(int pageNum, int pageSize) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> baseMapper.selectByExample(null));
    }

    @Override
    public Page<T> offsetList(int offset, int size) {
        return PageHelper.offsetPage(offset, size).doSelectPage(() -> baseMapper.selectByExample(null));
    }
    @Override
    public Page<T> pageList(PageNumBean pageNumBean) {
        return PageHelper.startPage(pageNumBean.getPageNum(), pageNumBean.getPageSize()).doSelectPage(() -> baseMapper.selectByExample(null));
    }
    @Override
    public Page<T> offsetList(OffsetBean offSetBean) {
        return PageHelper.offsetPage(offSetBean.getOffSet(), offSetBean.getSize()).doSelectPage(() -> baseMapper.selectByExample(null));
    }

    public List<T> selectByExample(F example) {
        return baseMapper.selectByExample(example);
    }

    public T selectOneByExample(F example) {
        List<T> ts = baseMapper.selectByExample(example);
        if (ts == null || ts.size() == 0) {
            return null;
        } else {
            return ts.get(0);
        }
    }

    public long countByExample(F example) {
        return baseMapper.countByExample(example);
    }

    @Transactional
    public int deleteByExample(F example) {
        return baseMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByExampleSelective(T record,
                                        F example) {
        return baseMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public int updateByExample(T record, F example) {
        return baseMapper.updateByExample(record, example);
    }



    @Override
    public T get(PK id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public int add(T obj) {
        return baseMapper.insert(obj);
    }

    @Transactional
    @Override
    public int remove(PK id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public int update(T obj) {
        return baseMapper.updateByPrimaryKey(obj);
    }

    @Transactional
    @Override
    public int addSelective(T obj) {
        return baseMapper.insertSelective(obj);
    }

    @Transactional
    @Override
    public int updateSelective(T obj) {
        return baseMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public List<T> selectAll() {
        return baseMapper.selectByExample(null);
    }







}
