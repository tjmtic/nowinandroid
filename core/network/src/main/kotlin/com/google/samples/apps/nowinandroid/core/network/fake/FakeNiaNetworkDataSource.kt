/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.core.network.fake

import JvmUnitTestFakeAssetManager
import com.google.samples.apps.nowinandroid.core.network.Dispatcher
import com.google.samples.apps.nowinandroid.core.network.NiaDispatchers.IO
import com.google.samples.apps.nowinandroid.core.network.NiaNetworkDataSource
import com.google.samples.apps.nowinandroid.core.network.model.NetworkChangeList
import com.google.samples.apps.nowinandroid.core.network.model.NetworkNewsResource
import com.google.samples.apps.nowinandroid.core.network.model.NetworkTopic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.Instant.Companion
import kotlinx.datetime.toInstant
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Request.Builder
import okhttp3.RequestBody
import okhttp3.Response
import okio.use
import javax.inject.Inject

/**
 * [NiaNetworkDataSource] implementation that provides static news resources to aid development
 */
class FakeNiaNetworkDataSource @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
) : NiaNetworkDataSource {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getTopics(ids: List<String>?): List<NetworkTopic> =
        withContext(ioDispatcher) {
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!TOPIC!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!TOPIC!!!!")

            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!TOPIC!!!")

            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!TOPIC!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!TOPIC!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!TOPIC!!!")

            assets.open(TOPICS_ASSET).use(networkJson::decodeFromStream)
        }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getNewsResources(ids: List<String>?): List<NetworkNewsResource> =
        withContext(ioDispatcher) {

            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")

            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")

            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")

            val client = OkHttpClient().newBuilder()
                .build()
            val mediaType: MediaType? = "text/plain".toMediaTypeOrNull()
            val body = RequestBody.create(mediaType, "")
            val request: Request = Builder()
                .url("https://ax-code-cabin-default-rtdb.firebaseio.com/test/articles/bbc/x1696204934804/test.json")
                .method("GET" ,null)
                .build()
            val response: Response = client.newCall(request).execute()

            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")

            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")

            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("TIME123 LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("TIME123 LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("TIME123 LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            println("TIME123  LOGGGING NEW CALL GET FIREBASBE BASE BASBE INFO DATATATATATAT!Q!!!!!!")
            val respo = response.body
            val respoB = respo?.string()

            println("TIME123 LOGGGING NEW CALL $respo $respoB")


            convertToList(respoB!!)

            //assets.open(NEWS_ASSET).use(networkJson::decodeFromStream)
        }


    fun convertToList(raw:String): List<NetworkNewsResource>{


        println("TIME123 Converting to list: $raw")

        var responseList: List<NetworkNewsResource> = emptyList()
        val obj = networkJson.decodeFromString<JsonArray>(raw)

       // val content = obj

        responseList = obj.map{
            println("Object ENtry: ${it}")
            //it.jsonObject.entries
                val id = it.jsonObject["title"].toString()
                val content = it.jsonObject["content"]?.jsonPrimitive?.content
                val headerImageUrl = it.jsonObject["headerImageUrl"]?.jsonPrimitive?.content?.split(" ")
                    ?.get(0)
                    //?.plus('"')
                val publishDate = "2022-05-04T23:00:00Z".toInstant()/* it.jsonObject["publishDate"].toString()*/
                val title = it.jsonObject["title"]?.jsonPrimitive?.content
                val type = it.jsonObject["type"]?.jsonPrimitive?.content
                val url = it.jsonObject["url"]?.jsonPrimitive?.content

                val url1 = it.jsonObject["url"]?.jsonPrimitive
                val url2 = it.jsonObject["url"].toString()
                val url3 = it.jsonObject.get("url").toString()
                val topics = listOf("21")

                println("MAking NEWS RESOURCE $id $title $url $type $headerImageUrl")
                println("URLS: $url $url1 $url2 $url3")

            if (title != null && content != null && url != null && headerImageUrl != null && publishDate != null && type != null && topics != null) {
                return@map NetworkNewsResource(
                    id,
                    title,
                    content,
                    url,
                    headerImageUrl,
                    publishDate,
                    type,
                    topics
                )
            }
            else{
                return@map NetworkNewsResource("","","","","","2022-05-04T23:00:00Z".toInstant(),"", emptyList())
            }
            }



        println("Made News Resources $responseList")

        return responseList
    }

    override suspend fun getTopicChangeList(after: Int?): List<NetworkChangeList> =
        getTopics().mapToChangeList(NetworkTopic::id)

    override suspend fun getNewsResourceChangeList(after: Int?): List<NetworkChangeList> =
        getNewsResources().mapToChangeList(NetworkNewsResource::id)

    companion object {
        private const val NEWS_ASSET = "news.json"
        private const val TOPICS_ASSET = "topics.json"
    }
}

/**
 * Converts a list of [T] to change list of all the items in it where [idGetter] defines the
 * [NetworkChangeList.id]
 */
private fun <T> List<T>.mapToChangeList(
    idGetter: (T) -> String,
) = mapIndexed { index, item ->
    NetworkChangeList(
        id = idGetter(item),
        changeListVersion = index,
        isDelete = false,
    )
}
