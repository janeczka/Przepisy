package intentlearning.aniastudio.com.przepisy;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ania on 2016-05-17.
 */
public class AdapterPrzepisow  extends ArrayAdapter<Przepisy>{
    Context mContext;
    int mResourceId;
    public int mValue;
    Przepisy mPrzepisy[]=null; //to jest to samo co -> static List<Przepisy> mData;
    Przepisy mPrzepisyWynikSearcha[]=null; //to chyba mozna uzunac
    Przepisy mTemporaryPrzepisy[]=null;
   // static List<Przepisy> data;
 //   static ArrayList<Przepisy> arrayList;


    public AdapterPrzepisow(Context context, int resourceId, Przepisy[] przepisy) {
        super(context, resourceId, przepisy);
        this.mContext = context;
        this.mResourceId = resourceId;
        this.mPrzepisy = przepisy; // to jest to samo co -> this.mData = data;
        this.mPrzepisyWynikSearcha = mPrzepisy;
    }

    @Override
    public Przepisy getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        View row = convertView;
        PlaceHolder placeHolder = null;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mResourceId, parent, false); // identyko ->  row = inflater.inflate(R.layout.lista_szablon, null);
            placeHolder = new PlaceHolder();

            placeHolder.tytulPrzepisu = (TextView) row.findViewById(R.id.row_text);
            placeHolder.miniaturkaPrzepisu = (ImageView) row.findViewById(R.id.row_icon); //Obrazek

            row.setTag(placeHolder);


        } else {
            placeHolder = (PlaceHolder) row.getTag();
        }


        Przepisy przepis = mPrzepisy[position]; // identico -> Przepisy przepis = mData.get(position);




        placeHolder.tytulPrzepisu.setText(przepis.mTytul);

        //Comment: Assign apropriate image by string name
        int id = mContext.getResources().getIdentifier(przepis.mIdentyfikator, "drawable", mContext.getPackageName());
        placeHolder.miniaturkaPrzepisu.setImageResource(id);
        return row;
    }

    private class PlaceHolder {
        TextView tytulPrzepisu;
        ImageView miniaturkaPrzepisu;
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mValue = 0;
        mPrzepisy=null;
        //mPrzepisyWynikSearcha=null;
        if (charText.length() == 0) {
            mPrzepisy = mPrzepisyWynikSearcha;
           // mPrzepisyWynikSearcha=mPrzepisy;
        } else {
            //for (Przepisy przepisyX : mPrzepisyWynikSearcha) {
            for(int i=0; i<mPrzepisyWynikSearcha.length; i++){
                //if (charText.length() != 0 && przepisyX.getmTytul().toLowerCase(Locale.getDefault()).contains(charText)) {
                if (charText.length() != 0 && mPrzepisyWynikSearcha[i].getmTytul().toLowerCase(Locale.getDefault()).contains(charText)){
                   // data.add(przepisy);
                   // mPrzepisyWynikSearcha= new Przepisy[]{przepisy};
                    //mPrzepisy[i] = przepisy;

                        mPrzepisy[mValue] = mPrzepisyWynikSearcha[i];

                    mValue=mValue+1;
                    //mPrzepisy = new Przepisy[]{new Przepisy (przepisyX.mTytul, przepisyX.mIdentyfikator, przepisyX.mProdukty)};
                }
            }}

        notifyDataSetChanged();

    }




}
