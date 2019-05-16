<#include "./head.ftlh">

<#if lombok>
@Getter
@Setter
@ToString
@Accessors(chain = true) </#if>
public class ${entityName}Dto ${content}
