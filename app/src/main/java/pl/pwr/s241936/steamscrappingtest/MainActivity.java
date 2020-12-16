package pl.pwr.s241936.steamscrappingtest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView info;
    private Button button;
    private EditText gameName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView)findViewById(R.id.textView);
        button = (Button)findViewById(R.id.button);
        gameName = (EditText)findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = MainActivity.this.gameName.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Document doc = Jsoup.connect("https://store.steampowered.com/search/?term=" + name).get();
                            String result = doc.select("div[class=search_results_count]").text();

                            if (result.equals("0 results match your search.")){
                                info.setText("can't find game with this title");
                            } else {
                                Elements el = doc.select("#search_resultsRows");
                                Element firstElement = el.select("a").first();
                                String title = firstElement.select("span.title").text();
                                String price = firstElement.select("div[class=col search_price  responsive_secondrow]").text();
                                String discountedPrice = firstElement.select("div[class=col search_price discounted responsive_secondrow]").text();
                                info.setText(title + " " + price + " " + discountedPrice);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}