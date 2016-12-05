package com.example.android.scanmove.appmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.android.scanmove.utilities.UrlsConfig;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 'Chayut' on 20/10/2559.
 * explain JSON formatted below
 * <JSON>
 * "evid" : "{{@link String} event ID}", e.g. -> "E0001"
 * "content" : "{@link String} event content", e.g. -> "Lorem ipsum .... bla bla bla"
 * "cover" : "{@link String} cover image url", e.g. -> "https://www.sample.in.th/somthig.jpg"
 * "begin" : "{@link String} start day of event", e.g. -> "YYYY-MM-DD"
 * "end" : "{@link String} last day of event", e.g. -> "YYYY-MM-DD"
 * "name" : "{@link String} event Name",
 * "url" : "{@link String} link to official website", e.g. -> "https://www.sample.in.th"
 * "tag" :{@link java.util.Arrays} e.g. -> ["tag1", "tag2", "tag3"....]
 * </JSON>
 */

@Parcel
public class Event extends Landmark {

    String evid;
    String content;
    String cover;
    String begin, end;
    String name;
    String url;
    String[] tag;


    public Event(ArrayList<Double> coordinates, String mSpotThaiName, String mSpotEngName, String imageUrl) {
        super(coordinates, mSpotThaiName, mSpotEngName, imageUrl);
    }

    public Event() {

    }

    public String getEvid() {
        return evid;
    }

    public void setEvid(String evid) {
        this.evid = evid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }

    @SuppressLint("DefaultLocale")
    public ArrayList<Event> createFakeEventList(int amount) {

        Log.v("createFakeEventList", "Creating...");

        ArrayList<Event> fakeList = new ArrayList<>();
        for (int i = 1; i < (25 % amount) + 1; i++) {

            ArrayList<Double> fakeCoordinate = new ArrayList<>();
            fakeCoordinate.add((double) 10 + i);
            fakeCoordinate.add((double) -10 + i);

            Event fakeEvent = new Event(fakeCoordinate, "ThaiName" + i, "EngName" + i, "https://www.fake" + i + ".in.th/palace.jpg");
            fakeEvent.setContent("lorem ipsum " + i);
            fakeEvent.setBegin(String.format("20%02d/%02d/%02d", i - 1, i + 1, i));
            fakeEvent.setEnd(String.format("20%02d/%02d/%02d", i - 1, i + 1, i + 3));
            fakeEvent.setEvid("E000" + i);
            fakeEvent.setCover(UrlsConfig.DUMMY_COVERIMAGE_URL);
            fakeEvent.setName("Event " + i);
            fakeEvent.setTag(null); // TODO: someday/some month/2559 'Expect to tag the Event'
            fakeEvent.setUrl("https://www.officialpage.in.th/fakeEvent" + i + ".html");

            fakeList.add(fakeEvent);

        }

        return fakeList;
    }

    /**
     * To output as a sample String easy to read as well as debug
     **/
    @Override
    public String toString() {
        return String.format("evid -> %s \n" +
                "content -> %s \n" +
                "cover -> %s \n" +
                "begin -> %s \n" +
                "end -> %s \n" +
                "name -> %s \n" +
                "url -> %s \n" +
                "tag -> %s ", evid, content, cover, begin, end, name, url, Arrays.toString(tag));
    }

    public static ArrayList<Event> searchInterestEvent(List<String> eventsId, ArrayList<Event> allEventList){

        ArrayList<Event> tmpEventList = new ArrayList<>();

        for (String evId: eventsId) {

            for (Event ev: allEventList) {

                if(ev.getEvid().equals(evId)){

                    tmpEventList.add(ev);

                }

            }

        }

        return tmpEventList;

    }
}
