package intentlearning.aniastudio.com.przepisy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//connected with activity view
public class OpisPrzepisu extends Activity {


    private ExpandableListView mListInDescription = null;
    private ExpandableListAdapter mListInDescriptionAdapter = null;
    List<String> listDataHeaders;
    public List<Integer> listHeadersIcons;
    HashMap<String,List<String>> listDataChild;
   // List<String> listaSkladnikowZPliku;
    String nameFromList;
    String produktyFromList;
    String titleFromList;
    List<String> skladniki = new ArrayList<String>();
    List<String> przygotowanie = new ArrayList<String>(); //NEW

   // String wholeText="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opis_przepisu);

        mListInDescription = (ExpandableListView) findViewById(R.id.lista_w_opisie);


        Intent intentRecivedFromListaPrzepsiow = this.getIntent();
        nameFromList = intentRecivedFromListaPrzepsiow.getStringExtra("identyfikator");
        produktyFromList = intentRecivedFromListaPrzepsiow.getStringExtra("produkty");



        prepareListData();
        mListInDescriptionAdapter = new AdapterOpisu(this, listHeadersIcons, listDataHeaders, listDataChild);

        mListInDescription.setAdapter(mListInDescriptionAdapter);

        ImageView imageView = (ImageView) findViewById(R.id.big_picture);



        //Comment: it assign appropriate image to display in "opisPrzepisu"
        int imageId= getResources().getIdentifier(nameFromList+"_duza","drawable",getPackageName());
        imageView.setImageResource(imageId);


        //comment: display name
        TextView textView = (TextView) findViewById(R.id.title);
        titleFromList = intentRecivedFromListaPrzepsiow.getStringExtra("nazwa");
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GreatVibes-Regular.otf");
        //textView.setTypeface(tf,Typeface.BOLD);
        textView.setTypeface(tf);
        textView.setText(titleFromList);
    }

    private void prepareListData() {
        listDataHeaders = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeaders.add("Skladniki");
        listDataHeaders.add("Przygotowanie");
        loadLineByLineFromFile(skladniki, "skladniki"); //niby ta metoda tylko uzupelnia skladniki
        loadLineByLineFromFile(przygotowanie, "przygotowanie");

        listDataChild.put(listDataHeaders.get(0), skladniki);
        listDataChild.put(listDataHeaders.get(1), przygotowanie);

        //comment: image
        listHeadersIcons = new ArrayList<Integer>();
        listHeadersIcons.add(R.drawable.skladniki);
        listHeadersIcons.add(R.drawable.przygotowanie);

    }

    private int setIconForGroup(String type){
        int imageId= getResources().getIdentifier(type,"drawable",getPackageName());
        return imageId;
    }


    private void loadLineByLineFromFile(List<String> listToFillWithText, String type){
        String value="";
        try {
            if(type == "skladniki" )
            {value = nameFromList+"_skladnik";}
            if(type == "przygotowanie")
            {value = nameFromList;}

            InputStream is = getResources().openRawResource(getResources().getIdentifier(value, "raw", getPackageName()));
            Log.e("FILE:", "Inputstream: "+is);
            InputStreamReader inputreader = new InputStreamReader(is);
            Log.e("FILE:", "Inputreader: "+inputreader);
            BufferedReader buffreader = new BufferedReader(inputreader);
            Log.e("FILE:", "buffreader: "+buffreader);

            String line;
            listToFillWithText.clear();
            // read every line of the file into the line-variable, on line at the time
            do {
                line = buffreader.readLine();
                //Convert polish charts
                Charset.forName("UTF-8").encode(line);


                    listToFillWithText.add(line);

              //  Log.e("FILE:", "line: "+line);
            } while (line != null);



            is.close();
            inputreader.close();
            buffreader.close();
          //  Log.e("FILE:", "END of read line: "+listToFillWithText);
          //  Toast.makeText(getBaseContext(), "END of read line: "+listToFillWithText, Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(getBaseContext(), "Failed to load ingradients1", Toast.LENGTH_LONG).show();
            Log.e("FILE:", "Failed to load ingradients");
        }
    }
}
