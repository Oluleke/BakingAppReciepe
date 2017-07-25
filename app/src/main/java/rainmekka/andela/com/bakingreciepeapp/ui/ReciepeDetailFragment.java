package rainmekka.andela.com.bakingreciepeapp.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.util.ArrayList;

import rainmekka.andela.com.bakingreciepeapp.R;
import rainmekka.andela.com.bakingreciepeapp.data.Reciepe;
import rainmekka.andela.com.bakingreciepeapp.data.Step;
import rainmekka.andela.com.bakingreciepeapp.data.StepListAdapter;

/**
 * A fragment representing a single Reciepe detail screen.
 * This fragment is either contained in a {@link ReciepeListActivity}
 * in two-pane mode (on tablets) or a {@link ReciepeDetailActivity}
 * on handsets.
 */
public class ReciepeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String RECIEPE_ITEM = "reciepe_item";
    public static final String STEP_LIST = "step_list_item";
    public static final String TAG = "RecipeDetailFragment";


    /**
     * The dummy content this fragment is presenting.
     */
    private Reciepe mItem;
    public ArrayList<Step> steps= new ArrayList<>();
    boolean mTwoPaneMode;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReciepeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null){
            mItem = savedInstanceState.getParcelable(STEP_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reciepe_detail, container, false);
        if (mItem != null ) {
            Button btn_showIngredients = (Button) rootView.findViewById(R.id.btn_showIngredients);

            btn_showIngredients.setText("Ingredients");

            btn_showIngredients.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                        Intent intent = new Intent(context, ReciepeIngredientsActivity.class);

                        Bundle b = new Bundle();
                        b.putParcelable(ReciepeDetailFragment.STEP_LIST, mItem);

                        intent.putExtras(b);
                        context.startActivity(intent);
                }
            });
            RecyclerView recyclerView = (RecyclerView)rootView.findViewById
                    (R.id.step_list_recyclerview);


            StepListAdapter adapter = new StepListAdapter(mItem,getContext());
            adapter.setTwoPaneMode(mTwoPaneMode);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(llm);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);


            return rootView;

        }

        return rootView;
    }

    public void setTwoPaneMode(boolean twopanemode){
        mTwoPaneMode = twopanemode;
    }
    public void setReciepeObject(Reciepe reciepe){
        mItem = reciepe;
        Log.e(TAG, "Number of recipe steps: " + steps.size());
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putParcelable(STEP_LIST,mItem);

    }
//    @Override
//    protected Parcelable onSaveInstanceState(){
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(SAVED_LAYOUT_MANAGER,);
//    }

}
