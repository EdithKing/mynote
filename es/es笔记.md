

### 配置

* elasticsearch.yml
* jvm.option:
  * ES_JAVA_OPTS:
  * xms:
  * xmx:
  * 8 : -xms : jdk版本为8时适用
  * 8 - ：-xms：jdk为8或者8以上适用
  * 8-9：-xms：jdk为8，9之间适用

* log4j.properties

### 建索引

* mapping：
  * properties
    * 字段名称
      * type：类型

### 

* 检查集群健康 :_cat/health?v

* 检查索引分片信息 :_cat/indices?v

### 增

* 
* 新增数据：PUT /index/type/id   { json字符串 }
* 批量新增：PUT /index/_bulk?pretty&refresh --data-binary "@json文件"
* 新增数据（id自增）： POST /index/type?pretty {json字符串}



### 删

* 删除所有 ：DELETE /index?pretty
* 删除一个：DELETE /index/type/id



### 改

* 改一条：POST /index/type/id/_update?pretty {json字符串}
* 改一条中的一个字段：POST /index/type/id/_update?pretty {“script”:"ctx._source.字段 = 值"}



### 批量处理	

* 批量处理: POST /index/type/_bulk?pretty

  改：{"update":{"_id":"1"}}

  {json字符串}

  建：{“index”:{"_id":"1"}}

  {json字符串}

  删：{“delete”:{"_id"}:"1"}

### 查

* query： 查询
  * macth_all： 全部
  * match ：相当于where
  * sort：排序
* aggs：指标
  * avg 平均值 
    * missing：缺省时初始值为
    * script：动态  
  * cardinality:统计
  * extended_stats:标准方差
    * sigma： 控制应该显示多少个与均值+/-的标准偏差
    * missing：
    * script：
  * max：最大值
  * min：最小值
  * sum：
* form：从某位置开始
* size ：查的个数

