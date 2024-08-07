## 下载插件

eclipse请使用：http://www.flowable.org/designer/update/，eclipse如果在插件市场搜索bpmn也能搜到一个画图工具，但是没有flowable这个好用


## 参考
* 【重要】https://juejin.cn/post/7136065886506450975
* https://blog.csdn.net/inrgihc/article/details/108292825

## 项目介绍

* flowable-spring-boot-starter-ui-{xxx} 这些是为了让项目启动一个web页面，一般项目发布不会有这些功能。
* flowable-spring-boot-starter 这个依赖是springboot集成flowable，并且可以使用flowable提供的java api接口进行流程发起，申请等。


## maven依赖区分

maven依赖区分和本项目无关，主要是介绍flowable错综复杂的mavne依赖。

#### spring-boot相关
* flowable-spring-boot-starter-ui-modeler
* flowable-spring-boot-starter-ui-admin
* flowable-spring-boot-starter-ui-idm
* flowable-spring-boot-starter-ui-task

* flowable-spring-boot-starter-process-rest 这个应该是通过rest接口操作flowable

#### flowable独立
* flowable-ui 不存在，flowable把ui都拆分成众多依赖，比如flowable-ui-common， flowable-ui-modeler-rest， flowable-ui-idm-logic