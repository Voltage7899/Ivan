package com.company.ivan

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.company.ivan.CurrentUser.Companion.currentUser
import com.company.ivan.databinding.ActivitySignInBinding
import com.google.firebase.database.*

class SignIn : AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signSign.setOnClickListener {
            val database = FirebaseDatabase.getInstance().getReference("User")
            if (TextUtils.isEmpty(binding.phoneSign.text.toString()) && TextUtils.isEmpty(binding.passSign.text.toString())) {
                Toast.makeText(this@SignIn, "Введите все данные", Toast.LENGTH_SHORT).show()
            } else {
                database.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(binding.phoneSign.text.toString()).exists()) {
                            val userCurrentData: User? = snapshot.child(binding.phoneSign.text.toString()).getValue(
                                User::class.java
                            )
                            CurrentUser.currentUser = userCurrentData
                            if (userCurrentData?.phone.equals(binding.phoneSign.text.toString()) && userCurrentData?.pass.equals(binding.passSign.text.toString())) {
                                Toast.makeText(this@SignIn, "Вы вошли как Юзер", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@SignIn, ListView::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@SignIn, "Неверные данные", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this@SignIn, "Номера не существует", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }
        binding.adminSign.setOnClickListener {
            val database = FirebaseDatabase.getInstance().getReference("Admin")
            if (TextUtils.isEmpty(binding.phoneSign.text.toString()) && TextUtils.isEmpty(binding.passSign.text.toString())) {
                Toast.makeText(this@SignIn, "Введите все данные", Toast.LENGTH_SHORT).show()
            } else {
                database.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(binding.phoneSign.text.toString()).exists()) {
                            val userCurrentData: User? = snapshot.child(binding.phoneSign.text.toString()).getValue(User::class.java)

                            CurrentUser.currentUser = userCurrentData

                            if (userCurrentData?.phone.equals(binding.phoneSign.text.toString()) && userCurrentData?.pass.equals(
                                    binding.passSign.text.toString()))
                            {
                                Toast.makeText(this@SignIn, "Вы вошли как админ", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@SignIn, ListViewAdmin::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@SignIn, "Неверные данные", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this@SignIn, "Номера не существует", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }
    }
}