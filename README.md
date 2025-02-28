# NahUtils
[![](https://jitpack.io/v/joelnah/NahUtils.svg)](https://jitpack.io/#joelnah/NahUtils)


## 개요

### Setting

```kotlin
//settings.gradle
maven(url = "https://jitpack.io")
//build.gradle
implementation ("com.github.joelnah:NahUtils:Tag")
```

## 사용법

### Application
```kotlin
NahUtils.init(this) // default tag is "Nlog"
NahUtils.init(this, "NewTag")
```

***Log***
```kotlin
Nlog.d("String")
Nlog.setTag("ChangeTag") // 전체 tag 변경
Nlog.d("newTag","String") // 해당 로그 tag 변경
Nlog.d("String", true) // method path 출력

Nlog.a("String") // build type 상관 없이 로그 출력
```

***DataStore***
 ```kotlin   
val text = rememberDataStore(stringKey, "nil")
val su = rememberDataStore(intKey, 0)
val data = rememberDataStore(anyKey, DataModel())

Nstore.putDS(scope, "keyValue", "default")
Nstore.removeDS(scope, viewModel.intKey)
Nstore.clearData(scope)
```
***SharedPreferences***
```kotlin
Npref.getData(intKey, -1)
Npref.putData(intKey, Random().nextInt(100))
Npref.removeData(intKey)
Npref.clearData()
```
***Net***
```kotlin
NetworkUtil.getWhatKindOfNetwork(context)
```
