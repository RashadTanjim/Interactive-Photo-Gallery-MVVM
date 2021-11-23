package info.rashadtanjim.interactivephotogallery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import info.rashadtanjim.interactivephotogallery.R
import info.rashadtanjim.interactivephotogallery.databinding.ListItemPhotosBinding
import info.rashadtanjim.interactivephotogallery.domain.model.PicsumPhotosItem

class GalleryAdapter(
    private val onClick: (PicsumPhotosItem) -> Unit
) : ListAdapter<PicsumPhotosItem, GalleryAdapter.ModuleViewHolder>(ModuleDiffUtil) {

    class ModuleViewHolder(
        private val itemBinding: ListItemPhotosBinding,
        onClick: (PicsumPhotosItem) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        private var selectedPhoto: PicsumPhotosItem? = null

        init {
            itemBinding.root.setOnClickListener {
                selectedPhoto?.let {
                    onClick(it)
                }
            }
        }

        fun bind(photosItem: PicsumPhotosItem) {
            selectedPhoto = photosItem

            itemBinding.textViewAuthorName.text = "Author: ${photosItem.author}"
            itemBinding.textViewPhotoNumber.text = "Photo ID: ${photosItem.id}"

            Glide.with(itemView).load(photosItem.download_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)  // caching image file using Glide
                .into(itemBinding.imageViewItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_photos, parent, false)
        val itemBinding =
            ListItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModuleViewHolder(itemBinding, onClick)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val unit = getItem(position)
        holder.bind(unit)
    }
}

object ModuleDiffUtil : DiffUtil.ItemCallback<PicsumPhotosItem>() {
    override fun areItemsTheSame(oldItem: PicsumPhotosItem, newItem: PicsumPhotosItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PicsumPhotosItem, newItem: PicsumPhotosItem): Boolean {
        return oldItem.id == newItem.id
    }

}