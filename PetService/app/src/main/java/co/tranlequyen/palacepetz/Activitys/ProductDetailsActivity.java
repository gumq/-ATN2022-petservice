package co.tranlequyen.palacepetz.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import co.tranlequyen.palacepetz.Adapters.LoadingDialog;


import co.tranlequyen.palacepetz.Data.User.DtoUser;
import co.tranlequyen.palacepetz.Methods.ToastHelper;
import co.tranlequyen.palacepetz.R;

public class ProductDetailsActivity extends AppCompatActivity {
    private LottieAnimationView arrowGoBack_ProductDetails;
    private TextView txt_product_name, txt_desc_prod, txt_price_product, txtQt_prod, txt_AddToCart;
    private CardView btnLessQT_Prod, btnPlusQT_Prod, cardBtn_AddToCart;
    private ImageView imgProductDetails;

    //  Product information
    private int qt_prod = 1;
    private int qt_prodGet;
    private float unit_prod_price;
    @SuppressWarnings("FieldCanBeLocal")
    private float full_prod_price = unit_prod_price;
    private LoadingDialog loadingDialog;
    int cd_prod;
    private String ToastAlert;
    String image_prod, nm_product, description,linkshopee;

    //  User information
    String _Email,cpf_user;
    int _IdUser;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setTheme(R.style.DevicePresentation);
        Ids();
        ToastAlert = getString(R.string.maximum_amount_reached);
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        Bundle bundle = getIntent().getExtras();
        DtoUser userInfo = MainActivity.getInstance().GetUserBaseInformation();
        _Email = userInfo.getEmail();
        cpf_user = userInfo.getCpf_user();
        cd_prod = bundle.getInt("cd_prod");
        linkshopee = bundle.getString("linkshopee");
      //  Log.d("VVV",linkshopee);
        if (bundle.getString("nm_product") == null){
            loadingDialog = new LoadingDialog(ProductDetailsActivity.this);
            loadingDialog.startLoading();


        }else{
            image_prod = bundle.getString("image_prod");
            nm_product = bundle.getString("nm_product");
            description = bundle.getString("description");
            unit_prod_price = bundle.getFloat("product_price");
            qt_prodGet = bundle.getInt("amount");
            linkshopee = bundle.getString("linkshopee");
            loadProdsTexts();
            setNewPrice(numberFormat);
            if(qt_prodGet <= 0){
                txt_desc_prod.setText(getString(R.string.warning_no_stock));
                txtQt_prod.setText(0 + "");
                txt_AddToCart.setText(getString(R.string.no_stock));
                btnLessQT_Prod.setEnabled(false);
                btnPlusQT_Prod.setEnabled(false);
                cardBtn_AddToCart.setEnabled(false);
            }
        }

        if (qt_prodGet < 20)
            ToastAlert = getString(R.string.maximum_amount_reached_no_stock, qt_prodGet + "");


        btnLessQT_Prod.setOnClickListener(v -> {
            if (qt_prod == 1)
                ToastHelper.toast(ProductDetailsActivity.this, getString(R.string.one_is_the_minumum_quantity));
            else{
                qt_prod--;
                setNewPrice(numberFormat);
                RefreshQtText();
            }
        });

        btnPlusQT_Prod.setOnClickListener(v -> {
            if (qt_prod == qt_prodGet || qt_prod == 20)
                ToastHelper.toast(ProductDetailsActivity.this, ToastAlert);
            else {
                qt_prod++;
                setNewPrice(numberFormat);
                RefreshQtText();
            }
        });

        arrowGoBack_ProductDetails.setOnClickListener(v -> finish());

        cardBtn_AddToCart.setOnClickListener(v -> {

        Log.e("BBB",linkshopee);
            // An implicit intent, request a URL.
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkshopee.toString()));
            this.startActivity(intent);
//            if (_IdUser != 0){
//                loadingDialog = new LoadingDialog(ProductDetailsActivity.this);
//                loadingDialog.startLoading();
//                DtoShoppingCart cartItems = new DtoShoppingCart(cd_prod, _IdUser, qt_prod, String.valueOf(unit_prod_price), String.valueOf(full_prod_price), String.valueOf(full_prod_price));
//                CartServices cartServices = CartRetrofit.create(CartServices.class);
//                Call<DtoShoppingCart> cartCall = cartServices.insertItemOnCart(cartItems);
//                cartCall.enqueue(new Callback<DtoShoppingCart>() {
//                    @Override
//                    public void onResponse(@NonNull Call<DtoShoppingCart> call, @NonNull Response<DtoShoppingCart> response) {
//                        if(response.code() == 201){
//                            loadingDialog.dimissDialog();
//                            ToastHelper.toast(ProductDetailsActivity.this, getString(R.string.product_successfully_inserted));
//                            finish();
//                        }else if(response.code() == 409){
//                            loadingDialog.dimissDialog();
//                            ToastHelper.toast(ProductDetailsActivity.this, getString(R.string.product_into_your_cart));
//                        }
//                        else{
//                            loadingDialog.dimissDialog();
//                            Warnings.showWeHaveAProblem(ProductDetailsActivity.this);
//                        }
//                    }
//                    @Override
//                    public void onFailure(@NonNull Call<DtoShoppingCart> call, @NonNull Throwable t) {
//                        loadingDialog.dimissDialog();
//                        Warnings.showWeHaveAProblem(ProductDetailsActivity.this);
//                    }
//                });
//            }else
//                Warnings.NeedLoginAlert(ProductDetailsActivity.this);
        });
    }



    @SuppressLint("SetTextI18n")
    private void loadProdsTexts() {
        txt_product_name.setText(nm_product);
        txt_desc_prod.setText(description);
        txt_price_product.setText(unit_prod_price+"đ");
        txtQt_prod.setText(qt_prod + "");
        Picasso.get().load(image_prod).into(imgProductDetails);
    }

    private void Ids() {
        arrowGoBack_ProductDetails = findViewById(R.id.arrowGoBack_ProductDetails);
        btnLessQT_Prod = findViewById(R.id.btnLessQT_Prod);
        cardBtn_AddToCart = findViewById(R.id.cardBtn_AddToCart);
        btnPlusQT_Prod = findViewById(R.id.btnPlusQT_Prod);
        txtQt_prod = findViewById(R.id.txtQt_prod);
        txt_price_product = findViewById(R.id.txt_price_product);
        imgProductDetails = findViewById(R.id.imgProductDetails);
        txt_AddToCart = findViewById(R.id.txt_AddToCart);
        txt_desc_prod = findViewById(R.id.txt_desc_prod);
        txt_product_name = findViewById(R.id.txt_product_name);
    }

    @SuppressLint("SetTextI18n")
    private void setNewPrice(NumberFormat numberFormat) {
        full_prod_price = unit_prod_price * qt_prod;
        txt_price_product.setText(numberFormat.format(full_prod_price)+"đ");
    }

    @SuppressLint("SetTextI18n")
    private void RefreshQtText() {
        txtQt_prod.setText(qt_prod + "");
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}