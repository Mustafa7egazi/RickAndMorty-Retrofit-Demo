package com.example.rickandmorty.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.CharactersListItemBinding
import com.example.rickandmorty.pojo.Results

class AllCharactersAdapter : ListAdapter<Results, AllCharactersAdapter.ViewHolder>(ItemDiff()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CharactersListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//       holder.binding.ageId.text = getItem(position).age.toString()
//        holder.binding.nameId.text = getItem(position).name

        holder.binding.characterName.text = getItem(position).name
        Glide.with(holder.binding.root.context)
            .load(getItem(position).image)
            .placeholder(R.drawable.loading)
            .error(R.drawable.failed)
            .into(holder.binding.characterImage)
        holder.binding.characterStatus.text = "${getItem(position).status} - ${getItem(position).species}"
        if (getItem(position).status == "Alive"){
            holder.binding.statusImage.setImageResource(R.drawable.online)
        }else{
            holder.binding.statusImage.setImageResource(R.drawable.offline)
        }

        holder.binding.characterLocation.text = getItem(position).location.name
        holder.binding.characterGender.text = getItem(position).gender

    }

    /*fun setData(newList:List<Person>){
        val diffUtil = MyDiffUtil(oldPersonsList,newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldPersonsList = newList
        diffResults.dispatchUpdatesTo(this)
    }*/

    inner class ViewHolder(val binding: CharactersListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ItemDiff : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(
            oldItem: Results, newItem:
            Results
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }

    }
    /*inner class MyDiffUtil(
        private val oldList:List<Person>,
        private val newList:List<Person>
        ):DiffUtil.Callback(){

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id== newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
          return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }*/
}