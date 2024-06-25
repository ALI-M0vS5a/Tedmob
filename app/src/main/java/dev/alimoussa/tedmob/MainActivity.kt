package dev.alimoussa.tedmob

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.alimoussa.tedmob.MainActivityUiState.Loading
import dev.alimoussa.tedmob.MainActivityUiState.Success
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var uiState: MainActivityUiState by mutableStateOf(Loading)

        enableEdgeToEdge()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    uiState = it
                    when (uiState) {
                        is Loading -> splashScreen.setKeepOnScreenCondition { true }
                        is Success -> {
                            splashScreen.setKeepOnScreenCondition { false }
                            if ((uiState as Success).userData.email.isEmpty() || (uiState as Success).userData.password.isEmpty()) {
                                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }
}