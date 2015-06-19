package com.example.jdraxler.arcadiaquestguildmanager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;



public class HeroesPullParser {

	private static final String LOGTAG = "AQGM";
	
	private static final String ID = "heroID";
	private static final String NAME = "heroName";
	private static final String DEFENSE = "defense";
	private static final String HEALTH = "health";
	private static final String ABILITY = "ability";
	private static final String IMAGE = "image";

	private Hero currentHero  = null;
	private String currentTag = null;
    List<Hero> heroes = new ArrayList<Hero>();

	public List<Hero> parseXML(Context context) {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			InputStream stream = context.getResources().openRawResource(R.raw.heroes);
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

		} catch (NotFoundException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (XmlPullParserException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (IOException e) {
			Log.d(LOGTAG, e.getMessage());
		}

		return heroes;
	}

	private void handleText(String text) {
		String xmlText = text;
		if (currentHero != null && currentTag != null) {
			if (currentTag.equals(ID)) {
				Integer id = Integer.parseInt(xmlText);
                currentHero.setId(id);
			} 
			else if (currentTag.equals(NAME)) {
                currentHero.setName(xmlText);
			}
			else if (currentTag.equals(DEFENSE)) {
                Integer x = Integer.parseInt(xmlText);
                currentHero.setDefense(x);
			}
			else if (currentTag.equals(HEALTH)) {
                Integer x = Integer.parseInt(xmlText);
                currentHero.setHealth(x);
			}
			else if (currentTag.equals(ABILITY)) {
                currentHero.setAbility(xmlText);
			}
            else if (currentTag.equals(IMAGE)) {
                currentHero.setImage(xmlText);
            }
		}
	}

	private void handleStartTag(String name) {
		if (name.equals("hero")) {
			currentHero = new Hero();
			heroes.add(currentHero);
		}
		else {
			currentTag = name;
		}
	}
}
