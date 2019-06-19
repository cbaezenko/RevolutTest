package com.singorenko.revoluttest.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.singorenko.revoluttest.R
import com.singorenko.revoluttest.ui.helper.UIHelper
import com.singorenko.revoluttest.ui.model.RateItem

class RecyclerViewAdapter(private var listRateItems: MutableList<RateItem>?,
                          private val mOnClickListener: ListItemClickListener,
                          private val context: Context?,
                          private val amount: Float) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val context = parent.context
        val layout = R.layout.item_value
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layout, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int { return listRateItems?.size?:0 }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currency: Float = this.listRateItems?.get(position)?.currencyValue ?: 0.1F
        val moneyShortName : String = this.listRateItems?.get(position)?.currencyAbb ?: "EMPTY"

        holder.tvCurrency.text = (currency * amount).toString()
        holder.tvMoneyDescription.text = UIHelper.getCurrencyLongName(moneyShortName)
        holder.tvMoneyShortName.text = moneyShortName
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        override fun onClick(v: View?) { showDialogPreferencesSettings(adapterPosition, context) }

        val tvMoneyDescription: TextView = itemView.findViewById(R.id.tv_money_description)
        val tvMoneyShortName: TextView = itemView.findViewById(R.id.tv_money_short_name)
        val ivMoneyImage: ImageView = itemView.findViewById(R.id.iv_money_image)
        val tvCurrency: TextView = itemView.findViewById(R.id.tv_currency)

        init { itemView.setOnClickListener(this) }
    }

    interface ListItemClickListener{ fun onListItemClick(clickedItemIndex: Int) }

    fun showDialogPreferencesSettings(adapterPosition: Int, context: Context?){
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Currency Settings")
        builder.setMessage("Set this currency as main?")
        builder.setPositiveButton("YES"){ _, _ ->
            mOnClickListener.onListItemClick(adapterPosition)
        }
        builder.setNegativeButton("NO"){ _, _ ->  }
        builder.show()
    }
}