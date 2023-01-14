package com.example.pacostproductie.piese

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*

class UnCanatGeamRotobasculant(var latime: Double, var lungime: Double) {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("Piese/unCanatGeamRotobasculant")

    var pierderigeneraltocZetMontant: Double = 0.0
    var solid700DifRamaZet: Double = 0.0
    var feronerieRotobasculant: Double = 0.0
    var solid700DifRamaSticla: Double = 0.0
    var solid700DifZetSticla: Double = 0.0

    fun init(callback: () -> Unit) {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                pierderigeneraltocZetMontant = dataSnapshot.child("pierderigeneraltocZetMontant").getValue(Double::class.java)!!
                solid700DifRamaZet = dataSnapshot.child("solid700DifRamaZet").getValue(Double::class.java)!!
                feronerieRotobasculant = dataSnapshot.child("feronerieRotobasculant").getValue(Double::class.java)!!
                solid700DifRamaSticla = dataSnapshot.child("solid700DifRamaSticla").getValue(Double::class.java)!!
                solid700DifZetSticla = dataSnapshot.child("solid700DifZetSticla").getValue(Double::class.java)!!

                Log.d("UnCanatGeamRotobasculant", "pierderigeneraltocZetMontant: $pierderigeneraltocZetMontant")
                Log.d("UnCanatGeamRotobasculant", "solid700DifRamaZet: $solid700DifRamaZet")
                Log.d("UnCanatGeamRotobasculant", "feronerieRotobasculant: $feronerieRotobasculant")
                Log.d("UnCanatGeamRotobasculant", "solid700DifRamaSticla: $solid700DifRamaSticla")
                Log.d("UnCanatGeamRotobasculant", "solid700DifZetSticla: $solid700DifZetSticla")

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
    fun getZF(): Double {
        val zf = (2 + pierderigeneraltocZetMontant) * ((latime - solid700DifRamaZet) + (lungime - solid700DifRamaZet))
        return zf
    }

    fun getSticla(): Double {
        val sticla = (latime - (solid700DifRamaSticla + solid700DifZetSticla)) * (lungime - (solid700DifRamaSticla + solid700DifZetSticla))
        return sticla
    }

}