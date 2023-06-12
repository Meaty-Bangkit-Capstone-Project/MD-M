package com.damar.meaty.home

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class ItemAnimator : DefaultItemAnimator() {
    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.itemView?.alpha = 0f
        holder?.itemView?.animate()?.alpha(1f)?.setDuration(400)?.start()
        return super.animateAdd(holder)
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.itemView?.animate()?.alpha(0f)?.setDuration(400)?.start()
        return super.animateRemove(holder)
    }
}