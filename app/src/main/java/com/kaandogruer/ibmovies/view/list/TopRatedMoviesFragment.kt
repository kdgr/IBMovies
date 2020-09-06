package com.kaandogruer.ibmovies.view.list

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kaandogruer.ibmovies.R
import com.kaandogruer.ibmovies.core.BaseListFragment
import com.kaandogruer.ibmovies.data.Status
import com.kaandogruer.ibmovies.data.local.entity.Movie
import com.kaandogruer.ibmovies.data.remote.RemoteConstants
import com.kaandogruer.ibmovies.data.remote.model.datamodels.requestmodels.TopRatedMoviesRequestModel
import com.kaandogruer.ibmovies.databinding.FragmentMainBinding
import com.kaandogruer.ibmovies.databinding.ItemMovieListBinding
import com.kaandogruer.ibmovies.di.Injectable
import com.kaandogruer.ibmovies.utils.Constants
import com.kaandogruer.ibmovies.view.detail.MovieDetailActivity
import com.kaandogruer.ibmovies.viewmodel.MoviesViewModel
import com.paulrybitskyi.persistentsearchview.utils.VoiceRecognitionDelegate

class TopRatedMoviesFragment : BaseListFragment<FragmentMainBinding, ItemMovieListBinding, Movie, MoviesViewModel>(), Injectable,LoadMoreCallback {
    override val layoutResourceId = R.layout.fragment_main
    override val viewModelClass: Class<MoviesViewModel> = MoviesViewModel::class.java
    override val viewHolderLayoutId: Int = R.layout.item_movie_list

    private var page = 1

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvMovies.adapter = adapter
        binding.callback = this
        getMovieList()

        with(binding.persistentSearchView) {
            setOnLeftBtnClickListener {
                getMovieList()
            }
            setOnClearInputBtnClickListener {
                getMovieList()
            }

            setOnSearchConfirmedListener { searchView, query ->
                getMovieList(query)
            }

            setOnSearchQueryChangeListener { searchView, oldQuery, newQuery ->
                if(!newQuery.isNullOrBlank() && newQuery.length > 2)
                    binding.persistentSearchView.confirmSearchAction()
            }

            // Disabling the suggestions since they are unused in
            // the simple implementation
            setSuggestionsDisabled(true)
        }
    }

    //get movie list from server and save db
    fun getMovieList(searchText:String = "") {
        viewModel.setTopRatedMoviesRequest(TopRatedMoviesRequestModel(page, RemoteConstants.mApiKey,searchText))
        viewModel.topRatedMoviesResponseLiveData
            .observe(viewLifecycleOwner, Observer {
                binding.resource = it
                if (it.data != null && it.data.size > 0) {
                    adapter.items = ArrayList(it.data)
                }
                binding.persistentSearchView.collapse()
            })
    }



    override fun bindView(binding: ItemMovieListBinding, item: Movie, adapterPosition: Int) {
        binding.movie = item

        Glide.with(activity!!)
            .asBitmap()
            .load(item.getPosterUrl())
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    activity!!.startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    activity!!.startPostponedEnterTransition()
                    if (resource != null) {
                        var p = Palette.from(resource).generate()
                        // Use generated instance
                        binding.mColorPalette = p.getMutedColor(ContextCompat.getColor(activity!!, R.color.bannerbg))
                        var transparentColor  = binding.mColorPalette!! and 0x00FFFFFF or 0xCC000000.toInt()
                        binding.vBg.setBackgroundColor(transparentColor.toInt())
                    }
                    return false
                }

            })
            .into(binding.ivMovie)
    }

    override fun onAdapterItemClickListener(item: Movie, position: Int, binding: ItemMovieListBinding) {
        val intent = Intent(activity!!, MovieDetailActivity::class.java).apply {
            this.putExtra(ARG_ITEM, item)
        }
        activity!!.startActivity(intent)
    }


    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM = "item"
    }

    override fun onLoadMore() {
        page = page+1
        getMovieList()
    }

}
