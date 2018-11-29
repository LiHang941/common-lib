import com.github.lihang941.Context;
import com.github.lihang941.TemplateGenerator;
import com.github.lihang941.generator.*;
import com.github.lihang941.generator.config.DtoConfig;
import com.github.lihang941.generator.config.PathPackage;
import com.github.lihang941.generator.config.ResourceConfig;
import com.github.lihang941.generator.config.ServiceConfig;
import com.github.lihang941.generator.config.dao.DaoConfig;
import com.github.lihang941.generator.config.dao.Table;
import com.github.lihang941.generator.config.dao.JdbcConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author : lihang941
 * @since : 2018/11/29
 */
public class GeneratorTest {


    private Context context = new Context();


    @Before
    public void before() {


        String basePath = "F:\\迅雷下载\\imooc-videos-dev 20180530 精简版\\src";
        DaoConfig daoConfig = new DaoConfig(
                new PathPackage(basePath, "com.github.lihang941.entity"),
                new PathPackage(basePath, "resource"),
                new PathPackage(basePath, "com.github.lihang941.dao"),
                new JdbcConfig()
                        .setConnectionURL("jdbc:postgresql://localhost:5432/btoken_project")
                        .setDriverClass("org.postgresql.Driver")
                        .setUserId("btoken_project")
                        .setPassword("a123520"),
                Arrays.asList(
                        new Table("id", "bt_unlock_record")
                )
        ).setDelimiter(DaoConfig.POSTGRESQL_DELIMITER);

        context.getGeneratorList().add(new DaoAndPojo(daoConfig));

        DtoConfig dtoConfig = new DtoConfig(
                new PathPackage(basePath, "com.github.lihang941.dto"),
                new PathPackage(basePath, "com.github.lihang941.convert"));

        context.getGeneratorList().add(new DtoAndConvert(dtoConfig,
                TemplateGenerator.mkdir(daoConfig.getJavaModel()).getAbsolutePath()
        ));
        context.getGeneratorList().add(new BeanBuilder(
                TemplateGenerator.mkdir(daoConfig.getJavaModel()).getAbsolutePath()
        ));

        ServiceConfig serviceConfig = new ServiceConfig(
                new PathPackage(basePath, "com.github.lihang941.service"),
                new PathPackage(basePath, "com.github.lihang941.service.impl")
        );

        context.getGeneratorList().add(new Service(
                serviceConfig,
                TemplateGenerator.mkdir(daoConfig.getJavaModel()).getAbsolutePath()
        ));

        context.getGeneratorList().add(new VertxResource(
                new ResourceConfig(
                        new PathPackage(basePath, "com.github.lihang941.resource")
                ),
                dtoConfig,
                serviceConfig,
                TemplateGenerator.mkdir(daoConfig.getJavaModel()).getAbsolutePath()
        ));
    }


    @Test
    public void dao() throws Exception {
        DaoAndPojo generator = context.generator(DaoAndPojo.class);
        generator.generator();
    }

    @Test
    public void dto() throws Exception {
        DtoAndConvert generator = context.generator(DtoAndConvert.class);
        generator.generator();
    }

    @Test
    public void beanBuilder() throws Exception {
        BeanBuilder generator = context.generator(BeanBuilder.class);
        generator.generator();
    }


    @Test
    public void service() throws Exception {
        Service generator = context.generator(Service.class);
        generator.generator();
    }

    @Test
    public void vertxResource() throws Exception {
        VertxResource generator = context.generator(VertxResource.class);
        generator.generator();
    }


    @Test
    public void all() {
        context.generator();
    }


}
