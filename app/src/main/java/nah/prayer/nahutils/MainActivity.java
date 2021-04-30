package nah.prayer.nahutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import nah.prayer.library.Nlog;
import nah.prayer.library.Nsharedpreferences.Npref;
import nah.prayer.library.SingleClickListener;

import static nah.prayer.library.TimeCheckUtil.isWaiting;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String key = "key";

        Nlog.d(String.format("에또... %s 입니당~~~", Npref.get(key,false)));
        Npref.put(key,"엄...테스트?");
        Nlog.d(String.format("에또... %s 입니당~~~", Npref.get(key,false)));

        Nlog.d(1);
        Nlog.d(1L);
        Nlog.d(1f);
        Nlog.d(1d);

        ((TextView) findViewById(R.id.tv)).setText(Npref.get(key,""));
        ((TextView) findViewById(R.id.tv)).setOnClickListener(new SingleClickListener(){
            @Override
            public void onSingleClick(View v) {
                Nlog.d("클릭");
                Toast.makeText(MainActivity.this, "클릭", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(!isWaiting()){
            Toast.makeText(this, "종료", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "백백", Toast.LENGTH_SHORT).show();
        }
    }
}
