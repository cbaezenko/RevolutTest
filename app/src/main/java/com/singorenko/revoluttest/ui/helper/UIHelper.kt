package com.singorenko.revoluttest.ui.helper

import com.singorenko.revoluttest.network.model.RatesModel
import com.singorenko.revoluttest.ui.model.RateItem
import com.singorenko.revoluttest.util.Constants

class UIHelper {

    companion object {

        fun fillListRareItems(ratesModel: RatesModel) : MutableList<RateItem>{
            val mutableList: MutableList<RateItem> = ArrayList()

            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))

            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))
            mutableList.add(RateItem(getCurrencyLongName(Constants.audShortName), Constants.audShortName, ratesModel.AUD))

            return mutableList
        }

        private fun getCurrencyLongName(currencyKey: String): String {
            return when (currencyKey) {
                Constants.audShortName -> Constants.audLongName
                Constants.bgnShortName -> Constants.bgnLongName
                Constants.brlShortName -> Constants.brlLongName
                Constants.cadShortName -> Constants.cadLongName
                Constants.chfShortName -> Constants.chfLongName
                Constants.cnyShortName -> Constants.cnyLongName
                Constants.czkShortName -> Constants.czkLongName
                Constants.dkkShortName -> Constants.dkkLongName
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