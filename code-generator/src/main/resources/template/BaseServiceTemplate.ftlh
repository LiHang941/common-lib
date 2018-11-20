<#include "./head.ftlh">
public interface ${entityName}Service extends BaseService<${entityName}, ${idType}> {

    List<${entityName}> selectByExample(${entityName}Example example);

    ${entityName} selectOneByExample(${entityName}Example example);

    long countByExample(${entityName}Example example);

    @Transactional
    public int deleteByExample(${entityName}Example example);

    @Transactional
    public int updateByExampleSelective(${entityName} record, ${entityName}Example example);

    @Transactional
    public int updateByExample(${entityName} record, ${entityName}Example example);

}
