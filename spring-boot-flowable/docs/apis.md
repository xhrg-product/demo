### flowable核心接口

* FlowableEventListener.java 记录flowable中的事件，请查阅onEvent方法的event.getType()可以得到FlowableEngineEventType，比如FlowableEngineEventType.MULTI_INSTANCE_ACTIVITY_STARTED表示开始执行。
* PlanItemInstanceLifeCycleListener.java
* JavaDelegate.java 