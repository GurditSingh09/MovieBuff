package com.example.moviebuff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso


class Description : AppCompatActivity() {

    var movie: String? = "500"
    lateinit var over_title: TextView
    lateinit var over_full: TextView
    lateinit var rev_title: TextView
    lateinit var rev_full: TextView
    lateinit var image1: ImageView
    lateinit var image2: ImageView
    lateinit var title: TextView
    lateinit var rating: TextView
    lateinit var date: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        getSupportActionBar()?.hide()

        over_title = findViewById(R.id.overview_title)
        over_full = findViewById(R.id.overview_full)
        rev_title = findViewById(R.id.review_title)
        rev_full = findViewById(R.id.review_full)
        image1 = findViewById(R.id.movieImage)
        image2 = findViewById(R.id.image)
        title = findViewById(R.id.title)
        rating = findViewById(R.id.rating)
        date = findViewById(R.id.date)


        movie = intent.getStringExtra("movieInfo")
        if (movie == "500") {
            finish()
            Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show()
        }

        if (intent != null) {
            movie = intent.getStringExtra("movieInfo")
        } else {
            finish()
            Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show()
        }


        val queue = Volley.newRequestQueue(this)
        val url = "https://api.themoviedb.org/3/movie/"
        val api_key = "?api_key=708c0e998c196fd65cb18eae1c5a6020"

        val jsonRequest = JsonObjectRequest(Request.Method.GET, url + movie + api_key, null,
            Response.Listener {
                val movieImage = it.getString("backdrop_path")
                val imageTwo = it.getString("poster_path")
                Picasso.with(this).load("https://image.tmdb.org/t/p/w500/" + movieImage).into(image1)
                Picasso.with(this).load("https://image.tmdb.org/t/p/w500/" + imageTwo).into(image2)
                title.text = it.getString("original_title")
                rating.text = it.getString("vote_average")
                date.text = it.getString("release_date")
                over_full.text = it.getString("overview")
            },
            Response.ErrorListener {
                Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show()
            })
        queue.add(jsonRequest)
        val queue_new = Volley.newRequestQueue(this)

        val jsonRequestTwo = JsonObjectRequest(Request.Method.GET, url + movie + "/reviews" + api_key, null,
            Response.Listener {
                val movieArray = it.getJSONArray("results")
                for (i in 0 until movieArray.length()) {
                    val movieObject = movieArray.getJSONObject(i)
                    rev_full.text = movieObject.getString("content")
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show()
            })
        queue_new.add(jsonRequestTwo)
    }
}