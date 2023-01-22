package com.example.pacostproductie.ui.uncanatrotobasculant

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import com.example.pacostproductie.R
import com.example.pacostproductie.databinding.FragmentUnCanatRotobasculantBinding
import com.example.pacostproductie.piese.UnCanatGeamRotobasculant
import java.math.BigDecimal
import java.math.RoundingMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UnCanatRotobasculantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UnCanatRotobasculantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var latime: Double = 0.0
    private var lungime: Double = 0.0

    private lateinit var binding: FragmentUnCanatRotobasculantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        binding = FragmentUnCanatRotobasculantBinding.inflate(layoutInflater)

        if (savedInstanceState != null) {
            latime = savedInstanceState.getDouble("latime")
            lungime = savedInstanceState.getDouble("lungime")
            binding.rgUcrCuloare.check(savedInstanceState.getInt("culoareId"))
            binding.rgUcrSticla.check(savedInstanceState.getInt("sticlaId"))

            binding.etUcrLatime.setText(latime.toString())
            binding.etUcrLungime.setText(lungime.toString())
            if (
                !binding.etUcrLatime.text.isEmpty() &&
                !binding.etUcrLungime.text.isEmpty() &&
                binding.rgUcrCuloare.checkedRadioButtonId != -1 &&
                binding.rgUcrSticla.checkedRadioButtonId != -1
            ) {
                calculateOutputValues()
            }
        }
        binding.bUcrCalculeaza.setOnClickListener {
            if (binding.etUcrLatime.text.isEmpty() || binding.etUcrLungime.text.isEmpty() || binding.rgUcrCuloare.checkedRadioButtonId == -1 || binding.rgUcrSticla.checkedRadioButtonId == -1) {
                if (context != null) {
                    Toast.makeText(context, "Please fill in all input fields", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                latime = binding.etUcrLatime.text.toString().toDouble()
                lungime = binding.etUcrLungime.text.toString().toDouble()
                calculateOutputValues()
            }
        }

        return binding.root
    }

    private fun calculateOutputValues() {
        val unCanatGeamRotobasculant = UnCanatGeamRotobasculant(latime, lungime)
        unCanatGeamRotobasculant.init {
            val toc = BigDecimal(unCanatGeamRotobasculant.getToc() / 1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val zf = BigDecimal(unCanatGeamRotobasculant.getZF() / 1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val sticla = BigDecimal(unCanatGeamRotobasculant.getSticla() / 100000)
                .setScale(2, RoundingMode.HALF_EVEN)

            val finalPrice = calculatePrice(toc.toDouble(), zf.toDouble(), sticla.toDouble())

            binding.tvUcrOutput.text = "Toc = $toc ml\nZF = $zf ml\nSticla = $sticla m2\n\nPret = $finalPrice lei"
            Log.d("PriceUnCanat", "Un canat = $finalPrice")
        }
    }

    fun calculatePrice(toc: Double, zf: Double, sticla: Double): BigDecimal? {
        val radioGroup: RadioGroup = binding.rgUcrCuloare
        val selectedId = radioGroup.checkedRadioButtonId
        var priceToc = 0.0
        var priceZf = 0.0

        when (selectedId) {
            R.id.rb_ucr_alb -> {
                priceToc = toc * 34
                priceZf = zf * 32
            }
            R.id.rb_ucr_color -> {
                priceToc = toc * 51
                priceZf = zf * 42
            }
            R.id.rb_ucr_alb_color -> {
                priceToc = toc * 40
                priceZf = zf * 40
            }
        }

        val radioGroupSticla: RadioGroup = binding.rgUcrSticla
        val selectedIdSticla = radioGroupSticla.checkedRadioButtonId
        var priceSticla = 0.0
        when (selectedIdSticla) {
            R.id.rb_ucr_f4_4s -> {
                priceSticla = sticla * 220
            }
            R.id.rb_ucr_f4_crossfield -> {
                priceSticla = sticla * 295
            }
        }

        return BigDecimal(priceToc + priceZf + priceSticla)
            .setScale(2, RoundingMode.HALF_EVEN)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("latime", latime)
        outState.putDouble("lungime", lungime)
        outState.putInt("culoareId", binding.rgUcrCuloare.checkedRadioButtonId)
        outState.putInt("sticlaId", binding.rgUcrSticla.checkedRadioButtonId)
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
                UnCanatRotobasculantFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}