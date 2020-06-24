package com.example.moviesearch.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.balysv.materialripple.MaterialRippleLayout
import com.example.moviesearch.R
import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.listerners.IAdapterListener
import com.squareup.picasso.Picasso


class MoviesAdapter(private val ctx: Context, private var items: List<Movie>, private var iAdapterListener: IAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


     class OriginalViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var image: ImageView = v.findViewById(R.id.image) as ImageView
        var name: TextView = v.findViewById(R.id.name) as TextView
        var counter: TextView = v.findViewById(R.id.counter) as TextView
        var itemLayout : MaterialRippleLayout = v.findViewById(R.id.item_layout) as MaterialRippleLayout

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item_list, parent, false)
        vh = OriginalViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = items[position]
        if (holder is OriginalViewHolder) {
            holder.name.setText(obj.title)
            Picasso.get()
                .load(obj.poster)
                .placeholder(R.drawable.image_2)
                .error(R.drawable.image_2)
                .into(holder.image)
            holder.counter.text = obj.imdbID

            holder.itemLayout.setOnClickListener{ view ->
                iAdapterListener.onItemClicked(items[position])
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}