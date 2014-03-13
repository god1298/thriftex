/*
 * 文件名称: TestServiceHandler.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift.server.spring.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;

/**
 * @author ted created on 2013-8-7
 * @since 1.0
 */
public class TestServiceHandler implements TestService.Iface {
    @Override
    public FeedResult testGetFeedResults(long uid) throws TException {
        long start = System.currentTimeMillis();
        List<Feed> feedData = new ArrayList<Feed>();
        List<InnerFeed> datas = new ArrayList<InnerFeed>();
        for (int i = 0; i < 10; i++) {
            InnerFeed innerFeed = new InnerFeed();
            innerFeed.setAlbumID(i + 103);
            innerFeed.setAlbumName("albumName" + i);
            innerFeed.setCid(i + 34343432);
            innerFeed.setCName("categoryName" + i);
            innerFeed.setContent("contentcontentcontentcontentcontent" + i);
            Date date = new Date();
            innerFeed.setCreatedAt(date.getTime() + 1023);
            innerFeed.setDuration(date.getTime() - 100232);
            innerFeed.setImagePath("1111111111111111111111imagePath" + i);
            innerFeed.setMUrl("murlmurlmrulmurl" + i);
            innerFeed.setNickName("nickName" + i);
            innerFeed.setRecordID(i);
            innerFeed.setTitle("title" + i);
            innerFeed.setToFeedid(i + 1111111111);
            innerFeed.setToImage("toImagtoImagetoImagetoImagee" + i);
            innerFeed.setToNickName("toNickName" + i);
            innerFeed.setToTid(i + 10000);
            innerFeed.setToUid(i + 444444444);
            innerFeed.setTransmitCreatedAt(date.getTime() + 23123);
            innerFeed.setType("fu" + i);
            innerFeed.setUid(i + 93433);
            innerFeed.setUploadSource(i);
            innerFeed.setUrl("http://www.ximalya.com/asdf" + i);
            innerFeed.setUserSource(i * 2);
            innerFeed.setWaveform("http://www.fdfs.com/asfdfdf" + i);
            innerFeed
                .setWtImagePath("setWtImagePathsetWtImagePathsetWtImagePathsetWtImagePath" + i);
            datas.add(innerFeed);
        }
        Feed feed = new Feed();
        feed.setId("fu");
        feed.setTimeLine(13654557255475D);
        feed.setDatas(datas);
        feedData.add(feed);
        FeedResult result = new FeedResult();
        result.setCurrentSize(1000);
        result.setDelNum(0);
        result.setPageSize(100);
        result.setUnreadNum(5);
        result.setFeedData(feedData);
        long stop = System.currentTimeMillis();
        //        System.out.println(stop - start);
        return result;
    }

    @Override
    public String testGetString(long uid) throws TException {
        List<Feed> feedData = new ArrayList<Feed>();
        List<InnerFeed> datas = new ArrayList<InnerFeed>();
        for (int i = 0; i < 10; i++) {
            InnerFeed innerFeed = new InnerFeed();
            innerFeed.setAlbumID(i + 103);
            innerFeed.setAlbumName("albumName" + i);
            innerFeed.setCid(i + 34343432);
            innerFeed.setCName("categoryName" + i);
            innerFeed.setContent("contentcontentcontentcontentcontent" + i);
            Date date = new Date();
            innerFeed.setCreatedAt(date.getTime() + 1023);
            innerFeed.setDuration(date.getTime() - 100232);
            innerFeed.setImagePath("1111111111111111111111imagePath" + i);
            innerFeed.setMUrl("murlmurlmrulmurl" + i);
            innerFeed.setNickName("nickName" + i);
            innerFeed.setRecordID(i);
            innerFeed.setTitle("title" + i);
            innerFeed.setToFeedid(i + 1111111111);
            innerFeed.setToImage("toImagtoImagetoImagetoImagee" + i);
            innerFeed.setToNickName("toNickName" + i);
            innerFeed.setToTid(i + 10000);
            innerFeed.setToUid(i + 444444444);
            innerFeed.setTransmitCreatedAt(date.getTime() + 23123);
            innerFeed.setType("fu" + i);
            innerFeed.setUid(i + 93433);
            innerFeed.setUploadSource(i);
            innerFeed.setUrl("http://www.ximalya.com/asdf" + i);
            innerFeed.setUserSource(i * 2);
            innerFeed.setWaveform("http://www.fdfs.com/asfdfdf" + i);
            innerFeed
                .setWtImagePath("setWtImagePathsetWtImagePathsetWtImagePathsetWtImagePath" + i);
            datas.add(innerFeed);
        }
        Feed feed = new Feed();
        feed.setId("fu");
        feed.setTimeLine(13654557255475D);
        feed.setDatas(datas);
        feedData.add(feed);
        FeedResult result = new FeedResult();
        result.setCurrentSize(1000);
        result.setDelNum(0);
        result.setPageSize(100);
        result.setUnreadNum(5);
        result.setFeedData(feedData);
        String s = null;
        long start = System.currentTimeMillis();
        s = result.toString();
        long stop = System.currentTimeMillis();
        //        System.out.println(stop - start);
        return s;
    }

    @Override
    public List<String> testGetStrings(long uid) throws TException {
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            strings.add("1234567890");
        }
        return strings;
    }

    @Override
    public List<Integer> testGetInt(long uid) throws TException {
        List<Integer> ints = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++) {
            ints.add(i * 10000 + (i - 1) * 123);
        }
        return ints;
    }

    @Override
    public List<Long> testGetLong(long uid) throws TException {
        List<Long> longs = new ArrayList<Long>();
        for (int i = 0; i < 100; i++) {
            longs.add(i * 10000 + (i - 1) * 123L);
        }
        return longs;
    }

    @Override
    public List<Double> testGetDouble(long uid) throws TException {
        List<Double> doubles = new ArrayList<Double>();
        for (int i = 0; i < 100; i++) {
            doubles.add(i * 10000 + (i - 1) * 123D);
        }
        return doubles;
    }
}
