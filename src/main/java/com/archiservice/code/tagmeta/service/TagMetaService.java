package com.archiservice.code.tagmeta.service;

import java.util.List;

public interface TagMetaService {
    List<String> extractTagsFromCode(Long tagCode);
}
