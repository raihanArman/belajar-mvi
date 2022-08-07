package com.randev.belajar_mvi.repository

import com.randev.belajar_mvi.data.User
import com.randev.belajar_mvi.event.StateEventManager

/**
 * @author Raihan Arman
 * @date 07/08/22
 */
interface MainRepository {
    val userStateEventmanager: StateEventManager<List<User>>

    suspend fun getUsers(page: Int = 1)
}