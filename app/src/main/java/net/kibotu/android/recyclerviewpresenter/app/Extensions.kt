package net.kibotu.android.recyclerviewpresenter.app

import androidx.recyclerview.widget.RecyclerView
import com.exozet.android.core.extensions.resInt
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

inline fun <VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.decorateWithAlphaScaleAdapter(): ScaleInAnimationAdapter {
    val alphaAdapter = AlphaInAnimationAdapter(this, 0f).apply {
        setFirstOnly(false)
        setDuration(android.R.integer.config_mediumAnimTime.resInt)
    }
    return ScaleInAnimationAdapter(alphaAdapter, 1.5f).apply {
        setFirstOnly(false)
        setDuration(android.R.integer.config_shortAnimTime.resInt)
    }
}