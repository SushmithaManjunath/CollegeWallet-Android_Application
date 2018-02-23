package com.example.hp.colwal;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.apache.http.client.ClientProtocolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import android.app.AlertDialog;
import android.text.InputType;
import android.content.DialogInterface;


public class RvActivity extends ActionBarActivity {
    LinearLayout linearMain;
    CheckBox checkBox[];
    String usn;
    Button b1;
    int j=0;
    String scode,s,sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        linearMain = (LinearLayout) findViewById(R.id.linearMain);
        Intent i = getIntent();
        usn = i.getStringExtra("usn");
        try {
            getsubjects();
        } catch (Exception e) {
        }
    }

    View.OnClickListener getOnClickDoSomething(final Button button) {
        return new View.OnClickListener()
        { public void onClick(View v) {

                //	tv.setText(button.getText());
                //sd=usn;


                   /* for(int k=0;k<j;k++)
                    {
                        if(checkBox[k].isChecked())
                        {
                            sd=sd+"#"+checkBox[k].getText().toString();
                        }
                    }
                    Toast.makeText(getApplicationContext(), sd, Toast.LENGTH_LONG).show();*/
                try {
                    recieveOTP();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };



    }

    private void recieveOTP() throws InterruptedException {


        // Connect with a server is a time consuming process.
        //Therefore we use AsyncTask to handle it
        // From the three generic types;
        //First type relate with the argument send in execute()
        //Second type relate with onProgressUpdate method which I haven't use in this code
        //Third type relate with the return type of the doInBackground method, which also the input type of the onPostExecute method
        class HttpGetAsyncTask extends AsyncTask<String, Void, String>{



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
                    // execute(); executes a request using the default context.
                    // Then we assign the execution result to HttpResponse
					/*HttpResponse httpResponse = httpClient.execute(httpGet);

					// getContent() ; creates a new InputStream object of the entity.
					// Now we need a readable source to read the byte stream that comes as the httpResponse
					InputStream inputStream = httpResponse.getEntity().getContent();

					// We have a byte stream. Next step is to convert it to a Character stream
					InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

					// Then we have to wraps the existing reader (InputStreamReader) and buffer the input
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

					// InputStreamReader contains a buffer of bytes read from the source stream and converts these into characters as needed.
					//The buffer size is 8K
					//Therefore we need a mechanism to append the separately coming chunks in to one String element
					// We have to use a class that can handle modifiable sequence of characters for use in creating String
					StringBuilder stringBuilder = new StringBuilder();

					String bufferedStrChunk = null;

					// There may be so many buffered chunks. We have to go through each and every chunk of characters
					//and assign a each chunk to bufferedStrChunk String variable
					//and append that value one by one to the stringBuilder
					while((bufferedStrChunk = bufferedReader.readLine()) != null){
						stringBuilder.append(bufferedStrChunk);
					}

					// Now we have the whole response as a String value.
					//We return that value then the onPostExecute() can handle the content
					System.out.println("Returninge of doInBackground :" + stringBuilder.toString());
                     s=stringBuilder.toString();

					//s=
					//tv.setText(stringBuilder.toString());

					return stringBuilder.toString();*/
                    String link="http://mwallete.esy.es/wallette/recvotp.php";
                    String data  = "";
                    URL url = new URL(link);
                    sd=usn;
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter
                            (conn.getOutputStream());
                    wr.write("data="+sd );
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
                } catch (IOException ioe) {
                    System.out.println("Secondption generates caz of httpResponse :" + ioe);
                    s=ioe.toString();
                    ioe.printStackTrace();
                }

                return s;
            }

            // Argument comes for this method according to the return type of the doInBackground() and
            //it is the third generic type of the AsyncTask
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                s=result;

                RvActivity.this.rotp();
			/*	if(result.equals("working")){
					Toast.makeText(getApplicationContext(), "HTTP GET is working...", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "Invalid...", Toast.LENGTH_LONG).show();
				}	*/
            }
        }

        // Initialize the AsyncTask class
        HttpGetAsyncTask httpGetAsyncTask = new HttpGetAsyncTask();
        // Parameter we pass in the execute() method is relate to the first generic type of the AsyncTask
        // We are passing the connectWithHttpGet() method arguments to that
        httpGetAsyncTask.execute();

    }


    public  void rotp()
    {
        //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Enter OTP");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String  m_Text = input.getText().toString();
                if(m_Text.equals(s)) {
                    Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_LONG).show();
                    sd=usn;
                    for(int k=0;k<j;k++)
                    {
                        if(checkBox[k].isChecked())
                        {
                            sd=sd+"#"+checkBox[k].getText().toString();
                        }
                    }
                    Toast.makeText(getApplicationContext(), sd, Toast.LENGTH_LONG).show();
                    try {
                        sendAttendance();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "incorrect", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private void sendAttendance() throws InterruptedException {


        // Connect with a server is a time consuming process.
        //Therefore we use AsyncTask to handle it
        // From the three generic types;
        //First type relate with the argument send in execute()
        //Second type relate with onProgressUpdate method which I haven't use in this code
        //Third type relate with the return type of the doInBackground method, which also the input type of the onPostExecute method
        class HttpGetAsyncTask extends AsyncTask<String, Void, String>{



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
                    // execute(); executes a request using the default context.
                    // Then we assign the execution result to HttpResponse
					/*HttpResponse httpResponse = httpClient.execute(httpGet);

					// getContent() ; creates a new InputStream object of the entity.
					// Now we need a readable source to read the byte stream that comes as the httpResponse
					InputStream inputStream = httpResponse.getEntity().getContent();

					// We have a byte stream. Next step is to convert it to a Character stream
					InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

					// Then we have to wraps the existing reader (InputStreamReader) and buffer the input
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

					// InputStreamReader contains a buffer of bytes read from the source stream and converts these into characters as needed.
					//The buffer size is 8K
					//Therefore we need a mechanism to append the separately coming chunks in to one String element
					// We have to use a class that can handle modifiable sequence of characters for use in creating String
					StringBuilder stringBuilder = new StringBuilder();

					String bufferedStrChunk = null;

					// There may be so many buffered chunks. We have to go through each and every chunk of characters
					//and assign a each chunk to bufferedStrChunk String variable
					//and append that value one by one to the stringBuilder
					while((bufferedStrChunk = bufferedReader.readLine()) != null){
						stringBuilder.append(bufferedStrChunk);
					}

					// Now we have the whole response as a String value.
					//We return that value then the onPostExecute() can handle the content
					System.out.println("Returninge of doInBackground :" + stringBuilder.toString());
                     s=stringBuilder.toString();

					//s=
					//tv.setText(stringBuilder.toString());

					return stringBuilder.toString();*/
                    String link="http://mwallete.esy.es/wallette/payfeesrv.php";
                    String data  = "";
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter
                            (conn.getOutputStream());
                    wr.write("data="+sd );
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
                } catch (IOException ioe) {
                    System.out.println("Secondption generates caz of httpResponse :" + ioe);
                    s=ioe.toString();
                    ioe.printStackTrace();
                }

                return s;
            }

            // Argument comes for this method according to the return type of the doInBackground() and
            //it is the third generic type of the AsyncTask
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                s=result;

                RvActivity.this.dolast();
			/*	if(result.equals("working")){
					Toast.makeText(getApplicationContext(), "HTTP GET is working...", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "Invalid...", Toast.LENGTH_LONG).show();
				}	*/
            }
        }

        // Initialize the AsyncTask class
        HttpGetAsyncTask httpGetAsyncTask = new HttpGetAsyncTask();
        // Parameter we pass in the execute() method is relate to the first generic type of the AsyncTask
        // We are passing the connectWithHttpGet() method arguments to that
        httpGetAsyncTask.execute();

    }

    public void dolast()
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), RvActivity.class);
        intent.putExtra("usn",usn);
        startActivity(intent);
        //finish();

    }


    private void getsubjects () throws InterruptedException {


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
                    String link="http://mwallete.esy.es/wallette/getrsub.php";
                    //Toast.makeText(getApplicationContext(), v.toString(),Toast.LENGTH_LONG).show();



                    //e1=(EditText)findViewById(R.id.editText);

                    String data  = "usn="+usn;
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

                //Toast.makeText(getApplicationContext(), "HTTP GET is working...", Toast.LENGTH_LONG).show();
                RvActivity.this.donext();
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
            Toast.makeText(getApplicationContext(), "ss", Toast.LENGTH_LONG).show();
        }

    }


    public void donext()
    {

        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
        LinkedHashMap<String, String> alphabet = new LinkedHashMap<String, String>();

        int x=1;
        String ss="";
        j=0;
        boolean chkd[]=new boolean[200];
        for (String retval: s.split("#")){

            scode=retval;
            chkd[j]=true;
            alphabet.put(String.valueOf(j),scode);
            j++;


        }
        if(checkBox==null)
            checkBox=new CheckBox[j];
        Set<?> set = alphabet.entrySet();
        // Get an iterator
        Iterator<?> i = set.iterator();
        // Display elements
        int t=0;
        while (i.hasNext())
        {
            @SuppressWarnings("rawtypes")
            Map.Entry me = (Map.Entry) i.next();
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
            if(checkBox[t]==null)
            {
                checkBox[t] = new CheckBox(this);
                linearMain.addView(checkBox[t]);
            }

            checkBox[t].setId(Integer.parseInt(me.getKey().toString()));
            checkBox[t].setText(me.getValue().toString());
            checkBox[t].setChecked(chkd[t]);
            //checkBox.setOnClickListener(getOnClickDoSomething(checkBox));


            t++;
        }
        if(b1==null)
        {
            b1 = new Button(this);
            b1.setText("Submit");
            linearMain.addView(b1);
            b1.setOnClickListener(getOnClickDoSomething(b1));
        }


        /*Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("usn",usn)
        startActivity(intent);*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exam_fees, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
