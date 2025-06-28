package vv.monika.admin_wavesoffood

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import vv.monika.admin_wavesoffood.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private val binding : ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val listOfLocation = arrayOf("Kolkata", "Gujrat", "Bareli", "Deoghar", "Australia")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfLocation)
        val autoCompleteTextView = binding.searchLocationEditText
        autoCompleteTextView.setAdapter(adapter)
    }
}