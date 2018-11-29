package com.github.lihang941.common.service;

import com.github.lihang941.common.mapper.BaseMapper;
import com.github.lihang941.common.page.OffsetBean;
import com.github.lihang941.common.page.PageNumBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/3
 */
public abstract class BaseMapperService<T, PK extends Serializable> implements BaseService<T, PK> {

    private BaseMapper<T> baseMapper;

    public BaseMapperService(BaseMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public Page<T> pageList(int pageNum, int pageSize) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> baseMapper.selectAll());
    }

    @Override
    public Page<T> offsetList(int offset, int size) {
        return PageHelper.offsetPage(offset, size).doSelectPage(() -> baseMapper.selectAll());
    }

    @Override
    public Page<T> pageList(PageNumBean pageNumBean) {
        return PageHelper.startPage(pageNumBean.getPageNum(), pageNumBean.getPageSize()).doSelectPage(() -> baseMapper.selectAll());
    }

    @Override
    public Page<T> offsetList(OffsetBean offSetBean) {
        return PageHelper.offsetPage(offSetBean.getOffSet(), offSetBean.getSize()).doSelectPage(() -> baseMapper.selectAll());
    }


    @Override
    public Page<T> pageList(int pageNum, int pageSize, Example example) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> baseMapper.selectByExample(example));
    }

    @Override
    public Page<T> offsetList(int offset, int size, Example example) {
        return PageHelper.offsetPage(offset, size).doSelectPage(() -> baseMapper.selectByExample(example));
    }

    @Override
    public Page<T> pageList(PageNumBean pageNumBean, Example example) {
        return PageHelper.startPage(pageNumBean.getPageNum(), pageNumBean.getPageSize()).doSelectPage(() -> baseMapper.selectByExample(example));
    }

    @Override
    public Page<T> offsetList(OffsetBean offSetBean, Example example) {
        return PageHelper.offsetPage(offSetBean.getOffSet(), offSetBean.getSize()).doSelectPage(() -> baseMapper.selectByExample(example));
    }


    @Override
    public Page<T> pageList(int pageNum, int pageSize, T record) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> baseMapper.select(record));
    }

    @Override
    public Page<T> offsetList(int offset, int size, T record) {
        return PageHelper.offsetPage(offset, size).doSelectPage(() -> baseMapper.select(record));
    }

    @Override
    public Page<T> pageList(PageNumBean pageNumBean, T record) {
        return PageHelper.startPage(pageNumBean.getPageNum(), pageNumBean.getPageSize()).doSelectPage(() -> baseMapper.select(record));
    }

    @Override
    public Page<T> offsetList(OffsetBean offSetBean, T record) {
        return PageHelper.offsetPage(offSetBean.getOffSet(), offSetBean.getSize()).doSelectPage(() -> baseMapper.select(record));
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(PK key) {
        return baseMapper.deleteByPrimaryKey(key);
    }

    @Transactional
    @Override
    public int delete(T record) {
        return baseMapper.delete(record);
    }

    @Transactional
    @Override
    public int insert(T record) {
        return baseMapper.insert(record);
    }

    @Transactional
    @Override
    public int insertSelective(T record) {
        return baseMapper.insertSelective(record);
    }

    @Override
    public boolean existsWithPrimaryKey(PK key) {
        return baseMapper.existsWithPrimaryKey(key);
    }

    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    @Override
    public T selectByPrimaryKey(PK key) {
        return baseMapper.selectByPrimaryKey(key);
    }

    @Override
    public int selectCount(T record) {
        return baseMapper.selectCount(record);
    }

    @Override
    public List<T> select(T record) {
        return baseMapper.select(record);
    }

    @Override
    public T selectOne(T record) {
        return baseMapper.selectOne(record);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(T record) {
        return baseMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Override
    public int deleteByExample(Example example) {
        return baseMapper.deleteByPrimaryKey(example);
    }

    @Override
    public List<T> selectByExample(Example example) {
        return baseMapper.selectByExample(example);
    }

    @Override
    public int selectCountByExample(Example example) {
        return baseMapper.selectCountByExample(example);
    }

    @Override
    public T selectOneByExample(Example example) {
        return baseMapper.selectOneByExample(example);
    }

    @Transactional
    @Override
    public int updateByExample(T record, Example example) {
        return baseMapper.updateByExample(record, example);
    }

    @Transactional
    @Override
    public int updateByExampleSelective(T record, Example example) {
        return baseMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Example example, RowBounds rowBounds) {
        return baseMapper.selectByExampleAndRowBounds(example, rowBounds);
    }

    @Override
    public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
        return baseMapper.selectByRowBounds(record, rowBounds);
    }


    @Transactional
    @Override
    public int insertList(List<? extends T> recordList) {
        return baseMapper.insertList(recordList);
    }

    @Transactional
    @Override
    public int insertUseGeneratedKeys(T record) {
        return baseMapper.insertUseGeneratedKeys(record);
    }
}
