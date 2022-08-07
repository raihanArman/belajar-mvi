package com.randev.belajar_mvi.repository

import com.randev.belajar_mvi.data.User
import com.randev.belajar_mvi.event.MutableStateEventManager
import com.randev.belajar_mvi.event.StateEventManager
import com.randev.belajar_mvi.network.NetworkSources
import com.randev.belajar_mvi.utils.default
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Single

/**
 * @author Raihan Arman
 * @date 07/08/22
 */

@Single
class MainRepositoryImpl(
    private val networkSources: NetworkSources
): MainRepository {

    private val _userStateEventManager = default<List<User>>()
    override val userStateEventmanager: StateEventManager<List<User>>
        get() = _userStateEventManager

    @OptIn(InternalCoroutinesApi::class)
    override suspend fun getUsers(page: Int) {
        networkSources.getList(page)
            .collect(_userStateEventManager)
    }
}