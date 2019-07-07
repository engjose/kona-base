# kona-base
kona-base项目为基础框架依赖, 我们其他服务只需要按需引入kona-base项目的相关依赖即可使用. 下面我会一一介绍kona-base项目中不同的子moudle和其用法

# 基础依赖
[(一) kona-dependencies](https://github.com/engjose/kona-dependencies)

在项目开发中,jar包管理是一件很重要的事. 为了能更轻便, 统一的管理我们项目中的依赖关系, 我将所有的jar依赖抽取到一个公共的项目中统一管理, 这个项目就是kona-dependencies; 这个项目很简单,就是一些依赖的管理, 我们只需要将其打包上传到自己的私服即可,同时可以根据自己项目中版本的需要进行添加或者修改版本.

# 相关模块
[(一) kona-base-mybatis](https://github.com/engjose/kona-base/wiki/kona-base-mybatis)

[(二) kona-base-model](https://github.com/engjose/kona-base/wiki/kona-base-model)



