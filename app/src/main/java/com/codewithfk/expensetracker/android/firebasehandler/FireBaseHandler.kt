package com.codewithfk.expensetracker.android.firebasehandler

import android.content.ContentValues
import android.util.Log
import com.codewithfk.expensetracker.android.viewmodel.AddExpenseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class FireBaseHandler {
    var database = FirebaseDatabase.getInstance()
    private fun getCars() {
        database.getReference("currency")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                } else {
                    Log.w(ContentValues.TAG, task.exception?.localizedMessage.toString())
                }
            }
    }


    val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            val post = dataSnapshot.getValue<AddExpenseViewModel>()
            // ...
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
}