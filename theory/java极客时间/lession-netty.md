1. 什么是高性能
	-	吞吐量 系统内部、技术指标
	-	低延迟 （用户体验不好）、技术指标 TPS、QPS 
		+	latancy（系统内部耗时，进入系统时间到出系统时间）、response time（响应时间）的区别
	-	并发用户数 系统外部、业务指标 （对于连接请求，先hold的住，在请求超时时间内解决）
		+	夫妻店的并发数 远小于 海底捞
2. 高性能的另一面（交通工具的类比，自行车、小汽车、高铁、客机、战斗机）
	-	系统复杂度提升
	-	建设与运维成本大大增加
	-	故障、bug导致的破坏性大大提升
3. 解决方案 （netfix）混沌工程
	-	容量（监控）TPS 国内公司多少（86400 * 1000TPS） = 8640w单 淘宝，热点时间（3000w） 京东、滴滴（1000w）
		+	双十一的0点（前10分钟） 一个小时，支付宝交易量 TPS 50w/s，一天大概有10亿订单
		+	之前是12306，10w+ w/s 过年抢票
		+	做活动，秒杀并发
		+	三大云厂商 aws、微软、阿里云，电商活动、双十一大量的机器冗余
	-	爆炸半径
		+	bug、故障影响面，业务系统变动导致的bug
	-   工程方面积累与引进 （redis挂了，oom被杀了）
4. netty的处理流程
	-	bossEventLoopGroup处理listenr
	-	workEventLoopGroup处理分发accept的socket,管理一组EventLoop
	-	eventLoop 本质是一个线程，处理 socketChannel
	-	channelHandle
		+	handle、invoke、processor、filter、listener
	-	
5. 	粘包与拆包
	-	如何获得一个完整语义的协议包 固定长度、特殊分隔符、TLV(type len value)
	-	tcp只是提供一个流式的数据流全双工通道
6. http断点续传问题
	-	
7. 多线程传送
	-	http请求头 range文件块的范围
8. 	tcp协议当中nagel算法 TCP_NODELAY（关闭nagel算法，关闭小包优化）
	-	tcp 小包的batch化处理
	-	tcp 的头部相关有20+20字节消耗
	-	优化条件
		+	缓冲区满了 （内核socket收发缓冲区）
		+	包的超时时间 200ms（一般）
	-	最大传输单元 1460 （1.6k性能小于1k数据）
9.  连接优化
	-	端口占用
		+	TCP建立连接（3次握手）、断开连接（4次挥手）
		+	为啥客户端会在TIME_WAIT等待2MSL(linux下1个msl=1分钟，windows=2分钟)时间，才关闭close
10. netty优化
	-	业务处理不要阻塞EventLoop，相当于redis的单线程处理模式，阻塞了会影响其他socket的事件处理
	-	系统参数优化 文件描述符是否够用 1024 -> 65535
		+	ulimit -a /proc/sys/net/ipv4/tcp_fin_timeout
	-	缓冲区优化
		+	SO_REVBUF 接受缓冲区buf
		+	SO_SNDBUF 发送缓冲区buf
		+	SO_BACKLOG 保持连接状态，listenr队列长度，半连接状态
		+	REUSEXXX 端口复用
	-	业务层面的心跳机制 断线重连 心跳过快、过慢 复用真实的协议包
	-	内存与ByteBuf（真实的物理内存）优化，减少内核态、用户态数据拷贝
		+	DirectBuffer、HeapBuffer
	-	其他优化
		+	ioRatio	1:1(io操作)
		+	WaterMark。配置参数，缓冲区相关
		+	TrafficShaping 流控机制，流量整型，面对突发流量
11. 流量网关、业务网关
	-	流量网关做业务方的限流比较麻烦，才有了业务网关（netflix zuul、spring cloud gateway、猫大人的soul）
	-	业务网关 （zuul2.x）
		+	限流、降级、熔断
		+	最简单的网关需要什么功能
			*	proxy
			*	过滤器 hanlder、filter
			*	router 路由服务（静态、动态）
			*	协议转换
12. 深深的






































