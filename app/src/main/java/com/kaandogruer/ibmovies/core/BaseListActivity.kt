package com.kaandogruer.ibmovies.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView.NO_ID
import com.kaandogruer.ibmovies.viewmodel.BaseViewModel
/**
 * Base activity with usefull functions&dagger injections&base adapter.
 *
 * Created by kaandogruer
 */
abstract class BaseListActivity<T : ViewDataBinding, K : ViewDataBinding, L, V : BaseViewModel> :
    BaseInjectableActivity<V, T>() {
    @get:LayoutRes
    protected abstract val viewHolderLayoutId: Int
    lateinit var adapter: BaseAdapter<K, L>


    override fun onCreate(savedInstanceState: Bundle?) {
        adapter = object : BaseAdapter<K, L>(viewHolderLayoutId, true) {
            override fun bindView(binding: K, item: L?, adapterPosition: Int) {
                item?.let { this@BaseListActivity.bindView(binding, item, adapterPosition) }
            }

            override fun clickListener(item: L?, position: Int, binding: K) {
                item?.let { onAdapterItemClickListener(binding, item, position) }

            }

            override fun getItemId(position: Int): Long {
                return this@BaseListActivity.getItemId(position)
            }
        }
        super.onCreate(savedInstanceState)

    }

    open fun getItemId(position: Int): Long {
        return NO_ID
    }


    abstract fun bindView(binding: K, item: L, adapterPosition: Int)

    open fun onAdapterItemClickListener(binding: K, item: L, position: Int) {

    }
}