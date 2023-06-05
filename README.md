# NahUtils
[![](https://jitpack.io/v/joelnah/NahUtils.svg)](https://jitpack.io/#joelnah/NahUtils)

implementation 'com.github.joelnah:NahUtils:Tag'

Application

    NahUtils.init(this, "tah")

Use

	Log : Nlog.d("String")
    DataStore : 
        viewmodel

        private val _text = MutableStateFlow("")
        val text = _text.asStateFlow()

        init {
            viewModelScope.launch {
                pref.getPref(stringKey, "nil").collect {
                    _text.value = it
                }
            }
        }
        
        compose

        read
        val readText = viewModel.text.collectAsState()

        write
        scope.launch {
                viewModel.pref.putPref("stringKey", "text")
            }
        
        remove
        scope.launch {
                viewModel.pref.removePref("stringKey")
            }

        removeAll
        scope.launch {
                viewModel.pref.clearAllPreference()
            }
