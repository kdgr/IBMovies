package com.kaandogruer.ibmovies.view.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.kaandogruer.ibmovies.R
import com.kaandogruer.ibmovies.data.local.entity.Movie
import com.kaandogruer.ibmovies.view.MainActivity
import com.kaandogruer.ibmovies.view.list.TopRatedMoviesFragment

class MovieDetailActivity : AppCompatActivity() {

    lateinit var movie: Movie
    var mPaletteColor: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))

        movie = intent.getSerializableExtra(TopRatedMoviesFragment.ARG_ITEM) as Movie
        findViewById<Toolbar>(R.id.detail_toolbar)?.title = movie?.title
        Glide.with(this)
            .asBitmap()
            .load(movie.getPosterUrl())
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    this@MovieDetailActivity.startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    this@MovieDetailActivity.startPostponedEnterTransition()
                    if (resource != null) {
                        var p = Palette.from(resource).generate()
                        // Use generated instance
                        mPaletteColor = p.getMutedColor(ContextCompat.getColor(this@MovieDetailActivity, R.color.bannerbg))

                        val actionBar = supportActionBar
                        if (actionBar != null) {
                            actionBar.setDisplayHomeAsUpEnabled(true)
                            //set color action bar
                            //actionBar.setBackgroundDrawable(ColorDrawable(manipulateColor(mPaletteColor!!, 0.62f)))

                            //set color status bar
                            window.statusBarColor = manipulateColor(mPaletteColor!!, 0.32f)
                        }


                        findViewById<Toolbar>(R.id.detail_toolbar)?.title = movie?.title
                        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).setExpandedTitleColor(getResources().getColor(android.R.color.transparent))
                        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).setContentScrimColor(mPaletteColor!!)
                        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).setBackgroundColor(mPaletteColor!!)
                        findViewById<NestedScrollView>(R.id.item_detail_container).setBackgroundColor(mPaletteColor!!)



                        (findViewById(R.id.detail_toolbar) as Toolbar).changeToolbarFont()

                        Glide.with(this@MovieDetailActivity).load(movie.getBackgroundUrl()).into(findViewById(R.id.cover_image))

                    }
                    return false
                }

            })
            .into(findViewById(R.id.iv_game))

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(MovieDetailFragment.ARG_ITEM,
                        intent.getSerializableExtra(MovieDetailFragment.ARG_ITEM))
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    fun manipulateColor(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = Math.round(Color.red(color) * factor)
        val g = Math.round(Color.green(color) * factor)
        val b = Math.round(Color.blue(color) * factor)
        return Color.argb(
            a,
            Math.min(r, 255),
            Math.min(g, 255),
            Math.min(b, 255)
        )
    }

    fun Toolbar.changeToolbarFont(){
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is TextView && view.text == title) {
                view.typeface = Typeface.createFromAsset(view.context.assets, "fonts/montserrat_bold.ttf")
                break
            }
        }
    }
}
