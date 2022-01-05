### netty 核心构成

1. ChannelFuture 表示的是一个netty的IO操作结果
	- netty所有的io操作都是异步的，所有调用之后只会返回一个 ChannelFuture 接口类型，里面包含了调用结果的状态（the result or status of the IO operation），方便调用方知晓是否io操作有了返回结果
	- ChannelFuture 表示 io操作的结果的状态（io: successful, canceled, failure）
                                        +---------------------------+
                                        | Completed successfully    |
                                        +---------------------------+
                                   +---->      isDone() = true      |
   +--------------------------+    |    |   isSuccess() = true      |
   |        Uncompleted       |    |    +===========================+
   +--------------------------+    |    | Completed with failure    |
   |      isDone() = false    |    |    +---------------------------+
   |   isSuccess() = false    |----+---->      isDone() = true      |
   | isCancelled() = false    |    |    |       cause() = non-null  |
   |       cause() = null     |    |    +===========================+
   +--------------------------+    |    | Completed by cancellation |
                                   |    +---------------------------+
                                   +---->      isDone() = true      |
                                        | isCancelled() = true      |
                                        +---------------------------+
    - 你可以添加 ChannelFutureListener 去获得io操作完成的通知消息
    - 虽然可以使用await()方法去同步阻塞当前线程等待结果，但是仅限于你要序列化每一步操作，不然不建议这么干，同时也不能在ChannelHandler中有这样的操作
2. Channel
	-	Channel是netty对*socket*的一种抽象，*read*，write，connect，bind等操作
	-	Channel包含了*socket*的状态的管理，isConnect, isActive
	-	ChannelConfig抽象了对Channel的一些配置项，比如receive buffer size
	-	ChannelPipeline抽象了对channel的IO操作
	-	Channel parent()取决于他的创建，比如ServerSocketChannel创建了SocketChannel,那么SocketChannel调用parent()返回的是ServerSocketChannel
3. 	