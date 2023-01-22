package com.example.pacostproductie.piese

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DouaCanateRotobasculantPlusRotativInversor(var latime: Double, var lungime: Double) {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("Piese/douaCanateRotobasculantPlusRotativInversor")

    var pierderigeneraltocZetMontant: Double = 0.0
    var solid700DifTocInv: Double = 0.0
    var solid700DifRamaSticla: Double = 0.0
    var solid700DifRamaZet: Double = 0.0
    var solid700DifZetSticla: Double = 0.0
    var solid700DifAdaosZetInvLaMijloc: Double = 0.0
    var rotobasculant: Double = 0.0
    var inversor: Double = 0.0


    fun init(callback: () -> Unit) {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                pierderigeneraltocZetMontant = dataSnapshot.child("pierderigeneraltocZetMontant").getValue(Double::class.java)!!
                solid700DifTocInv = dataSnapshot.child("solid700DifTocInv").getValue(Double::class.java)!!
                solid700DifRamaSticla = dataSnapshot.child("solid700DifRamaSticla").getValue(Double::class.java)!!
                solid700DifRamaZet = dataSnapshot.child("solid700DifRamaZet").getValue(Double::class.java)!!
                solid700DifZetSticla = dataSnapshot.child("solid700DifZetSticla").getValue(Double::class.java)!!
                solid700DifAdaosZetInvLaMijloc = dataSnapshot.child("solid700DifAdaosZetInvLaMijloc").getValue(Double::class.java)!!
                rotobasculant = dataSnapshot.child("rotobasculant").getValue(Double::class.java)!!
                inversor = dataSnapshot.child("inversor").getValue(Double::class.java)!!


                Log.d("douaCanateRotobasculantPlusRotativInversor", "pierderigeneraltocZetMontant: $pierderigeneraltocZetMontant")
                Log.d("douaCanateRotobasculantPlusRotativInversor", "solid700DifTocInv: $solid700DifTocInv")
                Log.d("douaCanateRotobasculantPlusRotativInversor", "solid700DifRamaSticla: $solid700DifRamaSticla")
                Log.d("douaCanateRotobasculantPlusRotativInversor", "solid700DifRamaZet: $solid700DifRamaZet")
                Log.d("douaCanateRotobasculantPlusRotativInversor", "solid700DifZetSticla: $solid700DifZetSticla")
                Log.d("douaCanateRotobasculantPlusRotativInversor", "solid700DifAdaosZetInvLaMijloc: $solid700DifAdaosZetInvLaMijloc")
                Log.d("douaCanateRotobasculantPlusRotativInversor", "rotobasculant: $rotobasculant")
                Log.d("douaCanateRotobasculantPlusRotativInversor", "inversor: $inversor")
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
        val sticla = 2 * (((latime/2) - solid700DifRamaSticla - solid700DifZetSticla + solid700DifAdaosZetInvLaMijloc) * (lungime - solid700DifRamaSticla - solid700DifZetSticla))
        return sticla
    }

    fun getFer(): Double {
        val fer = rotobasculant - inversor
        return fer
    }
}