package com.vohidov.firebasedatabasecloud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vohidov.firebasedatabasecloud.databinding.ActivityStorageBinding

class StorageActivity : AppCompatActivity() {

    lateinit var binding: ActivityStorageBinding
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference

    private val TAG = "StorageActivity"

    var imageUrl:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseStorage = FirebaseStorage.getInstance()

        reference = firebaseStorage.getReference("Photo")

        binding.imageView.setOnClickListener {
            getImageContent.launch("image/*")
        }
    }

    private var getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        binding.imageView.setImageURI(uri)

        val m = System.currentTimeMillis()

        val uploadTask = reference.child(m.toString()).putFile(uri)
        binding.progressBar.visibility = View.VISIBLE

        uploadTask.addOnSuccessListener {
            if (it.task.isSuccessful){
                val downloadUrl = it.metadata?.reference?.downloadUrl
                downloadUrl?.addOnSuccessListener {imageUri->
                    imageUrl = imageUri.toString()
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }.addOnFailureListener{

            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
    }
}