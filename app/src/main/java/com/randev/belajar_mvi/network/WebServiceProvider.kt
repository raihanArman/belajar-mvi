package com.randev.belajar_mvi.network

import org.koin.core.annotation.Single

/**
 * @author Raihan Arman
 * @date 07/08/22
 */

@Single
class WebServiceProvider {
    fun get(): WebService{
        return WebService.build()
    }
}