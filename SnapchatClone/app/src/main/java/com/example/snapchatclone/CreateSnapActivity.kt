package com.example.snapchatclone

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class CreateSnapActivity : AppCompatActivity() {

    var createSnapImageView: ImageView? = null
    var messageEditText: EditText? = null
    val imageName = UUID.randomUUID().toString() + ".jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)

        createSnapImageView = findViewById(R.id.imageView)
        messageEditText = findViewById(R.id.messageEditText)
        val nextButton: Button = findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            // Get the data from an ImageView as bytes
            createSnapImageView?.isDrawingCacheEnabled = true
            createSnapImageView?.buildDrawingCache()
            val bitmap = (createSnapImageView?.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask =
                FirebaseStorage.getInstance().reference.child("images").child(imageName)
                    .putBytes(data)
            uploadTask.addOnFailureListener {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener { taskSnapshot ->
                val downloadUrl = taskSnapshot.storage.downloadUrl
                Log.i("URL", downloadUrl.toString())
                val intent = Intent(this, ChooseUserActivity::class.java)
                intent.putExtra("imageURL", downloadUrl.toString())
                intent.putExtra("imageName", imageName)
                intent.putExtra("message", messageEditText?.text.toString())
                startActivity(intent)
            }
        }
        val chooseImageButton: Button = findViewById(R.id.chooseImageButton)
        chooseImageButton.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            else {
                getPhoto()
            }
        }

    }

    private fun getPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data!!.data

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                createSnapImageView?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                getPhoto()
            }
        }
    }
}