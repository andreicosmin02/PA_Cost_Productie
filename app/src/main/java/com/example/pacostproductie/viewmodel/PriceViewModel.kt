package com.example.pacostproductie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PriceViewModel : ViewModel() {
    val priceUnCanatGeamRotobasculant = MutableLiveData<Double>(0.0)
    val priceDouaCanateFix = MutableLiveData<Double>(0.0)
    val priceDouaCanateRotativInversor = MutableLiveData<Double>(0.0)
    val priceDouaCanateRotobasculantPlusRotativInversor = MutableLiveData<Double>(0.0)
    val priceDouaCanateFixPlusRotobasculant = MutableLiveData<Double>(0.0)
    val priceTreiCanateFix = MutableLiveData<Double>(0.0)
    val priceTreiCanateFixPlusRotobasculant = MutableLiveData<Double>(0.0)
}