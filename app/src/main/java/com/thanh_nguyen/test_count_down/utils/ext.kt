package com.thanh_nguyen.test_count_down.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.google.gson.Gson
import com.thanh_nguyen.test_count_down.common.Constants
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*
import java.util.concurrent.TimeUnit

fun Any.toJson(): String{
    return Gson().toJson(this)
}

fun Context.hasPermissions(vararg permissions: String) = permissions.all { permission ->
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.copyToClipboard(label: String = "copied text", content: String) {
    val clipboardManager = ContextCompat.getSystemService(this, ClipboardManager::class.java)!!
    val clip = ClipData.newPlainText(label, content)
    clipboardManager.setPrimaryClip(clip)
}

fun Date.plusDays(days: Int): Date {
    return Date(this.time + (days * 24 * 60 * 60 * 1000))
}

fun Date.plusMillis(millis: Long): Date {
    return Date(this.time + millis)
}

fun Any?.isNull() = this == null

fun Double.formatPrice(pattern: String? = "###,###,###.00"): String = DecimalFormat(pattern).format(this)

fun inflateView(parent: ViewGroup, @LayoutRes layoutRes: Int): View{
    return LayoutInflater
        .from(parent.context)
        .inflate(layoutRes, null)
}

inline fun <reified T: View>T.onClick(crossinline onClick: (view: T) -> Unit){
    setOnClickListener {
        onClick.invoke(it as T)
    }
}

fun Activity.showMessage(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

inline fun <reified T, reified LD: LiveData<T>> Fragment.observeLiveDataChanged(liveData: LD, crossinline onChanged: (T) -> Unit){
    liveData.observe(viewLifecycleOwner, onChanged)
}

inline fun <reified T, reified LD: LiveData<T>> AppCompatActivity.observeLiveDataChanged(liveData: LD, observer: Observer<in T>){
    liveData.observe(this, observer)
}

fun cmn(msg: String){
    Log.e("CMN", msg)
}

fun Long.formatTwoNumber(): String{
    return when{
        this < 10 -> "0$this"
        else -> "$this"
    }
}


fun String.toCalendar(format: String = "dd/MM/yyyy"): Calendar{
    val cal = Calendar.getInstance()
    val sdf = SimpleDateFormat(format, Locale.ENGLISH)
    try {
        cal.time = sdf.parse(this) // all done

    }catch (e: Exception){

    }
    return cal
}

fun getSecondsUntilDate(endDate: String): Long{
    val currentMillis = System.currentTimeMillis()
    val endMillis = endDate.toCalendar().timeInMillis
    return TimeUnit.MILLISECONDS.toSeconds(endMillis - currentMillis)
}

fun isTetOnGoing(): Boolean = getSecondsUntilDate(Constants.EventDate.LUNAR_NEW_YEAR) <= 0

fun getDaysUntilDate(endDate: String): Long{
    val currentMillis = System.currentTimeMillis()
    val endMillis = endDate.toCalendar().timeInMillis
    return TimeUnit.MILLISECONDS.toDays(endMillis - currentMillis)
}

fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}

fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))

fun Context.showToastMessage(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}