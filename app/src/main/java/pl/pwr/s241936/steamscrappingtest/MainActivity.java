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
    private TextView wrongName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView)findViewById(R.id.textView);
        button = (Button)findViewById(R.id.button);
        gameName = (EditText)findViewById(R.id.editText);
        wrongName = (TextView)findViewById(R.id.wrongName);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = MainActivity.this.gameName.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final Document doc = Jsoup.connect("https://store.steampowered.com/search/?term=" + name + "&category1=998").get();
                            String result = doc.select("div[class=search_results_count]").text();


                            if (result.equals("0 results match your search.")){
                                info.setText("can't find game with this title");
                            } else {
                                Elements el = doc.select("#search_results");
                                Element firstElement = el.select("a").first();
                                String title = firstElement.select("span.title").text();
                                String price = firstElement.select("div[class=col search_price  responsive_secondrow]").text();
                                String discountedPrice = firstElement.select("div[class=col search_price discounted responsive_secondrow]").text();
                                info.setText(title + " " + price + " " + discountedPrice);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongName.setText("Wrong title ?");
                                        wrongName.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                info.setText(" ");
                                                Elements el2 = doc.select("#search_results");
                                                Elements elements = el2.select("a");
                                                boolean found = false;
                                                for (Element element : elements){
                                                    String title = element.select("span.title").text();
                                                    if (name.toUpperCase().equals(title.toUpperCase())){
                                                        String price = element.select("div[class=col search_price  responsive_secondrow]").text();
                                                        String discountedPrice = element.select("div[class=col search_price discounted responsive_secondrow]").text();
                                                        info.setText(title + " " + price + " " + discountedPrice);
                                                        found = true;
                                                        wrongName.setText("");
                                                    }
                                                }
                                                if (found == false){
                                                    info.setText("can't find game with this title");
                                                    wrongName.setText("");
                                                }
                                            }
                                        });
                                    }
                                });
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