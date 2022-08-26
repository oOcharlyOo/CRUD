package com.example.crud.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class SqliteH(context: Context): SQLiteOpenHelper(
    context,  "empleados.db", null, 1){
    override fun onCreate(p0: SQLiteDatabase?) {
        val creacionTabla = "CREATE TABLE empleados " + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT, email TEXT)"
        p0!!.execSQL(creacionTabla)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        val borrarTabla = "DROP TABLE IF EXISTS empleados"
        p0!!.execSQL(borrarTabla)
        onCreate(p0)
    }
    fun agregar (nombre: String, email: String){
        val datos = ContentValues()
        datos.put("nombre", nombre)
        datos.put("email", email)

        val p0 =this.writableDatabase
        p0.insert("empleados", null, datos)
        p0.close()
    }
    fun borrarDatos (id: Int): Int{
        val args = arrayOf(id.toString())

        val p0 =this.writableDatabase
        val borrado = p0.delete("empleados", "_id = ?", args)
        p0.close()
        return borrado

    }fun update (id: Int, nombre: String, email: String){
        val args = arrayOf(id.toString())
        val datos = ContentValues()
        datos.put("nombre", nombre)
        datos.put("email",email)
        val p0 =this.writableDatabase
        p0.update("empleados",datos, "_id = ?", args)
        p0.close()

    }


}