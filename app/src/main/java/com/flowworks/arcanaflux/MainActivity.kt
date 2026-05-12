package com.flowworks.arcanaflux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.flowworks.arcanaflux.ui.theme.SimpleTarotTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flowworks.arcanaflux.data.local.TarotDatabase
import com.flowworks.arcanaflux.data.remote.TarotApiService
import com.flowworks.arcanaflux.data.repository.TarotRepository
import com.flowworks.arcanaflux.ui.viewmodel.TarotViewModel
import com.flowworks.arcanaflux.ui.viewmodel.TarotViewModelFactory
import com.flowworks.arcanaflux.util.NetworkUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database by lazy { TarotDatabase.getDatabase(this) }
        val repository by lazy { TarotRepository(
            tarotApiService = TarotApiService.create(),
            tarotDao = database.tarotDao()) }

        enableEdgeToEdge()
        setContent {
            SimpleTarotTheme {
                val viewModel : TarotViewModel = viewModel(
                    factory = TarotViewModelFactory(repository)
                )
                viewModel.toggleNetworkStatus(
                    enabled = NetworkUtils.isOnline(context = this))
                TarotMain(viewModel)
            }
        }
    }
}