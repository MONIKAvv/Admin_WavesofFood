package vv.monika.admin_wavesoffood.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import vv.monika.admin_wavesoffood.databinding.DeliveryItemsBinding

class DeliveryAdapter (private val itemCustomerName:ArrayList<String>, private val itemMoneyStatus:ArrayList<String>) :RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
   val binding = DeliveryItemsBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return itemCustomerName.size
    }
    inner class DeliveryViewHolder (private val binding: DeliveryItemsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
          binding.apply {
              customerName.text = itemCustomerName[position]
              moneyStatus.text = itemMoneyStatus[position]
//              since money status will change so we make a variable
              val colorMap = mapOf(
                  "received" to Color.GREEN,
                  "notReceived" to Color.RED,
                  "Pending" to Color.GRAY
              )


              moneyStatus.setTextColor(colorMap[itemMoneyStatus[position]]?:Color.BLACK)
              statusColor.backgroundTintList = ColorStateList.valueOf(colorMap[itemMoneyStatus[position]]?:Color.BLACK)

          }
        }

    }

}