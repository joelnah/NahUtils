# NahUtils

implementation 'com.github.joelnah:NahUtils:Tag'

Application

    NahUtils.init(this)

Use

***Log***

	Log : Nlog.d("String")
        Nlog.setTag("NewTag") // default tag is "nah"

***DataStore***
        
    ### read
    val text = rememberDataStore(stringKey, "nil")
    val su = rememberDataStore(intKey, 0)
    val data = rememberDataStore(anyKey, DataModel())

    Nstore.putDS(scope, "keyValue", "default")
    Nstore.removeDS(scope, viewModel.intKey)
    Nstore.clearData(scope)

***SharedPreferences***

    Npref.getData(intKey, -1)
    Npref.putData(intKey, Random().nextInt(100))
    Npref.removeData(intKey)
    Npref.clearData()

***Net***

    NetworkUtil.getWhatKindOfNetwork(context)