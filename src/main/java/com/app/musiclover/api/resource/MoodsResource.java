package com.app.musiclover.api.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MoodsResource.MOODS)
public class MoodsResource {

    static final String MOODS = "/api/v1/moods";
    static final String MOOD_ID = "/api/v1/moods";

}
