package com.company.ivan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.ivan.databinding.ActivityListViewAdminBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListViewAdmin : AppCompatActivity(), RecAdapter.ClickListener {
    lateinit var binding: ActivityListViewAdminBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var raceListAdapter:RecAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListViewAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newAdmin.setOnClickListener {
            startActivity(Intent(this,AddSweet::class.java))
        }
//        binding.updateAdmin.setOnClickListener {
//            raceListAdapter?.loadListToAdapter(getList())
//        }


        binding.recyclerViewAdmin.layoutManager= LinearLayoutManager(this)
        raceListAdapter= RecAdapter(this)
        binding.recyclerViewAdmin.adapter=raceListAdapter
        raceListAdapter?.loadListToAdapter(getList())

        val simpleCallback =object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id =raceListAdapter?.deleteItem(viewHolder.adapterPosition)
                database.getReference("Sweet").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (id != null) {
                            database.getReference("Sweet").child(id).removeValue()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            }

        }
        val itemTouchHelper= ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewAdmin)
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

    }
}