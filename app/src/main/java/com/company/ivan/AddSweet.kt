package com.company.ivan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.ivan.databinding.ActivityAddSweetBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class AddSweet : AppCompatActivity() {
    lateinit var binding: ActivityAddSweetBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddSweetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addDb.setOnClickListener {


            val id = Integer.toString((1 + Math.random() * 10000).toInt())
            val sw=Sweet(
                binding.nameAdd.text.toString(),
                binding.priceAdd.text.toString(),
                binding.linkAdd.text.toString(),
                id.toString()
            )
            database.getReference("Sweet").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(sw.id.toString()).exists()){
                        Toast.makeText(this@AddSweet,"Уже есть", Toast.LENGTH_SHORT).show()
                    }
                    else{

                        database.getReference("Sweet").child(id).setValue(sw)
                        startActivity(Intent(this@AddSweet,ListViewAdmin::class.java))
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
        binding.imageAdd.setOnClickListener{

            try{
                Picasso.get().load(binding.linkAdd.text.toString()).fit().into(binding.imageAdd)
            }
            catch (ex:Exception){
                Toast.makeText(this,"Нет ссылки на картинку", Toast.LENGTH_SHORT).show()
            }




        }
    }
}