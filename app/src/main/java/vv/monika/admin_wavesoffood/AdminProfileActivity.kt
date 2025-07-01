package vv.monika.admin_wavesoffood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vv.monika.admin_wavesoffood.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
private val binding:ActivityAdminProfileBinding by lazy {
    ActivityAdminProfileBinding.inflate(layoutInflater)
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }
        binding.adminName.isEnabled = false
        binding.adminAddress.isEnabled = false
        binding.adminEmail.isEnabled = false
        binding.adminPhone.isEnabled = false
        binding.adminPassword.isEnabled = false

        var isEnabled = false

        binding.editButton.setOnClickListener {
            isEnabled = !isEnabled
            binding.adminName.isEnabled = isEnabled
            binding.adminAddress.isEnabled = isEnabled
            binding.adminEmail.isEnabled = isEnabled
            binding.adminPhone.isEnabled = isEnabled
            binding.adminPassword.isEnabled = isEnabled

            if(isEnabled){
              binding.adminName.requestFocus()
            }
        }

    }
}