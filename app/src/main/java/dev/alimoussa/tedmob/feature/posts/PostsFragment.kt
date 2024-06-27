package dev.alimoussa.tedmob.feature.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.alimoussa.tedmob.R
import dev.alimoussa.tedmob.model.Post
import dev.alimoussa.tedmob.ui.theme.TedmobTheme

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter
    private val viewModel: PostsFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_posts, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewPosts)
        handleInsets(view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PostAdapter(emptyList(), childFragmentManager)
        recyclerView.adapter = adapter

        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ComposeContent(
                uiState = uiState,
                updatePosts = adapter::updatePosts,
                modifier = Modifier
            )
        }

        return view
    }


    private fun handleInsets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val statusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            recyclerView.updatePadding(top = statusBars.top)
            insets
        }
    }
}

@Composable
private fun ComposeContent(
    modifier: Modifier = Modifier,
    uiState: PostsScreenUiState,
    updatePosts: (List<Post>) -> Unit
) {
    TedmobTheme {
        Scaffold { padding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                when (uiState) {
                    is PostsScreenUiState.Error -> {
                        Text(text = uiState.message, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                    }

                    PostsScreenUiState.Loading -> {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is PostsScreenUiState.Success -> updatePosts(uiState.posts)
                }
            }
        }
    }
}
