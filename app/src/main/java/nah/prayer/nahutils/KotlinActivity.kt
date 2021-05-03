package nah.prayer.nahutils

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import nah.prayer.library.Nlog
import nah.prayer.library.ktlib.setOnSingleClickListener

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        (findViewById<TextView>(R.id.btnTest)).setOnSingleClickListener {
            Nlog.d("클릭")
            Toast.makeText(this, "클릭", Toast.LENGTH_SHORT).show()
        }

    }
}