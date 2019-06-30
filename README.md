# kona-base
kona-base项目为基础框架依赖, 我们其他服务只需要按需引入kona-base项目的相关依赖即可使用. 下面我会一一介绍kona-base项目中不同的子moudle和其用法

### kona-base-model
#### 目的
kona-base-model 项目的目的主要是为了之后一系列微服务指定相同的pojo模型, 我们这里将pojo分为 po-对接持久层对象; vo-与视图层交互的基类; dto-rpc通信数据传输对象; 除了基本模型之外我们这里还定义相关anno && enums基础枚举的包.

#### model介绍

##### BaseReq 统一请求基类
BaseReq其实是一个空类, 具体可以根据自己业务统一实现, 这里主要为了请求参数统一设计

##### BaseResp 统一响应基类
BaseResp 主要定义了统一响应的基类, code-响应码; msg-响应消息; data-不同的响应body; errorLevel: 当异常时具体的错误级别(客户端, 服务端异常等...),具体见代码汇总的枚举
```java
/**
 * @author : Yuan.Pan 2019/6/29 1:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class BaseResp<T> {

    /** 返回码 */
    private String code = "0000";

    /** 返回消息 */
    private String msg = "操作成功";

    /** 返回数据 */
    private T data;

    /** 错误级别 */
    private String errorLevel;

    public static BaseResp SUCCESS = new BaseResp("0000", "操作成功", null, null);
    public static BaseResp SERVER_SYS_ERROR = new BaseResp("5000", "系统异常", null, ErrorLevelEnum.SERVER_SYSTEM_ERR.getCode());
    public static BaseResp CLIENT_ERROR = new BaseResp("4000", "客户端异常", null, ErrorLevelEnum.CLIENT_ERR.getCode());
}
```

##### BasePo 统一持久层对象
BasePo此处也是空实现, 主要为了持久层基类统一, 可根据自己业务需要扩展实现

##### BaseDto 统一传输层对象

#### 其他:定义一些枚举,公用注解



