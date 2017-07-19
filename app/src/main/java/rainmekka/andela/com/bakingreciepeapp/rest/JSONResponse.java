package rainmekka.andela.com.bakingreciepeapp.rest;

import java.util.ArrayList;

import rainmekka.andela.com.bakingreciepeapp.data.Reciepe;

/**
 * Created by Oluleke on 7/14/2017.
 */

public class JSONResponse {
    //private Reciepe[] reciepes;
    ArrayList<Reciepe> reciepList;
    public ArrayList<Reciepe> getReciepes(){
        return reciepList;
    }
}
