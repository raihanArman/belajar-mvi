package com.randev.belajar_mvi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randev.belajar_mvi.data.User
import com.randev.belajar_mvi.event.StateEventSubscriber
import com.randev.belajar_mvi.repository.MainRepository
import com.randev.belajar_mvi.utils.convertEventToSubscriber
import kotlinx.coroutines.launch
import org.koin.core.annotation.Scope

/**
 * @author Raihan Arman
 * @date 07/08/22
 */

@Scope(MainActivity::class)
class MainViewModel(
    private val repository: MainRepository
): ViewModel() {

    private val userManager = repository.userStateEventmanager

    private val userScope = userManager.createScope(viewModelScope)

    fun subscriberUser(subscriber: StateEventSubscriber<List<User>>){
        convertEventToSubscriber(userManager, subscriber)
    }

    fun getUsers(page: Int) = userScope.launch {
        repository.getUsers(page)
    }

}