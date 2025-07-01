package vv.monika.admin_wavesoffood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import vv.monika.admin_wavesoffood.adapter.PendingOrdersAdapter
import vv.monika.admin_wavesoffood.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {
private lateinit var binding:ActivityPendingOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val customerName = arrayListOf("Monika", "madhu", "Rishi Raj")
        val orderedQuantity = arrayListOf("2", "5", "7")
        val orderedFoodImage = arrayListOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3)

        val adapter = PendingOrdersAdapter(customerName, orderedQuantity, orderedFoodImage, this)
        binding.pendingRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pendingRecyclerView.adapter = adapter
    }
}