package vv.monika.admin_wavesoffood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import vv.monika.admin_wavesoffood.adapter.DeliveryAdapter
import vv.monika.admin_wavesoffood.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val customerName = arrayListOf("Monika", "Madhu", "Rishi Raj")
        val moneyStatus = arrayListOf("received", "notReceived", "Pending")
val adapter = DeliveryAdapter(customerName, moneyStatus)
        binding.DeliveryRacyclerView.adapter = adapter
        binding.DeliveryRacyclerView.layoutManager = LinearLayoutManager(this)

    }
}