package com.example.pacostproductie.ui.douacanatefix
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.pacostproductie.R
import com.example.pacostproductie.databinding.FragmentDouaCanateFixBinding
import com.example.pacostproductie.piese.DouaCanateFix
import java.math.BigDecimal
import java.math.RoundingMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DouaCanateFix.newInstance] factory method to
 * create an instance of this fragment.
 */
class DouaCanateFixFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var latime: Double = 0.0
    private var lungime: Double = 0.0

    private lateinit var binding: FragmentDouaCanateFixBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDouaCanateFixBinding.inflate(layoutInflater)

        if (savedInstanceState != null) {
            latime = savedInstanceState.getDouble("latime")
            lungime = savedInstanceState.getDouble("lungime")
            binding.rgDcfCuloare.check(savedInstanceState.getInt("culoareId"))
            binding.rgDcfSticla.check(savedInstanceState.getInt("sticlaId"))

            binding.etDcfLatime.setText(latime.toString())
            binding.etDcfLungime.setText(lungime.toString())
            if (
                !binding.etDcfLatime.text.isEmpty() &&
                !binding.etDcfLungime.text.isEmpty() &&
                binding.rgDcfCuloare.checkedRadioButtonId != -1 &&
                binding.rgDcfSticla.checkedRadioButtonId != -1
            ) {
                calculateOutputValues()
            }
        }
        binding.bDcfCalculeaza.setOnClickListener {
            if (binding.etDcfLatime.text.isEmpty() || binding.etDcfLungime.text.isEmpty() || binding.rgDcfCuloare.checkedRadioButtonId == -1 || binding.rgDcfSticla.checkedRadioButtonId == -1) {
                if (context != null) {
                    Toast.makeText(context, "Please fill in all input fields", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                latime = binding.etDcfLatime.text.toString().toDouble()
                lungime = binding.etDcfLungime.text.toString().toDouble()
                calculateOutputValues()
            }
        }

        return binding.root
    }

    private fun calculateOutputValues() {
        val DouaCanateFix = DouaCanateFix(latime, lungime)
        DouaCanateFix.init {
            val toc = BigDecimal(DouaCanateFix.getToc() / 1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val montant =  BigDecimal(DouaCanateFix.getMontant() / 1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val sticla = BigDecimal(DouaCanateFix.getSticla() / 100000)
                .setScale(2, RoundingMode.HALF_EVEN)

            val finalPrice = calculatePrice(toc.toDouble(),montant.toDouble(), sticla.toDouble())

            binding.tvDcfOutput.text = "Toc = $toc ml\nMontant = $montant ml\nSticla = $sticla m2\n\nPret = $finalPrice lei"
            Log.d("PriceUDouaCanateFix", "DouaCanateFix = $finalPrice")
        }
    }

    fun calculatePrice(toc: Double, montant: Double, sticla: Double): BigDecimal? {
        val radioGroup: RadioGroup = binding.rgDcfCuloare
        val selectedId = radioGroup.checkedRadioButtonId
        var priceToc = 0.0
        var priceMontant = 0.0

        when (selectedId) {
            R.id.rb_dcf_alb -> {
                priceToc = toc * 34
                priceMontant = montant * 48
            }
            R.id.rb_dcf_color -> {
                priceToc = toc * 51
                priceMontant = montant * 72

            }
            R.id.rb_dcf_alb_color -> {
                priceToc = toc * 40
                priceMontant = montant * 55

            }
        }


        val radioGroupSticla: RadioGroup = binding.rgDcfSticla
        val selectedIdSticla = radioGroupSticla.checkedRadioButtonId
        var priceSticla = 0.0
        when (selectedIdSticla) {
            R.id.rb_dcf_f4_4s -> {
                priceSticla = sticla * 220
            }
            R.id.rb_dcf_f4_crossfield -> {
                priceSticla = sticla * 295
            }
        }

        return BigDecimal(priceToc + priceSticla + priceMontant)
            .setScale(2, RoundingMode.HALF_EVEN)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("latime", latime)
        outState.putDouble("lungime", lungime)
        outState.putInt("culoareId", binding.rgDcfCuloare.checkedRadioButtonId)
        outState.putInt("sticlaId", binding.rgDcfSticla.checkedRadioButtonId)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UnCanatRotobasculantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
            DouaCanateFixFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}