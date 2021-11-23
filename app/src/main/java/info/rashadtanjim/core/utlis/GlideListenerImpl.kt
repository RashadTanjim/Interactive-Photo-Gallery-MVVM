package info.rashadtanjim.core.utlis

import android.graphics.drawable.Drawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

object GlideListenerImpl {

    object OnCompleted : RequestListener<Drawable> {

        private lateinit var onComplete: () -> Unit

        operator fun invoke(onComplete: () -> Unit): OnCompleted {
            OnCompleted.onComplete = { onComplete() }
            return this
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onComplete()
            return false
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onComplete()
            return false
        }
    }
}