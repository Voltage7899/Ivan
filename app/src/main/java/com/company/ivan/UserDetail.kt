package com.company.ivan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.ivan.databinding.ActivityUserDetailBinding
import com.squareup.picasso.Picasso

class UserDetail : AppCompatActivity() {
    lateinit var binding: ActivityUserDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item= intent.getSerializableExtra("Sweet") as Sweet
        Picasso.get().load(item.image).fit().into(binding.imageItem)

        binding.nameItem.setText(item.name)
        binding.priceItem.setText(item.price)


        binding.buyItem.setOnClickListener{

            Toast.makeText(this@UserDetail, "Данные высланы в смс", Toast.LENGTH_SHORT).show()
        }
    }
}