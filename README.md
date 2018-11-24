# NahUtils

implementation 'com.github.joelnah:NahUtils:Tag'


        //Nlog 생성
        //new Nlog(this);
        //Nlog 생성, tag 변경
        new Nlog(this, "nah");



        Npref.init(this).build();
        Npref.put(key,T);
        
        
        
        Npref.init(context)
        .setEncryption(new NoEncryption())
        .setLogInterceptor(new MyLogInterceptor())
        .setConverter(new MyConverter())
        .setParser(new MyParser())
        .setStorage(new MyStorage())
        .build();
        
        
        
        BackPressDestroy backKey = new BackPressDestroy();
        backKey.onBackPressed(this, "한번더 누르면 끝~~~");
