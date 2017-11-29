# JavaConcurrentTemplate
CSDN:http://blog.csdn.net/michael1112/article/details/78653055

Semaphore 实现 互斥 与 连接池:

Semaphore中管理着一组虚拟的许可，许可的初始数量可通过构造函数来指定【new Semaphore(1);】，执行操作时可以首先获得许可【semaphore.acquire();】，并在使用后释放许可【semaphore.release();】。如果没有许可，那么acquire方法将会一直阻塞直到有许可（或者直到被终端或者操作超时）。
作用：可以用来控制同时访问某个特定资源的操作数量，或者某个操作的数量。

MutexPrint.java

<img src="https://raw.githubusercontent.com/whtchl/JavaConcurrentTemplate/master/art/semaphore.png"/>

ConnectPool.java

<img src="https://raw.githubusercontent.com/whtchl/JavaConcurrentTemplate/master/art/semaphorePool.png"/>

====================================================================

CSDN:  http://blog.csdn.net/michael1112/article/details/78605713

带返回结果的批量任务执行 CompletionService ExecutorService.invokeAll
一般情况下，我们使用Runnable作为基本的任务表示形式，但是Runnable是一种有很大局限的抽象，run方法中只能记录日志，打印，或者把数据汇总入某个容器（一方面内存消耗大，另一方面需要控制同步，效率很大的限制），总之不能返回执行的结果；比如同时1000个任务去网络上抓取数据，然后将抓取到的数据进行处理（处理方式不定），我觉得最好的方式就是提供回调接口，把处理的方式最为回调传进去；但是现在我们有了更好的方式实现：CompletionService + Callable
Callable的call方法可以返回执行的结果;
CompletionService将Executor（线程池）和BlockingQueue（阻塞队列）结合在一起，同时使用Callable作为任务的基本单元，整个过程就是生产者不断把Callable任务放入阻塞对了，Executor作为消费者不断把任务取出来执行，并返回结果；
优势：
a、阻塞队列防止了内存中排队等待的任务过多，造成内存溢出（毕竟一般生产者速度比较快，比如爬虫准备好网址和规则，就去执行了，执行起来（消费者）还是比较慢的）
b、CompletionService可以实现，哪个任务先执行完成就返回，而不是按顺序返回，这样可以极大的提升效率；



CompletionServiceDemo.java

<img src="https://raw.githubusercontent.com/whtchl/JavaConcurrentTemplate/master/art/CompletionService1.png"/>

TestInvokeAll.java

<img src="https://raw.githubusercontent.com/whtchl/JavaConcurrentTemplate/master/art/CompletionServiceInvoiceAll.png"/>

====================================================================

CSDN:http://blog.csdn.net/michael1112/article/details/78665175

FutureTask:有点类似Runnable，都可以通过Thread来启动，不过FutureTask可以返回执行完毕的数据，并且FutureTask的get方法支持阻塞。

PreLoaderUseFutureTask.java

<img src="https://raw.githubusercontent.com/whtchl/JavaConcurrentTemplate/master/art/Futuretask1.png"/>

BookInstance.java

<img src="https://raw.githubusercontent.com/whtchl/JavaConcurrentTemplate/master/art/Futuretask2.png"/>
