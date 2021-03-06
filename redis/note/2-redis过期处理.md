redis是可以给一个key设置一个过期时间的，假设当key到了过期时间时，应该如何处理。


普遍的处理策略有：

* 立即删除：一过期则删除，然后回收内存空间。
* 惰性删除：只有当你下一次查字典发现这个key过期时，才回收内存空间。
* 定期删除：定期，并且定执行时间长度扫描将过期的key的空间回收掉。

优缺点：
* 立即删除：

	内存利用率高，一过期就删除，回收空间，不浪费任何内存
	不足在于需要消耗大量cpu，需要有定时器，监控每一个key的过期时间，一过期就回收空间
	
* 惰性删除：
	只有当下一次获取这个key，判断key是否过期，过期则删除，占用cpu是很少的
	不足在于，key过期了，假设没有去获取这个key，那么这个key将一直存在，浪费内存空间

* 定期删除：	
	定期删除内存利用率没立即删除好，单不会让key过期还一直浪费内存，cpu占用时间比惰性删除长，但不会跟立即删除一样太消耗cpu。


>redis是采用惰性删除+定期删除的策略，Redis 默认会每秒进行十次过期扫描，过期扫描不会遍历过期字典中所有的 key，而是采用了一种简单的贪心策略。

>从过期字典中随机 20 个 key；
删除这 20 个 key 中已经过期的 key；
如果过期的 key 比率超过 1/4，那就再此扫描；
同时，为了保证过期扫描不会出现循环过度，导致线程卡死现象，算法还增加了扫描时间的上限，默认不会超过 25ms。




### 假设redis的内存空间不足，如何处理？

在redis的配置中，是可以设置淘汰key的机制的，默认空间不足是不处理的，只能接受读请求，写则报错。
```
# volatile-lru -> 从过期的key中删除最近最久没使用的key
# allkeys-lru -> 删除最近最久没使用的key
# volatile-lfu -> 最近最少使用的过期的key
# allkeys-lfu -> 最近最少使用的key
# volatile-random -> 随机删除过期的key
# allkeys-random -> 随机删除key
# volatile-ttl -> 删除那些过期的key以及即将过期的key
# noeviction -> 不做任何，返回报错

maxmemory-policy noeviction
```
每一种处理方式都有各自的优点，需要结合实际情况选择。

