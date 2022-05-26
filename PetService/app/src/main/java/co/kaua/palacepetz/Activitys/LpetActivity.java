package co.kaua.palacepetz.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.L;

import org.w3c.dom.ls.LSParser;

import co.kaua.palacepetz.R;

public class LpetActivity extends AppCompatActivity{

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
 //   public static LpetActivity instance = null;
    ImageButton btn_cho, btn_meo, btn_khac, btn_thucan;
    ListView list;
    String ten[] = {"Alaska", "Beagle", "Corgi", "Phốc Sóc", "Bull", "Mèo Anh", "Mèo Ba Tư", "Mèo Munchkin","Chó Pug","Chó Poodle","Chó Chihuahua","Chó Bully","Chó Corgi","Chó Ngao Tây Tạng","Chó Pitbull","Chó Husky"};
    int img[] = {R.drawable.alaska,R.drawable.beagle,R.drawable.corgi,R.drawable.phoc_soc,R.drawable.bull,R.drawable.meo_anh,R.drawable.meo_ba_tu,R.drawable.munchkin,R.drawable.chopug2,R.drawable.anhchopoodle,R.drawable.chihuahua,R.drawable.chobully,R.drawable.chocorgi1,R.drawable.chongaotaytang1,R.drawable.chopitbull1,R.drawable.husky};
    String gia[] = {"13.000.000đ-18.000.000đ","9.500.000đ- 15.000.000đ","25.000.000đ- 30.000.000đ","9.500.000đ- 17.000.000đ","13.000.000đ - 22.000.000đ","15.000.000đ- 30.000.000đ","12.000.000đ- 20.000.000đ","18.000.000đ - 26.000.000đ","18.000.000đ - 26.000.000đ","18.000.000đ - 26.000.000đ","18.000.000đ - 26.000.000đ","18.000.000đ - 26.000.000đ","18.000.000đ - 26.000.000đ","18.000.000đ - 26.000.000đ","180.000.000đ - 300.000.000đ","22.000.000đ - 26.000.000đ","22.000.000đ - 26.000.000đ"};
    String chitiet[] = {"Chó Alaska đang là loại chó được ưa chuộng nhất tại Việt Nam hiện nay. Với ngoại hình oai vệ, bộ lông tuyệt đẹp, rất tình cảm và thân thiện."
            ,"Nó là giống chó săn nhỏ được lai tạo giữa chó săn thỏ Talbot và giống chó Anh bản địa."
            ,"Corgi nổi tiếng thế giới là cún cưng của Nữ hoàng Anh. Có nguồn gốc là giống chó chăn cừu ở xứ Wales."
            ,"Chó Pomeranian có nguồn gốc từ con German spitzen. Đây là con chó nhỏ nhất của gia đình chó kéo xe."
            ,"Tổ tiên của con chó Bulldog là con Molussus, tên của một con chó chiến đấu từ một bộ tộc cổ Moloosi của Hy Lạp."
            ,"Mèo Anh lông ngắn hay còn được gọi tắt là mèo Aln. Có nguồn gốc từ vương quốc Anh với tên gọi quốc tế là British Shorthair."
            ,"Mèo Ba Tư là một trong những giống mèo cảnh được đông đảo người yêu mèo yêu mến, lựa chọn làm thú cưng trong gia đình."
            ,"Munchkin được mệnh danh là những chú mèo chân ngắn lùn nhất trong thế giới các loài mèo. Munchkin có nguồn gốc bắt nguồn từ Hoa Kỳ (Mỹ)."
            ,"Nguồn gốc thật sự của những chú chó Pug “mặt xệ” hiện nay vẫn chưa được xác định. theo nhiều nguồn thông tin thì chú chó “mặt xệ” này đã xuất hiện từ thời nhà Hán - Trung Quốc vào khoảng những năm 200 TCN. Lúc bấy giờ, chó Pug được xếp vào dòng dõi quý tộc, được hưởng thụ cuộc sống xa hoa và những người nuôi chó Pug chủ yếu là quan lại, hoàng thân, quốc thích Trung Quốc."
            ,"Chó Poodle được biết đến ở Tây Âu vào khoảng 400 năm trước và chúng chính là hậu duệ của các giống chó như French Water Dog, Hungarian Water Hound và Barbet. Ngày nay, con người đã lai tạo giống chó săn vịt này thành một hình tượng quý tộc và xinh xắn, trở thành chú chó được nhiều người yêu thích."
            ,"Chihuahua là chú chó nhỏ nhất trong mọi loài chó trên thế giới và cũng là giống chó lâu đời nhất ở châu Mỹ. Chú chó này có nguồn gốc từ Mexico nhưng lại được cả thế giới biết đến nhờ Trung Quốc. Tên của chúng được lấy từ tên một bang của Mexico, bang Chihuahuan, là nơi mà chú chó được tìm thấy."
            ,"Chó Bully xuất hiện từ những năm 1995 và là hậu duệ của chó Pitbull nhưng chúng sở hữu vẻ ngoài đô hơn và cơ bắp hơn. Đây là giống chó cảnh đang được yêu thích tại Việt Nam."
            ,"orgi là chú chó đến từ Anh quốc và đây là giống chó lùn vô cùng nổi tiếng với đôi chân ngắn ngủn.\n" +
            "\n"
            ,"Chó Ngao Tây Tạng xuất hiện từ khá lâu và người ta ước tính được khoảng thời gian là 5000 - 6000 năm trước tại vùng cao nguyên Tây Tạng của Trung Quốc."
            ,"Chó Pitbull có nguồn gốc từ châu Mỹ và là một trong những hung thần của những loài chó khác trong nhóm chó chiến. Trong những năm gần đây, Pitbull khá phổ biến ở Việt Nam",
            "Siberian Husky, là giống chó có nguồn gốc từ vùng Đông Bắc Siberia, Nga. Tại đây, từ khoảng hơn 3000 năm trước, chúng đã được tộc người Chukchi nuôi dưỡng và huấn luyện để giúp đỡ con người trong việc di chuyển hay vận chuyển hàng hóa."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("PET SHOP");
//        btn_cho = (ImageButton) findViewById(R.id.imgbtnCho);
//        btn_meo = (ImageButton) findViewById(R.id.imgbtnMeo);
//        btn_thucan = (ImageButton) findViewById(R.id.imgbtnThucAn);
//        btn_khac = (ImageButton) findViewById(R.id.imgbtnKhac);
      //  instance = this;

        list = (ListView)findViewById(R.id.listviewhome);
        MyAdapter myAdapter = new MyAdapter(this, ten, gia, img, chitiet);
        list.setAdapter(myAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(LpetActivity.this, PetDetailActivity.class);
                intent.putExtra("name", ten[position]);
                intent.putExtra("image", img[position]);
                intent.putExtra("gia", gia[position]);
                intent.putExtra("chitiet", chitiet[position]);
                startActivity(intent);

            }
        });
