package com.nandohusni.testtrackingworkshop

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.itemlayout.view.*

/**
 * Created by nandoseptianhusni on 24/08/18.
 */
class ItemRecyclerview (var data : List<Users>) : RecyclerView.Adapter<ItemRecyclerview.MyHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.itemlayout,parent,false)

        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return  data.size
    }

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {

        holder?.bind(data.get(position))
        holder?.itemView?.setOnClickListener {


            var intent = Intent(holder.itemView.context,MapsActivity::class.java)
            intent.putExtra("id",data.get(position).uuid)
            holder.itemView.context.startActivity(intent)
        }
    }

    class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(get: Users) {

            itemView.itemName.text = get.name
            itemView.itemEmail.text  = get.email




        }

    }
}