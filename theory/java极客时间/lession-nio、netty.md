1. io模型（I input、 O output）
	-	同步阻塞
	-	异步阻塞
	-	同步非阻塞
	-	异步非阻塞
	-	同步与异步、阻塞与非阻塞的区别
		+	纬度不同，同步与异步关注的请求方对结果的处理方式，同步处理，必须等待处理结果返回才能往下执行，异步不同等待当前调用的结果就继续往下处理了。阻塞与非阻塞关注的是请求处理方对结果的返回方式，阻塞有结果才返回给请求调用方，非阻塞不管有没有结果都会返回一个当前结果（结果或者没有结果的标识）给请求调用方
			*	秦说：同步异步 关注的通讯方式，阻塞、非阻塞关注的是线程处理模式
		+	同步阻塞，例子 很多函数都是同步阻塞的， 比如bind操作等，或者我们日常的一般函数调用
		+	异步阻塞，例子 golang中的多个服务调用，没有依赖关系的服务调用，可以并发调用，goroutine直接异步调用过去，最后在主goroutine等待各个服务的结果，汇总，其中开goroutine调用各个服务相当于主goroutine来讲就是异步阻塞的操作
		+	同步非阻塞， 典型的 epoll_wait操作 （信号驱动的IO模型，烧开水有个水哨）
		+	异步非阻塞， nginx的事件处理机制，AIO, listener、callback回调函数处理的，调用方已经不用额外管这个处理结果
			*	异步编程的代码可读性比较差，人阅读代码还是喜欢沿着控制流去阅读，但是异步的执行流就完全不可控了。人脑难以消化。
2. 端口概念的问题
	-	端口对应的计算机的某个进程，这个是大多数教程、很多人的理解
3. 多个进程监听同一个端口的说法
	-	为什么不同进程监听同一进程会出现端口已经被占用的错误，说明多个进程不能同时监听同一个端口
		+	那为啥nginx可以做到多个worker进程。
			*	1. nginx的worker进程是从master进程fork出来的，继承句柄
			*	2. nginx worker各个进程中直接使用listen socket accept操作，这里的socket是不是同一个socket地址（写时复制？）
			*	3. 惊群问题，当有一个client链接过来，直接会导致多个worker被唤醒，但又只有一个worker能accept这个链接，如果不处理也是可以正常使用的，但是会有性能问题，nginx的处理方式时accept_mutex锁，来处理链接，拿到锁的进程，则处理accpet事件，这样就不会有惊群问题
	-	linux 3.9以上内核支持SO_REUSEPORT选项，这个又是怎么回事
		+	这个机制允许了多个进程socket绑定同一个端口，但为了防止恶意复用，有限制
			*	使用SO_REUSEPORT option 选项
			*	必须是同一user id 
		+	如果有了这个机制，是否可以解决nginx那个惊群问题了
			*	这个是内核层面的支持，所以nginx的多个worker进程，可以同时listen同一个端口，然后accept，内核对于不同的worker进程的协调是做了负载均衡的，
4. 压测的命令
	-	windows下 sb -u http://localhost:8081 -c 40 -N 30
		+	superBenchmaker 简称 sb
	-	linux wrk -c 40 -d 30s http://lcalhost:8081
5. 连接处理方式
	-	单线程accept后，依次处理所有的accept socket
	-	一个accpet socket，一个线程处理
	-	固定线程池处理所有的accept socket，就像太二酸菜鱼的服务员，门口有一个服务员,相当于内核那个端口连接请求的调度员，他不会让你自己进去，他会让你等一会，用对讲机惊群所有带餐的服务员，让其中一个服务员来门口到指定的桌位上就餐，这里带餐的服务员就是我们固定线程池的worker
6. 模式比较
	-	1. cpu时间片的浪费
	-	2. 用户态、内核态数据拷贝
7. 时钟的重要性
	-	时钟回拨只能有一个窗口
	-	打仗对表，分布式事务
8. 线程池->EDA->SEDA
	-	多级缓冲层，线程池也是一样
9. 	编程模型 reactor, proactor
10. 代码，越便底层的代码，代码越不在意通用性
	-	底层代码可能更在意性能问题， 通用型的trade off(平衡)
11. netty
	-	连接管理与业务逻辑处理的分离
	-	协议支持，兼容一些比较通用的协议，还支持自定义设计的协议
	-	应用场景，http,https,tcp,udp,tcp,udp,im vm pipe
	-	webserver与http server的区别是那些
		+	httpServer只是说一些简单的http协议的支持，但没有设计成web server的容器
12. netty组成
	-	channel handler的接口或者继承类
13. 网关
	-	pipeline + filter 过滤器 管道加过滤器模式
14. 书籍
	-	权威指南，用户指南，实战 一般都是官方文档相关
	-	李林峰 netty权威指南
	-	java并发编程
	-	官方的simple
15. 