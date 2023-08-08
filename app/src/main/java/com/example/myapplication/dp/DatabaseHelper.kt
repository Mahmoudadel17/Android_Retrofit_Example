package com.example.myapplication.dp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


const val DATABASE_NAME = "MyDatabase"
const val DATABASE_VERSION = 1

//const val USERS_TABLE = "USERS"
//const val USER_ID_COLUMN = "USER_ID"
//const val USER_NAME_COLUMN = "USER_NAME"
//const val USER_EMAIL_COLUMN = "USER_EMAIL"

const val TASK_TABLE = "TASKS"
const val TASK_ID_COLUMN = "TASK_ID"
const val TASK_TITLE_COLUMN = "TASK_TITLE"
const val TASK_CONTENT_COLUMN = "TASK_CONTENT"
class DatabaseHelper(context:Context)
    :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
//        val createQuery =
//            "CREATE TABLE $USERS_TABLE ($USER_ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT,$USER_NAME_COLUMN TEXT NOT NULL,$USER_EMAIL_COLUMN TEXT)"
//
//        db?.execSQL(createQuery)

        val createQuery2 =
            "CREATE TABLE $TASK_TABLE ($TASK_ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT,$TASK_TITLE_COLUMN TEXT NOT NULL,$TASK_CONTENT_COLUMN TEXT )"
        db?.execSQL(createQuery2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        onCreate(db)
    }

//    fun insertUserData(user:User):Boolean{
//        // make object to write in database
//        val db = writableDatabase
//        // make val to contain all values
//        val values = ContentValues()
//        // add values to variable
//        values.put(USER_NAME_COLUMN,user.userName)
//        values.put(USER_EMAIL_COLUMN,user.email)
//        return db.insert(USERS_TABLE,"",values).let {
//            db.close()
//            it != -1L
//        }
//    }
    fun insertTaskData(task: Task):Boolean{
        // make object to write in database
        val db = writableDatabase
        // make val to contain all values
        val values = ContentValues()
        // add values to variable
        values.put(TASK_TITLE_COLUMN,task.title)
        values.put(TASK_CONTENT_COLUMN,task.content)
        return db.insert(TASK_TABLE,"",values).let {
            db.close()
            it != -1L
        }
    }

//    fun getAllUsers(): List<User> {
//        val users = mutableListOf<User>()
//        val query = "SELECT * FROM $USERS_TABLE"
//        val db = readableDatabase
//        val cursor = db.rawQuery(query, emptyArray())
//        if (cursor.moveToFirst())
//            do {
//                val idIndex = cursor.getColumnIndex(USER_ID_COLUMN)
//                val usernameIndex = cursor.getColumnIndex(USER_NAME_COLUMN)
//                val emailIndex = cursor.getColumnIndex(USER_EMAIL_COLUMN)
//                val id = cursor.getInt(idIndex)
//                val name = cursor.getString(usernameIndex)
//                val email = cursor.getString(emailIndex)
//                users.add(User(userName = name,id = id,email=email))
//            } while (cursor.moveToNext())
//        cursor.close()
//        db.close()
//        return users
//    }

    fun getAllTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val query = "SELECT * FROM $TASK_TABLE"
        val db = readableDatabase
        val cursor = db.rawQuery(query, emptyArray())
        if (cursor.moveToFirst())
            do {
                val idIndex = cursor.getColumnIndex(TASK_ID_COLUMN)
                val titleIndex = cursor.getColumnIndex(TASK_TITLE_COLUMN)
                val contentIndex = cursor.getColumnIndex(TASK_CONTENT_COLUMN)
                val id = cursor.getInt(idIndex)
                val title = cursor.getString(titleIndex)
                val content = cursor.getString(contentIndex)
                tasks.add(Task(id,title,content))
            } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return tasks
    }

}