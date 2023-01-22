package com.example.pacostproductie.ui.douacanatefixplusrotobasculant
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
import com.example.pacostproductie.databinding.FragmentDouaCanateFixPlusRotobasculantBinding
import com.example.pacostproductie.piese.DouaCanateFixPlusRotobasculant
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
class DouaCanateFixPlusRotobasculantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var latime: Double = 0.0
    private var lungime: Double = 0.0

    private lateinit var binding: FragmentDouaCanateFixPlusRotobasculantBinding

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
        binding = FragmentDouaCanateFixPlusRotobasculantBinding.inflate(layoutInflater)

        if (savedInstanceState != null) {
            latime = savedInstanceState.getDouble("latime")
            lungime = savedInstanceState.getDouble("lungime")
            binding.rgDcfprCuloare.check(savedInstanceState.getInt("culoareId"))
            binding.rgDcfprSticla.check(savedInstanceState.getInt("sticlaId"))

            binding.etDcfprLatime.setText(latime.toString())
            binding.etDcfprLungime.setText(lungime.toString())
            if (
                !binding.etDcfprLatime.text.isEmpty() &&
                !binding.etDcfprLungime.text.isEmpty() &&
                binding.rgDcfprCuloare.checkedRadioButtonId != -1 &&
                binding.rgDcfprSticla.checkedRadioButtonId != -1
            ) {
                calculateOutputValues()
            }
        }
        binding.bDcfprCalculeaza.setOnClickListener {
            if (binding.etDcfprLatime.text.isEmpty() || binding.etDcfprLungime.text.isEmpty() || binding.rgDcfprCuloare.checkedRadioButtonId == -1 || binding.rgDcfprSticla.checkedRadioButtonId == -1) {
                if (context != null) {
                    Toast.makeText(context, "Please fill in all input fields", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                latime = binding.etDcfprLatime.text.toString().toDouble()
                lungime = binding.etDcfprLungime.text.toString().toDouble()
                calculateOutputValues()
            }
        }

        return binding.root
    }

    private fun calculateOutputValues() {
        val douaCanateFixPlusRotobasculant = DouaCanateFixPlusRotobasculant(latime, lungime)
        douaCanateFixPlusRotobasculant.init {
            val toc = BigDecimal(douaCanateFixPlusRotobasculant.getToc() / 1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val zf = BigDecimal(douaCanateFixPlusRotobasculant.getZF() / 1000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val sticla = BigDecimal(douaCanateFixPlusRotobasculant.getSticla() / 100000)
                .setScale(2, RoundingMode.HALF_EVEN)
            val fer = BigDecimal(douaCanateFixPlusRotobasculant.getFer())
                .setScale(2, RoundingMode.HALF_EVEN)
            val montant = BigDecimal(douaCanateFixPlusRotobasculant.getFer())
                .setScale(2, RoundingMode.HALF_EVEN)


            val finalPrice = calculatePrice(toc.toDouble(), zf.toDouble(), montant.toDouble(), sticla.toDouble(), fer.toDouble() )

            binding.tvDcfprOutput.text = "Toc = $toc ml\nZF = $zf ml\nSticla = $sticla m2\n\nPret = $finalPrice lei"
            Log.d("PriceUnCanat", "Un canat = $finalPrice")
        }
    }


    fun calculatePrice(toc: Double, zf: Double, montant: Double, sticla: Double, fer: Double): BigDecimal? {
        val radioGroup: RadioGroup = binding.rgDcfprCuloare
        val selectedId = radioGroup.checkedRadioButtonId
        var priceToc = 0.0
        var priceZf = 0.0
        var pricemontant = 0.0

        when (selectedId) {
            R.id.rb_dcfpr_alb -> {
                priceToc = toc * 34
                priceZf = zf * 32
                pricemontant = montant * 48
            }
            R.id.rb_dcfpr_color -> {
                priceToc = toc * 51
                priceZf = zf * 42
                pricemontant = montant * 72
            }
            R.id.rb_dcfpr_alb_color -> {
                priceToc = toc * 40
                priceZf = zf * 40
                pricemontant = montant * 55
            }
        }

        var pricefer = fer * 21
        val radioGroupSticla: RadioGroup = binding.rgDcfprSticla
        val selectedIdSticla = radioGroupSticla.checkedRadioButtonId
        var priceSticla = 0.0
        when (selectedIdSticla) {
            R.id.rb_dcfpr_f4_4s -> {
                priceSticla = sticla * 220
            }
            R.id.rb_dcfpr_f4_crossfield -> {
                priceSticla = sticla * 295
            }
        }

        return BigDecimal(priceToc + priceZf + priceSticla + pricemontant + pricefer)
            .setScale(2, RoundingMode.HALF_EVEN)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("latime", latime)
        outState.putDouble("lungime", lungime)
        outState.putInt("culoareId", binding.rgDcfprCuloare.checkedRadioButtonId)
        outState.putInt("sticlaId", binding.rgDcfprSticla.checkedRadioButtonId)
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
            DouaCanateFixPlusRotobasculantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
