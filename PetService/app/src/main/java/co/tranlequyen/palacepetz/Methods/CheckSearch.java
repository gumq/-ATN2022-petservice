package co.tranlequyen.palacepetz.Methods;

import android.content.Context;

import androidx.annotation.NonNull;

import co.tranlequyen.palacepetz.Activitys.MainActivity;
import co.tranlequyen.palacepetz.R;

public class CheckSearch {

    public static void DoSearchCut(@NonNull String searchText, @NonNull Context context){
        String services = context.getString(R.string.services);
        String myCards = context.getString(R.string.myCards);
        String my_shopping_cart = context.getString(R.string.my_shopping_cart);
        String myOrders = context.getString(R.string.myOrders);
        String products = context.getString(R.string.products);
        String palaceFountain = context.getString(R.string.palaceFountain);
        String edit_address = context.getString(R.string.edit_address);
        String editProfile = context.getString(R.string.editProfile);
        String help = context.getString(R.string.help);

        if (searchText.equals(services))
            MainActivity.getInstance().OpenServices();

        else if(searchText.equals(myCards))
            MainActivity.getInstance().OpenMyCards();

        else if(searchText.equals(my_shopping_cart))
            MainActivity.getInstance().OpenShoppingCart();
        else if(searchText.equals(palaceFountain))
            MainActivity.getInstance().Openprofuct();


        else if(searchText.equals(products))
            MainActivity.getInstance().OpenAllProducts();



        else if(searchText.equals(edit_address))
            MainActivity.getInstance().OpenAddressEdit();

        else if(searchText.equals(editProfile))
            MainActivity.getInstance().OpenProfile();

        else if(searchText.equals(help))
            MainActivity.getInstance().OpenHelp();
    }
}
