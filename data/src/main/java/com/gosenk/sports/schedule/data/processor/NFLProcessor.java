package com.gosenk.sports.schedule.data.processor;

import org.springframework.stereotype.Service;

@Service
public class NFLProcessor extends MySportsFeedsProcessor implements Processor {

    public NFLProcessor(){
        super("NFL", "nfl", "2018-2019-regular");
    }

    @Override
    public void process() throws Exception {
        super.process();
    }
}
