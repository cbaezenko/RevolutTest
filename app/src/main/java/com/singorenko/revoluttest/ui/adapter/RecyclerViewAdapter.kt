package com.singorenko.revoluttest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.singorenko.revoluttest.R
import com.singorenko.revoluttest.ui.helper.UIHelper
import com.singorenko.revoluttest.ui.model.RateItem
import java.util.Collections.swap

class RecyclerViewAdapter(private var listRateItems: MutableList<RateItem>?,
                          private val mOnClickListener: ListItemClickListener,
                          private val amount: Float) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val context = parent.context
        val layout = R.layout.item_value
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layout, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listRateItems?.size?:0
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currency: Float = this.listRateItems?.get(position)?.currencyValue ?: 0.1F
        val moneyShortName : String = this.listRateItems?.get(position)?.currencyAbb ?: "EMPTY"

        holder.tvCurrency.text = (currency * amount).toString()
        holder.tvMoneyDescription.text = UIHelper.getCurrencyLongName(moneyShortName)
        holder.tvMoneyShortName.text = moneyShortName
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvMoneyDescription: TextView = itemView.findViewById(R.id.tv_money_description)
        val tvMoneyShortName: TextView = itemView.findViewById(R.id.tv_money_short_name)
        val ivMoneyImage: ImageView = itemView.findViewById(R.id.iv_money_image)
        val tvCurrency: TextView = itemView.findViewById(R.id.tv_currency)
    }

    fun swapItem(fromPosition: Int, toPosition: Int) {
        swap(listRateItems, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    interface ListItemClickListener{
        fun onListItemClick(clickedItemIndex: Int, itemToMove: RateItem?)
    }
}

