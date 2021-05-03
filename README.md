# NahUtils
[![](https://jitpack.io/v/joelnah/NahUtils.svg)](https://jitpack.io/#joelnah/NahUtils)

implementation 'com.github.joelnah:NahUtils:Tag'

Application

        NahUtil.set(this, "nah");

Use

	Nlog.d("String");
	Nlog.d(1);
	Nlog.d(1L);
	Nlog.d(1f);
	Nlog.d(1d);

	Npref.put(key,T);
	Npref.get(key);

back key

	//isWaiting() or isWaiting(waitingSec)
        @Override
    	public void onBackPressed() 
	{
        if(!isWaiting()){Toast.makeText(this, "종료", Toast.LENGTH_SHORT).show();}
	else{Toast.makeText(this, "백백", Toast.LENGTH_SHORT).show();}
    	}

single click

	//new SingleClickListener() or new SingleClickListener(waitingSec)
	((TextView) findViewById(R.id.tv)).setOnClickListener(new SingleClickListener(){
        @Override
        public void onSingleClick(View v) {
              Nlog.d("클릭");
              Toast.makeText(MainActivity.this, "클릭", Toast.LENGTH_SHORT).show();
        }
        });
