package com.token.samplemerchantapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.token.samplemerchantapp.R
import com.token.samplemerchantapp.domain.model.RowItem

class MerchantAdapter(private val dataSet: List<RowItem>) :
    RecyclerView.Adapter<MerchantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val modelText: TextView
        val priceText: TextView
        val image: ImageView

        init {
            modelText = view.findViewById(R.id.text_model)
            priceText = view.findViewById(R.id.text_price)
            image = view.findViewById(R.id.item_image)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.basket_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.modelText.text = "Model : " + item.name
        viewHolder.priceText.text = "Price : " + item.price.toString()
        viewHolder.image.setImageResource(item.image)
    }

    override fun getItemCount() = dataSet.size

}