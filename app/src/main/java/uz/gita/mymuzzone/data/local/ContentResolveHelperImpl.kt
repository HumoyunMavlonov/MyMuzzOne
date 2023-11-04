package uz.gita.mymuzzone.data.local

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.mymuzzone.data.local.model.AudioModel
import javax.inject.Inject

class ContentResolveHelperImpl @Inject constructor(
    @ApplicationContext val context: Context
) : ContentResolverHelper{

    private var mCursor:Cursor? = null

    private val projection: Array<String> = arrayOf(
        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
        MediaStore.Audio.AudioColumns._ID,
        MediaStore.Audio.AudioColumns.ARTIST,
        MediaStore.Audio.AudioColumns.DATA,
        MediaStore.Audio.AudioColumns.DURATION,
        MediaStore.Audio.AudioColumns.TITLE
    )
    private val selectionClause:String? = "${MediaStore.Audio.AudioColumns.IS_MUSIC} = ?"
    private val selectionArg = arrayOf("1")
    private val sortOrder = "${MediaStore.Audio.AudioColumns.DISPLAY_NAME} ASC"


    override suspend fun getAudioData(): List<AudioModel> {
        return getCursorData()
    }

    private fun getCursorData():MutableList<AudioModel>{
        val audioList = mutableListOf<AudioModel>()

        mCursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selectionClause,
            selectionArg,
            sortOrder
        )

        mCursor?.use { cursor->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)
            val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)
            val artistNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)


            cursor.apply {
                if (count == 0){

                }else{
                    while (cursor.moveToNext()){
                        val displayName = getString(displayNameColumn)
                        val id = getLong(idColumn)
                        val data = getString(dataColumn)
                        val artistName = getString(artistNameColumn)
                        val duration = getInt(durationColumn)
                        val title = getString(titleColumn)
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,id
                        )
                        audioList += AudioModel (uri, displayName, id,artistName,data, duration,title)
                    }
                }
            }
        }
        return audioList
    }
}