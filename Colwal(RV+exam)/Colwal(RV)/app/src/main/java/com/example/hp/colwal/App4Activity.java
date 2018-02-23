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


public class App4Activity extends Activity {

    EditText e1,e2,e3,e4,e5,e6,e7;

    Button button;
    String name,usn,passwd,pno,email,branch,sem,s;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_main);
        button=(Button)findViewById(R.id.button4);
        e1=(EditText)findViewById(R.id.editText3);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //               Toast.makeText(getApplicationContext(), v.toString(),Toast.LENGTH_LONG).show();

                try {
                    register();
                }
                catch(Exception e){
                    //Toast.makeText(getApplicationContext(), v.toString(),Toast.LENGTH_LONG).show();
                }


            }
        });

    }


    private void register () throws InterruptedException {


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

                    name=e1.getText().toString();

                    usn=e2.getText().toString();

                    passwd=e3.getText().toString();

                    pno=e4.getText().toString();

                    email=e5.getText().toString();

                    branch=e6.getText().toString();

                    sem=e7.getText().toString();
                    String data  = "usn="+usn+"&name="+name+"&passwd="+passwd+"&branch="+branch+"&email="+email+"&sem="+sem+"&pno="+pno;
                    //;"+name+";"+passwd+";"+branch+";"+email+";"+pno+";"+sem;
                    String link="http://mwallete.esy.es/wallette/register.php";
                    //String data="data="+data1;

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
                    //return "";

                } catch (ClientProtocolException cpe) {
                    System.out.println("Exceptionrates caz of httpResponse :" + cpe);
                    s="e1";
                    cpe.printStackTrace();
                    Toast.makeText(getApplicationContext(), cpe.toString(),Toast.LENGTH_LONG).show();
                } catch (IOException ioe) {
                    System.out.println("Secondption generates caz of httpResponse :" + ioe);
                    s=ioe.toString();
                    ioe.printStackTrace();
                    Toast.makeText(getApplicationContext(), ioe.toString(),Toast.LENGTH_LONG).show();
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
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

                //Toast.makeText(getApplicationContext(), "HTTP GET is working...", Toast.LENGTH_LONG).show();
                App4Activity.this.donext();
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

        }

    }


    public void donext()
    {

        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();


    }



}

