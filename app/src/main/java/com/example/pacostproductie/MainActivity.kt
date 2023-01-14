package com.example.pacostproductie

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.pacostproductie.databinding.ActivityMainBinding
import com.example.pacostproductie.piese.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_uncanatrotobasculant
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // UN CANAT GEAM ROTOBASCULANT EXEMPLU
//        val unCanatGeamRotobasculant = UnCanatGeamRotobasculant(latime = 3000.0, lungime = 4000.0)
//        unCanatGeamRotobasculant.init {
//            val toc = unCanatGeamRotobasculant.getToc()
//            val zf = unCanatGeamRotobasculant.getZF()
//            val sticla = unCanatGeamRotobasculant.getSticla()
//        }
//
//        val douaCanateFix = DouaCanateFix(3000.0, 4000.0)
//        douaCanateFix.init {
//            val toc = douaCanateFix.getToc()
//        }
        val douaCanateRotativInversor = DouaCanateRotativInversor(3000.0, 4000.0)
        douaCanateRotativInversor.init {
            val toc = douaCanateRotativInversor.getToc()
        }
//        val douaCanateRotobasculantPlusRotativInversor = DouaCanateRotobasculantPlusRotativInversor(3000.0, 4000.0)
//        douaCanateRotobasculantPlusRotativInversor.init {
//            val toc = douaCanateRotobasculantPlusRotativInversor.getToc()
//        }
//        val douaCanateFixPlusRotobasculant = DouaCanateFixPlusRotobasculant(3000.0, 4000.0)
//        douaCanateFixPlusRotobasculant.init {
//            val toc = douaCanateFixPlusRotobasculant.getToc()
//        }
//        val treiCanateFix = TreiCanateFix(3000.0, 4000.0)
//        treiCanateFix.init {
//            val toc = treiCanateFix.getToc()
//        }
//        val treiCanateFixPlusRotobasculant = TreiCanateFixPlusRotobasculant(3000.0, 4000.0)
//        treiCanateFixPlusRotobasculant.init {
//            val toc = treiCanateFixPlusRotobasculant.getToc()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}