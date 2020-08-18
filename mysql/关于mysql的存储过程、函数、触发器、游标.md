### 关于mysql的存储过程、函数、触发器、游标



#### 存储过程

> 是一种在数据库中存储复杂程序，以便外部程序调用的一种数据库对象。
>
> 是为了完成特定功能的SQL语句集，经编译创建并保存在数据库中，用户可通过指定存储过程的名字并给定参数来调用执行。

* 语法：

  > CREATE    [DEFINER = { user | CURRENT_USER }] PROCEDURE sp_name ([proc_parameter[,...]])    [characteristic ...] routine_body  
  >
  > proc_parameter:    [ IN | OUT | INOUT ] param_name type  
  >
  > characteristic:    COMMENT 'string'  | LANGUAGE SQL  | [NOT] DETERMINISTIC  | { CONTAINS SQL | NO SQL | READS SQL DATA | MODIFIES SQL DATA }  | SQL SECURITY { DEFINER | INVOKER }  routine_body: 　Valid SQL routine statement 
  >
  >  [begin_label:] BEGIN 　
  >
  > [statement_list] 　　　
  >
  > …… END [end_label]

* 例子：例如给test_a表添加10000条数据

  ```	mysql
  CREATE TABLE `test_a` (
    `id` int(11) NOT NULL,
    `name` char(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB;
  delimiter $$
  create procedure i_data()
  begin 
  declare i int default 1;
  while(@i <= 100000) do
  	insert into test_a
  	values(@i,@i);
  	set @i = @i + 1;
  end while;
  end 
  $$
  ```

* 优缺点

  * 优：存储过程可封装，隐藏复杂的商业逻辑；可以回传值，并可以接受参数；可以用在数据检验，强行实行商业逻辑等。
  * 缺：往往定制化于特定的数据库上吗，因为支持的编程语言不同就需要重写原来的存储过程；受限于个钟数据库系统。

  

---



#### 自定义函数

> 函数存储着一系列的sql语句，调用函数就是一次性执行这些语句。减低语句重复。函数只能返回一个值。

* 语法：

> create function 函数名([参数列表]) 
>
> returns 数据类型 
>
> begin 
>
> sql语句; 
>
> return 值;
>
>  end;

* 例子：计算两个数的和

```mysql
create function my_sum(i int,j int)returns int
begin
	return i + j;
end
```





---



#### 触发器

> 触发器是一种特殊类型的存储过程，它不同于存储过程，主要是通过事件触发而被执行的，即不是主动调用而执行的；而存储过程则需要主动调用其名字执行

* 语法：

>delimiter $$
>
>create trigger trigger_name time trigger_event on table for each row
>
>begin
>
>​	trigger-body
>
>end
>
>delimiter ;

* 例子：比如在上述test_a表删除的后记录id

```mysql
delimiter $$
create trigger trigger_test 
after delete for each row
begin
	select old.id into @res;
end;
delimiter ;
```

* 触发器应用

  触发器针对的是数据库中每一行记录，每行数据在操作前后都会有一个对应的状态，触发器将没有操作之前的状态保存到old关键字中，将操作后的状态保存到new中。

| 触发器类型     | new和old的使用                                               |
| -------------- | ------------------------------------------------------------ |
| insert型触发器 | 没有old，只有new，new表示将要（插入前）或者已经增加（插入后）的数据 |
| update型触发器 | 即有old也有new，old表示更新之前的数据，new表示更新之后的数据 |
| delete型触发器 | 只有old，没有new，old表示将要（删除前）或者已经被删除（删除后）的数据 |

* 优缺点

  * 优：可以保证数据安全，并进行安全检验，可以通过数据库中的关联表实现级联更新，即一张表数据的改变会影响其他表的数据。
  * 缺：过分依赖触发器，影响数据库的结构，增加数据库的维护成本。

  

---



#### 游标：

>  游标实际上是一种能从包括多条数据记录的结果集中每次提取一条记录的机制。

* 例子：读取test_a表中id小于20的数据

```mysql
delimiter $$
create procedure test_cursor()
begin
declare i int;
declare j char(20);
declare done int default 0;
declare cur cursor for select id,name from test_a where id < 20;
declare continue handler for not found set done = 1;
open cur;
test_loop:loop
	fetch cur into i,j;
	if done = 1 then
		leave test_loop;
	end if;
	select i,j from dual;
end loop;
close cur;
end;
$$
```

优点：可以分批查询数据

























