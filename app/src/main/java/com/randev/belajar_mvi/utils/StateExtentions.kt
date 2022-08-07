package com.randev.belajar_mvi.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randev.belajar_mvi.event.MutableStateEventManager
import com.randev.belajar_mvi.event.StateEvent
import com.randev.belajar_mvi.event.StateEventManager
import com.randev.belajar_mvi.event.StateEventSubscriber
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 * @author Raihan Arman
 * @date 07/08/22
 */

typealias FlowState<T> = Flow<StateEvent<T>>

fun <T>default() = MutableStateEventManager<T>()
fun <T>ViewModel.convertEventToSubscriber(
    eventManager: StateEventManager<T>,
    subscriber: StateEventSubscriber<T>
){
    eventManager.subscribe(
        scope = viewModelScope,
        onIdle = subscriber::onIdle,
        onLoading = subscriber::onLoading,
        onFailure = subscriber::onFailure,
        onSuccess = subscriber::onSuccess
    )
}

fun <T, U> Response<T>.asFlowStateEvent(mapper: (T) -> U): FlowState<U>{
    return flow {
        emit(StateEvent.Loading())
        delay(2000)
        val emitData = try {
            val body = body()
            if (isSuccessful && body != null){
                val dataMapper = mapper.invoke(body)
                StateEvent.Success(dataMapper)
            }else{
                val exception = Throwable(message())
                StateEvent.Failure(exception)
            }
        }catch (e: Throwable){
            StateEvent.Failure(e)
        }

        emit(emitData)
    }
}

fun <T, U>FlowState<T>.flatMap(transform: (T) -> U): FlowState<U>{
    return this.flatMapMerge {
        flow {
            when(it){
                is StateEvent.Loading ->
                    emit(StateEvent.Loading())
                is StateEvent.Idle ->
                    emit(StateEvent.Idle())
                is StateEvent.Failure ->
                    emit(StateEvent.Failure(it.throwable))
                is StateEvent.Success -> {
                    val data = transform.invoke(it.data)
                    emit(StateEvent.Success(data))
                }
            }
        }
    }
}

