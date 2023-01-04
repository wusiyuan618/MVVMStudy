package com.example.mvvmstudy.ext

import com.btpj.lib_base.utils.LogUtil
import kotlinx.coroutines.*

/**
 * 协程的常见写法
 */
fun launchNormalRun(){
    val job=Job()
    val scope= CoroutineScope(job)
    scope.launch(Dispatchers.IO) {

    }
    //因为所有scope创建的协程都会被关联在job作用域下，所以调用cancel可以将同一作用域下的所有协程全部取消
    job.cancel()
}


fun launchRun(){
    //GlobalScope.launch创建的是最顶部的协程
    GlobalScope.launch {
//        printAndCreate()
//        series()
        parallel()
    }

    //runBlocking能保证协程作用域内的所有代码在执行完之前，一直阻塞当前线程。仅用于测试，否则影响性能
    runBlocking {

    }
}
/**
 * 串联和并联
 * 如果代码块中的代码还没执行完，那么await()方法会将当前协程阻塞住，直到可以获得async函数的执行结果。
 */
private suspend fun series()=coroutineScope{
    val startTime=System.currentTimeMillis()
    val result1= async {
        delay(1000)
        5+5
    }.await()
    //withContext可以理解为async+await的简化吧。他会立即执行代码块中的代码同时将外部协程挂起。执行完毕后最后一行结果作为返回值返回。
    //withContext会强制要求我们指定一个线程.Default=低并发的策略 IO=高并发的策略 Main=主线程中执行
    val result2= withContext(Dispatchers.Default) {
        delay(1000)
        3 + 7
    }

    LogUtil.i("计算出来的结果为：${result1+result2}")
    val endTime=System.currentTimeMillis()
    LogUtil.i("计算的时间为：${endTime-startTime}")
}
private suspend fun parallel()=coroutineScope{
    val startTime=System.currentTimeMillis()
    val result1= async {
        delay(1000)
        5+5
    }
    val result2= async {
        delay(3000)
        3+7
    }
    LogUtil.i("计算出来的结果为：${result1.await()+result2.await()}")
    val endTime=System.currentTimeMillis()
    LogUtil.i("计算的时间为：${endTime-startTime}")
}
//coroutineScope会继承外部的协程作用域，并创建一个子协程。实现在挂起函数中调用launch函数.
//coroutineScope也会保证在作用域内的所哟代码执行完之前将外部协程挂起
private suspend fun printAndCreate()= coroutineScope {
    val startTime=System.currentTimeMillis()
    repeat(10000){
        launch {
//                LogUtil.i("创建巨量的协程--${it}")
        }
    }
    val endTime=System.currentTimeMillis()
    LogUtil.i("创建巨量的协程耗时-->${endTime-startTime}")
}