### fastjson的一次循环引用问题导致前台除了第一条剩下都是"$ref":"$[0]"



> 为避免json字符串出现循环引用导致StackOverflowError异常，fastjson会对引用进行检测。如果检测到存在重复/循环引用的情况，fastjson默认会以“引用标识”代替同一对象，而非继续循环解析导致StackOverflowError。


```java
/**
 * obj为需要转化成字符串的json对象，
 * SerializerFeature.DisableCircularReferenceDetect:禁用循环引用检测
 */
JSON.toJSONString(obj,SerializerFeature.DisableCircularReferenceDetect);
```


### 引用标志说明

```
“$ref”:”..” 上一级
“$ref”:”@” 当前对象，也就是自引用
“$ref”:”$” 根对象
“$ref”:”$.children.0” 基于路径的引用，相当于root.getChildren().get(0)
```