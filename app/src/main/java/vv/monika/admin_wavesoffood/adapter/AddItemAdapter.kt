package vv.monika.admin_wavesoffood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vv.monika.admin_wavesoffood.databinding.AllItemItemMenuBinding

class AddItemAdapter(private val menuItemName:ArrayList<String>, private val menuItemPrice:ArrayList<String>, private val menuItemImage: ArrayList<Int>): RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray(menuItemName.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = AllItemItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return menuItemName.size
    }

    inner class AddItemViewHolder (private val binding:AllItemItemMenuBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
          binding.apply {

              val quantity = itemQuantities[position]
              itemQuantityTextView.text = quantity.toString()

              itemFoodName.text=menuItemName[position]
              itemFoodPrice.text = menuItemPrice[position]
              ItemFoodImage.setImageResource(menuItemImage[position])

              itemMinusBtn.setOnClickListener {
                  decreaseQuantity(position)
              }
              itemAddBtn.setOnClickListener {
                  increaseQuantity(position)
              }
              itemDeleteBtn.setOnClickListener {
                  deleteItem(position)
              }
              
          }
        }

        private fun deleteItem(position: Int) {
            menuItemName.removeAt(position)
            menuItemPrice.removeAt(position)
            menuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuItemName.size)

        }

        private fun increaseQuantity(position: Int) {
            if(itemQuantities[position] < 10){
                itemQuantities[position] ++
                binding.itemQuantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
          if(itemQuantities[position] >1){
              itemQuantities[position] --
              binding.itemQuantityTextView.text = itemQuantities[position].toString()
          }
        }

    }
}