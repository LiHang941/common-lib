<#include "./head.ftlh">
@URL("${entityName?uncap_first}")
public class ${entityName}Resource {

    @Autowired
    private ${entityName}Service ${entityName?uncap_first}Service;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @POST
    public IdDto add(@Body ${entityName}Dto ${entityName?uncap_first}Dto) {
        ${entityName} ${entityName?uncap_first}= ${entityName}Convert.to${entityName}().apply(${entityName?uncap_first}Dto);
        ${entityName?uncap_first}Service.insertSelective(${entityName?uncap_first});
        return new IdDto(${entityName?uncap_first}.get${idName?cap_first}());
    }

    @URL(":id")
    @POST
    public void update(@Path("id") ${idType} ${idName},@Body ${entityName}Dto ${entityName?uncap_first}Dto) {
        ${entityName} ${entityName?uncap_first}= ${entityName}Convert.to${entityName}().apply(${entityName?uncap_first}Dto);
        ${entityName?uncap_first}.set${idName?cap_first}(${idName});
        ${entityName?uncap_first}Service.updateByPrimaryKeySelective(${entityName?uncap_first});
    }

    @URL(":id")
    @DELETE
    public void delete(@Path("id") ${idType} ${idName}) {
        ${entityName?uncap_first}Service.deleteByPrimaryKey(${idName});
    }

    @GET
    @URL(":id")
    public ${entityName}Dto get(@Path("id") ${idType} ${idName}) {
        ${entityName} ${entityName?uncap_first} = ${entityName?uncap_first}Service.selectByPrimaryKey(${idName});
        if(${entityName?uncap_first} == null){
            throw new NoSuchElementException();
        }
        return ${entityName}Convert.to${entityName}Dto().apply(${entityName?uncap_first});
    }


    @URL("list")
    @GET
    public void get(@HeaderMap Map<String, String> headers, @Context Serializer serializer, @Context HttpServerResponse response) {
        <#if isPage>
        PageNumBean pageNumBean = RequestTool.toPageNumBean(headers);
        Page<${entityName}> pageList = ${entityName?uncap_first}Service.pageList(pageNumBean);
        <#else>
        OffsetBean offsetBean = RequestTool.toOffsetBean(headers);
        Page<${entityName}> pageList = ${entityName?uncap_first}Service.offsetList(offsetBean);
        </#if>
        RequestTool.pageEnd(pageList.getTotal(),
                        pageList.getResult()
                        .stream()
                        .map(${entityName}Convert.to${entityName}Dto())
                        .collect(Collectors.toList())
                    , response, serializer);
    }

}
