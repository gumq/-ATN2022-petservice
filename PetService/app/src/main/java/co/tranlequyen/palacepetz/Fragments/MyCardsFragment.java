package co.tranlequyen.palacepetz.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import co.tranlequyen.palacepetz.Activitys.MainActivity;
import co.tranlequyen.palacepetz.Adapters.IOnBackPressed;
import co.tranlequyen.palacepetz.Adapters.LoadingDialog;

import co.tranlequyen.palacepetz.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@SuppressWarnings("ALL")
public class MyCardsFragment extends Fragment implements IOnBackPressed {
    //  Screen Items
    private static RecyclerView recyclerView_Cards;
    private static CardView cardContainer_AddCard, BtnMyCard_AddCard, BtnMyCard_AddNewCard;
    private static ConstraintLayout container_noCard;
    private static FragmentTransaction transaction;
    private static LoadingDialog loadingDialog;

    //  Fragments Arguments
    Bundle args;
    private static MyCardsFragment instante;

    //  User information
    private static int id_user;
    private static String name_user, _Email, cpf_user, address_user, complement, zipcode, phone_user, birth_date, img_user;

    //  Retrofit
    static String baseurl = "";
    static final Retrofit retrofitCard = new Retrofit.Builder()
            .baseUrl( baseurl + "user/cards/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_mycards, container, false);
        Ids(view);
        instante = this;
        cardContainer_AddCard.setElevation(19);
        BtnMyCard_AddCard.setElevation(20);
        BtnMyCard_AddNewCard.setElevation(20);

        args = getArguments();
        assert args != null;
        _Email = args.getString("email_user");
        id_user = args.getInt("id_user");
        getCardsInformation();

        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;


        BtnMyCard_AddCard.setOnClickListener(v -> {
            cardContainer_AddCard.setElevation(0);
            BtnMyCard_AddCard.setElevation(0);
            Goto_CardRegistration();
        });

        BtnMyCard_AddNewCard.setOnClickListener(v -> {
            BtnMyCard_AddNewCard.setElevation(0);
            Goto_CardRegistration();
        });

        return view;
    }

    public static MyCardsFragment getInstance(){ return instante; }

    public static void getCardsInformation() {
        loadingDialog.startLoading();

    }

    private void Goto_CardRegistration() {
        CardRegistrationFragment cardregistrationFragment = new CardRegistrationFragment();
        args = new Bundle();
        args.putString("email_user", _Email);
        args.putInt("id_user", id_user);
        cardregistrationFragment.setArguments(args);
        transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutMain, cardregistrationFragment);
        transaction.commit();
    }

    private void Ids(@NonNull View view) {
        recyclerView_Cards = view.findViewById(R.id.recyclerView_Cards);
        cardContainer_AddCard = view.findViewById(R.id.cardContainer_AddCard);
        BtnMyCard_AddCard = view.findViewById(R.id.BtnMyCard_AddCard);
        BtnMyCard_AddNewCard = view.findViewById(R.id.BtnMyCard_AddNewCard);
        container_noCard = view.findViewById(R.id.container_noCard);
        loadingDialog = new LoadingDialog(getActivity());
    }

    @Override
    public boolean onBackPressed() {
        //action not popBackStack
        requireActivity().getWindow().setNavigationBarColor(requireActivity().getColor(R.color.background_top));
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("email_user", _Email);
        mainFragment.setArguments(args);
        transaction.replace(R.id.frameLayoutMain, mainFragment);
        transaction.commit();
        return true;
    }
}
