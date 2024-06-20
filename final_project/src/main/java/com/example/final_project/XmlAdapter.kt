//package com.example.final_project
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.final_project.databinding.ItemMainBinding
//
//class XmlAdapter(private var items: List<String>) : RecyclerView.Adapter<XmlAdapter.XmlViewHolder>() {
//
//    inner class XmlViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: MyXmlItem) {
//            binding.tm.text = item.tm
//            binding.totalCityName.text = item.totalCityName
//            binding.doName.text = item.doName
//            binding.cityName.text = item.cityName
//            binding.cityAreaId.text = item.cityAreaId
//            binding.kmaTci.text = item.kmaTci
//            binding.tciGrade.text = item.tciGrade
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XmlViewHolder {
//        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return XmlViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: XmlViewHolder, position: Int) {
//        holder.bind(items[position])
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    fun setData(newItems: List<MyXmlItem>) {
//        items = newItems
//        notifyDataSetChanged()
//    }
//}
package com.example.final_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.ItemMainBinding

class XmlAdapter(private var items: List<MyXmlItem>) : RecyclerView.Adapter<XmlAdapter.XmlViewHolder>() {

    inner class XmlViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyXmlItem) {
            binding.tm.text = item.tm
            binding.totalCityName.text = item.totalCityName
            binding.doName.text = item.doName
            binding.cityName.text = item.cityName
            binding.cityAreaId.text = item.cityAreaId
            binding.kmaTci.text = item.kmaTci
            binding.tciGrade.text = item.tciGrade
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XmlViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return XmlViewHolder(binding)
    }

    override fun onBindViewHolder(holder: XmlViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<MyXmlItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
