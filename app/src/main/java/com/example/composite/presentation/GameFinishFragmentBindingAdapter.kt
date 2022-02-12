package com.example.composite.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composite.R
import com.example.composite.domain.entyti.GameResult
import kotlin.math.max
interface OnAnswerCLickListener{
    fun onAnswerClick(answer:Int)
}
@BindingAdapter("getString")
fun bindQuestionCount(tv:TextView,count:Int){
    tv.text = count.toString()
}
@BindingAdapter("getRightPercent")
fun bindAnswerPercent(tv:TextView,gameResult: GameResult){
    tv.text = String.format(
        "%.3f",
        (100 * gameResult.countOfRightAnswers.toDouble() / max(gameResult.countOfQuestions, 1))
    )
}
@BindingAdapter("getImageByResult")
fun bindImage(iv:ImageView,isWin:Boolean){
    val imageId = if (isWin) {
        R.drawable.win_smile
    } else {
        R.drawable.sad_smile
    }
    iv.setImageResource(imageId)
}
@BindingAdapter("progressBarPrimary")
fun bindProgressBarPrimary(pb:ProgressBar,progress:Int){
    pb.setProgress(progress, true)
}
@BindingAdapter("enoughRightPercent")
fun bindEnoughRightPercent(pb:ProgressBar,enough:Boolean){
    pb.progressTintList = ColorStateList.valueOf(getColorByState(enough,pb.context))
}
@BindingAdapter("enoughRightCount")
fun bindEnoughRightCountt(tv:TextView,enough:Boolean){
    tv.setTextColor(getColorByState(enough,tv.context))
}
fun getColorByState(state: Boolean,context: Context): Int {
    val colorId = if (state) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return getColorByResId(colorId,context)
}

fun getColorByResId(resId: Int,context: Context): Int {
    return ContextCompat.getColor(context, resId)
}
@BindingAdapter("onAnswerClickListener")
fun bindOnAnswerCLickListener(tv:TextView,onAnswerCLick:OnAnswerCLickListener){
    tv.setOnClickListener {
        onAnswerCLick.onAnswerClick(tv.text.toString().toInt())
    }
}