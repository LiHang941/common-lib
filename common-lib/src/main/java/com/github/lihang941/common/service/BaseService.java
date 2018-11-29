package com.github.lihang941.common.service;

import com.github.lihang941.common.page.OffsetBean;
import com.github.lihang941.common.page.PageNumBean;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/3
 */
public interface BaseService<T, PK extends Serializable>  {

    Page<T> pageList(int pageNum, int pageSize);

    Page<T> offsetList(int offset, int pageSize);

    Page<T> pageList(PageNumBean pageNumBean);

    Page<T> offsetList(OffsetBean offSetBean);

    Page<T> pageList(int pageNum, int pageSize, Example example);

    Page<T> offsetList(int offset, int size, Example example);

    Page<T> pageList(PageNumBean pageNumBean, Example example);

    Page<T> offsetList(OffsetBean offSetBean, Example example);

    Page<T> pageList(int pageNum, int pageSize, T record);

    Page<T> offsetList(int offset, int size, T record);

    Page<T> pageList(PageNumBean pageNumBean, T record);

    Page<T> offsetList(OffsetBean offSetBean, T record);

    int deleteByPrimaryKey(PK key);

    int delete(T record);

    int insert(T record);

    int insertSelective(T record);

    boolean existsWithPrimaryKey(PK key);

    List<T> selectAll();

    T selectByPrimaryKey(PK key);

    int selectCount(T record);

    List<T> select(T record);

    T selectOne(T record);

    int updateByPrimaryKey(T record);

    int updateByPrimaryKeySelective(T record);

    int deleteByExample(Example example);

    List<T> selectByExample(Example example);

    int selectCountByExample(Example example);

    T selectOneByExample(Example example);

    int updateByExample(T record, Example example);

    int updateByExampleSelective(T record, Example example);

    List<T> selectByExampleAndRowBounds(Example example, RowBounds rowBounds);

    List<T> selectByRowBounds(T record, RowBounds rowBounds);

    int insertList(List<? extends T> recordList);

    int insertUseGeneratedKeys(T record);
}
