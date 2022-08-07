package com.randev.belajar_mvi.event

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope

/**
 * @author Raihan Arman
 * @date 07/08/22
 */

abstract class StateEventManager<T> {
    var value: StateEvent<T> = StateEvent.Idle()
        protected set

    abstract var errorDispatcher: CoroutineExceptionHandler
    abstract var listener: StateEvent<T>.(StateEventManager<T>) -> Unit

    abstract fun subscribe(
        scope: CoroutineScope,
        onIdle: () -> Unit = {},
        onLoading: () -> Unit = {},
        onFailure: (throwable :Throwable) -> Unit = {},
        onSuccess: (T) -> Unit = {}
    )

    abstract fun <U> map(mapper: (T) -> U): StateEventManager<U>

    abstract fun invoke():  T?
    abstract fun createScope(another: CoroutineScope): CoroutineScope

}