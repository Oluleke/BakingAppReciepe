package rainmekka.andela.com.bakingreciepeapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import rainmekka.andela.com.bakingreciepeapp.R;
import rainmekka.andela.com.bakingreciepeapp.data.DummyContent;
import rainmekka.andela.com.bakingreciepeapp.data.Reciepe;
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
    public static final String ARG_ITEM_ID = "item_id";
    public static final String RECIEPE_ITEM = "reciepe_item";
    public static final String STEP_LIST = "step_list_item";


    /**
     * The dummy content this fragment is presenting.
     */
    private Reciepe mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReciepeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mItem!=null) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            //Already passed in through setreciepeObject
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reciepe_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            //((TextView) rootView.findViewById(R.id.reciepe_detail)).setText(mItem.name);
            Button btn_showIngredients = (Button) rootView.findViewById(R.id.btn_showIngredients);

            btn_showIngredients.setText("Ingredients");

            //btn_showIngredients.setOnClickListener();

            btn_showIngredients.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Context context = v.getContext();
//                        Intent intent = new Intent(context, ReciepeIngredientsActivity.class);
//
//                        Bundle b = new Bundle();
//                        b.putParcelable(ReciepeDetailFragment.STEP_LIST, mItem);
//
//                        intent.putExtras(b);
//                        context.startActivity(intent);
                    Toast.makeText(getContext(),"I was clicked"+Integer.toString(mItem.steps.size())
                    ,Toast.LENGTH_SHORT).show();
                    //Display Steps Detail
                }
            });
            //setup recyclerview

            RecyclerView recyclerView = (RecyclerView)rootView.findViewById
                    (R.id.step_list_recyclerview);

            //recyclerView.setLayoutManager();
            StepListAdapter adapter = new StepListAdapter(mItem,getContext());
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(llm);
            recyclerView.setHasFixedSize(true);
            return rootView;

        }

        return rootView;
    }
    public void setReciepeObject(Reciepe reciepe){
        mItem = reciepe;
    }
}
