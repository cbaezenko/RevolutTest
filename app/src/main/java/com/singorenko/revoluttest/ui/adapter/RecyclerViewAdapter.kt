package com.singorenko.revoluttest.ui.adapter

import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.singorenko.revoluttest.R
import com.singorenko.revoluttest.ui.helper.UIHelper
import com.singorenko.revoluttest.ui.model.RateItem
import java.util.Collections.swap


class RecyclerViewAdapter(private var listRateItems: MutableList<RateItem>?,
                          private val mOnClickListener: ListItemClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

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
        val moneyShortName : String = this.listRateItems?.get(position)?.currencyAbb ?: "EMPTY"

        var quantity: Float = 10F
//        if(position == 0){
//            quantity = ((holder.etCurrency.text).toString()).toFloat()
//        }

        if(position != 0) {
            holder.etCurrency.text =
                Editable.Factory.getInstance()
                    .newEditable(((quantity )*(this.listRateItems!![position].currencyValue).toString().toFloat()).toString())
        }
        holder.tvMoneyDescription.text = UIHelper.getCurrencyLongName(moneyShortName)
        holder.tvMoneyShortName.text = moneyShortName
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val tvMoneyDescription: TextView = itemView.findViewById(R.id.tv_money_description)
        val tvMoneyShortName: TextView = itemView.findViewById(R.id.tv_money_short_name)
        val ivMoneyImage: ImageView = itemView.findViewById(R.id.iv_money_image)
        var etCurrency: EditText = itemView.findViewById(R.id.et_currency)

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.et_currency ->
//        swapItem(adapterPosition, 0)
                mOnClickListener.onListItemClick(adapterPosition, listRateItems?.get(adapterPosition))
            else -> Log.d("TAG", "clicked something else")
            }
        }

        init {
            etCurrency.setOnClickListener(this)
        }
    }

    fun swapItem(fromPosition: Int, toPosition: Int) {
        swap(listRateItems, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    interface ListItemClickListener{
        fun onListItemClick(clickedItemIndex: Int, itemToMove: RateItem?)
    }
}

