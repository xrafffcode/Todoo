package com.app.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        ref = FirebaseDatabase.getInstance().getReference(user?.uid.toString())


        val btnSave = findViewById<Button>(R.id.save)
        btnSave.setOnClickListener {
            savedata()


        }
    }

    private fun savedata() {
        val inputTodo = findViewById<EditText>(R.id.inputTodo)

        val todo = inputTodo.text.toString()

        val userId = ref.push().key.toString()
        val user = User(userId,todo)


        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            inputTodo.setText("")
            val back = Intent(this, MainActivity::class.java)
            startActivity(back)
        }
    }
}