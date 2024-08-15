## netty学习笔记


#### 随笔笔记

* 在netty中，任何一个操作比如bind，比如write，比如connect，都返回一个ChannelFuture，这个ChannelFuture可以加入一个监听的回调代码用来表示
刚才的操作执行后的动作。
* ChannelFutureListener.CLOSE的正确场景应该是，channel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
* ChannelFutureListener.CLOSE
 

#### 未解决问题

#### 已解决问题

* 2024.08.15. channel().closeFuture()和channel.close().future()的最终调用逻辑是一致的，都是通过AbstractChannel.java的属性对象closeFuture去触发调用
如果使用channel().closeFuture().addListener(ChannelFutureListener.CLOSE)这个错误代码，也就是说触发channel.close会调用ChannelFutureListener.CLOSE,
而后者又会触发channel.close，但是这不会有问题的，因为AbstractChannel.java的closeInitiated是一个boolean值，会控制close相关回调只触发一次。


## 附录

* 2024.08.15.对closeFuture的错误理解

=====================如下理解是错误的=================================

channel.close().future();等价于channel().closeFuture()，指的是当关闭channel后，返回的future。
不过是channel().closeFuture()把Future提前返回了，但是和"操作()"返回的future一样。
==
对于这个代码channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
给closeFuture动作追加一个ChannelFutureListener.CLOSE，ChannelFutureListener.CLOSE内部又是channel.close()
从看代码好像会循环，实际测试不会循环。

=====================如上理解是错误的==================================