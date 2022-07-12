package com.example.thecatapi.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.a14_recyclerviewdragandswipekotlin.helper.SpacesItemDecoration
import com.example.pinterest.network.RetrofitHttp
import com.example.thecatapi.R
import com.example.thecatapi.activity.MainActivity
import com.example.thecatapi.adapter.AllCatsAdapter
import com.example.thecatapi.model.Photo
import com.example.thecatapi.utils.DeepLink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AllCatsFragment: Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AllCatsAdapter
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_allcats, container, false)

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
        var tv_link = view.findViewById<TextView>(R.id.tv_link)
        val intent = Intent(requireActivity().application, MainActivity::class.java)
        DeepLink.retrieveLink(intent ,tv_link)

    }

    private fun apiPhotoList() {
        progressBar.visibility = View.VISIBLE
        RetrofitHttp.photoService.getPhotos(1, 30)
            .enqueue(object : Callback<ArrayList<Photo>> {
                override fun onResponse(call: Call<ArrayList<Photo>>, response: Response<ArrayList<Photo>>) {
                    val body = response.body()
                    Log.d("@@@",body.toString())
                    progressBar.visibility = View.GONE
                    if (body != null) {
                        refreshAdapter(body)
                    }
                }
                override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                    Log.e("@@@onFailure", t.message.toString())
                }
            })
    }

    private fun refreshAdapter(items: ArrayList<Photo>) {
        adapter = AllCatsAdapter(this, items)
        recyclerView.adapter = adapter
    }

}