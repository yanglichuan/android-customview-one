
package com.ylc.activity;

import com.example.bbbbe.R;
import com.example.bbbbe.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void bt_function1(View view){
        startActivity(new Intent(this, ActivityOne.class));
    }
    public void bt_function2(View view){
        startActivity(new Intent(this, ActivityTwo.class));
    }
    public void bt_function3(View view){
        startActivity(new Intent(this, ActivityThree.class));
    }
    public void bt_function4(View view){
        startActivity(new Intent(this, ActivityFour.class));
    }
    public void bt_function5(View view){
        startActivity(new Intent(this, ActivityFive.class));
    }
}
