package com.github.lihang941.common.service;

import com.github.pagehelper.Page;
import org.springframework.transaction.annotation.Transactional;
import com.github.lihang941.common.page.OffsetBean;
import com.github.lihang941.common.page.PageNumBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/3
 */
public interface BaseService<T ,PK extends Serializable> {

    Page<T> pageList(int pageNum, int pageSize);

    Page<T> offsetList(int offset, int pageSize);

    Page<T> pageList(PageNumBean pageNumBean);

    Page<T> offsetList(OffsetBean offSetBean);


    T get(PK id);

    @Transactional
    int add(T obj);

    int addSelective(T obj);

    @Transactional
    int remove(PK id);

    @Transactional
    int update(T obj);

    @Transactional
    int updateSelective(T obj);

    List<T> selectAll();

}
