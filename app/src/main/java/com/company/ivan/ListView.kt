package com.company.ivan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.ivan.databinding.ActivityListViewBinding
import com.google.firebase.database.FirebaseDatabase

class ListView : AppCompatActivity(), RecAdapter.ClickListener {
    lateinit var binding: ActivityListViewBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var raceListAdapter:RecAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerViewUser.layoutManager= LinearLayoutManager(this)
        raceListAdapter= RecAdapter(this)
        binding.recyclerViewUser.adapter=raceListAdapter
        raceListAdapter?.loadListToAdapter(getList())
    }

    fun getList():ArrayList<Sweet>{



        val commonList=ArrayList<Sweet>()
        database.getReference("Sweet").get().addOnSuccessListener {
            for (sw in it.children){
                var sweet=sw.getValue(Sweet::class.java)
                if(sweet!=null){
                    commonList.add(sweet)
                    raceListAdapter?.loadListToAdapter(commonList)
                }

            }
        }
        return commonList
    }
    override fun onClick(sw: Sweet) {
        startActivity(Intent(this, UserDetail::class.java).apply {
            putExtra("Sweet",sw)

        })
    }
}