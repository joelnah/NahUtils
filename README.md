# NahUtils
[![](https://jitpack.io/v/joelnah/NahUtils.svg)](https://jitpack.io/#joelnah/NahUtils)

implementation 'com.github.joelnah:NahUtils:Tag'

Application

    NahUtils.init(this)

Use

	Log : Nlog.d("String")
        Nlog.setTag("NewTag") // default tag is "nah"


    DataStore : 

    viewmodel
    val text: StateFlow<String> = Npref.getPref(viewModelScope, stringKey, "nil")
    val su: StateFlow<Int> = Npref.getPref(viewModelScope, intKey, 0)
    val data: StateFlow<DataModel?> = Npref.getPref(viewModelScope, anyKey, DataModel::class.java)
        
    compose
    ### read
    val scope = rememberCoroutineScope()
    val su = viewModel.su.collectAsState()

    ### write
    Npref.putPref(scope, viewModel.intKey, Random().nextInt(100))

    ### remove
    Npref.removePref(scope, viewModel.intKey)

    ### removeAll
    Npref.clearAllPreference(scope)

