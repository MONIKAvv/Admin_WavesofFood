package vv.monika.admin_wavesoffood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import vv.monika.admin_wavesoffood.databinding.PendingOrderItemsBinding
import java.util.ArrayList

class PendingOrdersAdapter(
    private val customerName: ArrayList<String>,
    private val itemQuantity: ArrayList<String>,
    private val itemImage: ArrayList<Int>,
    private val context: Context
) :
    RecyclerView.Adapter<PendingOrdersAdapter.PendingViewHOlder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingViewHOlder {
        val binding =
            PendingOrderItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingViewHOlder(binding)
    }

    override fun onBindViewHolder(holder: PendingViewHOlder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return customerName.size
    }

    inner class PendingViewHOlder(private val binding: PendingOrderItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            var isAccepted = false

            binding.apply {
                pendingName.text = customerName[position]
                pendingQuantity.text = itemQuantity[position]
                pendingImage.setImageResource(itemImage[position])

                orderStatusButton.apply {
                    if(!isAccepted){
                        text = "Accept"
                    }else{
                        text = "Dipatch"
                    }
                    setOnClickListener {
                        if(!isAccepted){
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is Accepted!")
                        }else{
                            customerName.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is Dispatched")
                        }
                    }
                }
            }

        }
        private fun showToast(message : String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}