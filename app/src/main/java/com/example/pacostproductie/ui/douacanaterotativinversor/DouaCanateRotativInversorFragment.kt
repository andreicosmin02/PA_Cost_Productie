package com.example.pacostproductie.ui.douacanaterotativinversor
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
import com.example.pacostproductie.databinding.FragmentDouaCanateRotativInversorBinding
import com.example.pacostproductie.piese.DouaCanateRotativInversor
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
class DouaCanateRotativInversorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var latime: Double = 0.0
    private var lungime: Double = 0.0
    private lateinit var binding: FragmentDouaCanateRotativInversorBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDouaCanateRotativInversorBinding.inflate(layoutInflater)

        if (savedInstanceState != null) {
            latime = savedInstanceState.getDouble("latime")
            lungime = savedInstanceState.getDouble("lungime")
            binding.rgDcriCuloare.check(savedInstanceState.getInt("culoareId"))
            binding.rgDcriSticla.check(savedInstanceState.getInt("sticlaId"))

            binding.etDcriLatime.setText(latime.toString())
            binding.etDcriLungime.setText(lungime.toString())
            if (
                !binding.etDcriLatime.text.isEmpty() &&
                !binding.etDcriLungime.text.isEmpty() &&
                binding.rgDcriCuloare.checkedRadioButtonId != -1 &&
                binding.rgDcriSticla.checkedRadioButtonId != -1
            ) {
                calculateOutputValues()
            }
        }
        binding.bDcriCalculeaza.setOnClickListener {
            if (binding.etDcriLatime.text.isEmpty() || binding.etDcriLungime.text.isEmpty() || binding.rgDcriCuloare.checkedRadioButtonId == -1 || binding.rgDcriSticla.checkedRadioButtonId == -1) {
                if (context != null) {
                    Toast.makeText(context, "Please fill in all input fields", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                latime = binding.etDcriLatime.text.toString().toDouble()
                lungime = binding.etDcriLungime.text.toString().toDouble()
                calculateOutputValues()
            }
        }

        return binding.root
    }

    private fun calculateOutputValues() {
        val douaCanateRotativInversor = DouaCanateRotativInversor(latime, lungime)
        douaCanateRotativInversor.init {
            val toc = BigDecimal(douaCanateRotativInversor.getToc() / 1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val zf = BigDecimal(douaCanateRotativInversor.getZF() / 1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val sticla = BigDecimal(douaCanateRotativInversor.getSticla() / 100000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val inv=BigDecimal(douaCanateRotativInversor.getInv()/1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val NRinv=BigDecimal(douaCanateRotativInversor.getNRInv())
                .setScale(2, RoundingMode.HALF_EVEN)
            val fer=BigDecimal(douaCanateRotativInversor.getFer())
                .setScale(2, RoundingMode.HALF_EVEN)
            val invf = BigDecimal(inv.toDouble() * NRinv.toDouble())
                .setScale(2, RoundingMode.HALF_EVEN)

            val finalPrice = calculatePrice(toc.toDouble(), zf.toDouble(), sticla.toDouble(), invf.toDouble(), fer.toDouble())

            binding.tvDcriOutput.text = "Toc = $toc ml\nZF = $zf ml\nSticla = $sticla m2\nInv = $invf ml\nFer = $fer ml\n\nPret = $finalPrice lei"
            Log.d("PriceDouaCanateRotativInversor", "Doua Canate Rotativ Inversor = $finalPrice")
        }
    }
    fun calculatePrice(toc: Double, zf: Double, sticla: Double,invf:Double,fer:Double): BigDecimal? {
        val radioGroup: RadioGroup = binding.rgDcriCuloare
        val selectedId = radioGroup.checkedRadioButtonId
        var priceToc = 0.0
        var priceZf = 0.0
        var priceInv = 0.0
        var priceFer = fer

        when (selectedId) {
            R.id.rb_dcri_alb -> {
                priceToc = toc * 34
                priceZf = zf * 32
                priceInv = invf * 23
            }
            R.id.rb_dcri_color -> {
                priceToc = toc * 51
                priceZf = zf * 42
                priceInv = invf * 34
            }
            R.id.rb_dcri_alb_color -> {
                priceToc = toc * 40
                priceZf = zf * 40
                priceInv = invf * 31
            }
        }

        val radioGroupSticla: RadioGroup = binding.rgDcriSticla
        val selectedIdSticla = radioGroupSticla.checkedRadioButtonId
        var priceSticla = 0.0
        when (selectedIdSticla) {
            R.id.rb_dcri_f4_4s -> {
                priceSticla = sticla * 220
            }
            R.id.rb_dcri_f4_crossfield -> {
                priceSticla = sticla * 295
            }
        }

        return BigDecimal(priceToc + priceZf + priceSticla+priceFer+priceInv)
            .setScale(2, RoundingMode.HALF_EVEN)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("latime", latime)
        outState.putDouble("lungime", lungime)
        outState.putInt("culoareId", binding.rgDcriCuloare.checkedRadioButtonId)
        outState.putInt("sticlaId", binding.rgDcriSticla.checkedRadioButtonId)
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
            DouaCanateRotativInversorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
