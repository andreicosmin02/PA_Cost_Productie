package com.example.pacostproductie.ui.douacanaterotobasculantplusrotativinversor
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import com.example.pacostproductie.R
import com.example.pacostproductie.databinding.FragmentDouaCanateRotobasculantPlusRotativInversorBinding
import com.example.pacostproductie.piese.DouaCanateRotobasculantPlusRotativInversor
import java.math.BigDecimal
import java.math.RoundingMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DouaCanateFixPlusRotobasculant.newInstance] factory method to
 * create an instance of this fragment.
 */
class DouaCanateRotobasculantPlusRotativInversorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var latime: Double = 0.0
    private var lungime: Double = 0.0

    private lateinit var binding: FragmentDouaCanateRotobasculantPlusRotativInversorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDouaCanateRotobasculantPlusRotativInversorBinding.inflate(layoutInflater)

        if (savedInstanceState != null) {
            latime = savedInstanceState.getDouble("latime")
            lungime = savedInstanceState.getDouble("lungime")
            binding.rgDcrpriCuloare.check(savedInstanceState.getInt("culoareId"))
            binding.rgDcrpriSticla.check(savedInstanceState.getInt("sticlaId"))

            binding.etDcrpriLatime.setText(latime.toString())
            binding.etDcrpriLungime.setText(lungime.toString())
            if (
                !binding.etDcrpriLatime.text.isEmpty() &&
                !binding.etDcrpriLungime.text.isEmpty() &&
                binding.rgDcrpriCuloare.checkedRadioButtonId != -1 &&
                binding.rgDcrpriSticla.checkedRadioButtonId != -1
            ) {
                calculateOutputValues()
            }
        }
        binding.bDcrpriCalculeaza.setOnClickListener {
            if (binding.etDcrpriLatime.text.isEmpty() || binding.etDcrpriLungime.text.isEmpty() || binding.rgDcrpriCuloare.checkedRadioButtonId == -1 || binding.rgDcrpriSticla.checkedRadioButtonId == -1) {
                if (context != null) {
                    Toast.makeText(context, "Please fill in all input fields", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                latime = binding.etDcrpriLatime.text.toString().toDouble()
                lungime = binding.etDcrpriLungime.text.toString().toDouble()
                calculateOutputValues()
            }
        }

        return binding.root
    }

    private fun calculateOutputValues() {
        val douaCanateRotobasculantPlusRotativInversor = DouaCanateRotobasculantPlusRotativInversor(latime, lungime)
        douaCanateRotobasculantPlusRotativInversor.init {
            val toc = BigDecimal(douaCanateRotobasculantPlusRotativInversor.getToc() / 1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val zf = BigDecimal(douaCanateRotobasculantPlusRotativInversor.getZF() / 1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val sticla = BigDecimal(douaCanateRotobasculantPlusRotativInversor.getSticla() / 100000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val inv= BigDecimal(douaCanateRotobasculantPlusRotativInversor.getInv()/1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val NRinv= BigDecimal(douaCanateRotobasculantPlusRotativInversor.getNRInv())
                .setScale(2, RoundingMode.HALF_EVEN)
            val fer = BigDecimal(douaCanateRotobasculantPlusRotativInversor.getFer())
                .setScale(2, RoundingMode.HALF_EVEN)
            val invf= inv * NRinv

            val finalPrice = calculatePrice(toc.toDouble(), zf.toDouble(), sticla.toDouble(),invf.toDouble(),fer.toDouble())

            binding.tvDcrpriOutput.text = "Toc = $toc ml\nZF = $zf ml\nSticla = $sticla m2\nInv = $invf ml\nFer = $fer ml\n\nPret = $finalPrice lei"
            Log.d("PriceDouaCanateRotobasculantPlusRotativInversor", "Doua Canate Rotativ Inversor = $finalPrice")
        }
    }
    fun calculatePrice(toc: Double, zf: Double, sticla: Double,invf:Double,fer:Double): BigDecimal? {
        val radioGroup: RadioGroup = binding.rgDcrpriCuloare
        val selectedId = radioGroup.checkedRadioButtonId
        var priceToc = 0.0
        var priceZf = 0.0
        var priceInv=0.0
        var priceFer=0.0

        when (selectedId) {
            R.id.rb_dcrpri_alb -> {
                priceToc = toc * 34
                priceZf = zf * 32
                priceInv = invf * 23
            }
            R.id.rb_dcrpri_color -> {
                priceToc = toc * 51
                priceZf = zf * 42
                priceInv = invf * 34
            }
            R.id.rb_dcrpri_alb_color -> {
                priceToc = toc * 40
                priceZf = zf * 40
                priceInv = invf * 31
            }
        }

        val radioGroupSticla: RadioGroup = binding.rgDcrpriSticla
        val selectedIdSticla = radioGroupSticla.checkedRadioButtonId
        var priceSticla = 0.0
        when (selectedIdSticla) {
            R.id.rb_dcrpri_f4_4s -> {
                priceSticla = sticla * 220
            }
            R.id.rb_dcrpri_f4_crossfield -> {
                priceSticla = sticla * 295
            }
        }

        return BigDecimal(priceToc + priceZf + priceSticla+ priceFer+ priceInv)
            .setScale(2, RoundingMode.HALF_EVEN)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("latime", latime)
        outState.putDouble("lungime", lungime)
        outState.putInt("culoareId", binding.rgDcrpriCuloare.checkedRadioButtonId)
        outState.putInt("sticlaId", binding.rgDcrpriSticla.checkedRadioButtonId)
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
            DouaCanateRotobasculantPlusRotativInversorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}