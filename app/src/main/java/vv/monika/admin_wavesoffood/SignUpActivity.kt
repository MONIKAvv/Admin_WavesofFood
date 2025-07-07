package vv.monika.admin_wavesoffood

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import vv.monika.admin_wavesoffood.databinding.ActivitySignUpBinding
import vv.monika.admin_wavesoffood.model.userModel
import kotlin.math.log

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var restaurant: String
    private lateinit var database: DatabaseReference


    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        initialization of auth
        auth = Firebase.auth
//        initialize databse
        database = Firebase.database.reference
//        now when userclick on signup button
        binding.signupButton.setOnClickListener {
//            get text from textview to our created variable
            userName = binding.ownerName.text.toString().trim()
            restaurant = binding.restaurantName.text.toString().trim()
            email = binding.ownerEmailAdd.text.toString().trim()
            password = binding.ownerPassword.text.toString().trim()

//            check no field is blank
            if (userName.isBlank() || restaurant.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all those fields", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }

        }


        val listOfLocation = arrayOf("Kolkata", "Gujrat", "Bareli", "Deoghar", "Australia")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfLocation)
        val autoCompleteTextView = binding.searchLocationEditText
        autoCompleteTextView.setAdapter(adapter)

        binding.alreadyHaveAccBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Account Creation Failed!", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }
    }

    //save data into database
    private fun saveUserData() {
        userName = binding.ownerName.text.toString().trim()
        restaurant = binding.restaurantName.text.toString().trim()
        email = binding.ownerEmailAdd.text.toString().trim()
        password = binding.ownerPassword.text.toString().trim()

        val user = userModel(userName, restaurant, email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            database.child("user").child(userId).setValue(user)
        }
//    save data to firebase


    }
}