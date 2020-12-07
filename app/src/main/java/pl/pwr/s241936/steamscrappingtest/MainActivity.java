package pl.pwr.s241936.steamscrappingtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView info = findViewById(R.id.textView);
        final Button button = findViewById(R.id.button);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://store.steampowered.com/search/?term=Wolcen").get();
                    Elements el = doc.select("#search_resultsRows");
                    Element e = el.select("a").first();
                    //System.out.println(e);
                    String e0 = el.select("span.title").first().text();
                    String e1 = e.select("div[class=col search_price  responsive_secondrow]").text();
                    String e2 = e.select("div[class=col search_price discounted responsive_secondrow]").text();
                    System.out.println(e0);
                    System.out.println(e1);
                    System.out.println(e2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("TUrururu !!!!");

                            }
                        });
                    }
                });
            }
        }).start();
    }
}