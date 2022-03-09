package com.downloader.utils

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import java.io.File
import java.util.*

class PathUtils {
    companion object{
            fun getRootDirPath(context: Context?) : String {
                return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                    var file = ContextCompat.getExternalFilesDirs(context!!.applicationContext,
                        null)[0];
                    file.absolutePath;
                } else {
                    context!!.applicationContext.filesDir.absolutePath;
                }
            }

              fun getProgressDisplayLine(currentBytes : Long,totalBytes : Long) : String {
                return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
            }

               private fun getBytesToMBString(bytes : Long) : String{
                return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
            }
        }
    }
