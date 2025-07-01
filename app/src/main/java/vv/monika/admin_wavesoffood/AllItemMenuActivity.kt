package vv.monika.admin_wavesoffood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import vv.monika.admin_wavesoffood.adapter.AddItemAdapter
import vv.monika.admin_wavesoffood.databinding.ActivityAllItemMenuBinding

class AllItemMenuActivity : AppCompatActivity() {
    private val binding: ActivityAllItemMenuBinding by lazy {
        ActivityAllItemMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
binding.backButton.setOnClickListener {
    finish()
}
//dummy data
        val menuFoodName = listOf("Burger", "Crab Fry", "icecream", "pasta", "Roll")
        val menuFoodPrice = listOf("$3", "$4", "$7", "$6", "$9")
        val menuFoodImage = listOf(
            R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4, R.drawable.menu5)

        val adapter = AddItemAdapter(ArrayList(menuFoodName), ArrayList(menuFoodPrice), ArrayList(menuFoodImage))
//        now we are binding recyclerview
        binding.allItemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.allItemRecyclerView.adapter = adapter
    }
}