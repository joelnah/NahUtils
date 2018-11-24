package nah.prayer.nahutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nah.prayer.library.DoubleBack.BackPressDestroy;
import nah.prayer.library.Nlog.Nlog;
import nah.prayer.library.Npref.Npref;

public class MainActivity extends AppCompatActivity {

    BackPressDestroy backKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backKey = new BackPressDestroy();

        String key = "key";

        Nlog.d(String.format("에또... %s 입니당~~~", Npref.get(key,false)));
        Npref.put(key,true);
        Nlog.d(String.format("에또... %s 입니당~~~", Npref.get(key,false)));

    }

    @Override
    public void onBackPressed() {
        backKey.onBackPressed(this, "한번더 누르면 끝~~~");
    }
}
