package ai.deepfine.presentation.base

import ai.deepfine.presentation.extensions.debounce
import ai.deepfine.presentation.utils.DiffCallback
import ai.deepfine.utility.utils.L
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @Description Class
 * @author jh.kim (DEEP.FINE)
 * @since 2/1/21
 * @version 1.0.0
 */
abstract class BaseAdapter<T, H : RecyclerView.ViewHolder> :
  RecyclerView.Adapter<H>() {

  var onItemClickListener: OnItemClickListener<T>? = null
  protected var itemList: MutableList<T>? = null
  var isClickable = true

  override fun getItemCount(): Int = itemList?.size ?: 0

  fun getList(): List<T>? = itemList

  open fun updateItems(items: List<T>, diffCallback: DiffCallback<*> = DiffCallback(itemList ?: listOf(), items)) {
    val diffResult = DiffUtil.calculateDiff(diffCallback)
    itemList = items.toMutableList()
    diffResult.dispatchUpdatesTo(this)
  }

  fun replaceItem(index: Int, item: T) {
    itemList?.apply {
      this[index] = item
    }

    notifyItemChanged(index)
  }

  fun addItem(item: T) {
    itemList?.let {
      it.add(item)
      notifyItemInserted(it.size - 1)
    }
  }

  override fun onBindViewHolder(holder: H, position: Int) {
    if (isClickable) {
      holder.itemView.debounce(300L) {
        try {
          onItemClickListener?.onItemClick(
            itemList?.get(holder.adapterPosition)
          )
        } catch (exception: Exception) {
          L.e(exception)
        }
      }
    }
  }

  fun interface OnItemClickListener<T> {
    fun onItemClick(item: T?)
  }
}