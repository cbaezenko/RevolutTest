package com.singorenko.revoluttest.ui.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.singorenko.revoluttest.R
import com.singorenko.revoluttest.ui.model.RateItem

class RecyclerViewAdapter(private var listRateItems: MutableList<RateItem>?) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val context = parent.context
        val layout = R.layout.item_value_exchange
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layout, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listRateItems?.size?:0
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currency: Float = this.listRateItems?.get(position)?.currencyValue ?: 0.1F
        val moneyDescription : String = this.listRateItems?.get(position)?.currencyName ?: "EMPTY"
        val moneyShortName : String = this.listRateItems?.get(position)?.currencyAbb ?: "EMPTY"

        holder.etCurrency.text = Editable.Factory.getInstance().newEditable((currency).toString())
        holder.tvMoneyDescription.text = moneyDescription
        holder.tvMoneyShortName.text = moneyShortName
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMoneyDescription = itemView.findViewById<TextView>(R.id.tv_money_description)
        val tvMoneyShortName = itemView.findViewById<TextView>(R.id.tv_money_short_name)
        val ivMoneyImage = itemView.findViewById<ImageView>(R.id.iv_money_image)
        val etCurrency = itemView.findViewById<EditText>(R.id.et_currency)
    }
}

