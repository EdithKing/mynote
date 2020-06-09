### @Autowired以及@Resource的区别，@Bean标签



#### @Bean

作用于方法上的一个主机，在spring启动时会创建这么一个对象，以方法名字作为key，对象作为内容放入spring的ioc容器中



#### @Autowired

依赖注入，作用于属性或者方法上，默认按照type进行注入

* 当容器只有一个相同type的bean时，自动注入，
* 当容器有两个相同type的bean时，根据name来注入
* 当容器没有此type的bean时，报异常



#### @Resource

JSR中的依赖注入的注解，默认按照name进行，只有name相同时，才会根据type进行注入

