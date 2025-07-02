package vv.monika.admin_wavesoffood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import vv.monika.admin_wavesoffood.databinding.ActivityLoginBinding
import vv.monika.admin_wavesoffood.model.userModel

class LoginActivity : AppCompatActivity() {

    private var username : String? = null
    private var resturant: String? = null
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference


    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
//now initializing auth and database
        auth = Firebase.auth
        database = Firebase.database.reference

        binding.dontHaveAccBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        binding.loginButton.setOnClickListener {
//            firebase signup
//            get text from edit text
            email = binding.email.text.toString().trim()
            password = binding.password.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()

            }else{
//                check karna h databse se, or chah rhe ki agar user nhi h databse me to create ho jaaye , hai to achhi baat hao
//                warna create ho jaaye
                createUserAccount(email, password)

            }

        }
    }

    private fun createUserAccount(email: String, password: String) {
//    sign in karwana hai
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user = auth.currentUser
                Toast.makeText(this, "Signin Successful!", Toast.LENGTH_SHORT).show()
                updateUI(user)
            }else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val user = auth.currentUser
                        Toast.makeText(this, "Login and new account created Successfully", Toast.LENGTH_SHORT).show()
//                        save karna h entered data to the data base
                        saveUserData()
                        updateUI(user)
                    }else{
                        Toast.makeText(this, "Failed to Login", Toast.LENGTH_SHORT).show()
                        Log.d("account", "create account failed", task.exception)
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        email = binding.email.text.toString().trim()
        password = binding.password.toString().trim()
        val user = userModel(username,resturant,email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
//        save
        userId?.let {
            database.child("user").child(it).setValue(user)
        }


    }

    private fun updateUI(user: FirebaseUser?) {
startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}





















