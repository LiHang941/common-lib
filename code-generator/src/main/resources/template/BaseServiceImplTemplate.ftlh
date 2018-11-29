<#include "./head.ftlh">
@Service
public class ${entityName}ServiceImpl extends BaseMapperService<${entityName}, ${idType}> implements ${entityName}Service {

    private ${entityName}Mapper ${entityName?uncap_first}Mapper;

    public ${entityName}ServiceImpl(${entityName}Mapper ${entityName?uncap_first}Mapper) {
        super(${entityName?uncap_first}Mapper);
        this.${entityName?uncap_first}Mapper = ${entityName?uncap_first}Mapper;
    }

}
