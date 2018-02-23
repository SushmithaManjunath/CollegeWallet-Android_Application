package com.example.hp.colwal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import 	android.graphics.BitmapFactory;
import android.view.Display;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
import android.provider.MediaStore;
import android.widget.Button;
import android.graphics.Typeface;

import org.apache.http.client.ClientProtocolException;


public class RvRecipt extends ActionBarActivity {

    Button b1;
    Bitmap bitmap;
    String s,usn,sem4,str4,sb,date,row0,data1,branch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_recipt);
        b1=(Button) findViewById(R.id.exmsave);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "jnnce" ,"exam");
                Toast.makeText(getApplicationContext(),"Recipt saved successfully",Toast.LENGTH_LONG).show();
            }
        });
       /* Display currentDisplay = getWindowManager().getDefaultDisplay();
        float dw = currentDisplay.getWidth();
        float dh = currentDisplay.getHeight();
        ImageView image = (ImageView) findViewById(R.id.imageView1);
       bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.recipt);
       // bitmap.setWidth((int)dw);
       // bitmap.setHeight((int)dh);
        bitmap = Bitmap.createScaledBitmap(bitmap,(int)dw, (int)dh, false);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(28);
        canvas.drawText("Chetan K R", 170, 333, paint);
        image.setImageBitmap(bitmap);
        image.buildDrawingCache();*/
        Intent i = getIntent();
        usn = i.getStringExtra("usn");
        try {

            //sem();
            getsem();
            //convert();
            //getsem();
        } catch (Exception e) {
        }

       /* Bitmap bm=image.getDrawingCache();
        OutputStream fOut = null;
        Uri outputFileUri;
        try {
            File root = new File(Environment.getExternalStorageDirectory()
                    + File.separator);
            root.mkdirs();
            File sdImageMainDirectory = new File(root, "r.png");
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
            fOut = new FileOutputStream(sdImageMainDirectory);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(),
                    Toast.LENGTH_SHORT).show();

        }

        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
        }*/

    }

    private void getsem () throws InterruptedException {


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
                    String link="http://mwallete.esy.es/wallette/getasubjects.php";
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
                RvRecipt.this.donext();
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


    private void sem () throws InterruptedException {


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
                    String link="http://mwallete.esy.es/wallette/sem.php";
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

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //if(result.length()>0)
                s=result;

                //Toast.makeText(getApplicationContext(), "HTTP GET is working...", Toast.LENGTH_LONG).show();
                RvRecipt.this.donext();
			/*	if(result.equals("working")){
					Toast.makeText(getApplicationContext(), "HTTP GET is working...", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "Invalid...", Toast.LENGTH_LONG).show();
				}	*/
            }

            // Argument comes for this method according to the return type of the doInBackground() and
            //it is the third generic type of the AsyncTask

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


    public void donext() {


        int cnt = 0;
        String name = "";
        String sem = "";
        String isem[] = new String[8];
        String scode[] = new String[8];
        String amt[] = new String[8];
        // String totalamt[]=new String[8];
        String tamt = "";
        String totalamt="";
        int sum=0;
        //  void num2word();
        int semcnt = 0, start = 0, amtcnt = 0, astart = 0, bstart = 0, scodecnt = 0;
        //String sem1="";
        //String scode="";
        // String date="";
        //   String usn="";

        for (String retval : s.split("#")) {
            if (cnt == 0)
                name = retval;

            if (cnt == 1)
                sem = retval;

            if (cnt == 2)
                branch = retval;
            if (retval.equals("SemStart")) {
                start = 1;
                continue;
            }
            if (retval.equals("SemEnd")) {
                start = 0;
                continue;
            }
            if (start == 1) {
                isem[semcnt++] = retval;
                //scode[scodecnt++]=retval;
            }


            //usn=retval;
            if (retval.equals("AmtStart")) {
                astart = 1;
                continue;
            }
            if (retval.equals("AmtEnd")) {
                astart = 0;
                continue;
            }
            if (astart == 1)
                amt[amtcnt++] = retval;


            if (retval.equals("subStart")) {
                bstart = 1;
                continue;
            }
            if (retval.equals("subEnd")) {
                bstart = 0;
                continue;
            }
            if (bstart == 1)
                scode[scodecnt++] = retval;




            cnt++;

        }


        Display currentDisplay = getWindowManager().getDefaultDisplay();
        float dw = currentDisplay.getWidth();
        float dh = currentDisplay.getHeight();
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.recipt);
        // bitmap.setWidth((int)dw);
        // bitmap.setHeight((int)dh);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) dw, (int) dh, false);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setTextSize(20);
        canvas.drawText(name, 150, 253, paint);
        canvas.drawText(sem, 170, 306, paint);
        for(int i=0;i<amtcnt;i++)
            sum =sum+Integer.parseInt(amt[i]);
        canvas.drawText(Integer.toString(sum), 200, 806, paint);
        //convert(Integer.parseInt(totalamt));
        //canvas.drawText(scode, 170, 306, paint);

        paint.setTextSize(15);
        canvas.drawText(usn, 298, 306, paint);
        paint.setTextSize(20);
        canvas.drawText(branch, 120, 360, paint);
        int semt = 638;
        int seml = 150;
        for (int j = 0; j < semcnt; j++) {
            canvas.drawText(isem[j], seml, semt, paint);
            semt += 42;

        }
        int asemt = 638;
        int aseml = 280;

        for (int j = 0; j < semcnt; j++) {
            canvas.drawText(amt[j], aseml, asemt, paint);
            asemt += 42;

        }
        int scodet = 48;
        int scodel = 490;
        for (int j = 0; j < scodecnt; j++) {

            canvas.drawText(scode[j], scodel, scodet, paint);
            scodet += 42;
//canvas.drawText(scode, 170, 306, paint);

            //canvas.drawText(scode, 170, 306, paint);

            //paint.setTextSize(15);
        }
        //  canvas.drawText(sem1, 270, 273, paint);

        //canvas.drawText(scode, 190, 233, paint);
        image.setImageBitmap(bitmap);
        image.buildDrawingCache();


    }


   /* public class numerals2words
    {
        public void main(String args[]) throws IOException
        {
            BufferedReader br = new BufferedReader(new
                    InputStreamReader(System.in));
            numerals2words call = new numerals2words();
           // System.out.print("Enter a number : ");
           // int n = Integer.parseInt(br.readLine());
            call.convert(totalamt);
        }*/

/*        public void convert(int totalamt)
        {
            int c;
            if(totalamt!=0)
            {
                c = totalamt%10;
                convert(totalamt/10);
                num2words(c);
            }
        }
        public void num2words(int n)
        {
            String words[] =
                    {"ZERO","ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE "};
                            System.out.print(words[n] +" ");
            }
        }*/






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exam_recipt, menu);
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
