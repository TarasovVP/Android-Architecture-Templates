package com.vnteam.architecturetemplates

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class OkHttpClientConnector {

    fun makeHttpUrlConnection(callback: (result: String?) -> Unit, errorCallBack: (String) -> Unit) {
        object : AsyncTask<Void, Void, String>() {
            @SuppressLint("StaticFieldLeak")
            override fun doInBackground(vararg params: Void): String? {
                val url = "https://api.github.com/repos/octocat/Spoon-Knife/forks"
                val okHttpClient = OkHttpClient()
                val request = Request.Builder()
                    .url(url)
                    .build()

                try {
                    val response: Response = okHttpClient.newCall(request).execute()
                    if (response.isSuccessful) {
                        return response.body?.string()
                    } else {
                        errorCallBack.invoke("Error responseCode ${response.code}")
                    }
                } catch (e: IOException) {
                    errorCallBack.invoke(e.localizedMessage)
                    e.printStackTrace()
                }
                return null
            }

            override fun onPostExecute(result: String?) {
                callback(result)
            }
        }.execute()
    }

    fun getBitmapFromHttpUrlUrl(imageUrl: String?, callback: (result: Bitmap?) -> Unit, errorCallBack: (String) -> Unit) {
        object : AsyncTask<Void, Void, Bitmap>() {
            @SuppressLint("StaticFieldLeak")
            override fun doInBackground(vararg params: Void): Bitmap? {
                try {
                    val url = URL(imageUrl)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()

                    val inputStream: InputStream = connection.inputStream
                    return BitmapFactory.decodeStream(inputStream)
                } catch (e: Exception) {
                    errorCallBack.invoke(e.localizedMessage)
                    return null
                }
            }

            override fun onPostExecute(result: Bitmap?) {
                callback(result)
            }
        }.execute()
    }
}