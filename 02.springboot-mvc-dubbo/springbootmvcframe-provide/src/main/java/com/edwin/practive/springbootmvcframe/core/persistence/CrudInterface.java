package com.edwin.practive.springbootmvcframe.core.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CrudInterface<T>{
    /**
     * 获取单条数据
     * @param id
     * @return
     */
    T get(String id);

    /**
     * 获取单条数据
     * @param entity
     * @return
     */
    T get(T entity);
    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    List<T> findList(T entity);

    /**
     * 查询所有数据列表
     * @param entity
     * @return
     */
    List<T> findAllList(T entity);

    /**
     * 查询所有数据列表
     * @see List<T> findAllList(T entity)
     * @return
     */
    Page findPage(Page page, T t);

    int save(T entity);

    int delete(T entity);

    void deleteAll(Collection<T> entitys);

    void deleteAllByLogic(Collection<T> entitys);

    T findUniqueByProperty(String propertyName, Object value);

    public List<Map<String, Object>>  executeSelectSql(String sql);

    //@Transactional(readOnly = false)
    public void executeInsertSql(String sql);

    //@Transactional(readOnly = false)
    public void executeUpdateSql(String sql);

    //@Transactional(readOnly = false)
    public void executeDeleteSql(String sql);
}
