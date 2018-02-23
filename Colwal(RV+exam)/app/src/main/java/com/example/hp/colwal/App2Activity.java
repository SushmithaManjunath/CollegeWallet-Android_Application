package com.example.hp.colwal;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;


public class App2Activity extends Activity {

    EditText e1,e2;

    Button button;
    CharSequence cs= "error";
    String name,usn,passwd,pno,email,branch,sem,s,v;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);
        button=(Button)findViewById(R.id.button);
        e1=(EditText)findViewById(R.id.editText);
        usn=e1.getText().toString();
        e2=(EditText)findViewById(R.id.editText2);
        passwd=e2.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //               Toast.makeText(getApplicationContext(), v.toString(),Toast.LENGTH_LONG).show();

                try {
                   // Toast.makeText(getApplicationContext(),"vvvv",Toast.LENGTH_LONG).show();
                    usn=e1.getText().toString();
                    //e2=(EditText)findViewById(R.id.editText2);
                    passwd=e2.getText().toString();
                   // Toast.makeText(getApplicationContext(),usn+" "+passwd,Toast.LENGTH_LONG).show();
                    login();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
                }


            }
        });

    }


    private void login () throws InterruptedException {


        // Connect with a server is a time consuming process.
        //Therefore we use AsyncTask to handle it
        // From the three generic types;
        //First type relate with the argument send in execute()
        //Second type relate with onProgressUpdate method which I haven't use in this code
        //Third type relate with the return type of the doInBackground method, which also the input type of the onPostExecute method
        class HttpGetAsyncTask extends AsyncTask<String, Void, String> {



            @Override
            protected String doInBackground(String... params) {

                // As you can see, doInBackground has taken an Array of Strings as the argument
                //We need to specifically get the givenUsername and givenPassword

                // Create an intermediate to connect with the Internet
                //HttpClient httpClient = new DefaultHttpClient();

                // Sending a GET request to the web page that we want
                // Because of we are sending a GET request, we have to pass the values through the URL
                //HttpGet httpGet = new HttpGet("http://jnnce.ac.in/cse/atd.php");


                try {
                    String link="http://mwallete.esy.es/wallette/login.php";
                    //Toast.makeText(getApplicationContext(), v.toString(),Toast.LENGTH_LONG).show();



                    //e1=(EditText)findViewById(R.id.editText);

                    String data  = "usn="+usn+"&passwd="+passwd;
                   // String data="usn=ased";
                    // "&branch="+branch+"&email="+email+"&pno="+pno+"&sem="+sem;
                    //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                    //txtLat=(TextView) findViewById(R.id.textview1);
                    //txtLat.setText(link);
                   URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);

                    OutputStreamWriter wr = new OutputStreamWriter
                            (conn.getOutputStream());
                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();


                } catch (ClientProtocolException cpe) {
                    System.out.println("Exceptionrates caz of httpResponse :" + cpe);
                    s="e1";
                    cpe.printStackTrace();
                  //  Toast.makeText(getApplicationContext(), cpe.toString(),Toast.LENGTH_LONG).show();
                } catch (IOException ioe) {
                    System.out.println("Secondption generates caz of httpResponse :" + ioe);
                    s=ioe.toString();
                    ioe.printStackTrace();
//                    Toast.makeText(getApplicationContext(), ioe.toString(),Toast.LENGTH_LONG).show();
                }
                catch(Exception e)
                {
                    //Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
                }

                return s;
            }

            // Argument comes for this method according to the return type of the doInBackground() and
            //it is the third generic type of the AsyncTask
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //if(result.length()>0)
                s=result;
                if( s.contains(cs))
                    return;

                //Toast.makeText(getApplicationContext(), "HTTP GET is working...", Toast.LENGTH_LONG).show();
               App2Activity.this.donext();
			/*	if(result.equals("working")){
					Toast.makeText(getApplicationContext(), "HTTP GET is working...", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "Invalid...", Toast.LENGTH_LONG).show();
				}	*/
            }
        }

        // Initialize the AsyncTask class
        try{
            HttpGetAsyncTask httpGetAsyncTask = new HttpGetAsyncTask();
            // Parameter we pass in the execute() method is relate to the first generic type of the AsyncTask
            // We are passing the connectWithHttpGet() method arguments to that
            httpGetAsyncTask.execute();
        }
        catch(Exception e)
        {
            //t1 = (TextView) findViewById(R.id.t1);
            //t1.setText(e.toString());
            Toast.makeText(getApplicationContext(),"ss", Toast.LENGTH_LONG).show();
        }

    }


    public void donext()
    {

        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("usn",usn);
        startActivity(intent);
    }



}

