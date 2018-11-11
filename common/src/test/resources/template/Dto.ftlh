<#include "./head.ftlh">

@JSONType(orders={
    ${jsonOder?no_esc}
})
public class ${entityName}Dto ${content}
