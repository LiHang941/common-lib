<#include "./head.ftlh">

<#if lombok>
@Data
@Accessors(chain = true) </#if>
public class ${entityName}Dto ${content}
