package xyz.lihang.common.tool;

import org.mybatis.generator.api.PluginAdapter;

import java.util.*;
import java.util.stream.Collectors;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/3
 */
public class BaseMapperGeneratorPlugin extends PluginAdapter {

    public boolean validate(List<String> warnings) {

        return true;
    }

    /**
     * 生成dao
     */
    @Override
    public boolean clientGenerated(Interface interFace,
                                   TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        /**
         * 主键默认采用java.lang.Integer
         */
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("BaseMapper<"
                + introspectedTable.getBaseRecordType() + ","
                + introspectedTable.getExampleType() + ","
                + introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getFullyQualifiedNameWithoutTypeParameters() + ">");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("xyz.lihang.common.mapper.BaseMapper");
        /**
         * 添加 extends MybatisBaseMapper
         */
        interFace.addSuperInterface(fqjt);

        //添加xyz.lihang.common.mapper.BaseMapper;
        interFace.addImportedType(imp);


        //方法不需要
        interFace.getMethods().clear();
        interFace.getAnnotations().clear();


        Set<FullyQualifiedJavaType> importedTypes = interFace.getImportedTypes();
        importedTypes
                .stream()
                .filter(o -> Arrays.asList("java.util.List", "org.apache.ibatis.annotations.Param")
                        .contains(o.getFullyQualifiedName())
                ).collect(Collectors.toList()).forEach(importedTypes::remove);
        return true;
    }

}