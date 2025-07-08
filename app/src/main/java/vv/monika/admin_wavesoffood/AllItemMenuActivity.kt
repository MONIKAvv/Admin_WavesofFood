package vv.monika.admin_wavesoffood

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import vv.monika.admin_wavesoffood.adapter.MenuItemAdapter
import vv.monika.admin_wavesoffood.databinding.ActivityAllItemMenuBinding
import vv.monika.admin_wavesoffood.model.AllMenu

class AllItemMenuActivity : AppCompatActivity() {
//     we will load our images to our recyclerview

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuItems: ArrayList<AllMenu> = ArrayList()


    private val binding: ActivityAllItemMenuBinding by lazy {
        ActivityAllItemMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

//        initialise database

        databaseReference = FirebaseDatabase.getInstance().reference
//         now we create a function to retrive item
        retrieveMenuItem()

        binding.backButton.setOnClickListener {
            finish()
        }
//dummy data
//        val menuFoodName = listOf("Burger", "Crab Fry", "icecream", "pasta", "Roll")
//        val menuFoodPrice = listOf("$3", "$4", "$7", "$6", "$9")
//        val menuFoodImage = listOf(
//            R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4, R.drawable.menu5
//        )


    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
//        we fatch data from firebase database
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuItems.clear()
//                loop for through each food item

                for (foodSnapshot in snapshot.children) {
                    val menuItem: AllMenu? = foodSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })
    }

    private fun setAdapter() {
        val adapter = MenuItemAdapter(
           this@AllItemMenuActivity,menuItems,databaseReference
        )
//        now we are binding recyclerview
        binding.allItemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.allItemRecyclerView.adapter = adapter
    }

}