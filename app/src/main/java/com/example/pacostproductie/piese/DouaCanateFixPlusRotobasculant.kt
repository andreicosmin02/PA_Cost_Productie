package com.example.pacostproductie.piese

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DouaCanateFixPlusRotobasculant(var latime: Double, var lungime: Double) {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("Piese/douaCanateFixPlusRotobasculant")

    var pierderigeneraltocZetMontant: Double = 0.0
    var solid700DifTocMontant: Double = 0.0
    var solid700DifRamaZet: Double = 0.0
    var solid700DifAdaosZetMontantLaMijloc: Double = 0.0
    var solid700DifRamaSticla: Double = 0.0
    var solid700DifZetSticla: Double = 0.0
    var rotobasculant: Double = 0.0


    fun init(callback: () -> Unit) {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                pierderigeneraltocZetMontant = dataSnapshot.child("pierderigeneraltocZetMontant").getValue(Double::class.java)!!
                solid700DifTocMontant = dataSnapshot.child("solid700DifTocMontant").getValue(Double::class.java)!!
                solid700DifRamaZet = dataSnapshot.child("solid700DifRamaZet").getValue(Double::class.java)!!
                solid700DifAdaosZetMontantLaMijloc = dataSnapshot.child("solid700DifAdaosZetMontantLaMijloc").getValue(Double::class.java)!!
                solid700DifRamaSticla = dataSnapshot.child("solid700DifRamaSticla").getValue(Double::class.java)!!
                solid700DifZetSticla = dataSnapshot.child("solid700DifZetSticla").getValue(Double::class.java)!!
                rotobasculant = dataSnapshot.child("rotobasculant").getValue(Double::class.java)!!


                Log.d("douaCanateFixPlusRotobasculant", "pierderigeneraltocZetMontant: $pierderigeneraltocZetMontant")
                Log.d("douaCanateFixPlusRotobasculant", "solid700DifTocMontant: $solid700DifTocMontant")
                Log.d("douaCanateFixPlusRotobasculant", "solid700DifRamaZet: $solid700DifRamaZet")
                Log.d("douaCanateFixPlusRotobasculant", "solid700DifAdaosZetMontantLaMijloc: $solid700DifAdaosZetMontantLaMijloc")
                Log.d("douaCanateFixPlusRotobasculant", "solid700DifZetSticla: $solid700DifZetSticla")
                Log.d("douaCanateFixPlusRotobasculant", "solid700DifRamaSticla: $solid700DifRamaSticla")
                Log.d("douaCanateFixPlusRotobasculant", "rotobasculant: $rotobasculant")

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

    fun getNrMontat(): Int {
        return 1
    }

    fun getZF(): Double {
        val zf = (2+pierderigeneraltocZetMontant)*((latime/2 - solid700DifRamaZet + solid700DifAdaosZetMontantLaMijloc) + (lungime - solid700DifRamaZet))
        return zf
    }

    fun getSticla(): Double {
        val sticla = (((latime/2) - solid700DifRamaSticla + solid700DifAdaosZetMontantLaMijloc) * (lungime-solid700DifRamaSticla)) + ((latime/2) - solid700DifRamaSticla - solid700DifZetSticla + solid700DifAdaosZetMontantLaMijloc)* (lungime-solid700DifRamaSticla-solid700DifZetSticla)
        return sticla
    }

    fun getFer(): Double {
        val fer = rotobasculant
        return fer
    }
}