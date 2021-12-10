package com.vohidov.firebasedatabasecloud

import Adapter.UserAdapter
import Model.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.vohidov.firebasedatabasecloud.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    var list = ArrayList<User>()
    lateinit var userAdapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val intent = Intent(this, StorageActivity::class.java)
            startActivity(intent)
        }

        firebaseFirestore = FirebaseFirestore.getInstance()

        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString()
            val number = binding.edtNumber.text.toString()

            val user = User(name, number)

            firebaseFirestore.collection("Users")
                .add(user)
                .addOnSuccessListener {
                    Toast.makeText(this, " User ${it.path.length} is successfully uploaded", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "${it.message} is error", Toast.LENGTH_SHORT).show()
                }
        }
        getFirestore()
    }

    private fun getFirestore() {
        firebaseFirestore.collection("Users")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    result?.forEach { queryDocumentSnapshot ->
                        val user = queryDocumentSnapshot.toObject(User::class.java)
                        list.add(user)
                        userAdapter = UserAdapter(this, list)
                        recyclerView.adapter = userAdapter
                    }
                }
            }
    }
}