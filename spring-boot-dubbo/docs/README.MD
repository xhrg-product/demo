
### 问题记录

##### dubbo-dependencies-zookeeper移除log4j1

参考代码，移除log4j,slf4j-reload4j的依赖。

#### dubbo-bom

在dependencyManagement有一个dubbo-bom，他的作用是如果加了他，那么dubbo-spring-boot-starter和dubbo-dependencies-zookeeper
就不需要写版本了，包括未来的dubbo-xxx等等都不需要加版本了。


### 功能点

##### 过滤器

过滤器主要2个点，1实现Filter, Filter.Listener接口，2 META-INF存相关文件即可

##### base-packages

这个如果配置和不配置，注册的方法有点差异，但是不影响使用，目前推荐配置上