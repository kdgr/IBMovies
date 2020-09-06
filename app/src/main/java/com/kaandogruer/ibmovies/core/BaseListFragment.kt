package com.kaandogruer.ibmovies.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.kaandogruer.ibmovies.viewmodel.BaseViewModel

/**
 * Base fragment with usefull functions&dagger injections&base adapter.
 *
 * Created by kaandogruer
 */
abstract class BaseListFragment<T : ViewDataBinding, K : ViewDataBinding, L, V : BaseViewModel> : BaseInjectableFragment<V, T>() {

    @get:LayoutRes
    protected abstract val viewHolderLayoutId: Int
    lateinit var adapter: BaseAdapter<K, L>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = object : BaseAdapter<K, L>(viewHolderLayoutId, true) {
            override fun bindView(binding: K, item: L?, adapterPosition: Int) {
                item?.let { this@BaseListFragment.bindView(binding, item, adapterPosition) }
            }

            override fun clickListener(item: L?, position: Int, binding: K) {
                item?.let { onAdapterItemClickListener(item, position, binding) }

            }
        }
        //adapter.setHasStableIds(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    abstract fun bindView(binding: K, item: L, adapterPosition: Int)

    open fun onAdapterItemClickListener(item: L, position: Int, binding: K) {

    }
}