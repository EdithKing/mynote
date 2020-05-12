

### 

* 检查集群健康 :_cat/health?v

* 检查索引分片信息 :_cat/indices?v
* 

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
* form：从某位置开始
* size ：查的个数