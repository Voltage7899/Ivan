package com.company.ivan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.ivan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reg.setOnClickListener {
            val intent = Intent(
                this,
                Register::class.java
            )
            startActivity(intent)
        }
        binding.sing.setOnClickListener {
            val intent = Intent(
                this,
                SignIn::class.java
            )
            startActivity(intent)
        }
    }
}