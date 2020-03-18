package com.example.penampilberita

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.json.JSONArray
import org.json.JSONObject

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val context = this

        simpan.setOnClickListener{

            var id_data :String = d_id.text.toString()
            var judul_berita :String = d_judul.text.toString()
            var waktu_berita :String = d_waktu.text.toString()
            var penulis_berita :String = d_penulis.text.toString()
            var isi_berita :String = d_isi.text.toString()

            postkeserver(id_data, judul_berita, waktu_berita, penulis_berita, isi_berita)

            val intent = Intent(context,DashboardActivity::class.java)
            startActivity(intent)

        }

        penampil.setOnClickListener{


            val intent = Intent(context,Hasil::class.java)
            startActivity(intent)

        }

//        penampil.setOnClickListener{
//
//            val intent = Intent(context,com.example.penampilberita.penampil::class.java)
//            startActivity(intent)
//
//        }



        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val users=ArrayList<User>()


        AndroidNetworking.get("http://172.20.10.3/uts_mpt/berita.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("id_berita"))

                        // txt1.setText(jsonObject.optString("shubuh"))
                        var isi1=jsonObject.optString("id_berita").toString()
                        var isi2=jsonObject.optString("judul_berita").toString()
                        var isi3=jsonObject.optString("waktu_berita").toString()
                        var isi4=jsonObject.optString("penulis_berita").toString()
                        var isi5=jsonObject.optString("isi_berita").toString()

                        users.add(User("$isi1", "$isi2", "$isi3", "$isi4", "$isi5"))


                    }

                    val adapter=CustomAdapter(users)
                    recyclerView.adapter=adapter


                }

                override fun onError(anError: ANError) {
                    Log.i("_err", anError.toString())
                }
            })

        button.setOnClickListener{
            val sharedPreferences=getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("STATUS","0")
            editor.apply()

            startActivity(Intent(this@DashboardActivity,MainActivity::class.java))
            finish()
        }
    }

    fun postkeserver(data1:String, data2:String, data3:String, data4:String, data5:String)
    {
        AndroidNetworking.post("http://172.20.10.3/uts_mpt/create_berita.php")
            .addBodyParameter("id_berita", data1)
            .addBodyParameter("judul_berita", data2)
            .addBodyParameter("waktu_berita", data3)
            .addBodyParameter("penulis_berita", data4)
            .addBodyParameter("isi_berita", data5)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {

                }

                override fun onError(anError: ANError?) {

                }
            })
    }
}
