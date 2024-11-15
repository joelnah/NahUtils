# NahUtils
[![](https://jitpack.io/v/joelnah/NahUtils.svg)](https://jitpack.io/#joelnah/NahUtils)

implementation 'com.github.joelnah:NahUtils:Tag'

Application

    NahUtils.init(this) // default tag is "Nlog"
    NahUtils.init(this, "NewTag")

Use

***Log***

	Nlog.d("String")
    Nlog.setTag("ChangeTag") // change tag

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