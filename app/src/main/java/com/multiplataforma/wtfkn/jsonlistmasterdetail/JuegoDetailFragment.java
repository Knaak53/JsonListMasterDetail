package com.multiplataforma.wtfkn.jsonlistmasterdetail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.multiplataforma.wtfkn.jsonlistmasterdetail.dummy.DummyContent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A fragment representing a single Juego detail screen.
 * This fragment is either contained in a {@link JuegoListActivity}
 * in two-pane mode (on tablets) or a {@link JuegoDetailActivity}
 * on handsets.
 */
public class JuegoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ProgressDialog pDialog;
    Bitmap bmp;
    private DummyContent.DummyItem mItem;
    private Context context;
    private View vista;
    CollapsingToolbarLayout appBarLayout;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JuegoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if (getArguments().containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

                Activity activity = this.getActivity();
                appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                    appBarLayout.setTitle(mItem.content);
                }
            }
    }
    public void setContext(Context context){
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.juego_detail, container, false);
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.textView)).setText(mItem.details);

        }
        vista=rootView;
        new CargarImagen().execute(mItem);
        return rootView;
    }
    public class CargarImagen extends AsyncTask<DummyContent.DummyItem, Void, Void> {
        @Override
        protected Void doInBackground(DummyContent.DummyItem... params) {
            URL url = null;
            try {
                url = new URL(mItem.urlimg);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Espere un momento...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ImageView img = ((ImageView) vista.findViewById(R.id.imageView));
            img.setImageBitmap(bmp);
            img.setAdjustViewBounds(true);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
        }

    }
}
