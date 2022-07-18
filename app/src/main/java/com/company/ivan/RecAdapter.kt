package com.company.ivan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.ivan.databinding.ListElementBinding
import com.squareup.picasso.Picasso

class RecAdapter(val clickListener: ClickListener) : RecyclerView.Adapter<RecAdapter.RaceViewHolder>()  {
    private var raceListInAdapter= ArrayList<Sweet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecAdapter.RaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_element,parent,false)

        return RaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecAdapter.RaceViewHolder, position: Int) {
        holder.bind(raceListInAdapter[position],clickListener)
    }

    override fun getItemCount(): Int {
        return raceListInAdapter.size
    }

    class RaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListElementBinding.bind(itemView)
        fun bind(sw: Sweet, clickListener: ClickListener){
            binding.nameElement.text="Название "+sw.name
            binding.priceElement.text="Цена "+sw.price

            Picasso.get().load(sw.image).fit().into(binding.imageElement)
            itemView.setOnClickListener{

                clickListener.onClick(sw)
            }

        }
    }
    fun loadListToAdapter(productList:ArrayList<Sweet>){
        raceListInAdapter= productList
        notifyDataSetChanged()
    }

    interface ClickListener{
        fun onClick(product: Sweet){

        }
    }
    fun deleteItem(i:Int):String?{
        var id=raceListInAdapter.get(i).id

        raceListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return id
    }
}