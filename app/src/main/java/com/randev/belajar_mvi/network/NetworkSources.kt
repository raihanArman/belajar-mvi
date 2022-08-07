package com.randev.belajar_mvi.network

import com.randev.belajar_mvi.data.Mapper
import com.randev.belajar_mvi.data.User
import com.randev.belajar_mvi.event.StateEvent
import com.randev.belajar_mvi.utils.FlowState
import com.randev.belajar_mvi.utils.asFlowStateEvent
import com.randev.belajar_mvi.utils.flatMap
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

/**
 * @author Raihan Arman
 * @date 07/08/22
 */

@Single
class NetworkSources(
    private val webServiceProvider: WebServiceProvider
){
    suspend fun getList(page: Int): FlowState<List<User>>{
        return webServiceProvider.get().getList(page)
            .asFlowStateEvent {
                Mapper.mapUserResponse(it)
            }
    }
}
