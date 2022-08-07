package com.randev.belajar_mvi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.randev.belajar_mvi.R
import com.randev.belajar_mvi.data.User
import com.randev.belajar_mvi.databinding.ActivityMainBinding
import com.randev.belajar_mvi.event.StateEventSubscriber
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class MainActivity : ScopeActivity() {
    private val viewModel: MainViewModel by viewModel()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            btnFetch.setOnClickListener {
                viewModel.getUsers(1)
            }
        }

        viewModel.subscriberUser(subscriberUser())
    }

    fun subscriberUser() = object : StateEventSubscriber<List<User>>{
        override fun onIdle() {
            binding.resultUser.append("idle..\n")
        }

        override fun onLoading() {
            binding.resultUser.append("loading..\n")
        }

        override fun onFailure(throwable: Throwable) {
            binding.resultUser.append("failure(${throwable.message})..\n")
        }

        override fun onSuccess(data: List<User>) {
            binding.resultUser.append("success($data)..\n")
        }

    }

}