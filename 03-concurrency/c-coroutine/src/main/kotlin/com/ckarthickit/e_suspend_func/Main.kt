package com.ckarthickit.e_suspend_func

import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

fun main() = runBlocking(context = EmptyCoroutineContext + CoroutineName("run_blocking")) {
    printContext()
    doSomeWork()
    doAnotherWork(this)
    doSomeWorkInAnotherCoRoutineOnCurrentScope()
    doSomeWorkOnObjectWithScope()
    doSomeWorkOnScopedObject()
}

suspend fun doSomeWork() {
    println("doSomeWork-start ${getContext()}")
    delay(500)
    println("doSomeWork-end ${getContext()}")
}

suspend fun doAnotherWork(scope: CoroutineScope) {
    delay(100)
    scope.launch {
        println("doAnotherWork-a_launch-start ${getContext()}")
        delay(500)
        println("doAnotherWork-a_launch-end ${getContext()}")
    }
}

suspend fun CoroutineScope.doSomeWorkInAnotherCoRoutineOnCurrentScope() {
    println("doSomeWorkInAnotherCoRoutineOnCurrentScope-start")
    launch {
        println("doSomeWorkInAnotherCoRoutineOnCurrentScope-a_launch-start ${getContext()}")
        delay(500)
        println("doSomeWorkInAnotherCoRoutineOnCurrentScope-a_launch-end ${getContext()}")
    }
    println("doSomeWorkInAnotherCoRoutineOnCurrentScope-end")
}

fun doSomeWorkOnObjectWithScope() {
    val activity = MyActivity()
    activity.launchSomeWork()
    Thread.sleep(400)
    activity.cancelAll()
}

fun doSomeWorkOnScopedObject() {
    val activity = MyActivity2()
    activity.launchSomeWork()
    Thread.sleep(400)
    activity.cancelAll()
}


class MyActivity {
    private val scope = CoroutineScope(EmptyCoroutineContext + Dispatchers.IO + CoroutineName("MyActivity"))

    fun launchSomeWork() {
        scope.launch {
            doSomeWork()
        }
    }



    private suspend fun doSomeWork() {
        println("MyActivity.doSomeAsyncWork-a_launch-start ${getContext()}")
        delay(200)
        println("MyActivity.doSomeAsyncWork-a_launch-mid ${getContext()}")
        delay(500)
        println("MyActivity.doSomeAsyncWork-a_launch-end ${getContext()}")
    }

    fun cancelAll() {
        scope.cancel()
    }
}

class MyActivity2: CoroutineScope by CoroutineScope(EmptyCoroutineContext + Dispatchers.IO + CoroutineName("MyActivity2")) {

    fun launchSomeWork() {
        launch {
            doSomeWork()
        }
    }



    private suspend fun doSomeWork() {
        println("MyActivity2.doSomeAsyncWork-a_launch-start ${getContext()}")
        delay(200)
        println("MyActivity2.doSomeAsyncWork-a_launch-mid ${getContext()}")
        delay(500)
        println("MyActivity2.doSomeAsyncWork-a_launch-end ${getContext()}")
    }

    fun cancelAll() {
        cancel()
    }
}

/******************* UTILS **************************/
suspend fun printContext() {
    println(getContext())
}

suspend fun getContext(): String {
    return "thread= ${Thread.currentThread().id}, context=${coroutineContext[CoroutineName.Key]}"
}
