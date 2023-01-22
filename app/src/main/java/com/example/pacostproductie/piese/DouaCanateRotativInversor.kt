package com.example.pacostproductie.piese

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*

class DouaCanateRotativInversor(var latime: Double, var lungime: Double) {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("Piese/douaCanateRotativInversor")

    var pierderigeneraltocZetMontant: Double = 0.0
    var solid700DifTocInv: Double = 0.0
    var solid700DifRamaZet: Double = 0.0
    var solid700DifAdaosZetInvLaMijloc: Double = 0.0
    var solid700DifZetSticla: Double = 0.0
    var rotativ: Double = 0.0
    var inversor: Double = 0.0

    fun init(callback: () -> Unit) {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                pierderigeneraltocZetMontant = dataSnapshot.child("pierderigeneraltocZetMontant").getValue(Double::class.java)!!
                solid700DifTocInv = dataSnapshot.child("solid700DifTocInv").getValue(Double::class.java)!!
                solid700DifRamaZet = dataSnapshot.child("solid700DifRamaZet").getValue(Double::class.java)!!
                solid700DifAdaosZetInvLaMijloc = dataSnapshot.child("solid700DifAdaosZetInvLaMijloc").getValue(Double::class.java)!!
                solid700DifZetSticla = dataSnapshot.child("solid700DifZetSticla").getValue(Double::class.java)!!
                rotativ = dataSnapshot.child("rotativ").getValue(Double::class.java)!!
                inversor = dataSnapshot.child("inversor").getValue(Double::class.java)!!

                Log.d("DouaCanateRotativInversor", "pierderigeneraltocZetMontant: $pierderigeneraltocZetMontant")
                Log.d("DouaCanateRotativInversor", "solid700DifTocInv: $solid700DifTocInv")
                Log.d("DouaCanateRotativInversor", "solid700DifRamaZet: $solid700DifRamaZet")
                Log.d("DouaCanateRotativInversor", "solid700DifAdaosZetInvLaMijloc: $solid700DifAdaosZetInvLaMijloc")
                Log.d("DouaCanateRotativInversor", "solid700DifZetSticla: $solid700DifZetSticla")
                Log.d("DouaCanateRotativInversor", "rotativ: $rotativ")
                Log.d("DouaCanateRotativInversor", "inversor: $inversor")

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

    fun getInv(): Double {
        val inv = (1 + pierderigeneraltocZetMontant / 2) * (lungime - solid700DifTocInv)
        return inv
    }

    fun getNRInv(): Double {
        return 1.0
    }

    fun getZF(): Double {
        val zf = (2 + pierderigeneraltocZetMontant) * (2 * (latime/2 - solid700DifRamaZet + solid700DifAdaosZetInvLaMijloc) + 2 * (lungime - solid700DifRamaZet))
        return zf
    }

    fun getSticla(): Double {
        val sticla = 2 * ((latime/2 - solid700DifRamaZet - solid700DifZetSticla + solid700DifAdaosZetInvLaMijloc) * (lungime - solid700DifRamaZet - solid700DifZetSticla))
        return sticla
    }

    fun getFer(): Double {
        val fer = 2 * rotativ - inversor
        return fer
    }
}