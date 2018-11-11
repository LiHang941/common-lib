<#include "./head.ftlh">
@URL("${entityName?uncap_first}")
@Component
public class ${entityName}Resource {

    @Autowired
    private ${entityName}Service ${entityName?uncap_first}Service;

    @Log
    private Logger logger;

    @POST
    public IdDto add(@Body ${entityName}Dto ${entityName?uncap_first}Dto) {
        ${entityName} ${entityName?uncap_first}= ${entityName}Convert.to${entityName}().apply(${entityName?uncap_first}Dto);
        ${entityName?uncap_first}Service.addSelective(${entityName?uncap_first});
        return new IdDto(${entityName?uncap_first}.getId());
    }

    @URL(":id")
    @POST
    public void update(@Path("id") ${idType} id,@Body ${entityName}Dto ${entityName?uncap_first}Dto) {
        ${entityName} ${entityName?uncap_first}= ${entityName}Convert.to${entityName}().apply(${entityName?uncap_first}Dto);
        ${entityName?uncap_first}.setId(id);
        ${entityName?uncap_first}Service.updateSelective(${entityName?uncap_first});
    }

    @DELETE
    public void delete(@Path("id") ${idType} id) {
        ${entityName?uncap_first}Service.remove(id);
    }

    @GET
    @URL(":id")
    public ${entityName}Dto get(@Path("id") ${idType} id) {
        ${entityName} ${entityName?uncap_first} = ${entityName?uncap_first}Service.get(id);
        if(${entityName?uncap_first} == null){
            throw new NoSuchElementException();
        }
        return ${entityName}Convert.to${entityName}Dto().apply(${entityName?uncap_first});
    }


    @GET
    public void get(@HeaderMap Map<String, String> headers, @Context Serializer serializer, @Context HttpServerResponse response) {
        OffsetBean offsetBean = RequestTool.toOffsetBean(headers);
        Page<${entityName}> pageList = ${entityName?uncap_first}Service.offsetList(offsetBean);
        RequestTool.pageEnd(pageList.getTotal(),
                        pageList.getResult()
                        .stream()
                        .map(${entityName}Convert.to${entityName}Dto())
                        .collect(Collectors.toList())
                    , response, serializer);
    }

}
