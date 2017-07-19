package rainmekka.andela.com.bakingreciepeapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Oluleke on 6/20/2017.
 */

public class Reciepe implements Parcelable{

    public String id;
    public String name;
    public ArrayList<Ingredient> ingredients = new ArrayList<>();
    public ArrayList<Step> steps = new ArrayList<>();
    public String servings;
    public String image;

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    public ArrayList<Step> getSteps() {
        return steps;
    }
    public String getServings() {
        return servings;
    }
    public String getImage() {
        return image;
    }


    protected Reciepe (Parcel in) {
        id = in.readString();
        name = in.readString();
        servings = in.readString();
        image = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(servings);
        dest.writeString(image);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }
    public static final Parcelable.Creator<Reciepe> CREATOR;

    static {
        CREATOR = new Creator<Reciepe>() {
            @Override
            public Reciepe createFromParcel(Parcel in) {
                return new Reciepe(in);
            }

            @Override
            public Reciepe[] newArray(int size) {
                return new Reciepe[size];
            }
        };
    }
}

