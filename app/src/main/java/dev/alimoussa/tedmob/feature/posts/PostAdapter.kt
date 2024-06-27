package dev.alimoussa.tedmob.feature.posts

import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.alimoussa.tedmob.model.Post
import dev.alimoussa.tedmob.ui.theme.TedmobTheme

class PostAdapter(private var posts: List<Post>, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(val composeView: ComposeView) : RecyclerView.ViewHolder(composeView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val composeView = ComposeView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return PostViewHolder(composeView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.composeView.setContent {
            PostItem(
                post = post,
                onClick = {
                    val bottomSheetFragment = PostDetailsBottomSheetFragment.newInstance(post.body)
                    bottomSheetFragment.show(fragmentManager, "PostDetailsBottomSheet")
                }
            )
        }
    }

    @Composable
    fun PostItem(post: Post, onClick: () -> Unit) {
        TedmobTheme {
            ListItem(
                headlineContent = {
                    Text(
                        text = post.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                supportingContent = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = post.body,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Show More",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable(onClick = onClick)
                        )
                    }
                },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                modifier = Modifier
            )
        }
    }

    fun updatePosts(newPosts: List<Post>) {
        val diffCallback = PostDiffCallback(this.posts, newPosts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.posts = newPosts
        diffResult.dispatchUpdatesTo(this)
    }

    class PostDiffCallback(private val oldPosts: List<Post>, private val newPosts: List<Post>) :
        DiffUtil.Callback() {
        override fun getOldListSize() = oldPosts.size
        override fun getNewListSize() = newPosts.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPosts[oldItemPosition].id == newPosts[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPosts[oldItemPosition] == newPosts[newItemPosition]
        }
    }

    override fun getItemCount() = posts.size
}
