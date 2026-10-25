package com.vnteam.architecturetemplates.network

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vnteam.architecturetemplates.models.DemoObject
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ValleyApiConnector(private val requestQueue: RequestQueue) {

    fun makeRequest(url: String): Observable<List<DemoObject>> {
        return Observable.create { emitter ->
            val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val listTypes = Types.newParameterizedType(List::class.java, DemoObject::class.java)
                    val adapter: JsonAdapter<List<DemoObject>>? = moshi?.adapter(listTypes)
                    val demoObjects = adapter?.fromJson(response) ?: listOf()
                    emitter.onNext(demoObjects)
                    emitter.onComplete()
                }
            ) { error ->
                emitter.onError(error)
                error.printStackTrace()
            }
            requestQueue.add(stringRequest)
        }.subscribeOn(Schedulers.io())
    }
}