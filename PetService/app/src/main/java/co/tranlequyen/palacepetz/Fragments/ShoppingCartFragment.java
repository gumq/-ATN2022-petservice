package co.tranlequyen.palacepetz.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import co.tranlequyen.palacepetz.Activitys.MainActivity;
import co.tranlequyen.palacepetz.Adapters.IOnBackPressed;
import co.tranlequyen.palacepetz.Adapters.LoadingDialog;


import co.tranlequyen.palacepetz.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShoppingCartFragment extends Fragment implements IOnBackPressed {
    //  Screen Items
    private ConstraintLayout container_noItemsOnCart, btn_seeProducts_ShoppingCart, containerTotalShoppingCart;
    private RecyclerView RecyclerViewCart;
    private TextView txt_total_shoppingCart, txt_OrderNow_ShoppingCart;
    private SwipeRefreshLayout swipe_recycler_shoppingCart;
    private CardView btnBuy_shoppingCart;

    private int CountToReload = 0;

    //  Fragments Arguments
    Bundle args;


    //  User information
    String _Email;
    int _IdUser;

    private  LoadingDialog loadingDialog;

    //  Firebase / Retrofit
    final Retrofit retrofitCart = new Retrofit.Builder()
            .baseUrl("https://gumq.github.io/palacepetzapi/petdataa.json")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_shoppingcart, container, false);
        requireActivity().getWindow().setNavigationBarColor(requireActivity().getColor(R.color.background_menu_sheet));
        Ids(view);
        container_noItemsOnCart.setVisibility(View.GONE);

        args = getArguments();
        assert args != null;
        _Email = args.getString("email_user");
        _IdUser = args.getInt("id_user");
        CheckShoppingCart();

        //  Set button to see all products
        btn_seeProducts_ShoppingCart.setOnClickListener(v -> {
            AllProductsFragment allProductsFragment = new AllProductsFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            args = new Bundle();
            args.putString("email_user", _Email);
            args.putInt("id_user", _IdUser);
            allProductsFragment.setArguments(args);
            transaction.replace(R.id.frameLayoutMain, allProductsFragment);
            transaction.commit();
        });

        swipe_recycler_shoppingCart.setOnRefreshListener(() -> {
            CheckShoppingCart();
            swipe_recycler_shoppingCart.setRefreshing(false);
        });

        btnBuy_shoppingCart.setOnClickListener(v -> {
            btnBuy_shoppingCart.setElevation(0);

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            Bundle args = new Bundle();
            args.putString("email_user", _Email);
            args.putInt("id_user", _IdUser);
            String startValue = txt_total_shoppingCart.getText().toString().replace("Tổng Tiền: VNĐ ", "").replace(" ", "");
            while (startValue.contains(".")) {
                startValue = startValue.substring(0,
                        startValue.indexOf(".")) +
                        startValue.substring(startValue.indexOf(".") + 1);
            }
            startValue = startValue.replaceAll(",", ".");
            double aFloat = Double.parseDouble(startValue);
            args.putDouble("total_Cart", aFloat);

        });

        return view;
    }

    @SuppressLint("SetTextI18n")
    public void CheckShoppingCart(){
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();
        MainActivity main = (MainActivity) getContext();
        assert main != null;

    }

    private void LoadCart() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerViewCart.setLayoutManager(layoutManager);

        //noinspection unchecked

    }

    private void Ids(@NonNull View view) {
        btn_seeProducts_ShoppingCart = view.findViewById(R.id.btn_seeProducts_ShoppingCart);
        container_noItemsOnCart = view.findViewById(R.id.container_noItemsOnCart);
        RecyclerViewCart = view.findViewById(R.id.RecyclerViewCart);
        containerTotalShoppingCart = view.findViewById(R.id.containerTotalShoppingCart);
        swipe_recycler_shoppingCart = view.findViewById(R.id.swipe_recycler_shoppingCart);
        txt_total_shoppingCart = view.findViewById(R.id.txt_total_shoppingCart);
        txt_OrderNow_ShoppingCart = view.findViewById(R.id.txt_OrderNow_ShoppingCart);
        btnBuy_shoppingCart = view.findViewById(R.id.btnBuy_shoppingCart);
        txt_total_shoppingCart.setText(requireContext().getString(R.string.loading));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CountToReload != 0){
            CountToReload = 0;
            requireActivity().getWindow().setNavigationBarColor(requireActivity().getColor(R.color.background_menu_sheet));
            MainActivity mainActivity = (MainActivity) getContext();
            assert mainActivity != null;

            mainActivity.ReOpenCart();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CountToReload = 1;
        requireActivity().getWindow().setNavigationBarColor(requireActivity().getColor(R.color.background_top));
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
