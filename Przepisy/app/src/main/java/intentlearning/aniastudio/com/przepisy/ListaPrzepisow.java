package intentlearning.aniastudio.com.przepisy;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.ActionBarOverlayLayout;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import android.app.SearchManager;
import android.widget.SearchView.OnQueryTextListener;



//connected with activity view
public class ListaPrzepisow extends AppCompatActivity {

    private ListView mListView = null;
    private AdapterPrzepisow  mArrayAdapter = null;
   // private ArrayAdapter  mArrayAdapter = null;
    MenuItem searchItem;
    SearchView searchView;
  //  private android.support.v7.widget.SearchView inputSearch;






    Przepisy[] mPrzepisy = new Przepisy[]{
            new Przepisy("Szarlotka", "szarlotka", "szarlotka-prod"),
            new Przepisy("Beza Pawlowa", "beza", "beza-prod"),
            new Przepisy("Z budyniem truskawkami pod beza", "budyniowe", "budyniowe-prod"),
            new Przepisy("Sernik z musem czekoladowym", "mus", "mus-prod"),
            new Przepisy("Babka", "babka", "babka-prod"),
            new Przepisy("Mufiny", "mufiny", "mufiny-prod"),
            new Przepisy("Slonecznikowiec", "slonecznikowiec", "slonecznikowiec-prod")
    };

    Przepisy[] mPrzepisySearchResult = new Przepisy[]{};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //comment: change main activity header color
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#F03861"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        //comment: Change font of main action bar
        SpannableString s = new SpannableString("Przepisy");
        s.setSpan(new TypefaceSpan(this, "GreatVibes-Regular.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        //comment: other things
        setContentView(R.layout.activity_lista_przepisow);

        mPrzepisySearchResult=mPrzepisy;

        mListView = (ListView) findViewById(R.id.lista_przepisow);
        mArrayAdapter = new AdapterPrzepisow(getApplicationContext(),R.layout.lista_szablon,mPrzepisy,mPrzepisySearchResult);
        mArrayAdapter.sort(new Comparator<Przepisy>() {
            @Override
            public int compare(Przepisy lhs, Przepisy rhs) {
                return lhs.mTytul.compareTo(rhs.mTytul);
            }
        });





        if(mListView!=null){
            mListView.setAdapter(mArrayAdapter);
        }

        //comment: action when click on row
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentOpisuPrzepisow = new Intent(ListaPrzepisow.this,OpisPrzepisu.class);
                String clickedTitle = mPrzepisy[position].mTytul;//!
                String clickedIdentifier = mPrzepisy[position].mIdentyfikator;//!
                String clickedProdukty = mPrzepisy[position].mProdukty;//!
                intentOpisuPrzepisow.putExtra("identyfikator",clickedIdentifier);
                intentOpisuPrzepisow.putExtra("produkty",clickedProdukty);
                intentOpisuPrzepisow.putExtra("nazwa",clickedTitle);
                startActivity(intentOpisuPrzepisow);

            }
        });

        //comment: Search
//        inputSearch = (android.support.v7.widget.SearchView) findViewById(R.id.inputSearch);
//        inputSearch.setQueryHint("Znajdz przepis");

        //*** setOnQueryTextFocusChangeListener ***
     /*   inputSearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        inputSearch.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
                                               @Override
                                               public boolean onQueryTextSubmit(String query) {

                                                   return false;
                                               }

                                               @Override
                                               public boolean onQueryTextChange(String searchQuery) {

                                                   mArrayAdapter.getFilter().filter(searchQuery.toString().trim());
                                                   // listView.invalidate();
                                                   // return true;
                                                   return false;
                                               }
                                           });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Wpisz nazwę");


        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {



                mArrayAdapter.Filter(searchQuery.toString().trim());
                mListView.invalidate();
                return true;
            }

        });

        return true;
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, ListaPrzepisow.class)));
        searchView.setIconifiedByDefault(false);

        return true;


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        mArrayAdapter.getFilter().filter(newText);

        return true;
    }
    */

}






      /*  inputSearch.addTextChangedListener(new TextWatcher(){

            @Override
            public void onTextChanged(CharSequence cs, int start, int before,int count){
              mArrayAdapter.getFilter().filter(cs.toString());





            }

            @Override
            public void beforeTextChanged(CharSequence cs, int start ,int cunt, int after){

            }

            @Override
            public void afterTextChanged(Editable cs){

                Log.d("LOG L:", "*** Search value changed: " + cs.toString());
                mArrayAdapter.getFilter().filter(cs.toString());
            }
        });*/
    //}


 /* @Override
        public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_main, menu);
      inputSearch = menu.findItem(R.id.action_search);
      inputSearch = (SearchView) MenuItemCompat.getActionView((MenuItem) inputSearch);
      inputSearch.setQueryHint("Wpisz nazwę lub kolor dominujący");


      //*** setOnQueryTextFocusChangeListener ***
      inputSearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {

          }
      });

      inputSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {

              return false;
          }

          @Override
          public boolean onQueryTextChange(String searchQuery) {

              mArrayAdapter.getFilter().filter(searchQuery.toString().trim());
              // listView.invalidate();
              // return true;
              return false;
          }

      });

      return true;

  }*/

