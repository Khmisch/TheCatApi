package com.example.thecatapi.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.a14_recyclerviewdragandswipekotlin.helper.SpacesItemDecoration
import com.example.pinterest.network.RetrofitHttp
import com.example.thecatapi.R
import com.example.thecatapi.adapter.AllCatsAdapter
import com.example.thecatapi.adapter.MyCatsAdapter
import com.example.thecatapi.model.Photo
import com.example.thecatapi.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCatsFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyCatsAdapter
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_mycats, container, false)

        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerPins)
        progressBar = view.findViewById(R.id.pb_cats)
        recyclerView.setLayoutManager(
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        )
        val decoration = SpacesItemDecoration(10)
        recyclerView.addItemDecoration(decoration)
        apiPhotoList()
    }

    private fun apiPhotoList() {
        progressBar.visibility = View.VISIBLE

        RetrofitHttp.photoService.getMyCats()
            .enqueue(object : Callback<ArrayList<Post>> {
                override fun onResponse(call: Call<ArrayList<Post>>, response: Response<ArrayList<Post>>) {
                    val body = response.body()
                    progressBar.visibility = View.GONE

                    Log.d("@@@",body.toString())
                    if (body != null) {
                        refreshAdapter(body)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                    Log.e("@@@onFailure", t.message.toString())
                }
            })
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        adapter = MyCatsAdapter(this, items)
        recyclerView.adapter = adapter
    }
}