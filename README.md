# kona-base
kona-base项目为基础框架依赖, 我们其他服务只需要按需引入kona-base项目的相关依赖即可使用. 下面我会一一介绍kona-base项目中不同的子moudle和其用法

### kona-base-mybatis
主要对mybatis的一些封装, 目前拥有功能1.mybatis-generatror注释添加插件 2.mybatis-generator生成po采用lombok

#### 引入mybatis-generator-plugin 插件, 为我们生成dal层
说明: 
- 在插件中dependencies引入了两个jar包, 一个是mysql驱动包, 用来连接数据库
- 引入kona-base-mybatis(需要push到自己私服或者本地仓库), 作用是使用我们自己封装的插件
```xml
    <build>
        <plugins>

            <!-- mybatis generator plugin -->
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.6</version>
                <configuration>
                    <configurationFile>${basedir}/src/main/resources/generatorConfig.xml</configurationFile>
                    <verbose>true</verbose>
                    <overwrite>true</overwrite>
                </configuration>

                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>5.1.45</version>
                    </dependency>
                    <dependency>
                        <groupId>com.kona</groupId>
                        <artifactId>kona-base-mybatis</artifactId>
                        <version>1.0-SNAPSHOT</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
```

#### 在resources目录下创建generatorConfig.xml(位置已经在上述插件中配置)
说明:
- commentGenerator中配置了我们自己封装的generator添加注释的插件
- plugin 配置了两个插件1-SerializablePlugin 序列化插件, 2-LombokPlugin 配置了我们自己封装的lombok插件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration PUBLIC
        "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <!--指定特定数据库的jdbc驱动jar包的位置 -->
    <!--<classPathEntry location="mysql-connector-java-5.1.37-bin.jar"/>-->

    <context id="XMBG-01" targetRuntime="MyBatis3">
        <!-- 生成的 Java 文件的编码 -->
        <property name="javaFileEncoding" value="UTF-8"/>
        <!-- 格式化 Java 代码 -->
        <property name="javaFormatter" value="org.mybatis.generator.api.dom.DefaultJavaFormatter"/>
        <!-- 格式化 XML 代码 -->
        <property name="xmlFormatter" value="org.mybatis.generator.api.dom.DefaultXmlFormatter"/>

        <!-- plugin -->
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin" />
        <plugin type="com.kona.base.mybatis.generator.LombokPlugin" />

        <!-- 自定义注释生成器 -->
        <commentGenerator type="com.kona.base.mybatis.generator.CommentGeneratorWrapper">
            <property name="author" value="Yuan.Pan"/>
            <property name="dateFormat" value="yyyy-MM-dd"/>
        </commentGenerator>

        <!-- 配置数据库连接 -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://ip:3306/spring_cloud?useUnicode=true&amp;characterEncoding=utf8&amp;autoReconnect=true&amp;failOverReadOnly=false&amp;useSSL=false"
                        userId="xxx"
                        password="xxx">
            <property name="useInformationSchema" value="true" />
        </jdbcConnection>

        <!--默认false Java type resolver will always use java.math.BigDecimal if the database column is of type DECIMAL or NUMERIC. -->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>

        <!-- 生成实体的位置 -->
        <javaModelGenerator targetPackage="com.kona.springboot.sample.model.po" targetProject="MAVEN">
            <property name="enableSubPackages" value="true"/>
            <property name="trimStrings" value="false"/>
            <property name="rootClass" value="com.kona.base.model.po.BasePo" />
        </javaModelGenerator>

        <!-- 生成 Mapper 接口的位置 -->
        <sqlMapGenerator targetPackage="com.kona.springboot.sample.dao" targetProject="MAVEN">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>

        <!-- 生成 Mapper XML 的位置 -->
        <javaClientGenerator targetPackage="com.kona.springboot.sample.dao" type="XMLMAPPER" targetProject="MAVEN">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

        <!-- 设置数据库的表名和实体类名 -->
        <table tableName="order_master" domainObjectName="OrderMaster" mapperName="OrderMasterDao"
               enableCountByExample="false" enableUpdateByExample="false"
               enableDeleteByExample="false" enableSelectByExample="false"
               selectByExampleQueryId="false">
            <generatedKey column="id" sqlStatement="JDBC" identity="true" />
        </table>

    </context>

</generatorConfiguration>
```

#### 生成po类 && xml && dao
此时我们配置好generator的xml文件之后运行maven的generator插件,以IDEA为例,双击相关插件即可, 此时就会在target下相关目录生成文件, 此时已经发现注释和lombok已经添加使用, xml && dao也已经生成
```
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMaster extends BasePo implements Serializable {
    private String orderId;

    /** 买家名字 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为新下单 */
    private Byte orderStatus;

    /** 支付状态, 默认未支付 */
    private Byte payStatus;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
```


