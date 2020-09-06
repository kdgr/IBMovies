package com.kaandogruer.ibmovies.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.kaandogruer.ibmovies.R
import com.kaandogruer.ibmovies.data.Resource
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) =
        Toast.makeText(this, message, duration).show()


inline fun Activity.alertDialog(body: AlertDialog.Builder.() -> AlertDialog.Builder): AlertDialog {
    return AlertDialog.Builder(this)
            .body()
            .show()
}

fun Context.connect(): Boolean {
    val connMgr = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connMgr.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnectedOrConnecting
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.textChanged(textChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textChanged(s.toString())
        }
    })
}

fun TabLayout.onTabSelectedListener(onTabChanged: (TabLayout.Tab?) -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(p0: TabLayout.Tab?) {

        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {

        }

        override fun onTabSelected(selectedTab: TabLayout.Tab?) {
            onTabChanged.invoke(selectedTab)
        }

    })

}

fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(s.toString())
        }
    })
}

fun Spinner.onItemSelected(onItemSelected: (Int) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected(position)
        }
    }
}

fun EditText.validate(validator: (String) -> Boolean, message: String) {
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}

fun String.isValidEmail(): Boolean =
        this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPhone(): Boolean =
        this.isNotEmpty() && Patterns.PHONE.matcher(this).matches()

fun Activity.hideKeyboardFrom() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

/**
 * Shows the Snackbar inside an Activity or Fragment
 *
 * @param messageRes Text to be shown inside the Snackbar
 * @param length Duration of the Snackbar
 * @param f Action of the Snackbar
 *
 */
fun View.showSnackbarD(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snackBar = Snackbar.make(this, resources.getString(messageRes), length)
    snackBar.f()
    snackBar.show()
}

fun View.showSnackbar(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG) {
    val snackBar = Snackbar.make(this, resources.getString(messageRes), length)
    snackBar.show()
}

fun View.showSnackbar(messageRes: String, length: Int = Snackbar.LENGTH_LONG) {
    val snackBar = Snackbar.make(this, messageRes, length)
    snackBar.show()
}


/**
 * Adds action to the Snackbar
 *
 * @param actionRes Action text to be shown inside the Snackbar
 * @param color Color of the action text
 * @param listener Onclick listener for the action
 */

fun Snackbar.action(@StringRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    setAction(actionRes, listener)
    color?.let { setActionTextColor(color) }
}

fun Activity.showAlert(message: String, positiveButtonText: String? = getString(R.string.ok)) {
    AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { _, _ -> }.show()
}

fun EditText.string() = text.toString()

fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean = this != null && !isEmpty()

fun Fragment.showAlert(message: String, positiveButtonText: String? = getString(R.string.ok), isCancelable: Boolean? = true, block: (DialogInterface) -> Unit) {
    AlertDialog.Builder(context)
            .setMessage(message)
            .setCancelable(isCancelable == false)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                block(dialog)
            }.show()
}

fun Fragment.showAlertNegative(message: String, positiveButtonText: String? = getString(R.string.ok), negativeButtonText: String? = getString(R.string.cancel), isCancelable: Boolean? = true, block: (DialogInterface) -> Unit) {
    AlertDialog.Builder(context)
            .setMessage(message)
            .setCancelable(isCancelable == false)
            .setNegativeButton(negativeButtonText) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                block(dialog)
            }.show()
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun String.parseDate(requestFormat: String, targetFormat: String): String {
    val date = SimpleDateFormat(requestFormat).parse(this)
    return SimpleDateFormat(targetFormat).format(date)
}

fun String.parseDate(formatStr: String): Date? {
    val sdFormat = SimpleDateFormat(formatStr)
    var date: Date? = null
    try {
        date = sdFormat.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return date
}


fun TabLayout.onTabSelected(onTabSelected: (Int) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(p0: TabLayout.Tab?) {

        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {

        }

        override fun onTabSelected(p0: TabLayout.Tab?) {
            p0?.let { onTabSelected(it.position) }
        }

    })
}

fun formatReturnDate(createdDate: String?): String {
    if (createdDate == null) {
        return ""
    }

    SimpleDateFormat("dd.MM.yyyy - HH:mm").apply {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(this.parse(createdDate))
    }
}

fun formatDate(createdDate: String?): Date {
    if (createdDate == null) {
        return Date()
    }

    SimpleDateFormat("dd.MM.yyyy - HH:mm").apply {
        return parse(createdDate)
    }
}

fun formatDateResponse(createdDate: String?): Date {
    if (createdDate == null) {
        return Date()
    }

    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:s").apply {
        return this.parse(createdDate)
    }
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { action(it) } })
}