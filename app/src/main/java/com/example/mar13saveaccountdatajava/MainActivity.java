package com.example.mar13saveaccountdatajava;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mar13saveaccountdatajava.model.Account;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Account> accountList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseXML();
        String username=accountList.get(0).getUsername();
        String password=accountList.get(0).getPassword();
        Toast.makeText(this, username+" "+password, Toast.LENGTH_SHORT).show();

    }

    private void parseXML() {
        XmlPullParserFactory xmlPullParserFactory;

        try {
            xmlPullParserFactory=XmlPullParserFactory.newInstance();
            XmlPullParser parser=xmlPullParserFactory.newPullParser();
            InputStream inputStream=getAssets().open("Account.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(inputStream,null);

            accountList=new ArrayList<Account>();
            int eventType=parser.getEventType();
            Account currentAccount=null;
            while (eventType!=XmlPullParser.END_DOCUMENT)
            {
                String elementName=null;
                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
                        elementName=parser.getName();

                        if("account".equals(elementName))
                        {
                            currentAccount=new Account();
                        }
                        else if(currentAccount!=null)
                        {
                            if("username".equals(elementName))
                            {
                                currentAccount.setUsername(parser.nextText());
                            }
                            else if("password".equals(elementName))
                            {
                                currentAccount.setPassword(parser.nextText());
                            }
                        };
                        break;

                    case XmlPullParser.END_TAG:
                        elementName=parser.getName();
                        if("account".equals(elementName))
                        {
                            accountList.add(currentAccount);
                        }
                }
                    eventType=parser.next();
            }

        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}