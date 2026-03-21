package com.example.simpletarot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.simpletarot.ui.theme.SimpleTarotTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpletarot.data.local.TarotDatabase
import com.example.simpletarot.data.local.TarotRepository
import com.example.simpletarot.ui.viewmodel.TarotViewModel
import com.example.simpletarot.ui.viewmodel.TarotViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database by lazy { TarotDatabase.getDatabase(this) }
        val repository by lazy { TarotRepository(database.tarotDao()) }

        enableEdgeToEdge()
        setContent {
            SimpleTarotTheme {
                val viewModel : TarotViewModel = viewModel(
                    factory = TarotViewModelFactory(repository)
                )
                TarotMain(viewModel)
            }
        }
    }
}