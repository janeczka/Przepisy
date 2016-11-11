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
    Przepisy mPrzepisy[]=null;
    Przepisy mPrzepisyWynikSearcha[]=null;
    Przepisy mTemporaryPrzepisy[]=null;
    static List<Przepisy> data;
    static ArrayList<Przepisy> arrayList;


    public AdapterPrzepisow(Context context, int resourceId, Przepisy[] przepisy, Przepisy[] przpisyWynikSearcha) {
        super(context, resourceId, przepisy);
        this.mContext = context;
        this.mResourceId = resourceId;
        this.mPrzepisy = przepisy;
        this.mPrzepisyWynikSearcha = przpisyWynikSearcha;
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
            row = inflater.inflate(mResourceId, parent, false);
            placeHolder = new PlaceHolder();

            placeHolder.tytulPrzepisu = (TextView) row.findViewById(R.id.row_text);
            placeHolder.miniaturkaPrzepisu = (ImageView) row.findViewById(R.id.row_icon); //Obrazek

            row.setTag(placeHolder);


        } else {
            placeHolder = (PlaceHolder) row.getTag();
        }


        Przepisy przepis = mPrzepisy[position];




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


    public void Filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mPrzepisyWynikSearcha=null;
        if (charText.length() == 0) {
            mPrzepisyWynikSearcha=mPrzepisy;
        } else {
            for (Przepisy przepisy : mPrzepisy) {
                if (charText.length() != 0 && przepisy.getmTytul().toLowerCase(Locale.getDefault()).contains(charText)) {
                   // data.add(przepisy);
                    mPrzepisyWynikSearcha= new Przepisy[]{przepisy};
                }
            }}

        notifyDataSetChanged();

    }




}
