package vv.monika.admin_wavesoffood

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import vv.monika.admin_wavesoffood.databinding.ActivityLoginBinding
import vv.monika.admin_wavesoffood.model.userModel

class LoginActivity : AppCompatActivity() {

    private var username: String? = null
    private var resturant: String? = null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    //    private lateinit var callbackManager: CallbackManager
    private lateinit var currentUser: FirebaseUser

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // From Firebase Console
            .requestEmail()
            .build()
//now initializing auth and database
        auth = Firebase.auth
        database = Firebase.database.reference
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)
//callbackManager = CallbackManager.Factory.create()

        binding.google.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }
        binding.facebook.setOnClickListener {

        }

        binding.dontHaveAccBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        binding.loginButton.setOnClickListener {
//            firebase signup
//            get text from edit text
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()

            } else {
//                check karna h databse se, or chah rhe ki agar user nhi h databse me to create ho jaaye , hai to achhi baat hao
//                warna create ho jaaye
                createUserAccount(email, password)

            }

        }
    }

    private fun createUserAccount(email: String, password: String) {
//    sign in karwana hai
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Toast.makeText(this, "Signin Successful!", Toast.LENGTH_SHORT).show()
                updateUI(user)
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(
                            this,
                            "Login and new account created Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
//                        save karna h entered data to the data base
                        saveUserData()
                        updateUI(user)
                    } else {
                        Toast.makeText(this, "Failed to Login", Toast.LENGTH_SHORT).show()
                        Log.d("account", "create account failed", task.exception)
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()
        val user = userModel(username, resturant, email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
//        save
        userId?.let {
            database.child("user").child(it).setValue(user)
        }

    }


    //launcher for google signin
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                Log.d("GOOGLE_AUTH", "Intent data not null, proceeding")
                val data = result.data
                if (data != null) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    try {
                        val account = task.result
                        if (account != null) {
                            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                            auth.signInWithCredential(credential)
                                .addOnCompleteListener { authTask ->
                                    if (authTask.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Signed in with Google",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        updateUI(auth.currentUser)
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Firebase Auth Failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.e(
                                            "GOOGLE_AUTH",
                                            "Firebase sign-in failed: ${authTask.exception?.message}"
                                        )
                                    }
                                }
                        } else {
                            Toast.makeText(this, "Google account is null", Toast.LENGTH_SHORT)
                                .show()
                            Log.e("GOOGLE_AUTH", "Google account is null")
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
                        Log.e("GOOGLE_AUTH", "Exception getting sign in account", e)
                    }
                } else {
                    Toast.makeText(this, "Intent data is null", Toast.LENGTH_SHORT).show()
                    Log.e("GOOGLE_AUTH", "Intent data was null")
                }
            } else {
                Log.e(
                    "GOOGLE_AUTH",
                    "Sign-in cancelled or failed. resultCode: ${result.resultCode}"
                )
                Toast.makeText(this, "Google Sign-In cancelled", Toast.LENGTH_SHORT).show()
            }
        }

//     hamara clientid galt tha essiliye nhi ho rha tha
//    private val launcher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                if (task.isSuccessful) {
//                    val account: GoogleSignInAccount = task.result
//                    val credential: AuthCredential =
//                        GoogleAuthProvider.getCredential(account.idToken, null)
//                    auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
//                        if (authTask.isSuccessful) {
//                            Toast.makeText(
//                                this,
//                                "Successfully Sign-in with Google",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            updateUI(auth.currentUser)
//                        } else {
//                            Toast.makeText(this, "Sign-in with Google Failed", Toast.LENGTH_SHORT)
//                                .show()
//                            Log.d("GOOGLE_AUTH", "Failed: ${authTask.exception?.message}")
//                        }
//                    }
//
//                } else {
//                    Toast.makeText(this, "Sign-in with google failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        }


    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "User not exist", Toast.LENGTH_SHORT).show()
        }

    }

}





















