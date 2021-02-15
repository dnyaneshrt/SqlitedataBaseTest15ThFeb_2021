package com.tech.mysqlitedbtest

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

    var button_insert: Button? = null
    var button_read: Button? = null
    var button_update: Button? = null
    var button_delete: Button? = null

    var et1: EditText? = null
    var et2: EditText? = null
    var et3: EditText? = null
    var et4: EditText? = null

    var listView: ListView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        //step 1 :create a database
        var sqLiteDatabase = openOrCreateDatabase("MyDataBase", Context.MODE_PRIVATE, null)

        //create a table
        sqLiteDatabase.execSQL("create table if not exists TechEmployees(_id integer primary key  autoincrement,id integer unique,name varchar(50),desig varchar(50),dept varchar(50))")

        button_insert?.setOnClickListener {

            var cv = ContentValues()
            cv.put("id", et1?.text.toString())
            cv.put("name", et2?.text.toString())
            cv.put("desig", et3?.text.toString())
            cv.put("dept", et4?.text.toString())


            var status = sqLiteDatabase.insert("TechEmployees", null, cv)

            if (status == (-1).toLong()) {
                Toast.makeText(this, "data inserted succesfully", Toast.LENGTH_SHORT).show()
                et1?.setText("")
                et2?.setText("")
                et3?.setText("")
                et4?.setText("")
            } else {
                Toast.makeText(this, "unable to insert data", Toast.LENGTH_SHORT).show()
            }
        }

        button_read?.setOnClickListener {

            var cursor = sqLiteDatabase.query("TechEmployees", null, null, null, null, null, null)

            var fromArray = arrayOf("id", "name", "desig", "dept")
            var intArray = intArrayOf(R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4)

            var myAdapter = SimpleCursorAdapter(
                this, R.layout.myview, cursor,
                fromArray, intArray
            )
            listView?.adapter=myAdapter

        }

        button_update?.setOnClickListener {
            var cv = ContentValues()
            cv.put("id", et1?.text.toString())
            cv.put("name", et2?.text.toString())
            cv.put("desig", et3?.text.toString())
            cv.put("dept", et4?.text.toString())
            var idarray=et1?.text.toString()
           var status =sqLiteDatabase.update("TechEmployees",cv,"id=?", arrayOf(idarray))
            if(status>0)
            {
                Toast.makeText(this,"data updated succesfully",Toast.LENGTH_SHORT).show()
                et1?.setText("")
                et2?.setText("")
                et3?.setText("")
                et4?.setText("")
            }else
            {
                Toast.makeText(this,"unabke to update data",Toast.LENGTH_SHORT).show()

            }
        }

        button_delete?.setOnClickListener {

        var status=    sqLiteDatabase.delete("TechEmployees", "id=?", arrayOf(et1!!.text.toString()))
            if(status>0)
            {
                Toast.makeText(this,"data deleted succesfully",Toast.LENGTH_SHORT).show()
                et1?.setText("")
                et2?.setText("")
                et3?.setText("")
                et4?.setText("")

            }else
            {
                Toast.makeText(this,"unable to delete the data",Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun init() {
        button_insert = findViewById(R.id.button_insert)
        button_read = findViewById(R.id.button_read)
        button_update = findViewById(R.id.button_update)
        button_delete = findViewById(R.id.button_delete)

        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        et3 = findViewById(R.id.et3)
        et4 = findViewById(R.id.et4)

        listView = findViewById(R.id.lview)


    }
}