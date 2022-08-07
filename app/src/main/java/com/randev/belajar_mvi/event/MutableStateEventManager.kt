package com.randev.belajar_mvi.event

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect

/**
 * @author Raihan Arman
 * @date 07/08/22
 */

open class MutableStateEventManager <T>: StateEventManager<T>(), FlowCollector<StateEvent<T>>{
    private val flowEvent: MutableStateFlow<StateEvent<T>> = MutableStateFlow(StateEvent.Idle())

    override var errorDispatcher: CoroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
        runBlocking {
            val stateError = StateEvent.Failure<T>(throwable)
            flowEvent.emit(stateError)
        }
    }
    override var listener: StateEvent<T>.(StateEventManager<T>) -> Unit = {}

    override fun subscribe(
        scope: CoroutineScope,
        onIdle: () -> Unit,
        onLoading: () -> Unit,
        onFailure: (throwable: Throwable) -> Unit,
        onSuccess: (T) -> Unit
    ) {
        createScope(scope).launch {
            flowEvent.collect {
                value = it
                listener.invoke(it, this@MutableStateEventManager)
                when(it){
                    is StateEvent.Idle -> onIdle.invoke()
                    is StateEvent.Loading -> onLoading.invoke()
                    is StateEvent.Failure -> onFailure.invoke(it.throwable)
                    is StateEvent.Success -> onSuccess(it.data)
                }
            }
        }
    }

    override fun invoke(): T? {
        return (value as? StateEvent.Success<T>)?.data
    }

    override fun createScope(another: CoroutineScope): CoroutineScope {
        return another + errorDispatcher
    }

    override fun <U> map(mapper: (T) -> U): StateEventManager<U> {
        return MapperStateEventManager(this, mapper)
    }

    inner class MapperStateEventManager<U>(
        private val stateEventManager: StateEventManager<T>,
        private val mapper: (T) -> U
    ): MutableStateEventManager<U>(){
        override fun subscribe(
            scope: CoroutineScope,
            onIdle: () -> Unit,
            onLoading: () -> Unit,
            onFailure: (throwable: Throwable) -> Unit,
            onSuccess: (U) -> Unit
        ) {
            stateEventManager.listener = {
                when(this){
                    is StateEvent.Idle -> {
                        value = StateEvent.Idle()
                        onIdle.invoke()
                    }
                    is StateEvent.Loading -> {
                        value = StateEvent.Loading()
                        onLoading.invoke()
                    }
                    is StateEvent.Failure -> {
                        value = StateEvent.Failure(this.throwable)
                        onFailure.invoke(this.throwable)
                    }
                    is StateEvent.Success -> {
                        val mapData = mapper.invoke(this.data)
                        value = StateEvent.Success(mapData)
                        onSuccess.invoke(mapData)
                    }
                }

                listener.invoke(value, this@MapperStateEventManager)
            }
        }
    }

    override suspend fun emit(value: StateEvent<T>) {
        flowEvent.emit(value)
    }

}