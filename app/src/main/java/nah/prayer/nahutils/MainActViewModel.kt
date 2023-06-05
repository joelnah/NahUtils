package nah.prayer.nahutils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nah.prayer.library.datastore.Npref

class MainActViewModel :ViewModel(){
    val pref = Npref()
    val stringKey = "string_key"
    val intKey = "int_key"
//    val text = pref.getPref("string_key", "")
//    val su = pref.getPref("int_key", 0)
    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()
    private val _su = MutableStateFlow(0)
    val su = _su.asStateFlow()

    init {
        viewModelScope.launch {
            pref.getPref(intKey, 0).collect {
                _su.value = it
            }


        }
        viewModelScope.launch {
            pref.getPref(stringKey, "nil").collect {
                _text.value = it
            }
        }
    }

//    fun getText(){
//        viewModelScope.launch {
//            pref.getPref("string_key", "").collect {
//                _text.value = it
//            }
//        }
//    }

}