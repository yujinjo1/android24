package com.example.final_project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.ActivityBoardBinding

class BoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainFab.setOnClickListener{
            if(MyApplication.checkAuth()){
                startActivity(Intent(this,AddActivity::class.java))
            }
            else{
                Toast.makeText(this,"인증을 먼저 해주세요..",Toast.LENGTH_LONG).show()
            }

        }

    }
    override fun onStart(){
        super.onStart()

        if(MyApplication.checkAuth()){
            MyApplication.db.collection("coments")
                .get()
                .addOnSuccessListener { result ->
                    val itemList = mutableListOf<ItemData>()
                    for(document in result){
                        val item = document.toObject(ItemData::class.java)
                        item.docId=document.id
                        itemList.add(item)
                    }
                    binding.recyclerView.layoutManager=LinearLayoutManager(this)
                    binding.recyclerView.adapter=BoardAdapter(this,itemList)
                }
                .addOnFailureListener {
                    Toast.makeText(this,"서버 데이터 획득 실패",Toast.LENGTH_LONG).show()
                }
        }
    }
}