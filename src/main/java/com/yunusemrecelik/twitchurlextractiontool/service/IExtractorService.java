package com.yunusemrecelik.twitchurlextractiontool.service;

import java.util.HashMap;
import java.util.List;

public interface IExtractorService {
    List<HashMap<String, String>> getStreamUrls(String name);
}
