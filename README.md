# MessengerDemo
利用Messenger可以进行进程间通信，这是一个demo
## 可通过Messenger传输
* Bundle
* 系统自带的实现了parcelable
* message的what,arg等。

它的底层其实也是用AIDL实现的。只是它用起来更简单。但它有局限性，就是不能传输自定义的Parcelable对象。
