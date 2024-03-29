package com.example.tasks.service.repository

import com.example.tasks.service.HeaderModel
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.repository.remote.PersonService
import com.example.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository {
    private val mRemote = RetrofitClient.createService(PersonService::class.java)

    fun login (email: String, password: String, listener: APIListener){
        val call: Call<HeaderModel> = mRemote.login(email, password)
        //Assíncrona
        call.enqueue(object : Callback<HeaderModel>{
            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                listener.onFailure(t.message.toString())

            }
            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
                if (response.code() != 200){
                    
                }
                val s = ""
                response.body()?.let { listener.onSuccess(it) }
            }
        })
    }
}