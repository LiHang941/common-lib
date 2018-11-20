<#include "./head.ftlh">

public class ${entityName}Convert extends BeanConvert {

    public static Function<${entityName}, ${entityName}Dto> to${entityName}Dto() {
        return ${entityName?uncap_first} ->
                copy(${entityName?uncap_first}, ${entityName}Dto.class);
    }

    public static Function<${entityName}Dto,${entityName}> to${entityName}() {
        return ${entityName?uncap_first}Dto ->
                copy(${entityName?uncap_first}Dto, ${entityName}.class);
    }

}
