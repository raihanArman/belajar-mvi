package com.randev.belajar_mvi.event

/**
 * @author Raihan Arman
 * @date 07/08/22
 */

interface StateEventSubscriber<T> {
    fun onIdle()
    fun onLoading()
    fun onFailure(throwable: Throwable)
    fun onSuccess(data: T)
}