package com.example.thecatapi.activity

import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pinterest.network.RetrofitHttp
import com.example.pinterest.network.service.PhotoService
import com.example.thecatapi.R
import com.google.android.material.imageview.ShapeableImageView
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream


class CreateActivity : AppCompatActivity() {

    lateinit var context:Context
    lateinit var service:PhotoService
    lateinit var ll_select:LinearLayout
    lateinit var ll_upload:LinearLayout
    lateinit var iv_select:ShapeableImageView
    lateinit var iv_upload:ShapeableImageView
    lateinit var tv_upload:TextView
    lateinit var tv_select:TextView
    private val REQUEST_CODE = 100
    var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        initViews()
    }

    private fun initViews() {
        context = this
        ll_select = findViewById(R.id.ll_select)
        ll_upload = findViewById(R.id.ll_upload)
        iv_select = findViewById(R.id.iv_select)
        iv_upload = findViewById(R.id.iv_upload)
        tv_select = findViewById(R.id.tv_select)
        tv_upload = findViewById(R.id.tv_upload)

        tv_select.setOnClickListener {
            Toast.makeText(context, "Select Image First", Toast.LENGTH_LONG).show();
        }
        iv_select.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, REQUEST_CODE)
        }
        tv_upload.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            iv_upload.setImageURI(data?.data) // handle chosen image
            ll_select.visibility = View.GONE
            ll_upload.visibility = View.VISIBLE

            val file = getFile(data?.data!!)
            val reqFile: RequestBody = RequestBody.create(MediaType.parse("image/jpg"), file)
            val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, reqFile)

            val responseBodyCall: Call<ResponseBody> = RetrofitHttp.photoService.uploadFile(body, file.name)

            responseBodyCall.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("Success", "success " + response.code());
                    Log.d("Success", "success " + response.message());
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("failure", "message = " + t.message);
                    Log.e("failure", "cause = " + t.cause);
                }

            })

        }
    }

    private fun getFile(uri: Uri): File {
        val ins = contentResolver.openInputStream(uri)
        val file = File.createTempFile(
            "file",
            ".jpg",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        val fileOutputStream = FileOutputStream(file)
        ins?.copyTo(fileOutputStream)
        ins?.close()
        fileOutputStream.close()
        return file
    }

}