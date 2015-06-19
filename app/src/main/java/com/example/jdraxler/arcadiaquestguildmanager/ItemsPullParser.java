package com.example.jdraxler.arcadiaquestguildmanager;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdraxler on 3/25/2015.
 */
public class ItemsPullParser {

    private static final String LOGTAG = "AQGM";

    private static final String ID      = "id";
    private static final String NAME    = "name";
    private static final String COST    = "cost";
    private static final String TYPE    = "type";
    private static final String GROUP   = "group";
    private static final String DICE    = "dice";
    private static final String EFFECT  = "effect";
    private static final String LEVEL   = "level";
    private static final String NUMBER  = "number";
    private static final String IMAGE   = "image";

    private Item currentItem = null;
    private String currentTag = null;
    List<Item> items = new ArrayList<Item>();

    public List<Item> parseXML(Context context) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            InputStream stream = context.getResources().openRawResource(R.raw.items);
            xpp.setInput(stream, null);

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    handleStartTag(xpp.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    currentTag = null;
                } else if (eventType == XmlPullParser.TEXT) {
                    handleText(xpp.getText());
                }
                eventType = xpp.next();
            }

        } catch (Resources.NotFoundException e) {
            Log.d(LOGTAG, e.getMessage());
        } catch (XmlPullParserException e) {
            Log.d(LOGTAG, e.getMessage());
        } catch (IOException e) {
            Log.d(LOGTAG, e.getMessage());
        }

        return items;
    }

    private void handleText(String text) {
        String xmlText = text;
        if (currentItem != null && currentTag != null) {
            if (currentTag.equals(ID)) {
                Integer id = Integer.parseInt(xmlText);
                currentItem.setId(id);
            }
            else if (currentTag.equals(NAME)) {
                currentItem.setName(xmlText);
            }
            else if (currentTag.equals(COST)) {
                Integer x = Integer.parseInt(xmlText);
                currentItem.setCost(x);
            }
            else if (currentTag.equals(TYPE)) {
                currentItem.setType(xmlText);
            }
            else if (currentTag.equals(GROUP)) {
                currentItem.setGroup(xmlText);
            }
            else if (currentTag.equals(DICE)) {
                Integer x = Integer.parseInt(xmlText);
                currentItem.setDice(x);
            }
            else if (currentTag.equals(EFFECT)) {
                currentItem.setEffect(xmlText);
            }
            else if (currentTag.equals(LEVEL)) {
                currentItem.setLevel(xmlText);
            }
            else if (currentTag.equals(NUMBER)) {
                Integer x = Integer.parseInt(xmlText);
                currentItem.setNumber(x);
            }
            else if (currentTag.equals(IMAGE)) {
                currentItem.setImage(xmlText);
            }
        }
    }

    private void handleStartTag(String name) {
        if (name.equals("item")) {
            currentItem = new Item();
            items.add(currentItem);
        }
        else {
            currentTag = name;
        }
    }
}
