# redis的通信协议

### RESP(Redis Serialization Protocol)
> RESP 是 Redis 序列化协议的简写。它是一种直观的文本协议，优势在于实现异常简
单，解析性能极好。
>Redis 协议将传输的结构数据分为 5 种最小单元类型，单元结束时统一加上回车换行符
号\r\n。
1. 单行字符串 以 + 符号开头。
2. 多行字符串 以 $ 符号开头，后跟字符串长度。
3. 整数值 以 : 符号开头，后跟整数的字符串形式。
4. 错误消息 以 - 符号开头。
5. 数组 以 * 号开头，后跟数组的长度。

以下是java使用socket操作redis的字符串操作，其他可自行尝试，也可以编写一个jedis类似的框架。

```java
package com.redis.test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
public class TestString {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("127.0.0.1", 6379);
		// 获取输出流
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		// 获取输入流
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// set字符串内容
		String setRequest = "*3\r\n$3\r\nSET\r\n$5\r\nhello\r\n$5\r\nworld\r\n";// set hello world
		// 发送请求到redis
		bufferedWriter.append(setRequest);
		bufferedWriter.flush();
		// 获取输出结果
		String value = bufferedReader.readLine();
		System.out.println(value);
		// get请求内容
		String getRequest = "*2\r\n$3\r\nGET\r\n$5\r\nhello\r\n";// get hello
		// 发送请求到redis
		bufferedWriter.append(getRequest);
		bufferedWriter.flush();
		// 获取输出结果
		String str = "";
		while ((str = bufferedReader.readLine()) != null) {
			System.out.println(str);
		}
		bufferedWriter.close();
		bufferedReader.close();
		socket.close();
	}
}

```
输出结果
![socket连接redis操作](../png/socket操作redis结果.png)

