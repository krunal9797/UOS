package com.example.uos.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BottomMarginItemDecoration(private val marginInDp: Int, private val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val adapter = parent.adapter
        val layoutManager = parent.layoutManager as? LinearLayoutManager

        if (adapter != null && layoutManager != null && layoutManager.orientation == LinearLayoutManager.VERTICAL) {
            val itemPosition = parent.getChildAdapterPosition(view)
            val lastItemPosition = adapter.itemCount - 1

            // Check if it's the last item
            if (itemPosition == lastItemPosition) {
                // Convert dp to pixels
                val marginInPixels = dpToPixels(marginInDp)
                outRect.bottom = marginInPixels
            }
        }
    }

    private fun dpToPixels(dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}