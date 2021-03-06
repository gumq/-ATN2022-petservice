package co.tranlequyen.palacepetz.Activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import co.tranlequyen.palacepetz.Data.DatabaseHelper;
import co.tranlequyen.palacepetz.R;


public class PetDetailActivity extends AppCompatActivity {
    FloatingActionButton backbutoon, btn_botSoLuong;
    TextView tv_name, tv_gia, tv_chitiet;
    ImageView img_hinh;
    DatabaseHelper db;
    int soLuong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);
//        setTitle("Detail");

        img_hinh =(ImageView)findViewById(R.id.img_hinh_detail);
        tv_gia = (TextView)findViewById(R.id.tv_gia_detail);
        tv_name = (TextView)findViewById(R.id.tv_name_detail);
      ///  tv_soluong = (TextView)findViewById(R.id.textview_soluong);
        tv_chitiet = (TextView)findViewById(R.id.tv_chitiet_detail);
       backbutoon = findViewById(R.id.backbutoon);
       // btn_botSoLuong = (Button)findViewById(R.id.btn_botsoluong);
      //  btn_addCart = (Button)findViewById(R.id.btn_add_cart);
        db = new DatabaseHelper(this);

        String name = getIntent().getStringExtra("name");
        int image =getIntent().getIntExtra("image",0);
        String gia = getIntent().getStringExtra("gia");
        String chitiet = getIntent().getStringExtra("chitiet");
        tv_name.setText(name);
        img_hinh.setImageResource(image);
        tv_gia.setText(gia);
        tv_chitiet.setText(chitiet+"Tuổi thọ\n" +
                "tuổi thọ từ 12 - 22 năm nếu không bị bắt thịt.\n" +
                "\n" +
                "Cách nuôi\n" +
                "Chăm sóc sức khỏe\n" +
                "\n" +
                "- Chải lông thường xuyên. Chải lông thường xuyên sẽ làm giảm bớt hiện tượng rụng lông ở giống chó này và giữ cho chúng luôn được khỏe mạnh.\n" +
                "\n" +
                "- Vệ sinh tai mỗi tuần một lần  bằng dung dịch làm sạch tai cho chó. \n" +
                "\n" +
                "- Kiểm tra móng định kỳ và cắt tỉa chúng nếu cần thiết để tránh nhiễm khuẩn.\n" +
                "\n" +
                "Chế độ dinh dưỡng\n" +
                "\n" +
                "- Thịt, nội tạng, rau củ, trứng và thức ăn dạng hạt là các loại thức ăn nên cung cấp . Đặc biệt là nội tạng, món khoái khẩu .\n" +
                "\n" +
                "- Từ 2 - 5 tháng tuổi: 4 bữa/ngày; 5 tháng - 1 năm: 3 bữa/ngày; 1 năm trở lên: 2 bữa/ngày.\n" +
                "\n" +
                "- Không cho ăn đồ sống sẽ ảnh hưởng đến tiêu hóa");




        backbutoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
                overridePendingTransition(R.anim.move_to_right_popular,R.anim.move_to_left);
                onEnterAnimationComplete();

            }
        });
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}