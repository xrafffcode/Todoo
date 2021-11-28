package com.app.todolist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {


    lateinit var mCtx: Context
    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<com.app.todolist.User>
    lateinit var listView: ListView
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val add = findViewById<FloatingActionButton>(R.id.add)
        val refresh = findViewById<SwipeRefreshLayout>(R.id.swipe_to_refresh_layout)
        val profile = findViewById<CircleImageView>(R.id.userpp)
        val name = findViewById<TextView>(R.id.name)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        name.setText(user?.displayName)


        profile.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }


        val progresbar = findViewById<ShimmerFrameLayout>(R.id.progressBar2)
        progresbar.startShimmerAnimation()

        refresh.setOnRefreshListener{
            Toast.makeText(this, "Page Refreshed", Toast.LENGTH_SHORT).show()

            refresh.isRefreshing = false
            recreate()
        }


        add.setOnClickListener {
            val intAdd = Intent(this, AddActivity::class.java)
            startActivity(intAdd)
        }

        if (user != null){
            val ivProvile = findViewById<CircleImageView>(R.id.userpp)

            if (user.photoUrl != null){

                Picasso.get().load(user.photoUrl).into(ivProvile)
            }else{

                Picasso.get().load("https://i.picsum.photos/id/1025/4951/33 Q 01.jpg?hmac=_aGh5AtoOChip_iaMo8ZvvytfEojcgqbCH7dzaz-H8Y").into(ivProvile)
            }




        }







        ref = FirebaseDatabase.getInstance().getReference(user?.uid.toString())
        list = mutableListOf()
        listView = findViewById(R.id.listView)



        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    for (h in p0.children){
                        val user = h.getValue(com.app.todolist.User::class.java)
                        list.add(user!!)
                    }
                    val adapter = Adapter(applicationContext,R.layout.todo,list)
                    listView.adapter = adapter
                }
            }


        })
    }




}