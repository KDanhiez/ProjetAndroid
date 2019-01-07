package com.example.kdanhiez.hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("CV","onStart vient de s'exécuter");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("CV","onResume vient de s'exécuter");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("CV","onPause vient de s'exécuter");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("CV","onStop vient de s'exécuter");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("CV","onRestart vient de s'exécuter");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("CV","onDestroy vient de s'exécuter");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.txtHello);
        tv.setText("Hello from the code !");
        System.out.println("Message de message");
    }
}
