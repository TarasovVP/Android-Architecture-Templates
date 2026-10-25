package com.vnteam.architecturetemplates

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import com.vnteam.architecturetemplates.MainActivity.Companion.ERROR
import com.vnteam.architecturetemplates.MainActivity.Companion.SUCCESS_HTTP_CONNECTION
import com.vnteam.architecturetemplates.MainActivity.Companion.SUCCESS_IMAGE_FROM_URL_CONNECTION
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpUrlConnector {

    fun makeHttpUrlConnection(handler: Handler) {
        val thread = Thread {
            try {
                val url = "https://api.github.com/repos/octocat/Spoon-Knife/forks"
                val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode: Int = connection.responseCode
                if (responseCode == 200) {
                    val inputStream: InputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val stringBuilder = StringBuilder()
                    var line: String? = reader.readLine()
                    while (line != null) {
                        stringBuilder.append(line)
                        line = reader.readLine()
                    }
                    val responseData = stringBuilder.toString()
                    val message = handler.obtainMessage(SUCCESS_HTTP_CONNECTION, responseData)
                    handler.sendMessage(message)
                } else {
                    val message = handler.obtainMessage(ERROR, "Error responseCode - $responseCode")
                    handler.sendMessage(message)
                }
            } catch (e: Exception) {
                val message = handler.obtainMessage(ERROR, e.localizedMessage)
                handler.sendMessage(message)
            }
        }
        thread.start()
    }

    fun getBitmapFromHttpUrlUrl(imageUrl: String?, handler: Handler) {
        val thread = Thread {
            val bitmap: Bitmap?
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()

                val inputStream: InputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(inputStream)
                val message = handler.obtainMessage(SUCCESS_IMAGE_FROM_URL_CONNECTION, bitmap)
                handler.sendMessage(message)
            } catch (e: Exception) {
                val message = handler.obtainMessage(ERROR, e.localizedMessage)
                handler.sendMessage(message)
            }
        }
        thread.start()
    }
}