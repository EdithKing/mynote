# node.js的数据类型	
*  数字number
	*  Infinity正无穷
	*  -Infinity负无穷
	*  NaN函数无法解析此参数
*  布尔boolean 
	*  false、0、空字符串（""）、NaN、null以及undefined都等价于false。其他都是true
*  字符串string
*  对象object
	*  函数function
	*  数组array

typeof可以查看变量的是什么数据类型，undefined表示变量还没赋值或者不存在

== 比较两个值是否相等
=== 比较两个值是否相等，操作类型是否一致

# 模块化
node.js使用一组相当简单的规则查找require的模块

1. 如果请求内置模块（比如fs，http），直接使用内置模块
2. 如何require函数的模块名是路径，那么根据路径去查找指定的js
3. 如何模块名没有路径，会在当前项目的node_modules/子文件夹下寻找，依次没有则搜索/usr/lib和/usr/local/lib

