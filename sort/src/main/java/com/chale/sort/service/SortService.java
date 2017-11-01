package com.chale.sort.service;

/**
 * Created by liangchaolei on 2016/10/24.
 */
public interface SortService {
    <T extends Comparable> T[] sort(T[] ts);
}
