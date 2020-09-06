package com.kaandogruer.ibmovies.view.base


import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.kaandogruer.ibmovies.R
import com.kaandogruer.ibmovies.core.BaseFragment
import com.kaandogruer.ibmovies.utils.GenericHelper
import java.io.Serializable


abstract class BaseActivity : AppCompatActivity(){

    //region Views
    lateinit var flBaseContent: FrameLayout
    //endregion

    private var progressDialog: Dialog? = null


    public override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        super.setContentView(R.layout.activity_base)
        findViewByIds()
        setListeners()
    }

    private fun setListeners() {

    }

    private fun findViewByIds() {
        flBaseContent = findViewById<View>(R.id.ll_main_content) as FrameLayout
    }


    override fun setContentView(id: Int) {
        val inflater = baseContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(id, flBaseContent)
    }


    fun showFullScreenLoading() {
        if (progressDialog == null) {
            progressDialog = Dialog(this).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                setContentView(R.layout.loading_state)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            }
        }
        progressDialog?.show()
    }

    fun hideFullScreenLoading() {
        progressDialog?.let {
            if (it.isShowing) {
                it.cancel()
            }
        }
    }

    override fun onDestroy() {
        hideFullScreenLoading()
        super.onDestroy()
    }



    //region Fragment operations
    fun replaceFragment(fragment: BaseFragment, container: Int,
                        manager: FragmentManager): BaseFragment {
        GenericHelper.getInstance().hideKeyboard(this@BaseActivity)
        val transaction = manager.beginTransaction()
        //transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out);
        for (i in 0 until manager.backStackEntryCount) {
            manager.popBackStack()
        }
        transaction.replace(container, fragment).commit()
        return fragment
    }


    fun addFragment(fragment: BaseFragment, container: Int,
                    manager: FragmentManager, activeFragment: BaseFragment): BaseFragment {
        GenericHelper.getInstance().hideKeyboard(this@BaseActivity)
        val transaction = manager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        transaction.hide(activeFragment)
        transaction.add(container, fragment)
                .addToBackStack(null).commit()
        return fragment
    }

    fun replaceFragmentBackstack(fragment: BaseFragment, container: Int,
                                 manager: FragmentManager, activeFragment: BaseFragment) {
        var activeFragment = activeFragment
        GenericHelper.getInstance().hideKeyboard(this@BaseActivity)
        val transaction = manager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        transaction.replace(container, fragment)
                .addToBackStack(null).commit()
        activeFragment = fragment
    }

    fun replaceFragmentBackstackAndBundle(fragment: BaseFragment, bundleKey: String, `object`: Serializable,
                                          container: Int, manager: FragmentManager, activeFragment: BaseFragment) {
        var activeFragment = activeFragment
        val bundle = Bundle()
        bundle.putSerializable(bundleKey, `object`)
        fragment.arguments = bundle
        val transaction = manager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        transaction.replace(container, fragment)
                .addToBackStack(null).commit()
        activeFragment = fragment
    }

    //endregion

    fun showAlertDialog(context: Context, title: String, negativeButton: String,
                        negativeListener: DialogInterface.OnClickListener,
                        positiveButton: String, positiveListener: DialogInterface.OnClickListener,
                        message: String) {
        val alertDialog = AlertDialog.Builder(context)
                .setNegativeButton(negativeButton, negativeListener)
                .setPositiveButton(positiveButton, positiveListener).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)

        alertDialog.show()
    }

    fun showAlertDialog(context: Context, title: String,
                        positiveButton: String, positiveListener: DialogInterface.OnClickListener,
                        message: String) {
        val alertDialog = AlertDialog.Builder(context)
                .setPositiveButton(positiveButton, positiveListener).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)

        alertDialog.show()
    }

    fun showAlertDialog(context: Context, title: String,
                        positiveButton: String, positiveListener: DialogInterface.OnClickListener,
                        message: String,
                        dismissListener: DialogInterface.OnDismissListener) {
        val alertDialog = AlertDialog.Builder(context)
                .setPositiveButton(positiveButton, positiveListener).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setOnDismissListener(dismissListener)

        alertDialog.show()
    }

    fun showAlertDialog(context: Context, title: String, negativeButton: String,
                        negativeListener: DialogInterface.OnClickListener,
                        positiveButton: String, positiveListener: DialogInterface.OnClickListener,
                        message: String, cancelable: Boolean) {
        val alertDialog = AlertDialog.Builder(context)
                .setNegativeButton(negativeButton, negativeListener)
                .setPositiveButton(positiveButton, positiveListener).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setCanceledOnTouchOutside(cancelable)
        alertDialog.setCancelable(cancelable)

        alertDialog.show()
    }

    fun showAlertDialog(context: Context, title: String,
                        positiveButton: String, positiveListener: DialogInterface.OnClickListener,
                        message: String, cancelable: Boolean) {

        val alertDialog = AlertDialog.Builder(context)
                .setPositiveButton(positiveButton, positiveListener).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setCanceledOnTouchOutside(cancelable)
        alertDialog.setCancelable(cancelable)


        alertDialog.show()
    }
}
