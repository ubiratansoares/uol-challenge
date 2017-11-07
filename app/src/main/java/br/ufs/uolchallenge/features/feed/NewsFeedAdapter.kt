package br.ufs.uolchallenge.features.feed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.ufs.uolchallenge.R
import br.ufs.uolchallenge.presentation.models.NewsFeedEntry
import br.ufs.uolchallenge.presentation.models.NewsFeedEntry.Plain
import br.ufs.uolchallenge.presentation.models.NewsFeedEntry.WithImage
import br.ufs.uolchallenge.util.Navigator
import br.ufs.uolchallenge.util.bindView
import com.squareup.picasso.Picasso

/**
 * Created by bira on 11/5/17.
 */

class NewsFeedAdapter(val models: List<NewsFeedEntry>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, layout: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(layout, parent, false)

        return when (layout) {
            R.layout.view_feed_entry_plain -> PlainCard(root)
            R.layout.view_feed_entry_with_image -> CardWithImage(root)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entry = models[position]
        when (holder) {
            is PlainCard -> holder.bind(entry as Plain)
            is CardWithImage -> holder.bind(entry as WithImage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val entry = models[position]

        return when (entry) {
            is Plain -> R.layout.view_feed_entry_plain
            is WithImage -> R.layout.view_feed_entry_with_image
        }
    }
}

class PlainCard(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleLabel by bindView<TextView>(R.id.titleLabel)
    val dateLabel by bindView<TextView>(R.id.dateLabel)
    val container by bindView<View>(R.id.container)
    val context = itemView.context

    fun bind(model: Plain) {
        titleLabel.text = model.title
        dateLabel.text = model.formmatedDate
        container.setOnClickListener {
            Navigator.toDisplayNews(context, model.shareableURL, model.visualizationURL)
        }
    }

}

class CardWithImage(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleLabel by bindView<TextView>(R.id.titleLabel)
    val dateLabel by bindView<TextView>(R.id.dateLabel)
    val newsImage by bindView<ImageView>(R.id.newsImage)
    val container by bindView<View>(R.id.container)
    val context = itemView.context

    fun bind(model: WithImage) {
        titleLabel.text = model.title
        dateLabel.text = model.formmatedDate
        container.setOnClickListener {
            Navigator.toDisplayNews(context, model.shareableURL, model.visualizationURL)
        }

        Picasso.with(itemView.context)
                .load(model.dimensionedImageURL)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .into(newsImage)
    }
}