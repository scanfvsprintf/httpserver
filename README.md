# httpserver<br/>
基于java的实现了http协议的简易服务器，并包含一个测试样例<br/>
src目录下包含着后端代码文件，其中<br/>
request类负责处理request请求，可从请求中获得请求地址、请求参数以及请求方式<br/>
  -Request(Socket client)构造方法：参数为客户端的client对象<br/>
  -getAddress()：返回请求地址<br/>
  -getMethod()：返回请求方式<br/>
  -getParam(String key):返回参数名key的所有参数构成的List对象。<br/>
response类负责发送，可以发送文本以及文件，可以根据发送内容生成header<br/>
  -response(Socket client)构造方法：参数为客户端的client对象<br/>
  -print(String text)：向发送缓冲区中写入文本<br/>
  -println(String text)：向发送缓冲区中写入一行文本<br/>
  -printFile(File file)：向发送缓冲区中写入文件<br/>
  -printFile(String file)：向发送缓冲区中写入文件<br/>
  -send()：生成header并连同缓冲区所有数据发送至客户端<br/>
