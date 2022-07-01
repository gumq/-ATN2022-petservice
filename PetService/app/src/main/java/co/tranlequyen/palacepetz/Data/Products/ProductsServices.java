package co.tranlequyen.palacepetz.Data.Products;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductsServices {


    @GET("list/filter/name/{nm_product}")
    Call<DtoProducts> getFilterName(@Path("nm_product") String nm_product);

    @GET("products/details/{cd_prod}")
    Call<DtoProducts> getDetails(@Path("cd_prod") int cd_prod);


}
