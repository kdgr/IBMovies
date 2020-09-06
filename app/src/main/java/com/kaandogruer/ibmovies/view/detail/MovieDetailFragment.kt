package com.kaandogruer.ibmovies.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.kaandogruer.ibmovies.R
import com.kaandogruer.ibmovies.core.BaseInjectableFragment
import com.kaandogruer.ibmovies.data.local.entity.Movie
import com.kaandogruer.ibmovies.databinding.ItemDetailBinding
import com.kaandogruer.ibmovies.view.components.CustomUnitFontTextView
import com.kaandogruer.ibmovies.viewmodel.MoviesViewModel

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [MovieDetailActivity]
 */
class MovieDetailFragment : BaseInjectableFragment<MoviesViewModel,ItemDetailBinding>() {
    override val layoutResourceId = R.layout.item_detail
    override val viewModelClass: Class<MoviesViewModel> = MoviesViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM)) {
                binding.movie = it.getSerializable(ARG_ITEM) as Movie?
            }
        }
    }

    companion object {
        const val ARG_ITEM = "item"
    }
}
