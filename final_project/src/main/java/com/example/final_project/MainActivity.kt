package com.example.final_project

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var headerView: View
    private lateinit var xmlAdapter: XmlAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DrawerLayout Toggle
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // Drawer menu
        binding.mainDrawerView.setNavigationItemSelectedListener(this)

        headerView = binding.mainDrawerView.getHeaderView(0)
        val button = headerView.findViewById<Button>(R.id.btnAuth)
        button.setOnClickListener {
            Log.d("MainActivity", "button.setOnClickListener")
            val intent = Intent(this, AuthActivity::class.java)
            intent.putExtra("status", if (button.text == "로그인") "logout" else "login")
            startActivity(intent)
            binding.drawer.closeDrawers()
        }

        // 간편 날짜 선택 버튼 클릭 시
        binding.btnDate.setOnClickListener {
            showDatePickerDialog()
        }

        // EditText에 입력된 날짜 변화 감지
        binding.edtDay.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnDate.text = s.toString()
                binding.btnDate.textSize = 24f
                binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
            }
        })

        // RecyclerView 설정
        setupRecyclerView()

        // 검색 버튼 클릭 시
        binding.btnSearch.setOnClickListener {
            if (MyApplication.checkAuth()) {
                val day = binding.edtDay.text.toString() // edtDay에서 사용자 입력 받기
                Log.d("MainActivity", "Day: $day")
                fetchData(day)
            } else {
                // 로그인이 필요한 경우 처리
                Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
                // 예를 들어 로그인 화면으로 이동하는 코드 추가
                startActivity(Intent(this, AuthActivity::class.java))
            }
        }
    }

    private fun setupRecyclerView() {
        binding.xmlRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.xmlRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        xmlAdapter = XmlAdapter(emptyList()) // 초기화할 때 빈 리스트로 설정
        binding.xmlRecyclerView.adapter = xmlAdapter
    }

    private fun fetchData(day: String) {
        val apiKey = "V5ys0ft63xXqrAbUAcBnr2nuScyLh+Y1kULrRl3v9ncPtoFlsToDxdnOeFSmGt8jHpZnD1/oy9pxDjHYz1t5XA=="
        val pageNo = 1
        val numOfRows = 10
        val dataType = "XML"
        val currentDate = "2018123110" // 예시로 고정된 현재 날짜 사용

        val call: Call<XmlResponse> = RetrofitConnection.xmlNetworkService.getXmlList(
            apiKey,
            pageNo,
            numOfRows,
            dataType,
            currentDate,
            day
        )

        call.enqueue(object : Callback<XmlResponse> {
            override fun onResponse(call: Call<XmlResponse>, response: Response<XmlResponse>) {
                if (response.isSuccessful) {
                    val items = response.body().body!!.items!!.item
                    Log.d("mobileapp","$items")
                    items?.let {
                        xmlAdapter.setData(it) // 데이터를 Adapter에 설정
                    }
                } else {
                    Log.e("MainActivity", "Failed to fetch data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<XmlResponse>, t: Throwable) {
                Log.e("MainActivity", "Failed to fetch data: ${t.message}")
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 뒤로가기 버튼을 눌렀을 때 메시지 띄우기
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val selectedDate = "$year 년 ${month + 1} 월 $dayOfMonth 일"
            Toast.makeText(applicationContext, selectedDate, Toast.LENGTH_LONG).show()
            binding.btnDate.text = selectedDate
            binding.btnDate.textSize = 24f
            binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
            binding.edtDay.setText("$year-${month + 1}-$dayOfMonth") // EditText에 날짜 설정
        }, year, month, dayOfMonth)

        datePickerDialog.show()
    }

    // DrawerLayout Toggle
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Drawer 메뉴
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_board -> {
                Log.d("MainActivity", "게시판 메뉴")
                startActivity(Intent(this, BoardActivity::class.java))
            }
            R.id.item_setting -> {
                Log.d("MainActivity", "설정 메뉴")
                startActivity(Intent(this, SettingActivity::class.java))
                binding.drawer.closeDrawers()
                return true // true를 반환하여 이벤트 처리 완료를 나타냄
            }
            R.id.item_profile -> {
                Log.d("MainActivity", "프로필 메뉴")
                startActivity(Intent(this, ProfileActivity::class.java))
                binding.drawer.closeDrawers()
                return true // true를 반환하여 이벤트 처리 완료를 나타냄
            }
        }
        return false // 처리할 수 없는 경우 false 반환
    }

    override fun onStart() {
        super.onStart()

        val button = headerView.findViewById<Button>(R.id.btnAuth)
        val tv = headerView.findViewById<TextView>(R.id.tvID)

        if (MyApplication.checkAuth()) {
            button.text = "로그아웃"
            tv.text = "${MyApplication.email}님 \n 반갑습니다."
        } else {
            button.text = "로그인"
            tv.text = "안녕하세요.."
        }
    }
}
