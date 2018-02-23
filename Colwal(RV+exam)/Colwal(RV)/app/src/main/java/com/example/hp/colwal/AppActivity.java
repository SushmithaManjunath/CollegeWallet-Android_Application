package com.example.hp.colwal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AppActivity extends Activity {

    Button button1,button2;
    EditText e1,e2,e3,e4,e5,e6,e7;
    String name,usn,passwd,pno,email,branch,sem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, App2Activity.class);
                startActivity(intent);

            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

       /*         e1=(EditText)findViewById(R.id.editText3);
                name=e1.getText().toString();
                e2=(EditText)findViewById(R.id.editText4);
                usn=e2.getText().toString();
                e3=(EditText)findViewById(R.id.editText5);
                passwd=e3.getText().toString();
                e4=(EditText)findViewById(R.id.editText6);
                pno=e4.getText().toString();
                e5=(EditText)findViewById(R.id.editText7);
                email=e5.getText().toString();
                e6=(EditText)findViewById(R.id.editText8);
                branch=e6.getText().toString();
                e7=(EditText)findViewById(R.id.editText9);
                sem=e7.getText().toString();
*/
                Intent intent = new Intent(context, App4Activity.class);
                intent.putExtra("usn",usn);
                intent.putExtra("name",name);
                intent.putExtra("passwd",passwd);
                intent.putExtra("email",email);
                intent.putExtra("pno",pno);
                intent.putExtra("branch",branch);
                intent.putExtra("sem",sem);
                startActivity(intent);

            }

        });

    }

}
