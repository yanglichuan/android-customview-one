
package com.ylc.activity;

import com.example.bbbbe.R;
import com.example.bbbbe.R.id;
import com.example.bbbbe.R.layout;
import com.ylc.view.MyHealthDialView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ActivityTwo extends Activity {

    
    public MyHealthDialView dialView;
    public TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        dialView  = (MyHealthDialView) this.findViewById(R.id.dialview);
        textview = (TextView) this.findViewById(R.id.textview);
        
        
        float ft = 0.87f;
        dialView.setDegree(ft);
        textview.setText(((int)(ft*100f))+"%");
    }
}
