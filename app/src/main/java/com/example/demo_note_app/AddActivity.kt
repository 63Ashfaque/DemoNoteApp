package com.example.demo_note_app


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.demo_note_app.databinding.ActivityAddBinding
import com.example.demo_note_app.roomdb.NoteDataClass
import com.example.demo_note_app.roomdb.DataBaseName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAddBinding
    private lateinit var dataBase: DataBaseName
    var valueId:Long=0
    private var isInsert=true;

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add)

        //initialize dataBase var
        dataBase = DataBaseName.getDataBase(this)

        mBinding.tvDate.text = Utils().getCurrentDateTime("dd MMMM yyyy hh:mm a")

        valueId = intent.getLongExtra("id", 0)

        if(valueId>0)
        {
            isInsert=false
            fetchData(valueId)
            mBinding.fabDelete.visibility = View.VISIBLE
        }

        mBinding.fabSave.setOnClickListener {
            validationChecker()
        }
        mBinding.fabBack.setOnClickListener {
            onBackPressed()
        }

        mBinding.fabDelete.setOnClickListener {
            scope.launch {
                val delete = dataBase.interfaceDao().deleteDataById(valueId)
                if (delete > 0) {
                    //when we run background that time we use withContext(dispatcher)
                    withContext(Dispatchers.Main) {
                        Utils().showToast(applicationContext, "One Record Deleted")
                        delay(2000)
                        onBackPressed()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Utils().showToast(applicationContext, "Not present")
                    }
                }
            }

        }

    }


    private fun validationChecker() {

        val mDate = mBinding.tvDate.text.toString()
        val edTitle = mBinding.edTitle.text.toString()
        val tags=""

        if (edTitle.isNotEmpty()) {
            val edDesc = mBinding.edDesc.text.toString()
            if (edDesc.isNotEmpty()) {
                mBinding.tLayoutDesc.error = null
                insertUpdateNote(edTitle, edDesc,tags,mDate)

            } else {
                mBinding.tLayoutDesc.error = "Please Enter Content"
                mBinding.tLayoutDesc.requestFocus()
                mBinding.tLayoutTitle.error = null
            }
        } else {
            mBinding.tLayoutTitle.error = "Please Enter Title"
            mBinding.tLayoutTitle.requestFocus()
        }
    }

    private fun insertUpdateNote( edTitle: String, edDesc: String, tags: String, mDate: String) {

        scope.launch {
            val result = if(isInsert) {
                dataBase.interfaceDao().insertData(NoteDataClass(0, edTitle, edDesc,tags,mDate)).toInt()
            } else {
                dataBase.interfaceDao().updateData(NoteDataClass(valueId,edTitle, edDesc,tags,mDate))
            }

            if (result > 0) {
                //when we run background that time we use withContext(dispatcher)
                withContext(Dispatchers.Main) {
                    Utils().showToast(applicationContext, if (isInsert) "Insert Successful" else "Update Successful")
                }
            } else {
                withContext(Dispatchers.Main) {
                    Utils().showToast(applicationContext, if (isInsert)"Already Present" else "Not Update")
                }
            }
        }

    }

    private fun fetchData(valueId: Long) {

        scope.launch {
            val fetch: NoteDataClass = dataBase.interfaceDao().getRecordById(valueId)
            withContext(Dispatchers.Main) {
                mBinding.edTitle.setText(fetch.title)
                mBinding.edDesc.setText(fetch.content)
                mBinding.tvDate.setText(fetch.createdDate)
            }

        }
    }
}


