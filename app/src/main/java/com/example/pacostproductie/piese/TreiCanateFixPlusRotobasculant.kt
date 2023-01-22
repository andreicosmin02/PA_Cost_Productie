package com.example.pacostproductie.piese

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TreiCanateFixPlusRotobasculant(var latime: Double, var lungime: Double) {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("Piese/treiCanateFixPlusRotobasculant")

    var pierderigeneraltocZetMontant: Double = 0.0
    var solid700DifAdaosZetMontantLaMijloc3canate: Double = 0.0
    var solid700DifTocMontant: Double = 0.0
    var solid700DifRamaZet: Double = 0.0
    var solid700DifRamaSticla: Double = 0.0
    var solid700DifZetSticla: Double = 0.0

    fun init(callback: () -> Unit) {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                pierderigeneraltocZetMontant = dataSnapshot.child("pierderigeneraltocZetMontant")
                    .getValue(Double::class.java)!!
                solid700DifTocMontant =
                    dataSnapshot.child("solid700DifTocMontant").getValue(Double::class.java)!!
                solid700DifRamaSticla =
                    dataSnapshot.child("solid700DifRamaSticla").getValue(Double::class.java)!!
                solid700DifAdaosZetMontantLaMijloc3canate =
                    dataSnapshot.child("solid700DifAdaosZetMontantLaMijloc3canate")
                        .getValue(Double::class.java)!!
                solid700DifRamaZet =
                    dataSnapshot.child("solid700DifRamaZet").getValue(Double::class.java)!!
                solid700DifZetSticla =
                    dataSnapshot.child("solid700DifZetSticla").getValue(Double::class.java)!!

                Log.d(
                    "treiCanateFixPlusRotobasculant",
                    "pierderigeneraltocZetMontant: $pierderigeneraltocZetMontant"
                )
                Log.d(
                    "treiCanateFixPlusRotobasculant",
                    "solid700DifTocMontant: $solid700DifTocMontant"
                )
                Log.d(
                    "treiCanateFixPlusRotobasculant",
                    "solid700DifRamaSticla: $solid700DifRamaSticla"
                )
                Log.d(
                    "treiCanateFixPlusRotobasculant",
                    "solid700DifAdaosZetMontatLaMijloc3canate: $solid700DifAdaosZetMontantLaMijloc3canate"
                )
                Log.d("treiCanateFixPlusRotobasculant", "solid700DifRamaZet: $solid700DifRamaZet")
                Log.d(
                    "treiCanateFixPlusRotobasculant",
                    "solid700DifZetSticla: $solid700DifZetSticla"
                )
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
        val montant = 2 * (1 + pierderigeneraltocZetMontant / 2) * (lungime - solid700DifTocMontant)
        return montant
    }

    fun getNrMontant(): Int {
        return 2
    }
    fun getZF(): Double {
        val zf = (2 + pierderigeneraltocZetMontant) * ((latime/2 - solid700DifRamaZet + solid700DifAdaosZetMontantLaMijloc3canate) + (lungime - solid700DifRamaZet))
        return zf
    }
    fun getSticla(): Double {
        val sticla = -2 * ((latime / 3 - latime - solid700DifRamaSticla + solid700DifAdaosZetMontantLaMijloc3canate) * (lungime - solid700DifRamaSticla)) + (latime / 3 - latime - solid700DifRamaSticla + solid700DifAdaosZetMontantLaMijloc3canate - solid700DifZetSticla) * (lungime - solid700DifRamaSticla - solid700DifZetSticla)
        return sticla
    }
}


