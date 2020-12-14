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

    private EditText gameName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView info = findViewById(R.id.textView);
        final Button button = findViewById(R.id.button);
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
                            Elements el = doc.select("#search_resultsRows");
                            Element e = el.select("a").first();
                            String e0 = el.select("span.title").first().text();
                            String e1 = e.select("div[class=col search_price  responsive_secondrow]").text();
                            String e2 = e.select("div[class=col search_price discounted responsive_secondrow]").text();
                            System.out.println(e0);
                            System.out.println(e1);
                            System.out.println(e2);
                            info.setText(e0 + e1 + e2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}