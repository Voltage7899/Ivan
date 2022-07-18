package com.company.ivan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.company.ivan.databinding.ActivityRegisterBinding
import com.google.firebase.database.*

class Register : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private var register: Button? = null

    //Переменная типа ссылки базы данных
    private var database: DatabaseReference? = null

    //Название создаваемой таблицы
    private val TABLE_NAME = "User"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Получаем ссылку на таблицу базы данных
        database = FirebaseDatabase.getInstance().getReference(TABLE_NAME)

        //Задаем слушателя
        binding.regReg.setOnClickListener(View.OnClickListener {
            //В переменные типа стринг пихаем введенные данные из полей
            val name = binding.nameReg.getText().toString()
            val phone = binding.phoneReg.getText().toString()
            val pass = binding.passReg.getText().toString()

            //Создаем разметку данных через ХэшМэп
            val userDataMap = HashMap<String, Any>()
            userDataMap["name"] = name
            userDataMap["phone"] = phone
            userDataMap["pass"] = pass

            //Устанавливаем слушателя на изменение данных в базе данных
            database?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //Как только мы считали данные с базы данных,нам приходят данные в качестве снэпшота
                    if (!snapshot.child(phone)
                            .exists()
                    ) { //Проверяем,есть ли введенный телефон уже в базе данных,если нет,то добавляем хэшмэп в бд
                        database!!.child(phone).updateChildren(userDataMap)
                        Toast.makeText(this@Register, "Вы зарегестрированы", Toast.LENGTH_SHORT)
                            .show()

                        finish()
                    } else {
                        Toast.makeText(
                            this@Register,
                            "Пользователь с такими данными уже есть",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        })
    }
}