package com.cheesejuice.fancymansionsample.data.repositories.file

import android.content.Context
import android.util.Log
import com.cheesejuice.fancymansionsample.Const

import com.cheesejuice.fancymansionsample.Const.Companion.TAG
import com.cheesejuice.fancymansionsample.data.SampleBook
import com.cheesejuice.fancymansionsample.data.models.Config
import com.cheesejuice.fancymansionsample.data.models.Logic
import com.cheesejuice.fancymansionsample.data.models.Slide
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.File.separator
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

class FileRepository constructor(private val context: Context){
    // file path
    fun dirBook(bookId: Long) = "book$bookId"
    fun dirContent(bookId: Long) = dirBook(bookId)+separator+"content"
    fun dirMedia(bookId: Long) = dirContent(bookId)+separator+"media"
    fun dirSlide(bookId: Long) = dirContent(bookId)+separator+"slide"

    fun fileConfig(bookId: Long) = dirBook(bookId)+separator+"config.json"
    fun fileImage(bookId: Long, imageName:String) = dirMedia(bookId)+separator+imageName
    fun fileSlide(bookId: Long, slideId: Long) = dirSlide(bookId)+separator+"slide_"+slideId+".json"
    fun fileLogic(bookId: Long) = dirContent(bookId)+separator+"logic.json"
    fun fileCover(bookId: Long, imageName:String) = dirBook(bookId)+separator+imageName

    private val rootPath = File(context.getExternalFilesDir(null), "book")

    fun initRootFolder():Boolean{
        return try{
            if(!rootPath.exists()){
                rootPath.mkdirs()
            }
            true
        }catch (e: Exception){
            false
        }
    }

    // make data
    fun makeBookFolder(bookId: Long): Boolean{
        try{
            val dir = File(rootPath, dirBook(bookId))
            if(dir.exists()){
                dir.deleteRecursively()
            }
            dir.mkdirs()
            val content = File(rootPath, dirContent(bookId))
            content.mkdirs()
            val media = File(rootPath, dirMedia(bookId))
            media.mkdirs()
            val slide = File(rootPath, dirSlide(bookId))
            slide.mkdirs()
            return true
        }catch (e: Exception){
            Log.d(TAG, ""+e.printStackTrace())
            return false
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun makeConfigFile(config: Config): Boolean{
        try{
            val file = File(rootPath, fileConfig(config.bookId))
            if(file.exists()){
                file.delete()
            }
            FileOutputStream(file).use {
                it.write(Json.encodeToString(config).toByteArray())
            }
        }catch (e: Exception){
            Log.d(TAG, ""+e.printStackTrace())
            return false
        }
        return true
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun makeSlideFile(bookId: Long, slide: Slide): Boolean{
        try{
            val file = File(rootPath, fileSlide(bookId, slide.slideId))
            if(file.exists()){
                file.delete()
            }
            FileOutputStream(file).use {
                it.write(Json.encodeToString(slide).toByteArray())
            }
        }catch (e: Exception){
            Log.d(TAG, ""+e.printStackTrace())
            return false
        }
        return true
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun makeLogicFile(logic: Logic): Boolean{
        try{
            val file = File(rootPath, fileLogic(logic.bookId))
            if(file.exists()){
                file.delete()
            }
            FileOutputStream(file).use {
                it.write(Json.encodeToString(logic).toByteArray())
            }
        }catch (e: Exception){
            Log.d(TAG, ""+e.printStackTrace())
            return false
        }
        return true
    }

    // get data
    @OptIn(ExperimentalSerializationApi::class)
    fun getConfigFromFile(bookId: Long): Config?{
        var config: Config? = null
        try{
            val file = File(rootPath, fileConfig(bookId))
            if(file.exists()){
                val configJson = FileInputStream(file).bufferedReader().use { it.readText() }
                config = Json.decodeFromString<Config>(configJson)
            }
        }catch (e: Exception){
            Log.d(TAG, ""+e.printStackTrace())
        }
        return config
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getLogicFromFile(bookId: Long): Logic?{
        var logic: Logic? = null
        try{
            val file = File(rootPath, fileLogic(bookId))

            if(file.exists()){
                val logicJson = FileInputStream(file).bufferedReader().use { it.readText() }
                logic = Json.decodeFromString<Logic>(logicJson)
            }

        }catch (e: Exception){
            Log.d(TAG, ""+e.printStackTrace())
        }
        return logic
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getSlideFromFile(bookId: Long, slideId: Long): Slide?{
        var slide: Slide? = null
        try{
            val file = File(rootPath, fileSlide(bookId, slideId))

            if(file.exists()){
                val slideJson = FileInputStream(file).bufferedReader().use { it.readText() }
                slide = Json.decodeFromString<Slide>(slideJson)
            }

        }catch (e: Exception){
            Log.d(TAG, ""+e.printStackTrace())
        }
        return slide
    }

    fun getImageFile(bookId: Long, imageName: String, isCover: Boolean = false): File?{
        val file = File(rootPath, if (isCover) {fileCover(bookId, imageName)} else {fileImage(bookId, imageName)})
        return if(imageName != "" && file.exists()) { file } else { null }
    }

    // Make Sample
    @OptIn(ExperimentalSerializationApi::class)
    fun createSampleBookFiles(){
        val sampleId = 12345L
        val config = Json.decodeFromString<Config>(SampleBook.getConfigSample(sampleId))
        val logic = Json.decodeFromString<Logic>(SampleBook.getLogicSample(sampleId))

        makeBookFolder(config.bookId)
        makeConfigFile(config)

        for(i in 1 .. 9){
            val slide = Json.decodeFromString<Slide>(SampleBook.getSlideSample(i * 1_00_00_00_00L))
            makeSlideFile(config.bookId, slide)
        }

        makeLogicFile(logic)
        val array = arrayOf("image_1.gif", "image_2.gif", "image_3.gif", "image_4.gif", "image_5.gif", "image_6.gif", "fish_cat.jpg", "game_end.jpg")
        for (imageName in array){
            val file = File(rootPath, fileImage(sampleId, imageName))
            val input: InputStream = context.resources.openRawResource(SampleBook.getSampleImageId(imageName))
            val out = FileOutputStream(file)
            val buff = ByteArray(1024)
            var read = 0
            try {
                while (input.read(buff).also { read = it } > 0) {
                    out.write(buff, 0, read)
                }
            } finally {
                input.close()
                out.close()
            }
        }

        val coverName = "image_1.gif"
        val coverImage = File(rootPath, fileCover(sampleId, coverName))
        val input: InputStream = context.resources.openRawResource(SampleBook.getSampleImageId(coverName))
        val out = FileOutputStream(coverImage)
        val buff = ByteArray(1024)
        var read = 0
        try {
            while (input.read(buff).also { read = it } > 0) {
                out.write(buff, 0, read)
            }
        } finally {
            input.close()
            out.close()
        }
    }
}