package xyz.lihang.common.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import xyz.lihang.common.page.OffsetBean;
import xyz.lihang.common.page.PageNumBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/3
 */
public interface BaseService<T ,PK extends Serializable> {

    Page<T> pageList(int pageNum,int pageSize);

    Page<T> offsetList(int offset,int pageSize);

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
