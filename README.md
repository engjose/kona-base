# kona-base
kona-base项目为基础框架依赖, 我们其他服务只需要按需引入kona-base项目的相关依赖即可使用. 下面我会一一介绍kona-base项目中不同的子moudle和其用法

# 基础依赖
[(一) kona-dependencies](https://github.com/engjose/kona-dependencies)

在项目开发中,jar包管理是一件很重要的事. 为了能更轻便, 统一的管理我们项目中的依赖关系, 我将所有的jar依赖抽取到一个公共的项目中统一管理, 这个项目就是kona-dependencies; 这个项目很简单,就是一些依赖的管理, 我们只需要将其打包上传到自己的私服即可,同时可以根据自己项目中版本的需要进行添加或者修改版本.

### 注意:
此项目是我们之后所有项目的顶层父依赖, 所以先打包到自己的maven本地仓库, 或者上传到自己项目组的私服中.

# kona-base 基础组件介绍和使用

### 使用前准备(重要)
1. 因为我们所有项目都依赖顶层父项目, 所以这里需要将顶层父项目(kona-dependencies)打到自己本地仓库, 或者上传私服
2. 将我们的kona-base项目clone到本地, mvn clean install, 发布到自己本地仓库, 或者在<distributionManagement>中配置好自己服务后上传到私服
  
### 组件介绍以及如何使用到自己项目
[(一) kona-base-mybatis](https://github.com/engjose/kona-base/wiki/kona-base-mybatis)

[(二) kona-base-model](https://github.com/engjose/kona-base/wiki/kona-base-model)

[(三) kona-base-bundle-plugin](https://github.com/engjose/kona-base/wiki/kona-base-bundle-plugin)

[(四) kona-base-lib](https://github.com/engjose/kona-base/wiki/kona-base-lib)

[(五) kona-base-lib](https://github.com/engjose/kona-base/wiki/kona-base-security)



