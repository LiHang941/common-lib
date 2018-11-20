## 基于mybatis的代码生成

## 生成 dto + dtoConvert + resource + service + dao + daoBean + Example


```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    testCompile 'com.github.LiHang941.common-lib:code-generator:lastVersion'
}
```

## demo

```java
import com.github.lihang941.tool.Config;
import com.github.lihang941.tool.JDBCConfig;
import com.github.lihang941.tool.MyBatisGeneratorTool;
import com.github.lihang941.tool.Table;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/11
 */
public class GeneratorMain {

    public static void main(String[] args) throws Exception {
        String basePath = "F:/mjoy/btoken/";
        String basePackage = "io.mjoy.btoken";
        JDBCConfig jdbcConfig = new JDBCConfig()
                .setConnectionURL("jdbc:postgresql://localhost:5432/btoken_project")
                .setDriverClass("org.postgresql.Driver")
                .setUserId("userName")
                .setPassword("Password");


        Config config = new Config(
                jdbcConfig
                , new Table()
                .setTableName("") // TABLE name
                .setColumn("id") // id name
                .setCreateConvert(false) 
                .setCreateService(false)
                .setGeneratedKey(false)
                .setCreateResource(false)
        );

        config.serializ(basePath, basePackage);
        new MyBatisGeneratorTool(config).generate();
    }
}
```


### 更多配置查看 com.github.lihang941.tool.Config
