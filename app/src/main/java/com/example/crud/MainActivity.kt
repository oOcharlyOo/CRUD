package com.example.crud


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.crud.databinding.ActivityMainBinding
import com.example.crud.model.SqliteH
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var empleadosDbhelper: SqliteH


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //creacion de canal
        val chanelID = "chat"
        val chanelName = "chat"

        //Contruir el canal

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importancia = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(chanelID, chanelName, importancia)

            //manager de las notificaciones

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)

            //configurando notificacion

        }

        empleadosDbhelper = SqliteH(this)


        binding.btnConsultar.setOnClickListener(){
            binding.txtRegistro.text =""
            val db: SQLiteDatabase = empleadosDbhelper.readableDatabase
            val cursor = db.rawQuery(
                "SELECT * FROM empleados ",
                null)
            if(cursor.moveToFirst()){
                do{
                    binding.txtRegistro.append(
                        cursor.getInt(0).toString() + ": ")
                    binding.txtRegistro.append(
                        cursor.getString(1).toString()+", ")
                    binding.txtRegistro.append(
                        cursor.getString(2).toString()+"\n")



                }while (cursor.moveToNext())
            }
        }
        binding.btnGuardar.setOnClickListener {
            if(binding.textName.text.isNotBlank() &&
                    binding.textEmail.text.isNotBlank()){
                empleadosDbhelper.agregar(binding.textName.text.toString(),
                binding.textEmail.text.toString())

                binding.textName.text.clear()
                binding.textEmail.text.clear()
                val notification = NotificationCompat.Builder(this,chanelID).also { noti->
                    noti.setContentTitle("Registro")
                    noti.setContentText("Se a guardado un nuevo registro")
                    noti.setSmallIcon(R.mipmap.ic_launcher)
                }.build()
                val notificationManager = NotificationManagerCompat.from(applicationContext)
                notificationManager.notify(1,notification)
                Toast.makeText(this, "Guardado ", Toast.LENGTH_SHORT).show()
                FirebaseMessaging.getInstance().subscribeToTopic("Guardar")
            }
            else{
                Toast.makeText(this, "No se a guardado ", Toast.LENGTH_LONG).show()
            }






        }
        binding.btnBorrar.setOnClickListener{
            var cantidad = 0
            if(binding.TxtId.text.isNotBlank()){
                cantidad = empleadosDbhelper.borrarDatos(
                    binding.TxtId.text.toString().toInt())

                binding.TxtId.text.clear()
                val notification = NotificationCompat.Builder(this,chanelID).also { noti->
                    noti.setContentTitle("Registro")
                    noti.setContentText("Se a borrado un nuevo registro")
                    noti.setSmallIcon(R.mipmap.ic_launcher)
                }.build()
                val notificationManager = NotificationManagerCompat.from(applicationContext)
                notificationManager.notify(1,notification)
                Toast.makeText(this, "Datos Borrados:  $cantidad", Toast.LENGTH_LONG).show()

            } else{
                Toast.makeText(this, "No se han borrado datos ", Toast.LENGTH_LONG).show()
            }

        }
        binding.btnEdit.setOnClickListener {
            if(binding.textName.text.isNotBlank() &&
                binding.textEmail.text.isNotBlank()&&
                binding.TxtId.text.isNotBlank()){
                empleadosDbhelper.update(binding.TxtId.text.toString().toInt(),
                    binding.textName.text.toString(),
                    binding.textEmail.text.toString())

                binding.textName.text.clear()
                binding.textEmail.text.clear()
                binding.TxtId.text.clear()
                val notification = NotificationCompat.Builder(this,chanelID).also { noti->
                    noti.setContentTitle("Registro")
                    noti.setContentText("Se a Actualizado un registro")
                    noti.setSmallIcon(R.mipmap.ic_launcher)
                }.build()
                val notificationManager = NotificationManagerCompat.from(applicationContext)
                notificationManager.notify(1,notification)
                Toast.makeText(this, "Actualizado ", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Estan Vacios los Campos ", Toast.LENGTH_LONG).show()
            }
        }
    }





}