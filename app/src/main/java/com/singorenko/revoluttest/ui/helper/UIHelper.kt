package com.singorenko.revoluttest.ui.helper

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.singorenko.revoluttest.R
import com.singorenko.revoluttest.network.model.RatesModel
import com.singorenko.revoluttest.ui.model.RateItem
import com.singorenko.revoluttest.util.Constants

class UIHelper {
    companion object {
        private const val PREF_NAME_CURRENCY = "preferenceNameCurrency"
        private const val PRIVATE_MODE = 0

        fun setPreferenceCurrency (context: Context?, tvMoneyDescription: TextView, tvMoneyShortName: TextView, ivFlag: ImageView){
            val moneyDescription = getCurrencyLongName(getPreferenceCurrency(context))
            val shortName = getPreferenceCurrency(context)
            tvMoneyDescription.text = moneyDescription
            tvMoneyShortName.text = shortName

            if (context != null) {
                Glide.with(context).load(getFlagIcon(shortName))
                    .apply(RequestOptions.circleCropTransform()).into(ivFlag)
            }
        }

        fun getFlagIcon(preferenceCurrency: String) : Int{
            when(preferenceCurrency){
                Constants.audShortName -> return R.drawable.flag_au
                Constants.bgnShortName -> return R.drawable.flag_bg
                Constants.brlShortName -> return R.drawable.flag_br
                Constants.cadShortName -> return R.drawable.flag_ca
                Constants.chfShortName -> return R.drawable.flag_ch
                Constants.cnyShortName -> return R.drawable.flag_cn
                Constants.czkShortName -> return R.drawable.flag_cz
                Constants.dkkShortName -> return R.drawable.flag_dk
                Constants.eurShortName -> return R.drawable.flag_eu
                Constants.gbpShortName -> return R.drawable.flag_gb
                Constants.hkdShortName -> return R.drawable.navigation_empty_icon
                Constants.hrkShortName -> return R.drawable.navigation_empty_icon
                Constants.hufShortName -> return R.drawable.flag_hu
                Constants.idrShortName -> return R.drawable.navigation_empty_icon
                Constants.ilsShortName -> return R.drawable.navigation_empty_icon
                Constants.inrShortName -> return R.drawable.navigation_empty_icon
                Constants.iskShortName -> return R.drawable.flag_is
                Constants.jpyShortName -> return R.drawable.flag_jp
                Constants.krwShortName -> return R.drawable.navigation_empty_icon
                Constants.mxnShortName -> return R.drawable.navigation_empty_icon
                Constants.myrShortName -> return R.drawable.navigation_empty_icon
                Constants.nokShortName -> return R.drawable.navigation_empty_icon
                Constants.nzdShortName -> return R.drawable.flag_nz
                Constants.phpShortName -> return R.drawable.navigation_empty_icon
                Constants.plnShortName -> return R.drawable.navigation_empty_icon
                Constants.ronShortName -> return R.drawable.navigation_empty_icon
                Constants.rubShortName -> return R.drawable.flag_ru
                Constants.sekShortName -> return R.drawable.navigation_empty_icon
                Constants.sgdShortName -> return R.drawable.navigation_empty_icon
                Constants.thbShortName -> return R.drawable.navigation_empty_icon
                Constants.tryShortName -> return R.drawable.navigation_empty_icon
                Constants.usdShortName -> return R.drawable.flag_us
                Constants.zarShortName -> return R.drawable.flag_za
                else -> return R.drawable.navigation_empty_icon
            }
        }

        fun savePreferenceCurrency(currencyShortName: String, context: Context){
            val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME_CURRENCY, PRIVATE_MODE)
            val editor = sharedPref.edit()
            editor.putString(PREF_NAME_CURRENCY, currencyShortName)
            editor.apply()
        }

        fun getPreferenceCurrency(context: Context?) : String{
            val sharedPref: SharedPreferences = context!!.getSharedPreferences(PREF_NAME_CURRENCY, PRIVATE_MODE)
            val currency = sharedPref.getString(PREF_NAME_CURRENCY, Constants.usdShortName)
            return currency
        }

