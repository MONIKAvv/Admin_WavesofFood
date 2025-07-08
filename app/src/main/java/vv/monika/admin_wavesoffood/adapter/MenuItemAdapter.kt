package vv.monika.admin_wavesoffood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vv.monika.admin_wavesoffood.databinding.AllItemItemMenuBinding
import vv.monika.admin_wavesoffood.model.AllMenu
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
//    private val menuItemName:ArrayList<String>, private val menuItemPrice:ArrayList<String>, private val menuItemImage: ArrayList<Int> we have to remove this variables and we are going to use menumodel
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    val databaseReference: DatabaseReference,

    ): RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray(menuList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = AllItemItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class AddItemViewHolder (private val binding:AllItemItemMenuBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
          binding.apply {

              val quantity = itemQuantities[position]
//              now we need to create three variables here
              val menuItem = menuList[position]
              val uriString = menuItem.foodImage
              val uri = uriString?.toUri()
//              val uri = Uri.parse(uriString)


              itemQuantityTextView.text = quantity.toString()
//now here we have to replace this position with the model field name
              itemFoodName.text= menuItem.foodName
              itemFoodPrice.text = menuItem.foodPrice
//              this image we will get image with glide (third party Library for fast load) glide helps in loading the images from firebase to our recyclerview fastly
//              ItemFoodImage.setImageResource(menuList[position]) we dont need this line

              Glide.with(context).load(uri).into(ItemFoodImage)

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
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)

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