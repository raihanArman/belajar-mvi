package com.randev.belajar_mvi.event

import com.randev.belajar_mvi.data.Data

/**
 * @author Raihan Arman
 * @date 07/08/22
 */
sealed class StateEvent<T> {
    class Idle<T>: StateEvent<T>()
    class Loading<T>: StateEvent<T>()
    data class Success<T>(val data: T): StateEvent<T>()
    data class Failure<T>(val throwable: Throwable): StateEvent<T>()
}