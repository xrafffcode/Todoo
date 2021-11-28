package com.app.todolist

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Adapter(val mCtx: Context, val layoutResId: Int, val  list: List<User>)
    : ArrayAdapter<User>(mCtx,layoutResId,list){

    private lateinit var auth : FirebaseAuth

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)



        val textNama = view.findViewById<TextView>(R.id.textNama)
        val textDelete = view.findViewById<TextView>(R.id.delete)



        val user = list[position]

        textNama.text = user.todo
        textDelete.setOnClickListener {
            Deleteinfo(user)
        }



        return view
    }

    private fun Deleteinfo(user: User) {
        auth = FirebaseAuth.getInstance()
        val todo = auth.currentUser

        val mydatabase = FirebaseDatabase.getInstance().getReference(todo?.uid.toString())
        mydatabase.child(user.id).removeValue()
        Toast.makeText(mCtx,"Deleted,Swipe To Refresh",Toast.LENGTH_SHORT).show()

    }

}


