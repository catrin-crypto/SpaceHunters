package com.example.spacehunters.main.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.spacehunters.databinding.EpicFragmentRecyclerViewItemBinding
import com.example.spacehunters.main.App
import com.example.spacehunters.main.model.entities.epicphotos.EpicPhoto
import com.example.spacehunters.main.ui.EpicPhotosFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class EpicFragmentAdapter() : RecyclerView.Adapter<EpicFragmentAdapter.MainViewHolder>() {
    private var epicData: ArrayList<EpicPhoto> = ArrayList()
    private lateinit var binding: EpicFragmentRecyclerViewItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setPhotos(data: ArrayList<EpicPhoto>) {
        epicData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = EpicFragmentRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(epicData[position])

    override fun getItemCount() = epicData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(photoData: EpicPhoto) = with(binding) {
            recyclerItemEpicCaption.text = photoData.caption
            Picasso.get().load(photoData.imageUrl).into(recyclerItemEpicImage, object : Callback {
                override fun onSuccess() {
                    //Toast.makeText(context,"success",Toast.LENGTH_LONG).show()
                }

                override fun onError(e: Exception?) {
                    Toast.makeText(App.appContext, "error:" + e.toString(), Toast.LENGTH_LONG)
                        .show()
                    e?.printStackTrace()
                }
            })
        }
    }
}
