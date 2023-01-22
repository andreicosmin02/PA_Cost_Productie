package com.example.pacostproductie.piese

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*

class DouaCanateFix(var latime: Double, var lungime: Double) {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("Piese/douaCanateFix")

    var pierderigeneraltocZetMontant: Double = 0.0
    var solid700DifTocMontant: Double = 0.0
    var solid700DifRamaSticla: Double = 0.0
    var solid700DifAdaosZetMontantLaMijloc: Double = 0.0

    fun init(callback: () -> Unit) {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                pierderigeneraltocZetMontant = dataSnapshot.child("pierderigeneraltocZetMontant").getValue(Double::class.java)!!
                solid700DifTocMontant = dataSnapshot.child("solid700DifTocMontant").getValue(Double::class.java)!!
                solid700DifRamaSticla = dataSnapshot.child("solid700DifRamaSticla").getValue(Double::class.java)!!
                solid700DifAdaosZetMontantLaMijloc = dataSnapshot.child("solid700DifAdaosZetMontantLaMijloc").getValue(Double::class.java)!!

                Log.d("DouaCanateFix", "pierderigeneraltocZetMontant: $pierderigeneraltocZetMontant")
                Log.d("DouaCanateFix", "solid700DifTocMontant: $solid700DifTocMontant")
                Log.d("DouaCanateFix", "solid700DifRamaSticla: $solid700DifRamaSticla")
                Log.d("DouaCanateFix", "solid700DifAdaosZetMontantLaMijloc: $solid700DifAdaosZetMontantLaMijloc")

                callback()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })
    }

    fun getToc(): Double {
        val toc = (2 + pierderigeneraltocZetMontant) * (latime + lungime)
        return toc
    }



    fun getMontant(): Double {
        val montant = (1 + pierderigeneraltocZetMontant / 2) * (lungime - solid700DifTocMontant)
        return montant
    }

    fun getNrMontant(): Int {
        return 1
    }

    fun getSticla(): Double {
        val sticla = 2 * ((latime / 2) - solid700DifRamaSticla + solid700DifAdaosZetMontantLaMijloc) * (lungime - solid700DifRamaSticla)
        return sticla
    }
}
