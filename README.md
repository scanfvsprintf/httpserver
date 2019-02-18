# httpserver
基于java的实现了http协议的简易服务器，并包含一个测试样例
src目录下包含着后端代码文件，其中
request类负责处理request请求，可从请求中获得请求地址、请求参数以及请求方式
  -Request(Socket client)构造方法：参数为客户端的client对象
  -getAddress()：返回请求地址
  -getMethod()：返回请求方式
  -getParam(String key):返回参数名key的所有参数构成的List对象。
response类负责发送，可以发送文本以及文件，可以根据发送内容生成header
  -response(Socket client)构造方法：参数为客户端的client对象
  -print(String text)：向发送缓冲区中写入文本
  -println(String text)：向发送缓冲区中写入一行文本
  -printFile(File file)：向发送缓冲区中写入文件
  -printFile(String file)：向发送缓冲区中写入文件
  -send()：生成header并连同缓冲区所有数据发送至客户端
