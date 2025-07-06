package vv.monika.admin_wavesoffood

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import vv.monika.admin_wavesoffood.databinding.ActivityAddItemBinding
import vv.monika.admin_wavesoffood.model.AllMenu

class AddItemActivity : AppCompatActivity() {
//    Entered Food Item needs to save in real time database

    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String
    private var foodImageUri: Uri? = null

    //    firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

//        initialization of firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        binding.addFoodButton.setOnClickListener {
//            get user entered data from fields
            foodName = binding.addFoodNameTextView.text.toString().trim()
            foodPrice = binding.addFoodPriceTextView.text.toString().trim()
            foodDescription = binding.addFoodDescription.text.toString().trim()
            foodIngredient = binding.addFoodIngredient.text.toString().trim()

            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredient.isBlank())) {
                uploadData()
                Toast.makeText(this, "Item Added Successfully!", Toast.LENGTH_SHORT).show()
                finish()

            } else {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show()
            }

        }

        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.backButton.setOnClickListener {
            finish()
        }

//        binding.selectImage.setOnClickListener {
//           pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//        } ---> it is saying that this is created double , it is same as pickImage function

    }

    private fun uploadData() {
//get a reference to the "menu" node in the database
        val menuRef: DatabaseReference = database.getReference("menu")
//        generate unique key for the new menu ---> so that i future we can update and delete the item through this id only
        val newItemKey: String? = menuRef.push().key

        if (foodImageUri != null) {
//            need to enable storage / firebase database in firebase console
            val storageRef = FirebaseStorage.getInstance().reference
            val imageReference = storageRef.child("menu_images/${newItemKey}.jpeg")
//            jpeg main convert kar dega
            val uploadTask = imageReference.putFile(foodImageUri!!)


            uploadTask.addOnSuccessListener {
                imageReference.downloadUrl.addOnSuccessListener { downloadUrl ->
//                    create a new menu Item
                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodImage = downloadUrl.toString(),
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredient
                    )

                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "data Uploaded Successfully!", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Data upload Fail", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }.addOnFailureListener {
                Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Please Select a Food Image", Toast.LENGTH_SHORT).show()
        }

    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.selectedImage.setImageURI(uri)
            foodImageUri = uri
        }
    }
}