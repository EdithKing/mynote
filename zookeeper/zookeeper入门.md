### 什么是zookeeper

> ZooKeeper是一个分布式的，开放源码的分布式应用程序协调服务，是Google的Chubby一个开源的实现，是Hadoop和Hbase的重要组件。它是一个为分布式应用提供一致性服务的软件，提供的功能包括：配置维护、域名服务、分布式同步、组服务等。
ZooKeeper的目标就是封装好复杂易出错的关键服务，将简单易用的接口和性能高效、功能稳定的系统提供给用户。


### 指令
```
ls path [watch]：查看某个路径下目录列表, watch注册监听，命令行可忽视

ls2 path [watch]: 显示当前节点下的节点和当前节点的属性信息

stat path [watch]: 显示数据的状态信息

get path [watch]: 获取path的属性信息和数据内容

create [-s] [-e] path data acl: 创建节点,-s表示顺序,-e表示临时,默认是持久节点,acl缺省表示不做任何权限限制,[-s]和[-e]是可选的

set path data [version]: 更新path的数据内容，version是做类似CAS的功能的对应dataversion，命令行可忽略

delete path [version]: 删除节点，不能递归删除，只能删除叶子节点
```

### 数据状态属性
cZxid: 节点被创建时的id
ctime: 节点被创建的时间
mZxid: 节点修改后的id
mtime: 节点被修改的时间
pZxid: 子节点id
cversion: 子节点version
dataVersion: 当前节点数据版本号，节点数据被修改版本号就会加1
aclVersion: 权限version
ephemeralOwner
dataLength: 数据长度
numChildren: 子节点个数


### zk的数据节点有那些？
* 持久化节点
* 持久化顺序节点
* 临时节点
* 临时顺序节点

### java使用zookeeper的api
1. 导入zookeeper的jar包
```
		<dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.6</version>
        </dependency>
```

2. 连接zk
Zookeeper可以填多个参数内容
	* connectString :连接字符串 比如：127.0.0.1：2181
	* sessionTimeout：会话过期时间
	* watcher：监听
	* sessionId：会话id 可选
	* sessionPasswd：会话认证密码 可选 
	* canBeReadOnly：是否只是可读 可选 默认false
```
	//无通知机制
	public ZooKeeper getZookeeper() {
		ZooKeeper zooKeeper = null;
		try {
			zooKeeper = new ZooKeeper("127.0.0.1:2181", 3000, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zooKeeper;
	}
	// 有通知机制，比如连接事件状态AuthFailed会打印如下的认证失败信息
	// 或者连接成功后可以触发的操作
	// ps:一次监听只能作用一次,多次使用需要重复设置通知机制
	public ZooKeeper getZookeeper2() throws IOException {
		ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 3000, new Watcher() {
			public void process(WatchedEvent event) {
				if (event.getState() == Event.KeeperState.AuthFailed) {
					System.out.println("认证失败");
				}
			}
		});
		return zooKeeper;
	}
```

3. 创建节点
	* path：路径名称
	* data：路径所含数据
	* acl：acl权限认证
	* createMode：创建模式，比如持久节点，有序，临时
	* cb： 包括回调函数的对象
	* ctx：上下文对象(异步回调时会传递给callback，方便出错时重新调用)






