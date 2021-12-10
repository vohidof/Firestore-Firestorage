package Adapter

import Model.User
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vohidov.firebasedatabasecloud.R
import kotlinx.android.synthetic.main.item_rv.view.*

class UserAdapter(var context: Context, var list: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    inner class MyViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: User, position: Int) {
            //Picasso.get().load(list[position]).into(itemView2.item_image)
            itemView.txt_name.text = model.name
            itemView.txt_number.text = model.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}