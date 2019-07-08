### 基本功能:
kona-base-lib主要封装了一些基础的公共功能, 具体功能如下:
- 异常统一封装, 我们这里将异常分为ClientException(由于前段操作不当造成的异常), ServerException(后端业务异常)
- ExceptionAdvance 异常的拦截类, 在业务系统中我们只需要添加一个配置类继承这个拦截类即可, 

```java
// @ControllerAdvice(value = "com.kona.framework.web.controller") value 代表要被拦截的包, 一般是异常在controller统一拦截处理
@ControllerAdvice(value = "com.kona.framework.web.controller")
public class ExceptionAdvanceImpl extends ExceptionAdvance {
}
```
- BaseService: 封装了service Base基类, 我们业务service只需要继承即可, BaseService中封装了两个功能, 1.引入了日志, 我们业务不需要自己
再写日志变量, 2.封装了MessageSourceAccessor 对象,抛出异常时使用, 详细见kona-base-bundle-plugin
- poi: 操作excel的通用操作, 使用如下

```java
// 1.创建导出excel的vo类, 并且添加注解, index表示表头列索引, label: 表示表头名称, 如果是日期还需要添加format,不添加默认为yyyy-MM-dd HH:mm:ss
@Getter
@Setter
public class RepayOrderExportDto {

    @ExcelColumnAnno(index = 0, name = "C端订单号")
    private String instalId;

    @ExcelColumnAnno(index = 1, name = "B端订单号")
    private String capitalInstalId;

    @ExcelColumnAnno(index = 2, name = "创建时间", format="yyyy-MM-dd")
    private Date createTime;
}

//2. 将 PoiService 注入到自己的业务系统使用 param-1:业务系统HttpServletResponse param-2: 要导出的数据 param-3: 导出数据类型的class
    poiService.exportExcel(HttpServletResponse response, List<T> dataList, Class clazz);
```
- 基础工具类: 包括了AmtUtil, DateUtil, FtpUtil, SftpUtil ... 也可以自行扩展
