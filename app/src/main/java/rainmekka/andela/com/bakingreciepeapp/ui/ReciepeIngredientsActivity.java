package rainmekka.andela.com.bakingreciepeapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rainmekka.andela.com.bakingreciepeapp.R;
import rainmekka.andela.com.bakingreciepeapp.data.Ingredient;
import rainmekka.andela.com.bakingreciepeapp.data.Reciepe;
import rainmekka.andela.com.bakingreciepeapp.data.StepListAdapter;

/**
 * Created by Oluleke on 7/19/2017.
 */

public class ReciepeIngredientsActivity extends AppCompatActivity {
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    Reciepe mReciepe;
    RecyclerView recyclerView;


    public ReciepeIngredientsActivity(){}

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_reciepe);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle b = getIntent().getExtras();

            if (b != null) {
                //
                mReciepe = b.getParcelable(ReciepeDetailFragment.STEP_LIST);
                ingredients = mReciepe.ingredients;

                recyclerView = (RecyclerView) findViewById
                        (R.id.recycler_view_ingredients);
                assert recyclerView != null;


                //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
                LinearLayoutManager lm = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(lm);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(new IngredientViewAdapter(ingredients));


            } else {
                //do somethine else here
            }

        }
    }

    public class IngredientViewAdapter
            extends RecyclerView.Adapter<IngredientViewAdapter.ViewHolder> {

        private final ArrayList<Ingredient> mValues;

        public IngredientViewAdapter(ArrayList<Ingredient> items) {
            mValues = items;
        }

        @Override
        public IngredientViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_ingredients_list, parent, false);
            return new IngredientViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final IngredientViewAdapter.ViewHolder holder, int position) {

            final String INGREDIENT_PREFIX = "Ingredient : " + mValues.get(position).ingredient;
            final String QUANTITY_PREFIX = "Quantity : " + mValues.get(position).quantity;
            final String MEASURE_PREFIX = "Measure : " + mValues.get(position).measure;

            holder.mItem = mValues.get(position);
            holder.txt_ingredient_ingredientent.setText(INGREDIENT_PREFIX);
            holder.txt_ingredient_quantity.setText(QUANTITY_PREFIX);
            holder.txt_ingredient_measure.setText(MEASURE_PREFIX);


            //Get current recipe
            final Ingredient ingredient = mValues.get(position);
        }

        @Override
        public int getItemCount() {
            Log.i("Ingredients ===>>> ",Integer.toString(mValues.size()));
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView txt_ingredient_ingredientent;
            public final TextView txt_ingredient_quantity;
            public final TextView txt_ingredient_measure;
            public Ingredient mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                txt_ingredient_ingredientent = (TextView) view.findViewById(R.id.txt_ingredient_ingredientent);
                txt_ingredient_quantity = (TextView) view.findViewById(R.id.txt_ingredient_quantity);
                txt_ingredient_measure = (TextView) view.findViewById(R.id.txt_ingredient_measure);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + txt_ingredient_ingredientent.getText() + "'";
            }
        }
    }
}

