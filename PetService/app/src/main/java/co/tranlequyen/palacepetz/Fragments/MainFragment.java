package co.tranlequyen.palacepetz.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import co.tranlequyen.palacepetz.Activitys.Developers;
import co.tranlequyen.palacepetz.Activitys.LpetActivity;
import co.tranlequyen.palacepetz.Activitys.MainActivity;
import co.tranlequyen.palacepetz.Data.Products.AsyncPopularProductsMain;
import co.tranlequyen.palacepetz.Data.Products.AsyncProducts_SearchMain;
import co.tranlequyen.palacepetz.Data.Products.DtoProducts;
import co.tranlequyen.palacepetz.Data.User.DtoUser;
import co.tranlequyen.palacepetz.Data.User.MainFindnearplace;
import co.tranlequyen.palacepetz.Methods.CheckSearch;
import co.tranlequyen.palacepetz.Methods.ToastHelper;
import co.tranlequyen.palacepetz.R;

import java.util.ArrayList;
import java.util.Arrays;


@SuppressWarnings({"ConstantConditions", "StaticFieldLeak", "unchecked"})
public class MainFragment extends Fragment {
    private ConstraintLayout btn_services_shortCut, btn_allProducts_shortCut,btn_pet, btn_cards_shortCut, btn_myOrders_shortCut;
    private AutoCompleteTextView edit_search;
    private RecyclerView RecyclerPopularProducts;
    private LottieAnimationView loadingPopularProducts;
    private final ArrayList<DtoProducts> arrayListDto = new ArrayList<>();

    private Bundle args;
    private View view;
    private static FragmentTransaction transaction;
    private ArrayList<String> SuggestionsSearch = new ArrayList<>();
    private String[] SuggestionsString;
    private static MainFragment instance;

    //  User information
   // private static int _IdUser;
    private static String _cpf_user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity_main, container, false);
        Ids();
        instance = this;
        SuggestionsString = new String[] { getContext().getString(R.string.services), getContext().getString(R.string.myCards),
                getContext().getString(R.string.my_shopping_cart), getContext().getString(R.string.myOrders), getContext().getString(R.string.products),
                getContext().getString(R.string.palaceFountain), getContext().getString(R.string.edit_address), getString(R.string.editProfile), getContext().getString(R.string.help)};
        SuggestionsSearch.addAll(Arrays.asList(SuggestionsString));

        assert args != null;
        DtoUser user = MainActivity.getInstance().GetUserBaseInformation();
        _cpf_user = user.getCpf_user();
        createShortCutsClick();
        AsyncProducts_SearchMain asyncProductsSearchMain = new AsyncProducts_SearchMain(_cpf_user, getActivity());
        asyncProductsSearchMain.execute();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerPopularProducts.setLayoutManager(layoutManager);
        arrayListDto.clear();
        AsyncPopularProductsMain async = new AsyncPopularProductsMain(RecyclerPopularProducts, loadingPopularProducts, arrayListDto, _cpf_user,getActivity());
        //noinspection unchecked
        async.execute();

        return view;
    }

    public static MainFragment getInstance() { return instance; }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void UpdateSearch(@NonNull ArrayList<String> list){
        SuggestionsSearch.clear();
        SuggestionsSearch.addAll(Arrays.asList(SuggestionsString));
        SuggestionsSearch.addAll(list);
        edit_search.setDropDownBackgroundDrawable(getContext().getDrawable(R.drawable.background_adapter_pets));
        edit_search.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, SuggestionsSearch));
    }

    private void createShortCutsClick() {
        //  Ser click
        btn_services_shortCut.setOnClickListener(v -> {
            ServicesFragment servicesFragment = new ServicesFragment();
            args = new Bundle();
            args.putString("id_user", _cpf_user);
            servicesFragment.setArguments(args);
            transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayoutMain, servicesFragment);
            transaction.commit();
        });
        btn_pet.setOnClickListener(v -> {
           // if(_IdUser != 0){
            Intent intent = new Intent(getActivity().getApplication(), LpetActivity.class);
            startActivity(intent);
          //  }else
              //  Warnings.NeedLoginAlert(requireActivity());
        });
        //  My Cards click
        btn_cards_shortCut.setOnClickListener(v -> {
          //  if(_IdUser != 0){
            Intent intent = new Intent(getActivity().getApplication(), MainFindnearplace.class);
            startActivity(intent);
            transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.commit();
           // }else
           //     Warnings.NeedLoginAlert(requireActivity());
        });

        //  My Orders click
        btn_myOrders_shortCut.setOnClickListener(v -> {
          Intent intent = new Intent(getActivity(), Developers.class);
          startActivity(intent);
          intent.putExtra("shortcut", 0);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getActivity(),R.anim.move_to_left, R.anim.move_to_right);
            ActivityCompat.startActivity(getActivity(), intent, activityOptionsCompat.toBundle());
        });

        //  All Products
        btn_allProducts_shortCut.setOnClickListener(v -> {
            AllProductsFragment productsFragment = new AllProductsFragment();
            args = new Bundle();
            args.putString("id_user", _cpf_user);
            args.putString("search", null);
            productsFragment.setArguments(args);
            transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayoutMain, productsFragment);
            transaction.commit();
        });

        edit_search.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, SuggestionsSearch));
        edit_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                DoSearch(edit_search.getText().toString().trim());
                return true;
            }
            return false;
        });

        edit_search.setOnItemClickListener((parent, view, position, id) -> DoSearch((String) parent.getItemAtPosition(position)));
    }

    private void DoSearch(@NonNull String searchText) {
        if (!searchText.equals("") || searchText.replace(" ", "").length() > 0){
            edit_search.setText("");
            if (    searchText.equals(getContext().getString(R.string.services)) || searchText.equals(getContext().getString(R.string.myCards)) ||
                    searchText.equals(getContext().getString(R.string.my_shopping_cart)) || searchText.equals(getContext().getString(R.string.myOrders)) ||
                    searchText.equals(getContext().getString(R.string.products)) || searchText.equals(getContext().getString(R.string.palaceFountain)) ||
                    searchText.equals(getContext().getString(R.string.edit_address)) || searchText.equals(getString(R.string.editProfile)))

                CheckSearch.DoSearchCut(searchText, getContext());
            else{
                AllProductsFragment productsFragment = new AllProductsFragment();
                args = new Bundle();
                args.putString("id_user", _cpf_user);
                args.putString("search", searchText);
                productsFragment.setArguments(args);
                transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutMain, productsFragment);
                transaction.commit();
            }
        }else
            ToastHelper.toast(requireActivity(), getString(R.string.search_cant_be_null));
    }

    private void Ids() {
        args = getArguments();
        edit_search = view.findViewById(R.id.edit_Search_Main);
        btn_services_shortCut = view.findViewById(R.id.btn_services_shortCut);
        btn_cards_shortCut = view.findViewById(R.id.btn_cards_shortCut);
        btn_pet = view.findViewById(R.id.btn_pet);
        btn_myOrders_shortCut = view.findViewById(R.id.btn_myOrders_shortCut);
        RecyclerPopularProducts = view.findViewById(R.id.RecyclerPopularProducts);
        loadingPopularProducts = view.findViewById(R.id.loadingPopularProducts);
        btn_allProducts_shortCut = view.findViewById(R.id.btn_allProducts_shortCut);
        btn_services_shortCut.setElevation(20);
        btn_pet.setElevation(20);
        btn_cards_shortCut.setElevation(20);
        btn_myOrders_shortCut.setElevation(20);
        btn_allProducts_shortCut.setElevation(20);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity main = (MainActivity) getContext();

    }
}
