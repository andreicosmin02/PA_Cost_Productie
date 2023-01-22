package com.example.pacostproductie.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.pacostproductie.databinding.FragmentHomeBinding
import com.example.pacostproductie.viewmodel.PriceViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val priceViewModel: PriceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
//
//        val textView: TextView = binding.textHome
//        priceViewModel.priceUnCanatGeamRotobasculant.observe(viewLifecycleOwner) {
//            val finalPrice = priceViewModel.priceUnCanatGeamRotobasculant.value!! +
//                    priceViewModel.priceDouaCanateFix.value!! +
//                    priceViewModel.priceDouaCanateRotativInversor.value!! +
//                    priceViewModel.priceDouaCanateRotobasculantPlusRotativInversor.value!! +
//                    priceViewModel.priceDouaCanateFixPlusRotobasculant.value!! +
//                    priceViewModel.priceTreiCanateFix.value!! +
//                    priceViewModel.priceTreiCanateFixPlusRotobasculant.value!!
//            textView.text = "UnCanatGeamRotobasculant: ${priceViewModel.priceUnCanatGeamRotobasculant.value}\n" +
//                    "DouaCanateFix: ${priceViewModel.priceDouaCanateFix.value}\n" +
//                    "DouaCanateRotativInversor: ${priceViewModel.priceDouaCanateRotativInversor.value}\n" +
//                    "DouaCanateRotobasculantPlusRotativInversor: ${priceViewModel.priceDouaCanateRotobasculantPlusRotativInversor.value}\n" +
//                    "DouaCanateFixPlusRotobasculant: ${priceViewModel.priceDouaCanateFixPlusRotobasculant.value}\n" +
//                    "TreiCanateFix: ${priceViewModel.priceTreiCanateFix.value}\n" +
//                    "TreiCanateFixPlusRotobasculant: ${priceViewModel.priceTreiCanateFixPlusRotobasculant.value}\n" +
//                    "Final Price: $finalPrice"
//
//            Log.d("PriceHome", "Home = $finalPrice")
//        }
        val textView: TextView = binding.textHome
        priceViewModel.priceUnCanatGeamRotobasculant.observe(viewLifecycleOwner) {
            textView.text = "UnCanatGeamRotobasculant: ${priceViewModel.priceUnCanatGeamRotobasculant.value}"
            Log.d("PriceHome", "Home = ${priceViewModel.priceUnCanatGeamRotobasculant.value}")
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}