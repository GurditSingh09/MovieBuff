package com.example.moviebuff

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(),MovieClicked {

    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        getSupportActionBar()?.setIcon(R.drawable.ic_baseline_menu_24)
        getSupportActionBar()?.setTitle("  Top Rated Movies")

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = MovieAdapter(this)
        recyclerView.adapter = adapter

        val queue = Volley.newRequestQueue(this)
        val url =
            "https://api.themoviedb.org/3/movie/top_rated?api_key=708c0e998c196fd65cb18eae1c5a6020"

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                progressBar.visibility = View.GONE
                val movieArray = it.getJSONArray("results")
                val movieInfoArray = ArrayList<Movie>()
                for (i in 0 until movieArray.length()) {
                    val movieObject = movieArray.getJSONObject(i)
                    val movie = Movie(
                        movieObject.getString("original_title"),
                        movieObject.getString("poster_path"),
                        movieObject.getString("id")
                    )
                    movieInfoArray.add(movie)
                }
                adapter.update(movieInfoArray)
            },
            Response.ErrorListener {
                progressBar.visibility = View.VISIBLE
                Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)
    }

        override fun onItemClicked(item: Movie) {
            val intent = Intent(this, Description::class.java)
            intent.putExtra("movieInfo", item.movieInfo)
            startActivity(intent)

        }

    }

