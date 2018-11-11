package ${packageName};

<#list importList as item>
import ${item};
</#list>

/**
 * @author : ${author}
 * @since : ${.now?string["yyyy-MM-dd HH:mm"]}
 */
