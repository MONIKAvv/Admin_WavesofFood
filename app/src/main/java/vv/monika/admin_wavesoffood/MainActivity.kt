package vv.monika.admin_wavesoffood

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import vv.monika.admin_wavesoffood.databinding.ActivityMainBinding
import vv.monika.admin_wavesoffood.ui.theme.Admin_WavesofFoodTheme

class MainActivity : ComponentActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
//        start code from here
        binding.addItem.setOnClickListener {
            startActivity(Intent(this, AddItemActivity::class.java))
        }
        binding.allItemMenu.setOnClickListener {
            startActivity(Intent(this, AllItemMenuActivity::class.java))
        }
        binding.orderDelivery.setOnClickListener {
            startActivity(Intent(this, OutForDeliveryActivity::class.java))
        }

        binding.adminProfile.setOnClickListener {
            startActivity(Intent(this, AdminProfileActivity::class.java))
        }
        binding.createNewUserBtn.setOnClickListener {
            startActivity(Intent(this, CreateNewUserActivity::class.java))
        }
        binding.pendingOrder.setOnClickListener {
            startActivity(Intent(this,PendingOrderActivity::class.java))
        }
    }
}