//        btn_cho.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, DogListActivity.class);
//                startActivity(intent);
//            }
//        });
//        btn_meo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CatListActivity.class);
//                startActivity(intent);
//            }
//        });
//        btn_thucan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, FoodListActivity.class);
//                startActivity(intent);
//            }
//        });btn_khac.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MoreListActivity.class);
//                startActivity(intent);
//            }
//        });


//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigration);
//        bottomNavigationView.setSelectedItemId(R.id.nav_home);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.nav_home:
//                        return true;
//                    case R.id.nav_search:
//                        startActivity(new Intent(getApplicationContext(),SearchFragment.class));
//                        overridePendingTransition(0,0);
//                        finish();
//                        return true;
//                    case R.id.nav_cart:
//                        startActivity(new Intent(getApplicationContext(),CartFragment.class));
//                        overridePendingTransition(0,0);
//                        finish();
//                        return true;
//                    case R.id.nav_more:
//                        startActivity(new Intent(getApplicationContext(),MoreFragment.class));
//                        overridePendingTransition(0,0);
//                        finish();
//                        return true;
//                }
//                return false;
//            }
//        });

    }
    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String myten[];
        String mygia[];
        int[] img;
        String mychitiet[];

        MyAdapter(Context c, String[] ten, String[] gia, int[] img, String[] mychitiet){
            super(c,R.layout.pet_row_item, R.id.textview_name_list, ten);
            this.context = c;
            this.myten = ten;
            this.mygia = gia;
            this.img = img;
            this.mychitiet = mychitiet;
        }
        public View getView(int position, View view, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.pet_row_item, parent, false);
            ImageView image = row.findViewById(R.id.imageView_list);
            TextView tenpet = row.findViewById(R.id.textview_name_list);
            TextView giapet = row.findViewById(R.id.textview_gia_list);
            TextView chitietpet = row.findViewById(R.id.textview_chitiet_list);
            image.setImageResource(img[position]);
            tenpet.setText(ten[position]);
            giapet.setText(gia[position]);
            chitietpet.setText(chitiet[position]);
            return row;
        }
    }
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_home,menu);
//        menuInflater.inflate(R.menu.cart,menu);
//        return true;
//    }
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_cart:
//            {
//                Intent intent = new Intent(LpetActivity.this, LoginActivity.class);
//                overridePendingTransition(0, 0);
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//                finish();
//            }
//            return true;
//            case R.id.menu_lienhe:
//            {
//                Intent intent = new Intent(LpetActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//            return true;
//            case R.id.thoat:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    @Override
    public void finish() {
        super.finish();
       //instance = null;
    }

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else {
            Toast toast = Toast.makeText(getBaseContext(), "Nhấn back lần nữa để thoát", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();

        }

        mBackPressed = System.currentTimeMillis();
    }

}

