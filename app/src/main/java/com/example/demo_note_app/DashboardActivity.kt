package com.example.demo_note_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demo_note_app.adaptor.MyAdapter
import com.example.demo_note_app.databinding.ActivityDashboardBinding
import com.example.demo_note_app.roomdb.NoteDataClass
import com.example.demo_note_app.roomdb.DataBaseName

import com.google.android.material.floatingactionbutton.FloatingActionButton


class DashboardActivity : AppCompatActivity(), MyAdapter.MyClickListener {

    //declare dataBase var
    private lateinit var dataBase: DataBaseName
    private lateinit var mBinding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        //initialize dataBase var
        dataBase = DataBaseName.getDataBase(this)

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            startActivity(Intent(applicationContext, AddActivity::class.java) )
        }

        showRecyclerView();

        mBinding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
                // No implementation needed here
            }

            override fun onTextChanged(chr: CharSequence?, i: Int, i1: Int, i2: Int) {
                // Check if char sequence is not null and not empty
                chr?.let {
                    if (it.isNotEmpty()) {
                        dataBase.interfaceDao().searchRecordByTitle(it.toString()).observe(this@DashboardActivity) {item->
                            val adapter = MyAdapter(item, this@DashboardActivity)
                            mBinding.recyclerView.adapter = adapter
                        }
                    }else
                    {
                        showRecyclerView();
                    }
                }
            }

            override fun afterTextChanged(editable: Editable?) {
                // No implementation needed here
            }
        })
    }

    private fun showRecyclerView() {

        dataBase.interfaceDao().getAllRecord().observe(this) {
            val adapter = MyAdapter(it, this)
            mBinding.recyclerView.adapter = adapter
        }

    }



    override fun onUpdateItemClick(item: NoteDataClass) {
        val intent = Intent(applicationContext, AddActivity::class.java)
        intent.putExtra("id", item.id)
        startActivity(intent)
    }

    override fun onDeleteItemClick(item: NoteDataClass) {
        TODO("Not yet implemented")
    }
}