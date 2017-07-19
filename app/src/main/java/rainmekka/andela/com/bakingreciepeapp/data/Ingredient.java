package rainmekka.andela.com.bakingreciepeapp.data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Oluleke on 6/20/2017.
 */

public class Ingredient implements Parcelable {
    public String quantity;
    public String measure;
    public String ingredient;

    public String getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }


    public static final Parcelable.Creator<Ingredient> CREATOR;

    static {
        CREATOR = new Creator<Ingredient>() {
            @Override
            public Ingredient createFromParcel(Parcel in) {
                return new Ingredient(in);
            }

            @Override
            public Ingredient[] newArray(int size) {
                return new Ingredient[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
    protected Ingredient (Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }
}
