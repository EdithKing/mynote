### 序列化问题

 

问题描述：当读取redis里面储存的类的对象，或者是读取对象流的文件内容转化成对象时，会出现以下错误

> java.io.InvalidClassException: com.example.demo3.pojo.Student; local class incompatible: stream classdesc serialVersionUID = -3455534165949833923, local class serialVersionUID = -2994562896418810879



* 序列化实例类问题，类结构不一样导致，不论是属性，方法还是构造器都必须相同，不同则会报这个错误
* jdk版本不一致



### RedisTemplate序列化问题

问题描述：使用RedisTemplate没有指定序列化方式时，会默认使用JDK的序列化方式，导致存储在redis里面的内容会出现乱码（如：\xac\xed\x00\x05sr\x00\x1ehello）

* 设置redisTemplate的序列化方式
  *  StringRedisSerializer
  *  GenericJackson2JsonRedisSerializer
  *  Jackson2JsonRedisSerializer