package nah.prayer.nahutils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nah.prayer.library.datastore.Npref

class MainActViewModel :ViewModel(){
    val stringKey = "string_key"
    val intKey = "int_key"
    val anyKey = "ANY"
    val text: StateFlow<String> = Npref.getPref(viewModelScope, stringKey, "nil")
    val su: StateFlow<Int> = Npref.getPref(viewModelScope, intKey, 0)
    val data: StateFlow<DataModel?> = Npref.getPref(viewModelScope, anyKey, DataModel::class.java)

}