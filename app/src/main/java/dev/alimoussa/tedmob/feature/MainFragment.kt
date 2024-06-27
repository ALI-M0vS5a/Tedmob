package dev.alimoussa.tedmob.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.alimoussa.tedmob.MainViewModel
import dev.alimoussa.tedmob.R
import dev.alimoussa.tedmob.ui.TedmobApp
import dev.alimoussa.tedmob.ui.theme.TedmobTheme

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            val navHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            setContent {
                TedmobTheme {
                    TedmobApp(
                        onPostsClick = {
                            navController.navigate(R.id.postsFragment)
                        }
                    )
                }
            }
        }
    }
}