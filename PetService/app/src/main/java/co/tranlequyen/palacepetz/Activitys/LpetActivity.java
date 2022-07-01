package co.tranlequyen.palacepetz.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import co.tranlequyen.palacepetz.R;

public class LpetActivity extends AppCompatActivity{

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
 //   public static LpetActivity instance = null;
    ImageButton btn_cho, btn_meo, btn_back, btn_thucan;
    ListView list;
    String ten[] = {"Chó Alaska", "Chó Beagle", "Chó Corgi", "Phốc Sóc", "Chó Bull",
            "Chó Pug","Chó Poodle","Chó Chihuahua","Chó Bully","Chó Corgi Lai","Ngao Tây Tạng","Chó Pitbull",
            "Chó Husky","Mèo Anh", "Mèo Ba Tư", "Mèo Munchkin","Mèo Xiêm","Anh Lông Dài","Mèo Tai Cụp Scottish Fold","Mèo Sphynx",
            "Chim Sáo","Chích Chòe","Chim Họa Mi","Chim Vẹt","Chim Khuyên","Chim Yến Phụng","Chim Chào Mào","Chim Vàng Anh"};
    int img[] = {R.drawable.alaska,R.drawable.beagle,R.drawable.corgi,R.drawable.phoc_soc,R.drawable.bull,R.drawable.chopug2,
            R.drawable.anhchopoodle,R.drawable.chihuahua,R.drawable.chobully,R.drawable.chocorgi1,R.drawable.chongaotaytang1,
            R.drawable.chopitbull1,R.drawable.husky,R.drawable.meo_anh,
            R.drawable.meo_ba_tu,R.drawable.munchkin,R.drawable.meoxiem,R.drawable.meoanhlongdai,R.drawable.meotaicup,R.drawable.meospynx,
            R.drawable.sao, R.drawable.chichchoe,R.drawable.hoami,R.drawable.vet,
            R.drawable.khuyen,R.drawable.yenphung,R.drawable.chaomao,R.drawable.vanganh};
    String gia[] = {"13.000.000đ-18.000.000đ","9.500.000đ- 15.000.000đ","25.000.000đ- 30.000.000đ","9.500.000đ- 17.000.000đ","13.000.000đ - 22.000.000đ","18.000.000đ - 26.000.000đ",
            "18.000.000đ - 26.000.000đ","18.000.000đ - 26.000.000đ","18.000.000đ - 26.000.000đ",
            "18.000.000đ - 26.000.000đ","18.000.000đ - 26.000.000đ","180.000.000đ - 300.000.000đ",
            "22.000.000đ - 26.000.000đ","22.000.000đ - 26.000.000đ","15.000.000đ- 30.000.000đ",
            "12.000.000đ- 20.000.000đ","18.000.000đ - 26.000.000đ","4.000.000đ- 6.000.000đ","7.000.000đ- 15.000.000đ"
    ,"3.500.000đ - 12.000.000đ","22.000.000đ - 70.000.000đ","200.000đ - 4.000.000đ","400.000đ - 2.000.000đ"
    ,"1.00.000đ - 1.500.000đ","150.000đ - 3.000.000đ","200.000đ - 1.000.000đ","100.000đ - 300.000đ","100.000đ - 800.000đ",
    "300.000đ - 500.000đ"};
    String chitiet[] = {"Chó Alaska đang là loại chó được ưa chuộng nhất tại Việt Nam hiện nay. Với ngoại hình oai vệ, bộ lông tuyệt đẹp, rất tình cảm và thân thiện."
            ,"Nó là giống chó săn nhỏ được lai tạo giữa chó săn thỏ Talbot và giống chó Anh bản địa."
            ,"Corgi nổi tiếng thế giới là cún cưng của Nữ hoàng Anh. Có nguồn gốc là giống chó chăn cừu ở xứ Wales."
            ,"Chó Pomeranian có nguồn gốc từ con German spitzen. Đây là con chó nhỏ nhất của gia đình chó kéo xe."
            ,"Tổ tiên của con chó Bulldog là con Molussus, tên của một con chó chiến đấu từ một bộ tộc cổ Moloosi của Hy Lạp."
            ,"Nguồn gốc thật sự của những chú chó Pug “mặt xệ” hiện nay vẫn chưa được xác định. theo nhiều nguồn thông tin thì chú chó “mặt xệ” này đã xuất hiện từ thời nhà Hán - Trung Quốc vào khoảng những năm 200 TCN. Lúc bấy giờ, chó Pug được xếp vào dòng dõi quý tộc, được hưởng thụ cuộc sống xa hoa và những người nuôi chó Pug chủ yếu là quan lại, hoàng thân, quốc thích Trung Quốc."
            ,"Chó Poodle được biết đến ở Tây Âu vào khoảng 400 năm trước và chúng chính là hậu duệ của các giống chó như French Water Dog, Hungarian Water Hound và Barbet. Ngày nay, con người đã lai tạo giống chó săn vịt này thành một hình tượng quý tộc và xinh xắn, trở thành chú chó được nhiều người yêu thích."
            ,"Chihuahua là chú chó nhỏ nhất trong mọi loài chó trên thế giới và cũng là giống chó lâu đời nhất ở châu Mỹ. Chú chó này có nguồn gốc từ Mexico nhưng lại được cả thế giới biết đến nhờ Trung Quốc. Tên của chúng được lấy từ tên một bang của Mexico, bang Chihuahuan, là nơi mà chú chó được tìm thấy."
            ,"Chó Bully xuất hiện từ những năm 1995 và là hậu duệ của chó Pitbull nhưng chúng sở hữu vẻ ngoài đô hơn và cơ bắp hơn. Đây là giống chó cảnh đang được yêu thích tại Việt Nam."
            ,"Corgi là chú chó đến từ Anh quốc và đây là giống chó lùn vô cùng nổi tiếng với đôi chân ngắn ngủn."
            ,"Chó Ngao Tây Tạng xuất hiện từ khá lâu và người ta ước tính được khoảng thời gian là 5000 - 6000 năm trước tại vùng cao nguyên Tây Tạng của Trung Quốc."
            ,"Chó Pitbull có nguồn gốc từ châu Mỹ và là một trong những hung thần của những loài chó khác trong nhóm chó chiến. Trong những năm gần đây, Pitbull khá phổ biến ở Việt Nam",
            "Siberian Husky, là giống chó có nguồn gốc từ vùng Đông Bắc Siberia, Nga. Tại đây, từ khoảng hơn 3000 năm trước, chúng đã được tộc người Chukchi nuôi dưỡng và huấn luyện để giúp đỡ con người trong việc di chuyển hay vận chuyển hàng hóa."
   ,"Mèo Anh lông ngắn hay còn được gọi tắt là mèo Aln. Có nguồn gốc từ vương quốc Anh với tên gọi quốc tế là British Shorthair."
            ,"Mèo Ba Tư là một trong những giống mèo cảnh được đông đảo người yêu mèo yêu mến, lựa chọn làm thú cưng trong gia đình."
            ,"Munchkin được mệnh danh là những chú mèo chân ngắn lùn nhất trong thế giới các loài mèo. Munchkin có nguồn gốc bắt nguồn từ Hoa Kỳ (Mỹ)."
   , "Đây là giống mèo cảnh thuần chủng có nguồn gốc từ Thái Lan và còn có tên gọi khác là Siamese Cat. Giống mèo này được đem về từ châu Âu trong khoảng giữa thế kỉ 18 và 19"
    ,"Một trong các loại mèo cảnh đang được nhiều bạn trẻ yêu thích đó là mèo Anh lông dài (British Longhair). Mèo Anh lông dài được đánh giá là loài mèo cảnh đẹp, có ngoại hình vô cùng xinh xắn, dễ thương với bộ lông dài mềm mại."
    ,"Mới xuất hiện vào khoảng giữa thế kỷ 20 và có nguồn gốc từ Scotland, mèo Scottish Fold đã khuấy đảo cộng đồng yêu mèo nhờ vào đôi tai cụp và ngoại hình dễ thương của mình"
    ,"Sphynx là loài mèo cảnh đẹp, bởi giống mèo này không có lông, đúng hơn là lông của chúng cực ngắn, tơ và bám sát vào da nhìn thật kỹ mới thấy. Tên gọi gắn liền với chúng nhất chính là mèo Ai Cập, tuy nhiên nguồn gốc của mèo Sphynx lại là Canada. "
   ,"Chim Sáo là một trong những loài chim cảnh dễ nuôi nhất của Việt Nam vì đây là một loài chim mang đặc tính thông minh và có thể bắt chước tiếng người. Tuy nhiên các loại chim Sáo ở Việt Nam thường khá hung dữ, bởi vậy để thuần được một con chim Sáo phải là người yêu chim, có đam mê và lòng kiên nhẫn."
    ,"Tuy Chích Chòe không sở hữu một ngoại hình quá ư bắt mắt, nhưng chúng lại có một dáng vẻ nhỏ nhắn siêu dễ thương, và cách nuôi chim này cũng khá là dễ dàng. Trước khi muốn nuôi loài chim này, có một vấn đề mà bạn cần lưu ý là chúng sở hữu một chất giọng khá đặc biệt."
    ,"Loài chim cảnh Việt Nam này khá nhỏ bé, chỉ ngang hoặc bé hơn chim Sơn Ca nhưng bù lại chúng có giọng hót rất hay, trong trẻo. Tiếng hót của chim Họa Mi có thể làm cho người nghe cảm giác dễ chịu, xóa tan căng thẳng."
    ,"Chim Vẹt có thể cho là loài chim gây được sự chú ý nhất tới mọi người, với màu sắc bắt mắt, đáng yêu và đặc biệt là khả năng bắt chước tiếng người. Người chơi Vẹt thường nuôi để giải trí và làm bầu bạn với mình."
    ,"Chim Khuyên là một trong các loài chim cảnh nhỏ được nuôi nhiều nhất ở Việt Nam nhờ màu lông đẹp và tiếng hót hay. Thân hình của chim Khuyên nhỏ nhắn, đầu chim khá to so với phần thân, trán rộng và cao, mắt hơi xếch nhẹ, mắt có vòng khuyên trắng, có bộ lông màu vàng lục."
    ,"Chim yến phụng có dáng khoằm, sắc nhọn, mắt tròn, to có một bộ lông hội tụ rất nhiều màu sắc. Loài chim này thường được nuôi theo cặp, thế nên người nuôi nên cần đảm bảo việc làm chuồng cần đủ lớn để cả hai có thể ở."
    ,"Chim Chào Mào là loại chim cảnh phổ biến nhất trong làng chim cảnh hiện nay tại Việt Nam. Đặc điểm nhận dạng dễ nhất của loài chim cảnh Việt Nam này là phần mào hình tam giác nhô hẳn lên trên đầu Có lẽ chính vì vậy mà cái tên Chào Mào ra đời. Lông chim có màu nâu nhạt, đậm nhất ở phần đầu và mào.Thông thường trong môi trường tự nhiên chim này rất thích ăn các loại hoa quả và côn trùng."
    ,"Vàng Anh hay còn được gọi là Hoàng Anh là một loài chim rất được giới chơi chim cảnh thích thú. Chim Vàng Anh có giọng hót khá giống với tiếng chim giẻ cùi nhưng vô cùng thánh thót. Thông thường để phân biệt biệt giống chim, ta nhìn vào màu sắc lông của chúng."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lpet);


      //  setTitle("PET SHOP");
//        btn_cho = (ImageButton) findViewById(R.id.imgbtnCho);
//        btn_meo = (ImageButton) findViewById(R.id.imgbtnMeo);
//        btn_thucan = (ImageButton) findViewById(R.id.imgbtnThucAn);

      btn_back = (ImageButton) findViewById(R.id.imgbtnback);
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
                overridePendingTransition(R.anim.move_to_left_popular,R.anim.move_to_right_popular);


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
//        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_main = new Intent(LpetActivity.this, MainActivity.class);
                goto_main.putExtra("shortcut", 0);
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),R.anim.move_to_left, R.anim.move_to_right);
                ActivityCompat.startActivity(LpetActivity.this, goto_main, activityOptionsCompat.toBundle());
                finish();

            }
        });


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



}

