## netty



#### 备注方法

在netty中，任何一个操作比如bind，比如write，比如connect，都返回一个ChannelFuture，这个ChannelFuture可以加入一个监听的回调代码用来表示
刚才的操作执行后的动作。


-------------------------------------------如下理解是错误的--------------------------------------------------------------
channel.close().future();等价于channel().closeFuture()，指的是当关闭channel后，返回的future。
不过是channel().closeFuture()把Future提前返回了，但是和"操作()"返回的future一样。
------------------
对于这个代码channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
给closeFuture动作追加一个ChannelFutureListener.CLOSE，ChannelFutureListener.CLOSE内部又是channel.close()
从看代码好像会循环，实际测试不会循环。
-------------------------------------------如上理解是错误的--------------------------------------------------------------


------------------
ChannelFutureListener.CLOSE的正确场景应该是，channel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);


#### 备注核心类
* ChannelFutureListener.CLOSE