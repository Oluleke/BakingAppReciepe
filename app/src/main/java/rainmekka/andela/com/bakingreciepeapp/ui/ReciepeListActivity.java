package rainmekka.andela.com.bakingreciepeapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import rainmekka.andela.com.bakingreciepeapp.R;
import rainmekka.andela.com.bakingreciepeapp.data.Reciepe;
import rainmekka.andela.com.bakingreciepeapp.rest.RequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Reciepes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ReciepeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ReciepeListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private boolean mIsConnected;
    //private DataAdapter adapter;
    private View recyclerView;
    private ArrayList<Reciepe> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIsConnected()){
            setContentView(R.layout.activity_reciepe_list);
        }else{
            //setContentView(R.layout.activity_connection_error);
            Intent intent = new Intent(this,NoConnectionActivity.class);
            startActivity(intent);

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //getdata from connection
        //loadJSON();


        recyclerView = findViewById(R.id.reciepe_list);
        assert recyclerView != null;
        //setupRecyclerView((RecyclerView) recyclerView);
        loadJSON((RecyclerView) recyclerView);

        if (findViewById(R.id.reciepe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }
    public boolean getIsConnected() {

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        mIsConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        return mIsConnected;
    }
    private void loadJSON(@NonNull RecyclerView recyclerView){
        final RecyclerView recyclerView1;
        recyclerView1 = recyclerView;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<Reciepe>> call = request.getJSON();
        call.enqueue(new Callback<List<Reciepe>>() {
            @Override
            public void onResponse(Call<List<Reciepe>> call, Response<List<Reciepe>> response) {
                List <Reciepe> jsonResponse = response.body();
                data = new ArrayList<>(jsonResponse);


                //adapter = new DataAdapter(data,getBaseContext());
                //recyclerView.setAdapter(adapter);
                //recyclerView.setAdapter(adapter);
                recyclerView1.setAdapter(new SimpleItemRecyclerViewAdapter(data));
            }
            @Override
            public void onFailure(Call<List<Reciepe>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }

        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //loadJSON();
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(data));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ArrayList<Reciepe> mValues;

        public SimpleItemRecyclerViewAdapter(ArrayList<Reciepe> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.reciepe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getId());
            holder.mContentView.setText(mValues.get(position).getName());



            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ReciepeDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        ReciepeDetailFragment fragment = new ReciepeDetailFragment();
                        fragment.setReciepeObject(holder.mItem);

                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.reciepe_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ReciepeDetailActivity.class);

                        Bundle b = new Bundle();
                        b.putParcelable(ReciepeDetailFragment.RECIEPE_ITEM, holder.mItem);

                        intent.putExtras(b);
                        //intent.putParecable()

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Reciepe mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