        fun fillListRareItems(ratesModel: RatesModel) : MutableList<RateItem>{
            val mutableList: MutableList<RateItem> = ArrayList()

            mutableList.add(RateItem((Constants.audShortName), ratesModel.AUD))
            mutableList.add(RateItem((Constants.bgnShortName), ratesModel.BGN))
            mutableList.add(RateItem((Constants.brlShortName), ratesModel.BRL))
            mutableList.add(RateItem((Constants.cadShortName), ratesModel.CAD))
            mutableList.add(RateItem((Constants.chfShortName), ratesModel.CHF))
            mutableList.add(RateItem((Constants.cnyShortName), ratesModel.CNY))
            mutableList.add(RateItem((Constants.czkShortName), ratesModel.CZK))
            mutableList.add(RateItem((Constants.dkkShortName), ratesModel.DKK))
            mutableList.add(RateItem((Constants.eurShortName), ratesModel.EUR))
            mutableList.add(RateItem((Constants.gbpShortName), ratesModel.GBP))
            mutableList.add(RateItem((Constants.hkdShortName), ratesModel.HKD))
            mutableList.add(RateItem((Constants.hrkShortName), ratesModel.HRK))
            mutableList.add(RateItem((Constants.hufShortName), ratesModel.HUF))
            mutableList.add(RateItem((Constants.idrShortName), ratesModel.IDR))
            mutableList.add(RateItem((Constants.ilsShortName), ratesModel.ILS))
            mutableList.add(RateItem((Constants.inrShortName), ratesModel.INR))
            mutableList.add(RateItem((Constants.iskShortName), ratesModel.ISK))
            mutableList.add(RateItem((Constants.jpyShortName), ratesModel.JPY))
            mutableList.add(RateItem((Constants.krwShortName), ratesModel.KRW))
            mutableList.add(RateItem((Constants.mxnShortName), ratesModel.MXN))
            mutableList.add(RateItem((Constants.myrShortName), ratesModel.MYR))
            mutableList.add(RateItem((Constants.nokShortName), ratesModel.NOK))
            mutableList.add(RateItem((Constants.nzdShortName), ratesModel.NZD))
            mutableList.add(RateItem((Constants.phpShortName), ratesModel.PHP))
            mutableList.add(RateItem((Constants.plnShortName), ratesModel.PLN))
            mutableList.add(RateItem((Constants.ronShortName), ratesModel.RON))
            mutableList.add(RateItem((Constants.rubShortName), ratesModel.RUB))
            mutableList.add(RateItem((Constants.sekShortName), ratesModel.SEK))
            mutableList.add(RateItem((Constants.sgdShortName), ratesModel.SGD))
            mutableList.add(RateItem((Constants.thbShortName), ratesModel.THB))
            mutableList.add(RateItem((Constants.tryShortName), ratesModel.TRY))
            mutableList.add(RateItem((Constants.usdShortName), ratesModel.USD))
            mutableList.add(RateItem((Constants.zarShortName), ratesModel.ZAR))
            return mutableList
        }

       fun getCurrencyLongName(currencyKey: String): String {
            return when (currencyKey) {
                Constants.audShortName -> Constants.audLongName
                Constants.bgnShortName -> Constants.bgnLongName
                Constants.brlShortName -> Constants.brlLongName
                Constants.cadShortName -> Constants.cadLongName
                Constants.chfShortName -> Constants.chfLongName
                Constants.cnyShortName -> Constants.cnyLongName
                Constants.czkShortName -> Constants.czkLongName
                Constants.dkkShortName -> Constants.dkkLongName
                Constants.eurShortName -> Constants.eurLongName
                Constants.gbpShortName -> Constants.gbpLongName
                Constants.hkdShortName -> Constants.hkdLongName
                Constants.hrkShortName -> Constants.hrkLongName
                Constants.hufShortName -> Constants.hufLongName
                Constants.idrShortName -> Constants.idrLongName
                Constants.ilsShortName -> Constants.ilsLongName
                Constants.inrShortName -> Constants.inrLongName
                Constants.iskShortName -> Constants.iskLongName
                Constants.jpyShortName -> Constants.jpyLongName
                Constants.krwShortName -> Constants.krwLongName
                Constants.mxnShortName -> Constants.mxnLongName
                Constants.myrShortName -> Constants.myrLongName
                Constants.nokShortName -> Constants.nokLongName
                Constants.nzdShortName -> Constants.nzdLongName
                Constants.phpShortName -> Constants.phpLongName
                Constants.plnShortName -> Constants.plnLongName
                Constants.ronShortName -> Constants.ronLongName
                Constants.rubShortName -> Constants.rubLongName
                Constants.sekShortName -> Constants.sekLongName
                Constants.sgdShortName -> Constants.sgdLongName
                Constants.thbShortName -> Constants.thbLongName
                Constants.tryShortName -> Constants.tryLongName
                Constants.usdShortName -> Constants.usdLongName
                Constants.zarShortName -> Constants.zarLongName
                else -> ("error")
            }
        }
    }
}